package com.arhat.chattest.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CommontAdapter<T> extends BaseAdapter {
	private Context mContext;
	private List<T> mList;
	private int id;
	private LayoutInflater inflater;

	public CommontAdapter(Context context, List<T> list, int layouId) {
		mContext = context;
		mList = list;
		id = layouId;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public T getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder = ViewHolder.getViewHolder(mContext, parent, convertView, id, position);
		convert(holder, getItem(position), position);
		return holder.getConvertView();
	}

	public abstract void convert(ViewHolder holder, T item, int position);

	public List<T> getList() {
		return mList;
	}
}
