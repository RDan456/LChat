package com.arhat.chattest.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.constants.Constants;
import com.arhat.chattest.constants.MyApplication;
import com.arhat.chattest.util.Network;
import com.arhat.chattest.util.T;
import com.arhat.chattest.xmpp.XmppConnection;

/**
 * Created by Arhat on 2016/8/29.
 *
 *
 */
public class LoginActivity extends Activity {

    private Button loginButton, registerButton, cannotLoginButton;
    private EditText loginNameEditText;
    private EditText loginPasswordEditText;
    private ProgressBar loginProgressBar;
    private Handler myHandler;

    public boolean isLogin() {

        SharedPreferences sharedPreferences= getSharedPreferences("login", Activity.MODE_PRIVATE);
        String account = sharedPreferences.getString("account","");
        String password = sharedPreferences.getString("password","");
        if(!("".equals(account)) && ! ("".equals(password))) {
            Constants.USER_NAME=account;
            Constants.PWD = password;
            T.showShort(LoginActivity.this,account+"欢迎回来");
            return true;
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if(isLogin()) {
            if(Network.isNetAvailable(MyApplication.getInstance())) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            try{
                                XmppConnection.getIntence().openConnection();
                                System.out.println("用户的账号和密码是"+Constants.USER_NAME+Constants.PWD);
                                XmppConnection.getIntence().login(Constants.USER_NAME,Constants.PWD);
                                Intent intent = new Intent();
                                intent.setClass(LoginActivity.this,MainActivity.class);
                                LoginActivity.this.startActivity(intent);
                                LoginActivity.this.finish();
                            }catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("登录或者连接失败");
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } else {
                T.showShort(LoginActivity.this,"网络连接错误");
            }
        }else {
            setContentView(R.layout.activity_login);
            loginButton = (Button)findViewById(R.id.login);
            loginNameEditText = (EditText) findViewById(R.id.loginName);
            loginPasswordEditText = (EditText) findViewById(R.id.loginPassword);
            cannotLoginButton = (Button) findViewById(R.id.cannotLogin);
            registerButton = (Button)findViewById(R.id.register);
            loginProgressBar = (ProgressBar)findViewById(R.id.loginProgressBar);

            loginButton.setOnClickListener(new LoginButtonOnClickListener());
            registerButton.setOnClickListener(new RegisterButtonListener());

            myHandler = new Handler(){

                @Override
                public void handleMessage(Message message) {

                    switch (message.what) {
                        case 1:
                            loginButton.setText("正在登录");
                            loginProgressBar.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            loginButton.setText("登录");
                            loginProgressBar.setVisibility(View.GONE);
                            T.showShort(LoginActivity.this,"账号或密码错误");
                            break;
                        case 3:
                            loginButton.setText("登录");
                            loginProgressBar.setVisibility(View.GONE);
                            T.showShort(LoginActivity.this,"服务器连接失败");
                            break;
                    }
                }
            };
        }
    }

    class LoginButtonOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            final String loginName = loginNameEditText.getText().toString();
            final String loginPassword = loginPasswordEditText.getText().toString();

            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Message msg = new Message();
                        msg.what=1;
                        myHandler.sendMessage(msg);
                        if(XmppConnection.getIntence().openConnection()) {

                             if(XmppConnection.getIntence().login(loginName,loginPassword)) {

                                 //将登录者的信息保存到SharedPreferences之中
                                 SharedPreferences mySharedPreferences= getSharedPreferences("login", LoginActivity.MODE_PRIVATE);
                                 SharedPreferences.Editor editor = mySharedPreferences.edit();
                                 editor.putString("account", loginName);
                                 editor.putString("password", loginPassword);
                                 editor.commit();

                                 //跳转到获取好友列表的页面
                                 Intent intent = new Intent();
                                 intent.setClass(LoginActivity.this, MainActivity.class);
                                 LoginActivity.this.startActivity(intent);
                                 LoginActivity.this.finish();
                             }else {
                                 Message message = new Message();
                                 message.what=2;
                                 myHandler.sendMessage(message);
                                 System.out.println("登录失败");
                             }
                        }else {
                            Message message = new Message();
                            message.what=3;
                            myHandler.sendMessage(message);
                            System.out.println("建立连接失败");
                        }
                    }
                }).start();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class RegisterButtonListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            Intent intent = new Intent();
            intent.setClass(LoginActivity.this,RegisterActivity.class);
            LoginActivity.this.startActivity(intent);
        }
    }
}
