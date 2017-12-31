package com.arhat.chattest.presenter;

import android.content.Intent;
import android.text.TextUtils;

import com.arhat.chattest.model.GroupName;
import com.arhat.chattest.xmpp.FriendsManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lihanguang on 2016/9/29.
 */
public class GroupManagerPresenter implements GroupManagerContact.Presenter {
    private GroupManagerContact.View mView;
    private GroupManagerContact.DataSource mDataSource;
    private GroupName mGroupName;

    public GroupManagerPresenter(GroupManagerContact.View view, GroupManagerContact.DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
        mView.setPresenter(this);
    }

    @Override
    public void back() {
        mView.back();
    }

    @Override
    public void next(String remark, String username) {
        FriendsManager manager = new FriendsManager();
        boolean isSuccess = false;
        if (mGroupName == null && !TextUtils.isEmpty(remark)) {
            isSuccess = manager.addFriend(username, remark);
        } else if (mGroupName == null && TextUtils.isEmpty(remark)) {
            isSuccess = manager.addFriend(username);
        } else if (mGroupName != null && !TextUtils.isEmpty(remark)) {
            isSuccess = manager.addFriend(username, remark, mGroupName.getName());
        }
        if(isSuccess){
            mView.showAddSuccess();
            mView.back();
        } else {
            mView.showAddFail();
        }
        mView.next();
    }

    @Override
    public void openGroupSelect() {
        mView.showGrooupSelect();
    }

    @Override
    public void result(int resultCode, int requestCode, Intent data) {
        if (resultCode == GroupManagerContact.RESULTCODE &&
                requestCode == GroupManagerContact.REQUESTCODE &&
                data != null) {
            GroupName grouname = data.getParcelableExtra("grouname");
            if (grouname == null) {
            } else {
                mGroupName = grouname;
                mView.setGroupName(mGroupName.getName());
            }
        }
    }

    @Override
    public void start() {
        Observable
                .create(new Observable.OnSubscribe<GroupName>() {
                    @Override
                    public void call(Subscriber<? super GroupName> subscriber) {
                        try {
                            mGroupName = mDataSource.getDefaultGroup();
                            subscriber.onNext(mGroupName);
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<GroupName>() {
                            @Override
                            public void call(GroupName groupName) {
                                if (mGroupName != null) {
                                    mView.setGroupName(mGroupName.getName());
                                } else {
                                    mView.showNoData();
                                }
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mView.showError();
                            }
                        });
    }
}
