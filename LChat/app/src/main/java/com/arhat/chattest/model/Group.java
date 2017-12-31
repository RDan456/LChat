package com.arhat.chattest.model;

import java.util.List;

/**
 * Created by lihanguang on 2016/9/9.
 */
public class Group {
    private String name;
    private int totalNum;
    private int nowNum;
    private List<Friend> mContactList;
    public Group(){

    }
    public Group(String name, int totalNum, int nowNum, List<Friend> contactList) {
        this.name = name;
        this.totalNum = totalNum;
        this.nowNum = nowNum;
        mContactList = contactList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getNowNum() {
        return nowNum;
    }

    public void setNowNum(int nowNum) {
        this.nowNum = nowNum;
    }

    public List<Friend> getContactList() {
        return mContactList;
    }

    public void setContactList(List<Friend> contactList) {
        mContactList = contactList;
    }
}
