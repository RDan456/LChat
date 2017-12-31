package com.arhat.chattest.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.model.SearchItem;
import com.arhat.chattest.util.ImageDownloader;

import java.util.List;

/**
 * Created by lihanguang on 2016/9/27.
 */
public class SearchItemAdapter extends CommontAdapter<SearchItem> {
    private ImageDownloader mImageDownloader;
    public SearchItemAdapter(Context context, List<SearchItem> list, int layouId) {
        super(context, list, layouId);
        mImageDownloader = new ImageDownloader(context);
    }

    @Override
    public void convert(ViewHolder holder, SearchItem item, int position) {
        holder.setText(R.id.tv_name, item.getName());
        holder.setText(R.id.tv_sinal, item.getDetail());
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
