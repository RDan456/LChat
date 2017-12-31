package com.arhat.chattest.presenter;

import com.arhat.chattest.model.ChatGroup;
import com.arhat.chattest.model.Group;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lihanguang on 2016/9/20.
 */
public class SearchGroupPresenter implements SearchGroupFragmentContact.Presenter {
    private SearchGroupFragmentContact.DataSource mDataSource;
    private SearchGroupFragmentContact.View mView;
    private List<ChatGroup> mChatGroups;

    public SearchGroupPresenter(SearchGroupFragmentContact.DataSource mDataSource,
                                SearchGroupFragmentContact.View mView) {
        this.mDataSource = mDataSource;
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public void loadRecommendGroup() {
        mView.setLoadingIndicator(true);
        Observable
                .create(new Observable.OnSubscribe<List<ChatGroup>>() {
                    @Override
                    public void call(Subscriber<? super List<ChatGroup>> subscriber) {
                        List<ChatGroup> chatGroups = null;
                        try {
                            chatGroups = mDataSource.getGroups();
                        } catch (Exception e) {
                            subscriber.onError(e);
                            return;
                        }
                        subscriber.onNext(chatGroups);
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ChatGroup>>() {
                    @Override
                    public void call(List<ChatGroup> groups) {
                        mChatGroups = groups;
                        if (mChatGroups == null || mChatGroups.size() == 0) {
                            //没有数据时
                            mView.showNoData();
                        } else {
                            //有数据时
                            mView.showRecommendGroups(mChatGroups);
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
    public void openGroupInfo(int position) {
        if (position < 0 || position > mChatGroups.size()) {
            mView.showError();
        } else {
            ChatGroup chatGroup = mChatGroups.get(position);
            mView.showGroupInfo(chatGroup);
        }
    }

    @Override
    public void start() {
        loadRecommendGroup();
    }
}
