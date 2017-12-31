package com.arhat.chattest.presenter;

import android.content.Context;
import android.content.Intent;

import com.arhat.chattest.model.Dialogue;

import java.util.List;

import rx.Observable;

/**
 * Created by lihanguang on 2016/9/23.
 */
public class DialogueFragmentContact {
    public interface Presenter extends BasePresenter {
        /**
         * 加载会话数据
         */
        void loadingDialogue();

        /**
         * 打开会话界面
         *
         * @param positipn
         */
        void openDialogue(int positipn);

        /**
         * 处理新的数据，将他放到第一行
         *
         * @param dialogue
         */
        void handleNewData(Dialogue dialogue);

        /**
         * 接受新消息
         *
         * @param context
         * @param intent
         */
        void onReceive(Context context, Intent intent);

        /**
         * 刷新数据
         */
        void refresh();
    }

    public interface View extends BaseView<Presenter> {

        /**
         * 显示会话信息
         *
         * @param dialogues
         */
        void showDialogue(List<Dialogue> dialogues);

        /**
         * 显示会话详细
         *
         * @param dialogue
         */
        void showDialogueInfo(Dialogue dialogue);

        /**
         * 有心数据是提醒
         */
        void showNewData();

        /**
         * 数据源改变
         */
        void notifyDataSourceChanged();

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
         * 获取会话列表
         *
         * @return
         */
        Observable<List<Dialogue>> getDialogue();

    }

}
