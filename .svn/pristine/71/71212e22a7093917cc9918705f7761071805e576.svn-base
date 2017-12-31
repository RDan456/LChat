package com.arhat.chattest.adapter;

import android.content.Context;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.model.ChatGroup;

import java.util.List;

/**
 * Created by lihanguang on 2016/9/21.
 */
public class FindGroupAdapter extends CommontAdapter<ChatGroup> {
    public FindGroupAdapter(Context context, List<ChatGroup> list, int layouId) {
        super(context, list, layouId);
    }

    @Override
    public void convert(ViewHolder holder, ChatGroup item, int position) {
        holder.setText(R.id.tv_name,item.getName());
        holder.setText(R.id.tv_sinal,item.getIntroduction());
    }
}
