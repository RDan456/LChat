package com.arhat.chattest.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.data.FriendIndoDataSource;
import com.arhat.chattest.model.Friend;
import com.arhat.chattest.presenter.DialogueFragmentContact;
import com.arhat.chattest.presenter.FriendInfoContact;
import com.arhat.chattest.presenter.FriendInfoPresenter;
import com.arhat.chattest.util.ImageDownloader;
import com.arhat.chattest.util.T;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendInfoActivity extends AppCompatActivity implements FriendInfoContact.View {
    private FriendInfoContact.Presenter mPresenter;
    private CircleImageView ci_header;
    private Button btn_send;
    private TextView tv_name;
    private ListView mListView;
    private RelativeLayout rl_loading;
    private ImageDownloader mImageDownloader;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_info);
        Intent intent = getIntent();
        //用户的id
        username = intent.getStringExtra("username");
        mImageDownloader = new ImageDownloader(getApplicationContext());
        new FriendInfoPresenter(this, new FriendIndoDataSource(), username);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    private void initView() {
        ci_header = (CircleImageView) findViewById(R.id.ci_header);
        btn_send = (Button) findViewById(R.id.btn_send);
        tv_name = (TextView) findViewById(R.id.tv_name);
        mListView = (ListView) findViewById(R.id.lv);
        rl_loading = (RelativeLayout) findViewById(R.id.rl_loading);
        ci_header.setOnClickListener(mIconListener);
        btn_send.setOnClickListener(mSendListener);
    }

    private View.OnClickListener mSendListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.openAddFriend();
        }
    };

    private View.OnClickListener mIconListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPresenter.openHeadIcon();
        }
    };

    @Override
    public void showFriendInfo(Friend friend) {
        tv_name.setText(friend.getUsername());
        ci_header.setTag(friend.getUsername());
        mImageDownloader.downloadImage(friend.getUsername(), new ImageDownloader.OnImageDownloadListener() {
            @Override
            public void onImageDownload(String url, Bitmap bitmap) {
                if (url != null && ci_header != null
                        && url.equals(ci_header.getTag()) && bitmap != null) {
                    ci_header.setImageBitmap(bitmap);
                }
            }
        });
    }

    @Override
    public void showAddFriend() {
        Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    @Override
    public void showBigIcon() {

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
    public void showError() {
        T.showShort(getApplicationContext(), "出错误啦！");
    }

    @Override
    public void showNoData() {
        T.showShort(getApplicationContext(), "没有数据！");
    }


    @Override
    public void setPresenter(FriendInfoContact.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }
}
