package com.arhat.chattest.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lihanguang on 2016/9/7.
 */
public class Dialogue implements Parcelable{
    private String iconUrl;
    private String name;
    private int unLookNumber;
    private int totalNumber;
    private String lastMessage;
    public Dialogue(String iconUrl, String name, int unLookNumber, int totalNumber, String lastMessage) {
        this.iconUrl = iconUrl;
        this.name = name;
        this.unLookNumber = unLookNumber;
        this.totalNumber = totalNumber;
        this.lastMessage = lastMessage;
    }
    public Dialogue(){

    }

    protected Dialogue(Parcel in) {
        iconUrl = in.readString();      //头像
        name = in.readString();         //聊天的姓名
        unLookNumber = in.readInt();    //没读的消息数
        totalNumber = in.readInt();     //总的消息数
        lastMessage = in.readString();//最新消息
    }

    public static final Creator<Dialogue> CREATOR = new Creator<Dialogue>() {
        @Override
        public Dialogue createFromParcel(Parcel in) {
            return new Dialogue(in);
        }

        @Override
        public Dialogue[] newArray(int size) {
            return new Dialogue[size];
        }
    };

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnLookNumber() {
        return unLookNumber;
    }

    public void setUnLookNumber(int unLookNumber) {
        this.unLookNumber = unLookNumber;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(iconUrl);
        dest.writeString(name);
        dest.writeInt(unLookNumber);
        dest.writeInt(totalNumber);
        dest.writeString(lastMessage);
    }
}
