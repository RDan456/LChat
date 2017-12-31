package com.arhat.chattest.adapter;

import android.content.Context;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.model.NewFriend;

import java.util.List;

/**
 * Created by lihanguang on 2016/10/3.
 */
public class NewFriendAdapter extends CommontAdapter<NewFriend> {
    public NewFriendAdapter(Context context, List<NewFriend> list, int layouId) {
        super(context, list, layouId);
    }

    @Override
    public void convert(ViewHolder holder, NewFriend item, int position) {
        holder.setText(R.id.tv_name, item.getName());
        holder.setText(R.id.tv_detail, item.getDetail());
        holder.setText(R.id.tv_from, item.getFrom());
        if (item.getType() == item.ACCEPTED) {
            holder.setText(R.id.tv_type, "已添加");
        } else if (item.getType() == item.NOACCEPTED) {
            holder.setText(R.id.tv_type, "未添加");
        }
    }
}
