package com.xiaoguang.happytoplay.model;

import com.xiaoguang.happytoplay.bean.User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * 用户相关模型(数据访问相关出现重复性代码,在model和Grather模型中)
 * Created by 11655 on 2016/10/8.
 */

public class UserModelImpl {
    public void queryUsers(String objectId, final QueryUserCallBack callBack){
        BmobQuery<User> query = new BmobQuery<>();
        //根据Id查询发布人的信息
        query.getObject(objectId, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                callBack.onDone(user,e);
            }
        });
    }

    /**
     * 查询发布人的回调信息的方法
     */
    public interface QueryUserCallBack{
        void onDone(User user, BmobException e);
    }
}
