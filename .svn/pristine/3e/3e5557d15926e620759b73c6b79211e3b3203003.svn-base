package com.arhat.chattest.presenter;

import com.arhat.chattest.model.ChatGroup;

import java.util.List;

/**
 * Created by lihanguang on 2016/9/21.
 */
public class SearchGroupFragmentContact {
    public interface View extends BaseView<Presenter> {
        /**
         * 显示查找页面
         */
        void showFindPeoplePop();

        /**
         * 显示指定群的资料
         *
         * @param chatGroup
         */
        void showGroupInfo(ChatGroup chatGroup);

        /**
         * 显示推荐群列表
         *
         * @param chatGroups
         */
        void showRecommendGroups(List<ChatGroup> chatGroups);

    }

    public interface Presenter extends BasePresenter {
        /**
         * 加载推荐群列表
         */
        void loadRecommendGroup();

        /**
         * 打开查找页面
         */
        void openFindPeoplePop();

        /**
         * 打开群资料页面
         *
         * @param position
         */
        void openGroupInfo(int position);

    }

    public interface DataSource {
        /**
         * 获得推荐群
         *
         * @return
         */
        List<ChatGroup> getGroups();

        /**
         * 根据关键字查找群
         *
         * @param text
         * @return
         */
        List<ChatGroup> getGroups(String text);
    }
}
