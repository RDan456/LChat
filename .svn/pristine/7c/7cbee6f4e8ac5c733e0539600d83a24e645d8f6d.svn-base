package com.arhat.chattest.presenter;

import com.arhat.chattest.model.NewFriend;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lihanguang on 2016/10/3.
 */
public class NewFriendPresenter implements NewFriendActivityContact.Presenter {
    private NewFriendActivityContact.DataSource mDataSource;
    private NewFriendActivityContact.View mView;
    private List<NewFriend> mNewFriends;

    public NewFriendPresenter(NewFriendActivityContact.DataSource dataSource, NewFriendActivityContact.View view) {
        mDataSource = dataSource;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadNewFriend() {
        mView.setLoadingIndicator(true);
        Observable
                .create(new Observable.OnSubscribe<List<NewFriend>>() {
                    @Override
                    public void call(Subscriber<? super List<NewFriend>> subscriber) {
                        try {
                            mNewFriends = mDataSource.getNewFriends();
                            subscriber.onNext(mNewFriends);
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<NewFriend>>() {
                    @Override
                    public void call(List<NewFriend> newFriends) {
                        if (mNewFriends == null || mNewFriends.size() == 0) {
                            mView.showNoData();
                            mView.setLoadingIndicator(false);
                        } else {
                            mView.showNewFriends(mNewFriends);
                            mView.setLoadingIndicator(false);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError();
                        mView.setLoadingIndicator(false);
                    }
                });
    }

    @Override
    public void back() {
        mView.back();
    }

    @Override
    public void openAddFriend() {
        mView.showAddNewFriend();
    }

    @Override
    public void openFriendInfo(int position) {
        if (mNewFriends == null) {
            mView.showError();
            return;
        }
        if (position < 0 || position > mNewFriends.size()) {
            mView.showError();
            return;
        }
        NewFriend newFriend = mNewFriends.get(position);
        mView.showNewFriendInfo(newFriend);
    }

    @Override
    public void start() {
        loadNewFriend();
    }
}
