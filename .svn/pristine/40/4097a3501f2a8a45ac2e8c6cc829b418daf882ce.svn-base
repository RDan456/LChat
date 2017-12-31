package com.arhat.chattest.presenter;

import android.content.Context;
import android.content.Intent;

import com.arhat.chattest.model.Friend;
import com.arhat.chattest.model.Group;

import java.util.List;

import rx.Observable;

/**
 * Created by lihanguang on 2016/9/23.
 */
public class ContactsFragmentContact {
    public interface Presenter extends BasePresenter {
        /**
         * 加载联系人信息
         */
        void loadContacts();

        /**
         * 打开新朋友页面
         */
        void openNewFriend();

        /**
         * 打开群聊页面
         */
        void openChatGroup();

        /**
         * 打开订阅号页面
         */
        void openSubscription();

        /**
         * 打开添加好友页面
         */
        void openAddNew();

        /**
         * 打开联系人信息页面
         *
         * @param groupPosition
         * @param childPosition
         */
        void openContactInfo(int groupPosition, int childPosition);

        /**
         * 打开分组长按时的popwindow
         *
         * @param groupPosition
         */
        void openGroupPop(int groupPosition);

        /**
         * 打开分组设置
         */
        void openGroupSetting();

        /**
         * 设置数据源是否已经改变
         *
         * @param isDataChange
         */
        void setIsDataChange(boolean isDataChange);

        /**
         * 好友状态发生改变
         *
         * @param context
         * @param intent
         */
        void onDataChanged(Context context, Intent intent);

        /**
         * 刷新联系人
         *
         * @param onRefreshListener
         */
        void refreshContacts(OnRefreshListener onRefreshListener);

        interface OnRefreshListener {
            /**
             * 刷新成功时
             */
            void onSuccess();

            /**
             * 刷新失败时
             */
            void onFail();
        }
    }

    public interface View extends BaseView<Presenter> {
        /**
         * 显示好友列表
         *
         * @param groups
         */
        void showContacts(List<Group> groups);

        /**
         * 显示好友信息
         *
         * @param friend
         */
        void showContactInfo(Friend friend);

        /**
         * 显示新朋友页面
         */
        void showNewFriend();

        /**
         * 进入群聊页面
         */
        void showChatGroup();

        /**
         * 进入订阅号页面
         */
        void showSubscription();

        /**
         * 进入添加好友页面
         */
        void showAddNew();

        /**
         * 显示分组设置popwindow
         */
        void showGroupSettingPop(int position);

        /**
         * 显示分组设置
         */
        void showGroupSetting();

        /**
         * 改变联系人状态
         * @param tag
         */
        void changeContactState(String tag, String textState);

        /**
         * 改变分组数量
         * @param tag
         * @param number
         */
        void changeContactGroupNumber(String tag, String number);

    }

    public interface DataSource {
        /**
         * 获得联系人页面
         *
         * @return
         */
        Observable<List<Group>> getGroup();

    }
}
