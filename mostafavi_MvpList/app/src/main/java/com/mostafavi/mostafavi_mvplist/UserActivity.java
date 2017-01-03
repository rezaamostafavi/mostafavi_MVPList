package com.mostafavi.mostafavi_mvplist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class UserActivity extends AppCompatActivity {

    private UserInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.profile);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(Keys.user.name())) {
            String sUser = bundle.getString(Keys.user.name());
            try {
                JSONObject jUser = new JSONObject(sUser);
                user = UserInfo.getFromJson(jUser);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (user == null)
            return;

        ImageView ivAvatar = (ImageView) findViewById(R.id.ivAvatar);
        ImageView ivCover = (ImageView) findViewById(R.id.ivCover);
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvEmail = (TextView) findViewById(R.id.tvEmail);
        TextView tvDescription = (TextView) findViewById(R.id.tvDescription);

        Glide.with(this).load(user.getLargeAvatar()).centerCrop().into(ivCover);
        Glide.with(this).load(user.getMediumAvatar()).into(ivAvatar);
        tvName.setText(user.getTitleName() + " " + user.getFirstName() + " " + user.getLastName());
        tvEmail.setText(user.getEmail());

        try {
            JSONObject jLocation = new JSONObject(user.getLocation());
            String text = jLocation.optString(Keys.state.name()) + " " + jLocation.optString(Keys.city.name())
                    + " " + jLocation.optString(Keys.street.name());
            tvDescription.setText(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
