package com.arhat.chattest.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.adapter.MyFragmentPagerAdapter;
import com.arhat.chattest.data.SearchGroupDataSource;
import com.arhat.chattest.data.SearchPeopleDataSource;
import com.arhat.chattest.fragment.SearchGroupFragment;
import com.arhat.chattest.fragment.SearchPeopleFragment;
import com.arhat.chattest.fragment.SearchSubscriptionFragment;
import com.arhat.chattest.model.Tab;
import com.arhat.chattest.presenter.AddActivityContact;
import com.arhat.chattest.presenter.AddActivityPresenter;
import com.arhat.chattest.presenter.SearchGroupFragmentContact;
import com.arhat.chattest.presenter.SearchGroupPresenter;
import com.arhat.chattest.presenter.SearchPeopleFragmentContact;
import com.arhat.chattest.presenter.SearchPeoplePresenter;

public class AddActivity extends AppCompatActivity implements AddActivityContact.View {
    private AddActivityContact.Presenter mPresenter;
    private ImageView iv_back;
    private TabLayout tbl;
    private ViewPager mViewPager;
    private Tab[] mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mPresenter = new AddActivityPresenter(this);
        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tbl = (TabLayout) findViewById(R.id.tbl);
        mViewPager = (ViewPager) findViewById(R.id.vp);

        //返回监听
        iv_back.setOnClickListener(mBackListener);

        //生成Tab
        mTabs = new Tab[3];

        mTabs[0] = new Tab();
        mTabs[0].mFragment = new SearchPeopleFragment();
        mTabs[0].tabName = "找人";
        //创建Presenter
        new SearchPeoplePresenter(new SearchPeopleDataSource(),
                (SearchPeopleFragmentContact.View) mTabs[0].mFragment);

        mTabs[1] = new Tab();
        mTabs[1].mFragment = new SearchGroupFragment();
        mTabs[1].tabName = "找群";
        new SearchGroupPresenter(new SearchGroupDataSource(),
                (SearchGroupFragmentContact.View) mTabs[1].mFragment);

        mTabs[2] = new Tab();
        mTabs[2].mFragment = new SearchSubscriptionFragment();
        mTabs[2].tabName = "找订阅号";

        //添加Tab
        for (int i = 0; i < mTabs.length; i++) {
            tbl.addTab(tbl.newTab().setText(mTabs[i].tabName));
        }
        tbl.setTabGravity(TabLayout.GRAVITY_FILL);
        //设置顶部标签指示条的颜色和选中页面时标签字体颜色
        tbl.setSelectedTabIndicatorColor(getResources().getColor(R.color.blue));
        tbl.setTabTextColors(Color.GRAY, getResources().getColor(R.color.blue));

        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mTabs));
        //在设置viewpager页面滑动监听时，创建TabLayout的滑动监听
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tbl));
        //设置TabLayout的选择监听
        tbl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), false);
                Log.d("carl","position1 = " + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    public void backToLast() {
        finish();
    }

    @Override
    public void setPresenter(AddActivityContact.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void showError() {

    }

    @Override
    public void showNoData() {

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    private View.OnClickListener mBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.back();
        }
    };
}
