package com.arhat.chattest.view;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arhat.arhat.chattest.R;

/**
 * Created by lihanguang on 2016/9/20.
 */
public class SearchPopWindow extends PopupWindow {
    private EditText et_input;
    private TextView tv_submit;
    private TextView tv_people;
    private TextView tv_group;
    private TextView tv_subscription;
    private RelativeLayout rl_people;
    private RelativeLayout rl_group;
    private RelativeLayout rl_subscription;
    private Context mContext;
    private boolean isCancel;

    public SearchPopWindow(Context context) {
        mContext = context;
        View view = View.inflate(mContext, R.layout.pop_search_people, null);
        et_input = (EditText) view.findViewById(R.id.et_input);
        tv_submit = (TextView) view.findViewById(R.id.tv_submit);
        tv_people = (TextView) view.findViewById(R.id.tv_people);
        tv_group = (TextView) view.findViewById(R.id.tv_group);
        tv_subscription = (TextView) view.findViewById(R.id.tv_subscription);
        rl_people = (RelativeLayout) view.findViewById(R.id.rl_people);
        rl_group = (RelativeLayout) view.findViewById(R.id.rl_group);
        rl_subscription = (RelativeLayout) view.findViewById(R.id.rl_subscription);
        rl_people.setOnClickListener(mPeopleListener);
        rl_group.setOnClickListener(mGroupListener);
        rl_subscription.setOnClickListener(mSubmitListener);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new PaintDrawable(0x00000000));
        setFocusable(true);

        //输入监听
        et_input.addTextChangedListener(mTextWatcher);
        isCancel = true;
        tv_submit.setOnClickListener(mSubmitListener);
    }

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
                isCancel = true;
            } else {
                tv_submit.setText("搜索");
                isCancel = false;
                tv_people.setText(s);
                tv_group.setText(s);
                tv_subscription.setText(s);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private View.OnClickListener mSubmitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isCancel) {
                dismiss();
            } else {
                //搜索
            }
        }
    };

    private View.OnClickListener mPeopleListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private View.OnClickListener mGroupListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private View.OnClickListener mSubscriptionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

}
