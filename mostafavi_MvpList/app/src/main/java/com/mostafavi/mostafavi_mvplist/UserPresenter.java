package com.mostafavi.mostafavi_mvplist;

import android.content.Context;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

/**
 * Created by admin on 1/2/17.
 */
public class UserPresenter extends MvpBasePresenter<UsersView> {

    public void loadUsers(Context context,final boolean pullToRefresh){

        getView().showLoading(pullToRefresh);


    }
}
