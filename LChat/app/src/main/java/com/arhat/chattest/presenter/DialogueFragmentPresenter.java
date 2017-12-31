package com.arhat.chattest.presenter;

import android.content.Context;
import android.content.Intent;

import com.arhat.chattest.constants.Constants;
import com.arhat.chattest.constants.MyApplication;
import com.arhat.chattest.model.Dialogue;
import com.arhat.chattest.sqllit.NewMessageDBHelper;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lihanguang on 2016/9/23.
 */
public class DialogueFragmentPresenter implements DialogueFragmentContact.Presenter {
    private DialogueFragmentContact.DataSource mDataSource;
    private DialogueFragmentContact.View mView;
    private List<Dialogue> mDialogues;

    public DialogueFragmentPresenter(DialogueFragmentContact.DataSource dataSource,
                                     DialogueFragmentContact.View view) {
        mDataSource = dataSource;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadingDialogue() {
        if (mView == null) {
            return;
        }
        mView.setLoadingIndicator(true);
        //加载数据
        mDataSource.getDialogue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Dialogue>>() {
                    @Override
                    public void call(List<Dialogue> dialogues) {
                        mDialogues = dialogues;
                        if (mDialogues == null || mDialogues.size() == 0) {
                            //没有数据时
                            mView.showNoData();
                        } else {
                            //有数据时
                            mView.showDialogue(mDialogues);
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
    public void openDialogue(int positipn) {
        if (mView == null) {
            return;
        }
        if (mDialogues == null || positipn < 0 || positipn > mDialogues.size()) {
            mView.showError();
            return;
        }
        Dialogue dialogue = mDialogues.get(positipn);
        //把新消息数目设置为0
        NewMessageDBHelper.getInstance(MyApplication.getInstance()).chanageMessageCount(dialogue.getName());
        mView.showDialogueInfo(dialogue);
    }

    @Override
    public void handleNewData(Dialogue dialogue) {
        if (mView == null) {
            return;
        }
        if (mDialogues.contains(dialogue)) {
            mDialogues.remove(dialogue);
            mDialogues.add(0, dialogue);
            mView.showDialogue(mDialogues);
        } else {
            mDialogues.add(0, dialogue);
            mView.showDialogue(mDialogues);
        }
        mView.showNewData();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context == null || intent == null) {
            return;
        }
        Dialogue dialogue = intent.getParcelableExtra(Constants.RECEIVER_NEWDIALOGUE);
        if (dialogue != null) {
            if (mDialogues == null) {
                return;
            }
            Dialogue currentDialogue = null;
            for (int i = 0; i < mDialogues.size(); i++) {
                currentDialogue = mDialogues.get(i);
                if (currentDialogue == null) {
                    continue;
                }
                if (currentDialogue.getName().equals(dialogue.getName())) {
                    currentDialogue.setLastMessage(dialogue.getLastMessage());
                    currentDialogue.setUnLookNumber(dialogue.getUnLookNumber());
                    mView.notifyDataSourceChanged();
                    mView.showNewData();
                    return;
                }
            }
            mDialogues.add(0, dialogue);
            mView.notifyDataSourceChanged();
            mView.showNewData();
        }
    }

    @Override
    public void refresh() {
        //加载数据
        mDataSource.getDialogue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Dialogue>>() {
                    @Override
                    public void call(List<Dialogue> dialogues) {
                        mDialogues = dialogues;
                        if (mDialogues == null || mDialogues.size() == 0) {
                            //没有数据时
                            mView.showNoData();
                        } else {
                            //有数据时
                            mView.showDialogue(mDialogues);
                        }
                        mView.refreshSuccess();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showError();
                        mView.refreshFail();
                    }
                });
    }

    @Override
    public void start() {
        loadingDialogue();
    }
}
