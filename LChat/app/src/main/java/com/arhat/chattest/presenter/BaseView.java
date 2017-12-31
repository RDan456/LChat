package com.arhat.chattest.presenter;

/**
 * Created by lihanguang on 2016/5/30.
 */
public interface BaseView<T> {
    void setPresenter(T mPresenter);

    /**
     * 显示错误信息
     */
    void showError();

    /**
     * 显示没有数据
     */
    void showNoData();

    /**
     * 设置加载标志的显示
     *
     * @param active
     */
    void setLoadingIndicator(boolean active);
}
