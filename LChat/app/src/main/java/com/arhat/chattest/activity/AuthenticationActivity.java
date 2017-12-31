package com.arhat.chattest.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arhat.arhat.chattest.R;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class AuthenticationActivity extends AppCompatActivity {
    private ImageView iv_back;
    private TextView tv_next;
    private EditText et_input;
    private CircleImageView ci_icon;
    private TextView tv_name;
    private TextView tv_user;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        username = getIntent().getStringExtra("username");
        initView();
    }

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        et_input = (EditText) findViewById(R.id.et_input);
        ci_icon = (CircleImageView) findViewById(R.id.ci_icon);
        tv_next = (TextView) findViewById(R.id.tv_next);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_user = (TextView) findViewById(R.id.tv_user);
        iv_back.setOnClickListener(mBackListener);
        tv_next.setOnClickListener(mNextListener);
    }

    private View.OnClickListener mBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener mNextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(AuthenticationActivity.this,GroupManagerActivity.class);
            String content = et_input.getText().toString();
            intent.putExtra("content",content);
            intent.putExtra("username", username);
            startActivity(intent);
        }
    };
}
