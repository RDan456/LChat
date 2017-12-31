package com.arhat.chattest.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.model.Friend;

import java.util.List;

/**
 * Created by lihanguang on 2016/9/20.
 */
public class FindPeopleAdapter extends CommontAdapter<Friend> {
    public FindPeopleAdapter(Context context, List<Friend> list, int layouId) {
        super(context, list, layouId);
    }

    @Override
    public void convert(ViewHolder holder, Friend item, int position) {
        holder.setText(R.id.tv_name,item.getUsername());
        holder.setText(R.id.tv_sinal,item.getSinal());
    }
}
