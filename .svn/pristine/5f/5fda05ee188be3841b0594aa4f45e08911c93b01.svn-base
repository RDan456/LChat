package com.arhat.chattest.presenter;

import com.arhat.chattest.model.Friend;
import com.arhat.chattest.model.SearchItem;

import java.util.List;

/**
 * Created by lihanguang on 2016/9/27.
 */
public class SearchActivityContact {
    public interface Presenter extends BasePresenter {
        int PEOPLE = 1;
        int GROUP = 2;
        int SUBSCRIPTION = 3;

        /**
         * 加载查找信息
         */
        void loadingSearchItem();

        /**
         * 根据关键字和查找模式查找
         *
         * @param context
         * @param mode
         */
        void search(String context, int mode);

        /**
         * 打开用户信息页面
         *
         * @param position
         */
        void openPeopleInfo(int position);

        /**
         * 清除输入数据
         */
        void clearInput();
    }

    public interface View extends BaseView<Presenter> {
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
         * 显示没有数据
         */
        void showNoData();

        /**
         * 加载查找数据
         *
         * @param items
         */
        void showSearchItem(List<SearchItem> items);

        /**
         * 显示指定好友的资料
         *
         * @param item
         */
        void showPeopleInfo(SearchItem item);

        /**
         * 清除输入框数据
         */
        void clearInputContent();
    }

    public interface DataSource {
        /**
         * 根据内容查找人
         *
         * @param content
         * @return
         */
        List<SearchItem> getPeopleByContent(String content);

        /**
         * 根据内容查找群
         *
         * @param content
         * @return
         */
        List<SearchItem> getGroupByContent(String content);

        /**
         * 根据内容查找订阅号
         *
         * @param content
         * @return
         */
        List<SearchItem> getSubscriptionByContent(String content);


    }
}
