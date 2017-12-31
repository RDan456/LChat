package com.arhat.chattest.presenter;

import com.arhat.chattest.model.GroupName;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lihanguang on 2016/9/29.
 */
public class SelectGroupPresenter implements SelectGroupContact.Presenter {
    private SelectGroupContact.View mView;
    private SelectGroupContact.DataSource mDataSource;
    private List<GroupName> mGroupNames;
    public SelectGroupPresenter(SelectGroupContact.View view, SelectGroupContact.DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
        mView.setPresenter(this);
    }

    @Override
    public void loadGroupName() {
        mView.setLoadingIndicator(true);
        Observable
                .create(new Observable.OnSubscribe<List<GroupName>>() {
                    @Override
                    public void call(Subscriber<? super List<GroupName>> subscriber) {
                        try{
                            mGroupNames = mDataSource.getGroupName();
                        }catch (Exception e){
                            subscriber.onError(e);
                        }
                        subscriber.onNext(mGroupNames);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<GroupName>>() {
                    @Override
                    public void call(List<GroupName> groupNames) {
                        if(mGroupNames == null || mGroupNames.size() == 0){
                            mView.showNoData();
                            mView.setLoadingIndicator(false);
                        } else {
                            mView.showGroupName(mGroupNames);
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
    public void selectGroupName(int position) {
        if(mGroupNames == null || mGroupNames.size() == 0){
            return;
        }
        GroupName groupName = mGroupNames.get(position);
        mView.back(groupName);
    }

    @Override
    public void back() {
        mView.back(null);
    }

    @Override
    public void start() {
        loadGroupName();
    }
}
