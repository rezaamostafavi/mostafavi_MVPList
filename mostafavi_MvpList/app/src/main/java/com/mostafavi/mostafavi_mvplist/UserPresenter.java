package com.mostafavi.mostafavi_mvplist;

import android.content.Context;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 1/2/17.
 */
public class UserPresenter extends MvpBasePresenter<UsersView> {

    public void loadUsers(Context context, int page, final boolean pullToRefresh) {

        if (getView() != null)
            getView().showLoading(pullToRefresh);

        String url = "https://randomuser.me/api/?results=10&seed=78954123&page=" + page;
        new CallRequst().callRequest(context, url, new CallRequst.ResponseReceiver() {
            @Override
            public void getResponse(Object sender, String result) {

                if (result != null && !result.equals("")) {
                    try {
                        JSONObject object = new JSONObject(result);
                        JSONArray results = object.optJSONArray(Keys.results.name());
                        if (results != null) {
                            List<UserInfo> users = new ArrayList<UserInfo>();
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject jUser = results.optJSONObject(i);
                                UserInfo user = UserInfo.getFromJson(jUser);
                                users.add(user);
                            }
                            if (getView() != null) {
                                getView().setData(users);

                            }
                        }
                    } catch (JSONException e) {
                        if (getView() != null)
                            getView().showError(e, pullToRefresh);
                    }
                } else if (getView() != null)
                    getView().showError(new Exception("Error In Network"), pullToRefresh);
            }
        });
    }
}
