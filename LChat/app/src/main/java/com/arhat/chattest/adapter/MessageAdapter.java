package com.arhat.chattest.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.model.ChatMessage;
import com.arhat.chattest.util.ImageDownloader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by lihanguang on 2016/9/6.
 */
public class MessageAdapter extends BaseAdapter {
    private List<ChatMessage> mMessages;
    private Context mContext;
    private ImageDownloader imageDownloader;

    public MessageAdapter(List<ChatMessage> messages, Context context) {
        mMessages = messages;
        mContext = context;
        imageDownloader = new ImageDownloader(context);
    }

    @Override
    public int getCount() {
        return mMessages.size();
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
        ChatMessage message = mMessages.get(position);
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_message, null);
            holder = new ViewHolder();
            holder.rl_left = (RelativeLayout) convertView.findViewById(R.id.rl_left);
            holder.rl_right = (RelativeLayout) convertView.findViewById(R.id.rl_right);
            holder.ci_left = (CircleImageView) convertView.findViewById(R.id.ci_left);
            holder.ci_right = (CircleImageView) convertView.findViewById(R.id.ci_right);
            holder.tv_name_left = (TextView) convertView.findViewById(R.id.tv_name_left);
            holder.tv_name_right = (TextView) convertView.findViewById(R.id.tv_name_right);
            holder.tv_text_left = (TextView) convertView.findViewById(R.id.tv_text_left);
            holder.tv_text_right = (TextView) convertView.findViewById(R.id.tv_text_right);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (message.isLeft()) {
            holder.rl_left.setVisibility(View.VISIBLE);
            holder.rl_right.setVisibility(View.GONE);
            holder.tv_name_left.setText(message.getName());
            holder.tv_text_left.setText(message.getText());
            holder.ci_left.setTag(message.getName());
            imageDownloader.downloadImage(message.getName(), new ImageDownloader.OnImageDownloadListener() {
                @Override
                public void onImageDownload(String url, Bitmap bitmap) {
                    if(holder.ci_left.getTag().equals(url) && bitmap != null) {
                        holder.ci_left.setImageBitmap(bitmap);
                    }else {
                        holder.ci_left.setImageResource(R.mipmap.icon_stub);
                    }
                }
            });
        } else {
            holder.rl_right.setVisibility(View.VISIBLE);
            holder.rl_left.setVisibility(View.GONE);
            holder.tv_name_right.setText(message.getName());
            holder.tv_text_right.setText(message.getText());
            holder.ci_right.setTag(message.getName());
            imageDownloader.downloadImage(message.getName(), new ImageDownloader.OnImageDownloadListener() {
                @Override
                public void onImageDownload(String url, Bitmap bitmap) {
                    if(holder.ci_right.getTag().equals(url) && bitmap != null) {
                        holder.ci_right.setImageBitmap(bitmap);
                    }else {
                        holder.ci_right.setImageResource(R.mipmap.icon_stub);
                    }
                }
            });
        }
        return convertView;
    }

    class ViewHolder {
        RelativeLayout rl_left;
        RelativeLayout rl_right;
        CircleImageView ci_left;
        CircleImageView ci_right;
        TextView tv_name_left;
        TextView tv_name_right;
        TextView tv_text_left;
        TextView tv_text_right;
    }
}
