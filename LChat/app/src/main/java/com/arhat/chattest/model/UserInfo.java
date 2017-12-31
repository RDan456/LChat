package com.arhat.chattest.model;

/**
 * Created by lihanguang on 2016/9/10.
 */
public class UserInfo {

    private static UserInfo mUserInfo;
    private String name;
    private String sex;
    private int age;
    private String birthday;
    private String constellation;
    private String address;
    private String description;
    private String signal;

    public UserInfo(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age=age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public static UserInfo getUserInfo(){
        if(mUserInfo == null){
            synchronized(UserInfo.class){
                if(mUserInfo == null){
                    mUserInfo = new UserInfo();
                }
            }
        }
        return mUserInfo;
    }
}
