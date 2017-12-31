package com.arhat.chattest.presenter;

import com.arhat.chattest.model.GroupName;

import java.util.List;

/**
 * Created by lihanguang on 2016/9/29.
 */
public class SelectGroupContact {
    public interface Presenter extends BasePresenter {
        /**
         * 加载组名
         */
        void loadGroupName();

        /**
         * 选择组名
         *
         * @param position
         */
        void selectGroupName(int position);

        /**
         * 返回
         */
        void back();

    }

    public interface View extends BaseView<Presenter> {

        /**
         * 显示分组数据
         */
        void showGroupName(List<GroupName> groupNames);

        /**
         * 返回
         */
        void back(GroupName groupName);

    }

    public interface DataSource {
        /**
         * 获取分组信息
         *
         * @return
         */
        List<GroupName> getGroupName();
    }

}
