package com.mostafavi.mostafavi_mvplist;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends MvpLceActivity<SwipeRefreshLayout, List<UserInfo>, UsersView, UserPresenter>
        implements UsersView, SwipeRefreshLayout.OnRefreshListener {

    List<UserInfo> users = new ArrayList<>();
    private RecyclerView recyclerView;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int page = 1;
    private Context mContext;
    private View loadiView;
    private int lastVisibleItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.users);

        contentView.setOnRefreshListener(this);

        loadiView = findViewById(R.id.loadingView);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(getAdapterUsers());

        recyclerView.addOnScrollListener(getScrollListener(layoutManager));

        loadData(false);

    }

    @NonNull
    private RecyclerView.OnScrollListener getScrollListener(final LinearLayoutManager layoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy < 0)
                    return;

                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    presenter.loadUsers(mContext, page, true);
                    page++;
                    loading = true;
                }

//                visibleItemCount = layoutManager.getChildCount();
//                totalItemCount = layoutManager.getItemCount();
//                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
//
//                if (loading) {
//                    if (totalItemCount > previousTotal) {
//                        loading = false;
//                        previousTotal = totalItemCount;
//                    }
//                }
//                if (!loading && (totalItemCount - visibleItemCount)
//                        <= (firstVisibleItem + visibleThreshold)) {
//                    Log.i("Yaeye!", "end called");
//                    loadData(false);
//                }
            }
        };
    }

    @NonNull
    private RecyclerView.Adapter<Holder> getAdapterUsers() {
        return new RecyclerView.Adapter<Holder>() {
            @Override
            public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = getLayoutInflater().inflate(R.layout.lst_item_user, parent, false);
                return new Holder(view);
            }

            @Override
            public void onBindViewHolder(Holder holder, int position) {
                final UserInfo user = users.get(position);

                Glide.with(mContext).load(user.getThumbnailAvatar()).into(holder.ivAvatar);
                holder.tvName.setText(user.getTitleName() + " " + user.getFirstName() + " " + user.getLastName());
                holder.tvEmail.setText(user.getEmail());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, UserActivity.class);
                        intent.putExtra(Keys.user.name(), user.getData());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return users.size();
            }
        };
    }

    @NonNull
    @Override
    public UserPresenter createPresenter() {
        return new UserPresenter();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        Toast.makeText(MainActivity.this, "Error In Network", Toast.LENGTH_SHORT).show();
        return "Error In Network";
    }

    @Override
    public void setData(List<UserInfo> data) {
        users.addAll(data);
        recyclerView.getAdapter().notifyDataSetChanged();
        loadiView.setVisibility(View.GONE);
        showContent();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loading = false;
            }
        }, 1000);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        if (pullToRefresh) {
            page = 1;
            users = new ArrayList<>();
        } else
            loadiView.setVisibility(View.VISIBLE);
        presenter.loadUsers(this, page, pullToRefresh);
        page++;
    }

    @Override
    public void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);
        contentView.setRefreshing(pullToRefresh);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    class Holder extends RecyclerView.ViewHolder {

        CircleImageView ivAvatar;
        TextView tvName, tvEmail;

        public Holder(View itemView) {
            super(itemView);
            ivAvatar = (CircleImageView) itemView.findViewById(R.id.ivAvatar);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
        }
    }
}
