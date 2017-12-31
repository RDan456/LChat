package com.arhat.chattest.model;

import org.jivesoftware.smack.roster.packet.RosterPacket.ItemType;
import org.jivesoftware.smack.roster.packet.RosterPacket.ItemStatus;

/**
 * Created by Arhat on 2016/8/31.
 */
public class Friend {

    private String id;
    private String username;//登录名称
    private String userHead;
    private String name;//备注名称
    private ItemStatus status;   //是否在线的状态
    private String textState; //把在线状态设置为String类型的
    private ItemType type;
    private String mood;
    private String sinal; //个性签名
    private boolean isChose;

    public Friend() {

    }

    public Friend(String username) {
        super();
        this.username = username;
    }

    public Friend(String username,String name) {
        super();
        this.username = username;
        this.name = name;
    }

    public Friend(String username, ItemStatus status) {
        super();
        this.username = username;
        this.status = status;

    }

    public Friend(String username, ItemType type) {
        super();
        this.username = username;
        this.type = type;
    }

    public Friend(String id, String username, ItemStatus status, String mood) {
        super();
        this.id = id;
        this.username = username;
        this.status = status;
        this.mood = mood;
    }

    public Friend(String username, String sinal,String id) {
        this.username = username;
        this.sinal = sinal;
        this.id = id;
    }

    public Friend(String id, String username, String userHead, ItemStatus status, String textState, ItemType type, String mood, String sinal, boolean isChose) {
        this.id = id;
        this.username = username;
        this.userHead = userHead;
        this.status = status;
        this.textState = textState;
        this.type = type;
        this.mood = mood;
        this.sinal = sinal;
        this.isChose = isChose;
    }

    //重写equals方法
    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj instanceof Friend) {
            Friend t = (Friend) obj;
            isEqual = this.username.equals(t.username);
            return isEqual;
        }
        return super.equals(obj);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setChose(boolean chose) {
        isChose = chose;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public void setTextState(String textState) {

        this.textState = textState;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public ItemType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getMood() {
        return mood;
    }

    public String getUserHead() {
        return userHead;
    }

    public String getUsername() {
        return username;
    }

    public String getTextState() {

        return this.textState;
    }

    public String getSinal() {
        return sinal;
    }

    public void setSinal(String sinal) {
        this.sinal = sinal;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
}
