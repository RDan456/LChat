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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.activity.MessageActivity;
import com.arhat.chattest.adapter.DialogueAdapter;
import com.arhat.chattest.constants.ActionUtil;
import com.arhat.chattest.constants.MyApplication;
import com.arhat.chattest.model.Dialogue;
import com.arhat.chattest.presenter.DialogueFragmentContact;
import com.arhat.chattest.util.T;
import com.jingchen.pulltorefresh.PullToRefreshLayout;

import java.util.List;

public class DialogueFragment extends Fragment implements DialogueFragmentContact.View, PullToRefreshLayout.OnPullListener {

    private TextView tv_right;
    private ListView mListView;
    private RelativeLayout rl_loading;
    private DialogueAdapter mDialogueAdapter;
    private List<Dialogue> mDialogues;
    private DialogueFragmentContact.Presenter mPresenter;
    private NewMessageBroadcastReceiver mReceiver;
    private PullToRefreshLayout prl;

    public DialogueFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_message, null);
        mListView = (ListView) v.findViewById(R.id.lv);
        prl = (PullToRefreshLayout) v.findViewById(R.id.prl);
        rl_loading = (RelativeLayout) v.findViewById(R.id.rl_loading);

        prl.setOnPullListener(this);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_right = (TextView) getActivity().findViewById(R.id.tv_right);
        if (tv_right != null) {
            tv_right.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start();
        resgiterBroadcast();
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mPresenter.openDialogue(position);
        }
    };

    @Override
    public void showDialogue(List<Dialogue> dialogues) {
        mDialogues = dialogues;
        mDialogueAdapter = new DialogueAdapter(mDialogues, getContext());
        mListView.setAdapter(mDialogueAdapter);
        mListView.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    public void showDialogueInfo(Dialogue dialogue) {
        Intent intent = new Intent(getActivity(), MessageActivity.class);
        intent.putExtra("username", dialogue.getName());
        intent.putExtra("name", dialogue.getName());
        startActivity(intent);
    }

    @Override
    public void showError() {
        T.showShort(getContext(), "出错误");
    }

    @Override
    public void showNoData() {
        T.showShort(getContext(), "没有数据");
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
    public void showNewData() {
        //空实现
    }

    @Override
    public void notifyDataSourceChanged() {
        if (mDialogueAdapter != null) {
            mDialogueAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void refreshSuccess() {
        if(prl != null){
            prl.refreshFinish(PullToRefreshLayout.SUCCEED);
        }
    }

    @Override
    public void refreshFail() {
        if(prl != null){
            prl.refreshFinish(PullToRefreshLayout.FAIL);
        }
    }

    @Override
    public void setPresenter(DialogueFragmentContact.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBordcast();
    }

    private void resgiterBroadcast() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(MyApplication.getInstance());
        mReceiver = new NewMessageBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ActionUtil.NEWMESSAGE_ACTION);
        manager.registerReceiver(mReceiver, filter);
    }

    private void unregisterBordcast() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(MyApplication.getInstance());
        manager.unregisterReceiver(mReceiver);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        mPresenter.refresh();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        //空实现
    }

    class NewMessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mPresenter.onReceive(context, intent);
        }
    }
}
