package com.arhat.chattest.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lihanguang on 2016/9/29.
 */
public class GroupName implements Parcelable {
    private String name;
    private String groupId;

    public GroupName(String name, String groupId) {
        this.name = name;
        this.groupId = groupId;
    }

    protected GroupName(Parcel in) {
        name = in.readString();
        groupId = in.readString();
    }

    public static final Creator<GroupName> CREATOR = new Creator<GroupName>() {
        @Override
        public GroupName createFromParcel(Parcel in) {
            return new GroupName(in);
        }

        @Override
        public GroupName[] newArray(int size) {
            return new GroupName[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(groupId);
    }
}
