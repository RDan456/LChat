package com.arhat.chattest.presenter;

import com.arhat.chattest.model.UserInfo;

/**
 * Created by Arhat on 2016/9/23.
 */

public class MainActivityContact {



    public interface DataSource{
        /**
         * 获得自己的信息
         * @return
         */
        UserInfo getUserInfo();
    }
}
