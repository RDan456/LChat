package com.arhat.chattest.presenter;

import com.arhat.chattest.model.ChatGroup;

import java.util.List;

import rx.Observable;

/**
 * Created by lihanguang on 2016/10/21.
 */
public class ChatGroupContact {
    public interface Presenter extends BasePresenter {

        /**
         * 加载聊天群
         */
        void loadChatGroup();

        /**
         * 打开群聊页面
         *
         * @param position
         */
        void openChatGroup(int position);
    }

    public interface View extends BaseView<Presenter> {

        /**
         * 显示chatGroup
         *
         * @param chatGroups
         */
        void showChatGroups(List<ChatGroup> chatGroups);

        /**
         * 显示群聊页面
         *
         * @param chatGroup
         */
        void showChatGroup(ChatGroup chatGroup);

        /**
         * 返回
         */
        void back();
    }

    public interface DataSource {

        /**
         * 获取群
         *
         * @return
         */
        Observable<List<ChatGroup>> getChatGroups();
    }
}
