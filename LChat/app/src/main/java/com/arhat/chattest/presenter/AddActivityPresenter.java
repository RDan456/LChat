package com.arhat.chattest.presenter;

/**
 * Created by lihanguang on 2016/9/19.
 */
public class AddActivityPresenter implements AddActivityContact.Presenter {
    private AddActivityContact.View mView;

    public AddActivityPresenter(AddActivityContact.View mView) {
        this.mView = mView;
    }

    @Override
    public void start() {

    }

    @Override
    public void back() {
        mView.backToLast();
    }
}
