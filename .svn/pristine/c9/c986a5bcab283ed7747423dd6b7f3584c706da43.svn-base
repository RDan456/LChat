package com.arhat.chattest.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.model.ChatGroup;
import com.arhat.chattest.util.ImageDownloader;

import java.util.List;

/**
 * Created by lihanguang on 2016/10/21.
 */
public class ChatGroupAdapter extends CommontAdapter<ChatGroup> {
    private ImageDownloader mImageDownloader;
    public ChatGroupAdapter(Context context, List<ChatGroup> list, int layouId) {
        super(context, list, layouId);
        mImageDownloader = new ImageDownloader(context);
    }

    @Override
    public void convert(ViewHolder holder, ChatGroup item, int position) {
        holder.setText(R.id.tv_name,item.getName());
        final ImageView ci_head = holder.getView(R.id.ci_head);
        ci_head.setTag(item.getIconUrl());
        mImageDownloader.downloadImage(item.getIconUrl(), new ImageDownloader.OnImageDownloadListener() {
            @Override
            public void onImageDownload(String url, Bitmap bitmap) {
                if(url != null && ci_head != null
                        && url.equals(ci_head.getTag()) && bitmap != null){
                    ci_head.setImageBitmap(bitmap);
                }
            }
        });
    }
}
