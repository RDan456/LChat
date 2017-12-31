package com.arhat.chattest.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.activity.AddActivity;
import com.arhat.chattest.activity.ChatGroupActivity;
import com.arhat.chattest.activity.MessageActivity;
import com.arhat.chattest.activity.NewFriendActivity;
import com.arhat.chattest.adapter.ContactsAdapter;
import com.arhat.chattest.constants.ActionUtil;
import com.arhat.chattest.model.Friend;
import com.arhat.chattest.model.Group;
import com.arhat.chattest.presenter.ContactsFragmentContact;
import com.arhat.chattest.util.L;
import com.arhat.chattest.util.ScreenUtils;
import com.arhat.chattest.util.T;
import com.jingchen.pulltorefresh.PullToRefreshLayout;

import java.util.List;

/**
 * Created by lihanguang on 2016/9/7.
 */
public class ContactsFragment extends Fragment implements ContactsFragmentContact.View, PullToRefreshLayout.OnPullListener {
    private ExpandableListView mExpandableListView;
    private PullToRefreshLayout prl;
    private RelativeLayout rl_loading;
    private ContactsAdapter mContactsAdapter;
    private TextView tv_right;
    private RelativeLayout rl_friend;
    private RelativeLayout rl_group;
    private RelativeLayout rl_subscription;
    private boolean isExpand;
    private List<Group> mGroups;
    private PresenterBroadcastReceiver mReceiver;
    private ContactsFragmentContact.Presenter mPresenter;

    public ContactsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts, null);
        mExpandableListView = (ExpandableListView) v.findViewById(R.id.elv);
        prl = (PullToRefreshLayout) v.findViewById(R.id.prl);
        rl_loading = (RelativeLayout) v.findViewById(R.id.rl_loading);

        prl.setOnPullListener(this);
        mExpandableListView.setOnChildClickListener(onChildClickListener);
        mExpandableListView.setOnItemLongClickListener(mOnItemLongClickListener);
        initHead();
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_right = (TextView) getActivity().findViewById(R.id.tv_right);
        if (tv_right != null) {
            tv_right.setVisibility(View.VISIBLE);
            tv_right.setOnClickListener(mRightListener);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        resgiterBrodcast();
        ScreenUtils.initScreen(getActivity());
        mPresenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unresgiterBrodcast();
    }

    /**
     * 初始化headerview
     */
    private void initHead() {
        if (mExpandableListView != null) {
            View headView = View.inflate(getContext(), R.layout.header_contacts, null);
            rl_subscription = (RelativeLayout) headView.findViewById(R.id.rl_subscription);
            rl_friend = (RelativeLayout) headView.findViewById(R.id.rl_friend);
            rl_group = (RelativeLayout) headView.findViewById(R.id.rl_group);
            rl_friend.setOnClickListener(mFriendListener);
            rl_group.setOnClickListener(mGroupListener);
            rl_subscription.setOnClickListener(mSubscriptionListener);
            mExpandableListView.addHeaderView(headView, null, false);
        }
    }

    /**
     * 联系人点击事件
     */
    private ExpandableListView.OnChildClickListener onChildClickListener = new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            mPresenter.openContactInfo(groupPosition, childPosition);
            return true;
        }
    };

    /**
     * 长按监听
     */
    private ExpandableListView.OnItemLongClickListener mOnItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            int type = ExpandableListView.getPackedPositionType(id);
            if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                L.d("carl", "position=" + position);
                mPresenter.openGroupSetting();
            }
            return true;
        }
    };

    /**
     * 添加按钮监听
     */
    private View.OnClickListener mRightListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            L.d("carl", "Right");
            mPresenter.openAddNew();
        }
    };

    /**
     * 新朋友点击事件
     */
    private View.OnClickListener mFriendListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.openNewFriend();
        }
    };

    /**
     * 群聊点击事件
     */
    private View.OnClickListener mGroupListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.openChatGroup();
        }
    };

    /**
     * 订阅号点击事件
     */
    private View.OnClickListener mSubscriptionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.openSubscription();
        }
    };

    @Override
    public void showContacts(List<Group> groups) {
        mGroups = groups;
        mContactsAdapter = new ContactsAdapter(getContext(), mGroups, mPresenter);
        mExpandableListView.setAdapter(mContactsAdapter);
    }

    @Override
    public void showContactInfo(Friend friend) {
        Intent intent = new Intent(getActivity(), MessageActivity.class);
        intent.putExtra("name", friend.getName().toString());
        intent.putExtra("username", friend.getUsername().toString());
        startActivity(intent);
    }

    @Override
    public void showNewFriend() {
        Intent intent = new Intent(getActivity(), NewFriendActivity.class);
        startActivity(intent);
    }

    @Override
    public void showChatGroup() {
        Intent intent = new Intent(getActivity(), ChatGroupActivity.class);
        startActivity(intent);
    }

    @Override
    public void showSubscription() {

    }

    @Override
    public void showAddNew() {
        Intent intent = new Intent(getActivity(), AddActivity.class);
        startActivity(intent);
    }

    @Override
    public void showError() {
        T.showShort(getContext(), "出错了!");
    }

    @Override
    public void showNoData() {
        T.showShort(getContext(), "没有联系人数据");
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            rl_loading.setVisibility(View.VISIBLE);
        } else {
            rl_loading.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showGroupSettingPop(int position) {
        //不实现？
    }

    @Override
    public void showGroupSetting() {

    }

    @Override
    public void changeContactState(String tag, String textState) {
        TextView tv_msg = (TextView) mExpandableListView.findViewWithTag(tag);
        if (tv_msg != null) {
            tv_msg.setText(textState);
        } else {
            L.d("tv_msg == null");
        }
    }

    @Override
    public void changeContactGroupNumber(String tag, String number) {
        TextView tv_num = (TextView) mExpandableListView.findViewWithTag(tag);
        if (tv_num != null) {
            tv_num.setText(number);
        } else {
            L.d("tv_num == null");
        }
    }

    @Override
    public void setPresenter(ContactsFragmentContact.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    private void resgiterBrodcast() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getContext());
        mReceiver = new PresenterBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ActionUtil.PRESENTER_ACTION);
        manager.registerReceiver(mReceiver, filter);
    }

    private void unresgiterBrodcast() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getContext());
        manager.unregisterReceiver(mReceiver);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        mPresenter.refreshContacts(new ContactsFragmentContact.Presenter.OnRefreshListener() {
            @Override
            public void onSuccess() {
                if (prl != null) {
                    prl.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }

            @Override
            public void onFail() {
                if (prl != null) {
                    prl.refreshFinish(PullToRefreshLayout.FAIL);
                }
            }
        });
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

    }

    class PresenterBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mPresenter.onDataChanged(context, intent);
        }
    }
}
