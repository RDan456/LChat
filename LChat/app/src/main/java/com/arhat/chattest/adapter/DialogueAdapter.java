package com.arhat.chattest.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.model.Dialogue;
import com.arhat.chattest.util.ImageDownloader;
import com.arhat.chattest.xmpp.FriendsManager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lihanguang on 2016/9/9.
 */
public class DialogueAdapter extends BaseAdapter {

    private List<Dialogue> mDialogues;
    private Context mContext;
    private ImageDownloader mImageDownloader;

    public DialogueAdapter(List<Dialogue> dialogues, Context context) {
        mDialogues = dialogues;
        mContext = context;
        mImageDownloader = new ImageDownloader(context);
    }

    @Override
    public int getCount() {
        return mDialogues.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        FriendsManager manager = new FriendsManager();
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_dialogue, null);
            holder = new ViewHolder();
            holder.ci_icon = (CircleImageView) convertView.findViewById(R.id.ci_icon);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_msg = (TextView) convertView.findViewById(R.id.tv_msg);
            holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Dialogue dialogue = mDialogues.get(position);
        if(dialogue != null){
            //设置聊天图片
            holder.tv_name.setText(manager.getNickName(dialogue.getName()));
            holder.tv_msg.setText(dialogue.getLastMessage());
            holder.ci_icon.setTag(dialogue.getName());
            if(dialogue.getUnLookNumber() != 0 ) {
                holder.tv_number.setVisibility(View.VISIBLE);
            }
            holder.tv_number.setText(String.valueOf(dialogue.getUnLookNumber()));
            mImageDownloader.downloadImage(dialogue.getName(), new ImageDownloader.OnImageDownloadListener() {
                @Override
                public void onImageDownload(String url, Bitmap bitmap) {
                    if(holder.ci_icon.getTag().equals(url) && bitmap != null) {
                        holder.ci_icon.setImageBitmap(bitmap);
                    }else {
                        holder.ci_icon.setImageResource(R.mipmap.icon_stub);
                    }
                }
            });
        }
        return convertView;
    }

    class ViewHolder {
        public CircleImageView ci_icon;
        public TextView tv_name;
        public TextView tv_msg;
        public TextView tv_number;
    }
}
