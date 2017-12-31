package com.arhat.chattest.presenter;

import com.arhat.chattest.model.ChatGroup;
import com.arhat.chattest.model.Friend;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lihanguang on 2016/9/20.
 */
public class SearchPeoplePresenter implements SearchPeopleFragmentContact.Presenter {
    private SearchPeopleFragmentContact.DataSource mDataSource;
    private SearchPeopleFragmentContact.View mView;
    private List<Friend> mFriends;

    public SearchPeoplePresenter(SearchPeopleFragmentContact.DataSource mDataSource,
                                 SearchPeopleFragmentContact.View mView) {
        this.mDataSource = mDataSource;
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void loadRecommendFriend() {
        mView.setLoadingIndicator(true);
        Observable
                .create(new Observable.OnSubscribe<List<Friend>>() {
                    @Override
                    public void call(Subscriber<? super List<Friend>> subscriber) {
                        List<Friend> friends = null;
                        try {
                            friends = mDataSource.getFriend();
                        } catch (Exception e) {
                            subscriber.onError(e);
                            return;
                        }
                        subscriber.onNext(friends);
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Friend>>() {
                    @Override
                    public void call(List<Friend> friends) {
                        mFriends = friends;
                        if (mFriends == null || mFriends.size() == 0) {
                            //没有数据时
                            mView.showNoData();
                        } else {
                            //有数据时
                            mView.showRecommendFriends(mFriends);
                        }
                        mView.setLoadingIndicator(false);
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
    public void openFindPeoplePop() {
        mView.showFindPeoplePop();
    }

    @Override
    public void openFriendInfo(int position) {
        if (position < 0 || position > mFriends.size()) {
            mView.showError();
        } else {
            Friend friend = mFriends.get(position);
            mView.showPeopleInfo(friend);
        }
    }

    @Override
    public void start() {
        loadRecommendFriend();
    }
}
