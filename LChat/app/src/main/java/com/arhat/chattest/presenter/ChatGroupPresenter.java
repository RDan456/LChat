package com.arhat.chattest.presenter;

import com.arhat.chattest.model.ChatGroup;
import com.arhat.chattest.util.L;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lihanguang on 2016/10/21.
 */
public class ChatGroupPresenter implements ChatGroupContact.Presenter {
    private ChatGroupContact.View mView;
    private ChatGroupContact.DataSource mDataSource;
    private List<ChatGroup> mChatGroups;
    public ChatGroupPresenter(ChatGroupContact.View view, ChatGroupContact.DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
        mView.setPresenter(this);
    }

    @Override
    public void loadChatGroup() {
        if(mView == null){
            L.d("mView为空!");
            return;
        }
        mView.setLoadingIndicator(true);
        mDataSource.getChatGroups()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ChatGroup>>() {
                    @Override
                    public void onCompleted() {
                        mView.setLoadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.d("loadChatGroup-onError:" + e.toString());
                        mView.showError();
                        mView.setLoadingIndicator(false);
                    }

                    @Override
                    public void onNext(List<ChatGroup> chatGroups) {
                        mChatGroups = chatGroups;
                        if(mChatGroups == null){
                            mChatGroups = new ArrayList<ChatGroup>();
                        }
                        if(mChatGroups.size() == 0){
                            mView.showNoData();
                        }
                        mView.showChatGroups(mChatGroups);
                    }
                });
    }

    @Override
    public void openChatGroup(int position) {
        if(position <0 ||position >= mChatGroups.size()){
            L.d("openChatGroup：数组越界");
            return;
        }
        mView.showChatGroup(mChatGroups.get(position));
    }

    @Override
    public void start() {
        loadChatGroup();
    }
}
