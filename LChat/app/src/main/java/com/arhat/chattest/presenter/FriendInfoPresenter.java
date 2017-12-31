package com.arhat.chattest.presenter;

import com.arhat.chattest.model.Friend;
import com.arhat.chattest.util.L;

/**
 * Created by lihanguang on 2016/9/26.
 */
public class FriendInfoPresenter implements FriendInfoContact.Presenter {
    private Friend mFriend;
    private FriendInfoContact.View mView;
    private FriendInfoContact.DataSource mDataSource;
    private String userName;

    public FriendInfoPresenter(FriendInfoContact.View view, FriendInfoContact.DataSource dataSource, String userName) {
        mView = view;
        mDataSource = dataSource;
        this.userName = userName;
        mView.setPresenter(this);
    }

    @Override
    public void loadFriendInfo() {
        mView.setLoadingIndicator(true);
        try {
            mFriend = mDataSource.getFriendInfo(userName);
            if (mFriend != null) {
                mView.showFriendInfo(mFriend);
            } else {
                mView.showNoData();
            }
            L.d("carl", "asdasdwd");
            mView.setLoadingIndicator(false);
        } catch (Exception e) {
            L.d("carl", "exception:" + e.toString());
            mView.setLoadingIndicator(false);
            mView.showError();
        }
    }

    @Override
    public void openAddFriend() {
        mView.showAddFriend();
    }

    @Override
    public void openHeadIcon() {
        mView.showBigIcon();
    }

    @Override
    public void start() {
        loadFriendInfo();
    }
}
