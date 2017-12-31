package com.arhat.chattest.presenter;

import com.arhat.chattest.model.SearchItem;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lihanguang on 2016/9/27.
 */
public class SearchActivityPresenter implements SearchActivityContact.Presenter {
    private SearchActivityContact.View mView;
    private SearchActivityContact.DataSource mDataSource;
    private List<SearchItem> mItems;
    private int mode;
    private String content;

    public SearchActivityPresenter(SearchActivityContact.View view, SearchActivityContact.DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
        mView.setPresenter(this);
    }

    @Override
    public void loadingSearchItem() {
        if (mView == null) {
            return;
        }
        mView.setLoadingIndicator(true);
        Observable
                .create(new Observable.OnSubscribe<List<SearchItem>>() {
                    @Override
                    public void call(Subscriber<? super List<SearchItem>> subscriber) {
                        switch (mode) {
                            case PEOPLE:
                                mItems = mDataSource.getPeopleByContent(content);
                                break;
                            case GROUP:
                                mItems = mDataSource.getGroupByContent(content);
                                break;
                            case SUBSCRIPTION:
                                mItems = mDataSource.getSubscriptionByContent(content);
                                break;
                        }
                        subscriber.onNext(mItems);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<SearchItem>>() {
                    @Override
                    public void call(List<SearchItem> items) {
                        if (items != null && items.size() != 0) {
                            mView.showSearchItem(items);
                            mView.setLoadingIndicator(false);
                        } else {
                            mView.setLoadingIndicator(false);
                            mView.showNoData();
                            mView.showSearchItem(new ArrayList<SearchItem>());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.setLoadingIndicator(false);
                        mView.showError();
                        mView.showSearchItem(new ArrayList<SearchItem>());
                    }
                });
    }

    @Override
    public void search(String context, int mode) {
        this.mode = mode;
        this.content = context;
        loadingSearchItem();
    }

    @Override
    public void openPeopleInfo(int position) {
        if (mView == null || mItems == null || mItems.size() == 0) {
            return;
        }

        if (mItems.size() < position || position < 0) {
            return;
        }
        SearchItem item = mItems.get(position);
        mView.showPeopleInfo(item);
    }

    @Override
    public void clearInput() {
        if(mView == null){
            return;
        }
        mView.clearInputContent();
    }

    @Override
    public void start() {
        mode = 1;
        content = "";
        loadingSearchItem();
    }
}
