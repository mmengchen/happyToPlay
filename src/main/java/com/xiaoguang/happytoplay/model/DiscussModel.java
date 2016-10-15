package com.xiaoguang.happytoplay.model;

import com.xiaoguang.happytoplay.bean.Discuss;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 11655 on 2016/10/14.
 */

public class DiscussModel {
    /**
     * 保存评论的信息
     * @param discuss
     * @param callBack
     */
    public void save(Discuss discuss, final CallBack callBack){
        discuss.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                callBack.done(s,e);
            }
        });
    }

    /**
     * 查询评论的详细信息
     */
    public void queryDisscuss(String objectId, final QueryCallBack callBack) {
        BmobQuery<Discuss> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId",objectId);
        //根据id查询出每条数据
        query.getObject(objectId, new QueryListener<Discuss>() {
            @Override
            public void done(Discuss discuss, BmobException e) {
                callBack.done(discuss, e);
            }
        });
    }
    public interface QueryCallBack{
        void done(Discuss discuss, BmobException e);
    }
    public interface CallBack{
        void done(String objectId, BmobException e);
    }
}
