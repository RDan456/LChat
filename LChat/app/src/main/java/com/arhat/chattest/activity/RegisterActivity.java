package com.arhat.chattest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.xmpp.XmppConnection;

import org.jivesoftware.smack.packet.Presence;

public class RegisterActivity extends AppCompatActivity {

    private Button commitInforButton;
    private EditText registerNameEditText;
    private EditText registerPasswordEditText,confirmPasswordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        commitInforButton = (Button)findViewById(R.id.commitInfor);
        registerNameEditText = (EditText) findViewById(R.id.registerName);
        confirmPasswordEditText = (EditText)findViewById(R.id.confirmPassword);
        registerPasswordEditText = (EditText)findViewById(R.id.registerPassword);

        commitInforButton.setOnClickListener(new CommitInforButtonOnClickListener());

    }
    class CommitInforButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            final String name = registerNameEditText.getText().toString();
            final String password = registerPasswordEditText.getText().toString();
            final String confirmPassword = confirmPasswordEditText.getText().toString();
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(confirmPassword.equals(password)) {
                            try {
                                XmppConnection.register(name,password);
                                //把我的状态修改为在线
                                Presence presence = new Presence(Presence.Type.available);
                                System.out.println("注册成功");
                            }catch (Exception e) {
                                e.printStackTrace();
                                System.out.println("注册失败");
                            }
                        }
//                        Intent intent = new Intent();
//                        intent.setClass(RegisterActivity.this,FriendListActivity.class);
//                        RegisterActivity.this.startActivity(intent);
                    }
                }).start();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
