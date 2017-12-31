package com.arhat.chattest.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.constants.Constants;
import com.arhat.chattest.data.ContactDataSource;
import com.arhat.chattest.data.DialogueDataSource;
import com.arhat.chattest.data.UserInfoDataSoure;
import com.arhat.chattest.fragment.ContactsFragment;
import com.arhat.chattest.fragment.DialogueFragment;
import com.arhat.chattest.model.Tab;
import com.arhat.chattest.model.UserInfo;
import com.arhat.chattest.presenter.ContactsFragmentContact;
import com.arhat.chattest.presenter.ContactsFragmentPresenter;
import com.arhat.chattest.presenter.DialogueFragmentContact;
import com.arhat.chattest.presenter.DialogueFragmentPresenter;
import com.arhat.chattest.util.ImageDownloader;
import com.arhat.chattest.util.ScreenUtils;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {
    private CircleImageView ci_icon;
    private TextView tv_name;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private FrameLayout fl;
    private RelativeLayout rl_dialogue;
    private RelativeLayout rl_contacts;
    private TextView tv_dialogue;
    private TextView tv_contacts;
    private View v_dialogue;
    private View v_contacts;

    private Tab[] tabs;
    private int currentItem;
    private ImageDownloader mImageDownloader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScreenUtils.initScreen(this);
        mImageDownloader = new ImageDownloader(getApplicationContext());
        initMenu();
        initContent();
    }


    private void initContent() {
        fl = (FrameLayout) findViewById(R.id.fl);
        rl_dialogue = (RelativeLayout) findViewById(R.id.rl_dialogue);
        rl_contacts = (RelativeLayout) findViewById(R.id.rl_contacts);
        tv_dialogue = (TextView) findViewById(R.id.tv_dialogue);
        tv_contacts = (TextView) findViewById(R.id.tv_contacts);
        v_dialogue = findViewById(R.id.v_dialogue);
        v_contacts = findViewById(R.id.v_contacts);
        currentItem = 0;
        tabs = new Tab[2];

        tabs[0] = new Tab();
        tabs[0].tabName = "消息";
        tabs[0].iconUrl = "";
        tabs[0].mFragment = new DialogueFragment();
        new DialogueFragmentPresenter(new DialogueDataSource(), (DialogueFragmentContact.View) tabs[0].mFragment);

        tabs[1] = new Tab();
        tabs[1].tabName = "联系人";
        tabs[1].iconUrl = "";
        tabs[1].mFragment = new ContactsFragment();
        new ContactsFragmentPresenter(new ContactDataSource(), (ContactsFragmentContact.View) tabs[1].mFragment);


        rl_dialogue.setOnClickListener(mMessageTabListener);
        rl_contacts.setOnClickListener(mContactsTabListener);
        change(0);

    }

    private void initMenu() {

        ci_icon = (CircleImageView) findViewById(R.id.ci_icon);
        tv_name = (TextView) findViewById(R.id.tv_name);
        mNavigationView = (NavigationView) findViewById(R.id.nv);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl);
        ci_icon.setOnClickListener(mNaviListener);
        tv_name.setText("消息");
        mNavigationView.setNavigationItemSelectedListener(mSelectedListener);
        //初始化菜单
        Menu menu = mNavigationView.getMenu();
        UserInfo info = new UserInfoDataSoure().getUserInfo();
        MenuItem sex = menu.findItem(R.id.sex);
        sex.setTitle("性别：" + info.getSex());
        MenuItem age = menu.findItem(R.id.age);
        age.setTitle("年龄：" + info.getAge());
        final MenuItem birthday = menu.findItem(R.id.birthday);
        birthday.setTitle("生日：" + info.getBirthday());
        MenuItem constellation = menu.findItem(R.id.constellation);
        constellation.setTitle("星座：" + info.getConstellation());
        MenuItem address = menu.findItem(R.id.address);
        address.setTitle("地址：" + info.getAddress());
        MenuItem description = menu.findItem(R.id.description);
        description.setTitle("说明：" + info.getDescription());

        //初始化headview
        View v = mNavigationView.getHeaderView(0);
        TextView tv_name = (TextView) v.findViewById(R.id.tv_name);
        TextView tv_sinal = (TextView) v.findViewById(R.id.tv_sinal);
        final CircleImageView ci_head = (CircleImageView) v.findViewById(R.id.ci_icon);
        tv_name.setText(info.getName());
        tv_sinal.setText(info.getSignal());


        ci_icon.setTag(Constants.USER_NAME);
        ci_head.setTag(Constants.USER_NAME);
        mImageDownloader.downloadImage(Constants.USER_NAME, new ImageDownloader.OnImageDownloadListener() {
            @Override
            public void onImageDownload(String url, Bitmap bitmap) {
                if(ci_icon.getTag().equals(url) && bitmap != null) {
                    ci_icon.setImageBitmap(bitmap);
                    ci_head.setImageBitmap(bitmap);
                }else {
                    ci_icon.setImageResource(R.mipmap.icon_stub);
                    ci_head.setImageResource(R.mipmap.icon_stub);
                }
            }
        });
    }

    private NavigationView.OnNavigationItemSelectedListener mSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return false;
        }
    };

    private void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl, fragment);
        transaction.commit();
    }

    private void change(int index) {
        if (index < 0 || index > 1) {
            return;
        }
        if (index == 0) {
            tv_dialogue.setTextColor(getResources().getColor(R.color.blue));
            tv_contacts.setTextColor(getResources().getColor(R.color.graydeep));
            v_dialogue.setVisibility(View.VISIBLE);
            v_contacts.setVisibility(View.INVISIBLE);
            tv_name.setText("消息");
        } else {
            tv_contacts.setTextColor(getResources().getColor(R.color.blue));
            tv_dialogue.setTextColor(getResources().getColor(R.color.graydeep));
            v_contacts.setVisibility(View.VISIBLE);
            v_dialogue.setVisibility(View.INVISIBLE);
            tv_name.setText("联系人");
        }
        Fragment fragment = tabs[index].mFragment;
        changeFragment(fragment);
    }

    private View.OnClickListener mNaviListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mNavigationView.isShown()) {
                mDrawerLayout.closeDrawers();
            } else {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        }
    };

    private View.OnClickListener mMessageTabListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (currentItem == 0) {

            } else {
                currentItem = 0;
                change(currentItem);

            }
        }
    };
    private View.OnClickListener mContactsTabListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (currentItem == 1) {

            } else {
                currentItem = 1;
                change(currentItem);

            }
        }
    };
}
