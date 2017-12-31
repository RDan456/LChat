package com.arhat.chattest.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.adapter.SearchItemAdapter;
import com.arhat.chattest.data.SearchDataSource;
import com.arhat.chattest.model.SearchItem;
import com.arhat.chattest.presenter.SearchActivityContact;
import com.arhat.chattest.presenter.SearchActivityPresenter;
import com.arhat.chattest.util.L;
import com.arhat.chattest.util.T;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchActivityContact.View {
    private EditText et_input;
    private TextView tv_submit;
    private ImageView iv_back;
    private RadioGroup mRadioGroup;
    private ListView mListView;
    private RelativeLayout rl_loading;
    private ImageView iv_delete;
    private SearchItemAdapter mAdapter;
    private SearchActivityContact.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        new SearchActivityPresenter(this, new SearchDataSource());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        et_input = (EditText) findViewById(R.id.et_input);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_search);
        iv_delete = (ImageView) findViewById(R.id.iv_delete);
        mListView = (ListView) findViewById(R.id.lv);
        rl_loading = (RelativeLayout) findViewById(R.id.rl_loading);
        iv_back.setOnClickListener(mBackListener);
        tv_submit.setOnClickListener(mSubmitListener);
        et_input.addTextChangedListener(mTextWatcher);
        iv_delete.setOnClickListener(mDeleteListener);
        mRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
        mListView.setOnItemClickListener(mOnItemClickListener);
    }


    private View.OnClickListener mBackListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener mSubmitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = tv_submit.getText().toString();
            if ("取消".equals(text)) {
                finish();
            } else {
                int checkedId = mRadioGroup.getCheckedRadioButtonId();
                search(checkedId);
            }
        }
    };

    private View.OnClickListener mDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.clearInput();
            L.d("点击");
        }
    };
    /**
     * 输入监听器
     */
    private TextWatcher mTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                tv_submit.setText("取消");
                iv_delete.setVisibility(View.GONE);
            } else {
                tv_submit.setText("搜索");
                iv_delete.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            search(checkedId);
        }
    };

    private void search(int checkedId) {
        String content = et_input.getText().toString();
        int mode = 0;
        switch (checkedId) {
            case R.id.rb_people:
                mode = mPresenter.PEOPLE;
                break;
            case R.id.rb_group:
                mode = mPresenter.GROUP;
                break;
            case R.id.rb_subscription:
                mode = mPresenter.SUBSCRIPTION;
                break;
        }
        mPresenter.search(content, mode);
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mPresenter.openPeopleInfo(position);
        }
    };

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            rl_loading.setVisibility(View.VISIBLE);
        } else {
            rl_loading.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showError() {
        T.showShort(getApplicationContext(), "出错误啦！");
    }

    @Override
    public void showNoData() {
        T.showShort(getApplicationContext(), "没有数据！");
    }

    @Override
    public void showSearchItem(List<SearchItem> items) {
        if (mAdapter != null) {
            mAdapter = null;
        }
        mAdapter = new SearchItemAdapter(getApplicationContext(), items, R.layout.item_searchpeople);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void showPeopleInfo(SearchItem item) {
        Intent intent = new Intent(this, FriendInfoActivity.class);
        intent.putExtra("username", item.getName());
        startActivity(intent);
    }

    @Override
    public void clearInputContent() {
        if (et_input == null) {
            return;
        }
        et_input.setText("");
    }

    @Override
    public void setPresenter(SearchActivityContact.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }
}
