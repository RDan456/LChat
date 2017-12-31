package com.arhat.chattest.presenter;

import android.content.Intent;

import com.arhat.chattest.model.GroupName;

/**
 * Created by lihanguang on 2016/9/29.
 */
public class GroupManagerContact {
    public final static int REQUESTCODE = 0x666;
    public final static int RESULTCODE = 0x667;

    public interface Presenter extends BasePresenter {

        /**
         * 返回
         */
        void back();

        /**
         * 下一步
         */
        void next(String remark, String username);

        /**
         * 打开选择分组
         */
        void openGroupSelect();

        /**
         * 选择分组结果
         *
         * @param resultCode
         * @param requestCode
         * @param data
         */
        void result(int resultCode, int requestCode, Intent data);

    }

    public interface View extends BaseView<Presenter> {

        /**
         * 返回
         */
        void back();

        /**
         * 下一步
         */
        void next();

        /**
         * 打开分组选择
         */
        void showGrooupSelect();

        /**
         * 设置分组名称
         *
         * @param groupName
         */
        void setGroupName(String groupName);

        /**
         * 显示错误信息
         */
        void showError();

        /**
         * 显示没有数据
         */
        void showNoData();

        /**
         * 添加成功
         */
        void showAddSuccess();

        /**
         * 添加失败
         */
        void showAddFail();
    }

    public interface DataSource {

        /**
         * 获得默认分组
         *
         * @return
         */
        GroupName getDefaultGroup();
    }
}
