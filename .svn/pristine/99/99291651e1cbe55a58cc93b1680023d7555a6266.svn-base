package com.arhat.chattest.presenter;

import com.arhat.chattest.model.Friend;

/**
 * Created by lihanguang on 2016/9/26.
 */
public class FriendInfoContact {
    public interface Presenter extends BasePresenter {
        /**
         * 加载好友信息
         */
        void loadFriendInfo();

        /**
         * 打开添加好友页面
         */
        void openAddFriend();

        /**
         * 打开放大头像
         */
        void openHeadIcon();
    }

    public interface View extends BaseView<Presenter> {

        /**
         * 显示好友信息
         *
         * @param friend
         */
        void showFriendInfo(Friend friend);

        /**
         * 显示添加好友
         */
        void showAddFriend();

        /**
         * 显示大头像
         */
        void showBigIcon();

        /**
         * 设置加载标志的显示
         *
         * @param active
         */
        void setLoadingIndicator(boolean active);

        /**
         * 显示错误信息
         */
        void showError();

        /**
         * 显示没有信息
         */
        void showNoData();

    }

    public interface DataSource {
        /**
         * 获取好友信息
         *
         * @return
         */
        Friend getFriendInfo(String username);
    }

}
