package com.arhat.chattest.presenter;

import com.arhat.chattest.model.Friend;

import java.util.List;

/**
 * Created by lihanguang on 2016/9/20.
 */
public class SearchPeopleFragmentContact {
    public interface View extends BaseView<Presenter> {
        /**
         * 显示查找好友页面
         */
        void showFindPeoplePop();

        /**
         * 显示指定好友的资料
         *
         * @param friend
         */
        void showPeopleInfo(Friend friend);

        /**
         * 显示推荐好友列表
         *
         * @param friends
         */
        void showRecommendFriends(List<Friend> friends);

    }

    public interface Presenter extends BasePresenter {
        /**
         * 加载推荐好友列表
         */
        void loadRecommendFriend();

        /**
         * 打开查找好友页面
         */
        void openFindPeoplePop();

        /**
         * 打开好友资料页面
         *
         * @param position
         */
        void openFriendInfo(int position);
    }

    public interface DataSource {
        /**
         * 获得推荐好友
         *
         * @return
         */
        List<Friend> getFriend();

        /**
         * 根据关键字查找好友
         *
         * @param text
         * @return
         */
        List<Friend> getFriend(String text);
    }
}
