package com.mostafavi.mostafavi_mvplist;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends MvpLceActivity<SwipeRefreshLayout, List<UserInfo>, UsersView, UserPresenter>
        implements UsersView, SwipeRefreshLayout.OnRefreshListener {

    List<UserInfo> users = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.users);

        contentView.setOnRefreshListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(getAdapterUsers());

    }

    @NonNull
    private RecyclerView.Adapter getAdapterUsers() {
        return new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = getLayoutInflater().inflate(R.layout.lst_item_user, parent, false);
                return new Holder(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                UserInfo user = users.get(position);
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
        return "Error In Network";
    }

    @Override
    public void setData(List<UserInfo> data) {
        users.addAll(data);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadUsers(pullToRefresh);
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
