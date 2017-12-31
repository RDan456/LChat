package com.arhat.chattest.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
    private View mConverView;
    private SparseArray<View> mView;

    private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        mView = new SparseArray<View>();
        mConverView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConverView.setTag(this);
    }

    public static ViewHolder getViewHolder(Context context, ViewGroup parent,
                                           View convertView, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            return (ViewHolder) convertView.getTag();
        }
    }

    public <T extends View> T getView(int viewId) {
        View view = mView.get(viewId);
        if (view == null) {
            view = mConverView.findViewById(viewId);
            mView.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConverView;
    }

    public void setText(int viewId, String text) {
        TextView tv = getView(viewId);
        if (tv != null && text != null) {
            tv.setText(text);
        }
    }

    public void setBitmap(int viewId, Bitmap bm) {
        ImageView iv = getView(viewId);
        if (iv != null && bm != null) {
            iv.setImageBitmap(bm);
        }
    }

    public void setImageResoure(int viewId, int resoureId) {
        ImageView iv = getView(viewId);
        if (iv != null) {
            iv.setImageResource(resoureId);
        }
    }
}
