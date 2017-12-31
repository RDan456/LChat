package com.arhat.chattest.data;

import com.arhat.chattest.constants.Constants;
import com.arhat.chattest.model.UserInfo;
import com.arhat.chattest.presenter.MainActivityContact;

/**
 * Created by Arhat on 2016/9/23.
 */

public class UserInfoDataSoure implements MainActivityContact.DataSource{
    @Override
    public UserInfo getUserInfo() {

        UserInfo user = new UserInfo();
        user.setName(Constants.USER_NAME);
        user.setSex("boy");
        user.setAge(20);
        user.setBirthday("1996-09-09");
        user.setAddress("四海为家");
        user.setDescription("低调");
        user.setSignal("我很帅");
        user.setConstellation("狮子座");
        return user;
    }
}
