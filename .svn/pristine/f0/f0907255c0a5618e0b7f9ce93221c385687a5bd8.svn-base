package com.arhat.chattest.presenter;

import android.content.Context;
import android.content.Intent;

import com.arhat.chattest.model.ChatMessage;

import java.util.List;

import rx.Observable;

/**
 * Created by lihanguang on 2016/10/18.
 */
public class MessageContact {

    public interface Presenter extends BasePresenter {
        /**
         * 加载聊天信息
         */
        void loadMessages();

        /**
         * 发送新消息
         *
         * @param content
         * @param username
         */
        void submitNewMessage(String content, String username);

        /**
         * 接受新消息的广播
         *
         * @param context
         * @param intent
         */
        void onReceive(Context context, Intent intent);

        /**
         * 上拉加载
         */
        void refresh();

    }

    public interface View extends BaseView<Presenter> {

        /**
         * 显示消息列表
         *
         * @param chatMessages
         */
        void showMessages(List<ChatMessage> chatMessages);

        /**
         * 通知数据源改变
         */
        void notifyDataSourceChanged();

        /**
         * 滑动到底部
         */
        void scrollToEnd();

        /**
         * 清空EditText
         */
        void clearEditText();

        /**
         * 滑动到指定位置
         *
         * @param position
         */
        void scrollToPostion(int position);

        /**
         * 刷新成功
         */
        void refreshSuccess();

        /**
         * 刷新失败
         */
        void refreshFail();
    }

    public interface DataSource {

        /**
         * 获取某一页的聊天记录
         *
         * @param username
         * @return
         */
        Observable<List<ChatMessage>> getLastChatMessage(String username);

        Observable<List<ChatMessage>> getChatMessage(String username, int size);
    }
}
