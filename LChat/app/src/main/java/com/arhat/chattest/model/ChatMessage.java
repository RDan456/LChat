package com.arhat.chattest.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lihanguang on 2016/9/6.
 */
public class ChatMessage implements Parcelable {
    private String name;
    private String text;
    private String iconUrl;
    private boolean isLeft;
    private String userName;

    public ChatMessage(String name, String text, String iconUrl, String userName, boolean isLeft) {
        this.name = name;
        this.text = text;
        this.iconUrl = iconUrl;
        this.userName = userName;
        this.isLeft = isLeft;

    }

    public ChatMessage(String name, String text, String iconUrl, boolean isLeft) {
        this.name = name;
        this.text = text;
        this.iconUrl = iconUrl;
        this.isLeft = isLeft;

    }

    protected ChatMessage(Parcel in) {
        name = in.readString();
        text = in.readString();
        iconUrl = in.readString();
        isLeft = in.readByte() != 0;
    }

    public static final Creator<ChatMessage> CREATOR = new Creator<ChatMessage>() {
        @Override
        public ChatMessage createFromParcel(Parcel in) {
            return new ChatMessage(in);
        }

        @Override
        public ChatMessage[] newArray(int size) {
            return new ChatMessage[size];
        }
    };

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(text);
        dest.writeString(iconUrl);
        dest.writeByte((byte) (isLeft ? 1 : 0));
    }
}
