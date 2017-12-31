package com.arhat.chattest.presenter;

import android.content.Context;
import android.content.Intent;

import com.arhat.chattest.model.Friend;
import com.arhat.chattest.model.Group;
import com.arhat.chattest.util.L;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lihanguang on 2016/9/23.
 */
public class ContactsFragmentPresenter implements ContactsFragmentContact.Presenter {
    private ContactsFragmentContact.DataSource mDataSource;
    private ContactsFragmentContact.View mView;
    private List<Group> mGroups;
    private boolean isDataChange;

    public ContactsFragmentPresenter(ContactsFragmentContact.DataSource dataSource, ContactsFragmentContact.View view) {
        mDataSource = dataSource;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadContacts() {
        mView.setLoadingIndicator(true);
        mDataSource.getGroup()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Group>>() {
                    @Override
                    public void call(List<Group> groups) {
                        mGroups = groups;
                        if (mGroups == null || mGroups.size() == 0) {
                            //没有数据时
                            mView.showNoData();
                        } else {
                            //有数据时
                            mView.showContacts(mGroups);
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
    public void openNewFriend() {
        mView.showNewFriend();
    }

    @Override
    public void openChatGroup() {
        mView.showChatGroup();
    }

    @Override
    public void openSubscription() {
        mView.showSubscription();
    }

    @Override
    public void openAddNew() {
        mView.showAddNew();
    }

    @Override
    public void openContactInfo(int groupPosition, int childPosition) {
        Friend friend = mGroups.get(groupPosition).getContactList().get(childPosition);
        mView.showContactInfo(friend);
    }

    @Override
    public void openGroupPop(int groupPosition) {
        mView.showGroupSettingPop(groupPosition);
    }

    @Override
    public void openGroupSetting() {
        mView.showGroupSetting();
    }

    @Override
    public void setIsDataChange(boolean isDataChange) {
        this.isDataChange = isDataChange;
        if (this.isDataChange) {
            loadContacts();
        } else {
            // 不做任何事
        }
        this.isDataChange = false;
    }

    @Override
    public void onDataChanged(Context context, Intent intent) {
        String userName = intent.getStringExtra("username");
        String textState = intent.getStringExtra("textstate");
        L.d("carl", "username = " + userName);
        L.d("carl", "textState = " + textState);
        if (userName == null || "".equals(userName) || textState == null || "".equals(textState)) {
            return;
        }
        if (mGroups == null || mGroups.size() == 0) {
            return;
        }
        for (Group group : mGroups) {
            List<Friend> friends = group.getContactList();
            if (friends == null || friends.size() == 0) {
                continue;
            }
            for (Friend friend : friends) {
                if (userName.equals(friend.getUsername())) {
                    if ((friend.getTextState().equals(textState))) {
                        continue;
                    }
                    friend.setTextState(textState);
//                    mView.showContacts(mGroups);
                    L.d("carl", "getNowNum=" + group.getNowNum());
                    int nowNum = 0;
                    if ("在线".equals(textState)) {
                        nowNum = group.getNowNum() + 1;
                    } else if ("离线".equals(textState)) {
                        nowNum = group.getNowNum() - 1;
                    }
                    group.setNowNum(nowNum);
//                    if (nowNum >= 0 && nowNum <= group.getTotalNum()) {
//                    } else {
//                        group.setNowNum(-1);
//                    }
                    mView.changeContactState(friend.getUsername() + friend.getName(), textState);
                    mView.changeContactGroupNumber(group.getName(), nowNum + "/" + group.getTotalNum());
                }
            }
        }
    }

    @Override
    public void refreshContacts(final OnRefreshListener onRefreshListener) {
        mDataSource.getGroup()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Group>>() {
                    @Override
                    public void call(List<Group> groups) {
                        mGroups = groups;
                        if (mGroups == null || mGroups.size() == 0) {
                            //没有数据时
                            mView.showNoData();
                        } else {
                            //有数据时
                            mView.showContacts(mGroups);
                        }
                        onRefreshListener.onSuccess();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError();
                        onRefreshListener.onFail();
                    }
                });
    }

    @Override
    public void start() {
        loadContacts();
    }

}
