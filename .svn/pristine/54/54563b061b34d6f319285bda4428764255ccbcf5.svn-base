package com.arhat.chattest.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.arhat.arhat.chattest.R;
import com.arhat.chattest.model.Friend;
import com.arhat.chattest.model.Group;
import com.arhat.chattest.presenter.ContactsFragmentContact;
import com.arhat.chattest.util.ImageDownloader;
import com.arhat.chattest.util.L;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lihanguang on 2016/9/9.
 */
public class ContactsAdapter extends BaseExpandableListAdapter {
    private List<Group> mGroups;
    private Context mContext;
    private ImageDownloader mImageDownloader;
    private ContactsFragmentContact.Presenter mPresenter;

    public ContactsAdapter(Context context, List<Group> groups, ContactsFragmentContact.Presenter mPresenter) {
        mContext = context;
        mGroups = groups;
        this.mPresenter = mPresenter;
        mImageDownloader = new ImageDownloader(mContext);
    }

    @Override
    public int getGroupCount() {
        if (mGroups == null) {
            return 0;
        }
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (mGroups == null) {
            return 0;
        }
        Group group = mGroups.get(groupPosition);
        if (group == null) {
            return 0;
        }
        List<Friend> contacts = group.getContactList();
        if (contacts == null) {
            return 0;
        }
        return contacts.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_group_contacts, null);
            holder = new GroupHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        Group group = mGroups.get(groupPosition);
        if (group != null) {
            holder.tv_name.setText(group.getName());
            String num = group.getNowNum() + "/" + group.getTotalNum();
            holder.tv_num.setText(num);
            holder.tv_num.setTag(group.getName());
        }
//        convertView.setOnLongClickListener(new OnGroupLongClickListenr(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_dialogue, null);
            holder = new ChildHolder();
            holder.ci_icon = (CircleImageView) convertView.findViewById(R.id.ci_icon);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_msg = (TextView) convertView.findViewById(R.id.tv_msg);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        Group group = mGroups.get(groupPosition);
        List<Friend> friends = group.getContactList();
        Friend friend = friends.get(childPosition);
        holder.ci_icon.setTag(friend.getUsername());
        System.out.println(friend.getUsername());
        if (friend != null) {
            mImageDownloader.downloadImage(friend.getUsername(), new ImageDownloader.OnImageDownloadListener() {
                @Override
                public void onImageDownload(String url, Bitmap bitmap) {
                    if (holder.ci_icon.getTag().equals(url) && bitmap != null) {
                        holder.ci_icon.setImageBitmap(bitmap);
                    } else {
                        holder.ci_icon.setImageResource(R.mipmap.icon_stub);
                    }
                }
            });
            holder.tv_name.setText(friend.getName());
            holder.tv_msg.setText(friend.getTextState());
            holder.tv_msg.setTag(friend.getUsername() + friend.getName());
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupHolder {
        TextView tv_name;
        TextView tv_num;
    }

    class ChildHolder {
        public CircleImageView ci_icon;
        public TextView tv_name;
        public TextView tv_msg;
    }

    class OnGroupLongClickListenr implements View.OnLongClickListener {
        int groupPosition;

        public OnGroupLongClickListenr(int groupPosition) {
            this.groupPosition = groupPosition;
        }

        @Override
        public boolean onLongClick(View v) {
            L.d("onLongClick:" + groupPosition);
            if (mPresenter != null) {
                mPresenter.openGroupPop(groupPosition);
            }
            return true;
        }
    }
}
