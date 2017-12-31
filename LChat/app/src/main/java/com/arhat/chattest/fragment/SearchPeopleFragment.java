package com.arhat.chattest.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.activity.FriendInfoActivity;
import com.arhat.chattest.activity.SearchActivity;
import com.arhat.chattest.adapter.FindPeopleAdapter;
import com.arhat.chattest.model.Friend;
import com.arhat.chattest.presenter.SearchPeopleFragmentContact;
import com.arhat.chattest.util.L;
import com.arhat.chattest.util.T;
import com.arhat.chattest.view.SearchPopWindow;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchPeopleFragment extends Fragment implements SearchPeopleFragmentContact.View {
    private SearchPeopleFragmentContact.Presenter mPresenter;
    private ListView mListView;
    private ProgressBar mProgressBar;
    private TextView tv_search;
    private FindPeopleAdapter mAdapter;
    private RelativeLayout rl_root;
    private SearchPopWindow mPopWindow;

    public SearchPeopleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_people, container, false);
        rl_root = (RelativeLayout) view.findViewById(R.id.rl_root);
        mListView = (ListView) view.findViewById(R.id.lv);
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_loading);
        mListView.setOnItemClickListener(mOnItemClickListener);
        //初始化headerview
        View headView = inflater.inflate(R.layout.header_search_people, null);
        tv_search = (TextView) headView.findViewById(R.id.tv_search);
        tv_search.setOnClickListener(mSearchListener);
        Log.d("carl", "进入onCreateView");
        mListView.addHeaderView(headView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    public void showFindPeoplePop() {
//        if (mPopWindow != null && mPopWindow.isShowing()) {
//            mPopWindow.dismiss();
//        }
//        mPopWindow = new SearchPopWindow(getContext());
//        mPopWindow.showAtLocation(rl_root, Gravity.NO_GRAVITY, 0, 0);
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void showPeopleInfo(Friend friend) {
        Intent intent = new Intent(getActivity(), FriendInfoActivity.class);
        intent.putExtra("id", friend.getId());
        startActivity(intent);
    }

    @Override
    public void showRecommendFriends(List<Friend> friends) {
        mAdapter = new FindPeopleAdapter(getContext(), friends, R.layout.item_searchpeople);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showNoData() {
        T.showShort(getContext(), "没有数据");
    }


    @Override
    public void setPresenter(SearchPeopleFragmentContact.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void showError() {
        T.showShort(getContext(), "数据出错");
    }

    /**
     * 搜索按钮的监听
     */
    private View.OnClickListener mSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.openFindPeoplePop();
            L.d("carl", "搜索点击");
        }
    };

    /**
     * 推荐好友点击事件
     */
    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            L.d("carl", "position=" + position);
            //加入headview后position会从1开始
            if (position == 0) {
                return;
            }
            mPresenter.openFriendInfo(position - 1);
        }
    };
}
