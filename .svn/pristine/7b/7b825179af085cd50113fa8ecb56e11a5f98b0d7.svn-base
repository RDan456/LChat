package com.arhat.chattest.presenter;

import com.arhat.chattest.model.Friend;
import com.arhat.chattest.model.NewFriend;

import java.util.List;

/**
 * Created by lihanguang on 2016/10/3.
 */
public class NewFriendActivityContact {
    public interface Presenter extends BasePresenter {

        /**
         * 加载新朋友
         */
        void loadNewFriend();

        /**
         * 返回
         */
        void back();

        /**
         * 打开添加新朋友页面
         */
        void openAddFriend();

        /**
         * 打开朋友信息
         *
         * @param position
         */
        void openFriendInfo(int position);

    }

    public interface View extends BaseView<Presenter> {

        /**
         * 返回上一个Activity
         */
        void back();

        /**
         * 显示新朋友
         *
         * @param newFriends
         */
        void showNewFriends(List<NewFriend> newFriends);

        /**
         * 显示添加好友页面
         */
        void showAddNewFriend();

        /**
         * 显示好友信息
         *
         * @param newFriend
         */
        void showNewFriendInfo(NewFriend newFriend);
    }

    public interface DataSource {
        /**
         * 获取新朋友
         *
         * @return
         */
        List<NewFriend> getNewFriends();
    }
}
