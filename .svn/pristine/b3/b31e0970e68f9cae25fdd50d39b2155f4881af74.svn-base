package com.arhat.chattest.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.model.GroupName;

import java.util.List;

/**
 * Created by lihanguang on 2016/9/29.
 */
public class SelectGroupAdapter extends CommontAdapter<GroupName> {

    public SelectGroupAdapter(Context context, List<GroupName> list, int layouId) {
        super(context, list, layouId);
    }

    @Override
    public void convert(ViewHolder holder, GroupName item, int position) {
        holder.setText(R.id.tv_group_name,item.getName());
    }
}
