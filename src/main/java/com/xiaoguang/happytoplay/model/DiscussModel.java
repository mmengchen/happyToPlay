package com.xiaoguang.happytoplay.model;

import com.xiaoguang.happytoplay.bean.Discuss;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
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
     * @param type 100 为加载更多 200 时 为查询所有当前活动的数据
     */
    public void queryDisscuss(String objectId, int type,final QueryCallBack callBack) {
        BmobQuery<Discuss> query = new BmobQuery<>();
        switch (type){
            case 100:
                query.addWhereEqualTo("gratehrId",objectId);
                query.setLimit(10);
                query.setSkip(10);
                break;
            case 200:
                query.addWhereEqualTo("gratehrId",objectId);
                break;
        }
        //查询多条数据
        query.findObjects(new FindListener<Discuss>() {
            @Override
            public void done(List<Discuss> list, BmobException e) {
                callBack.done(list, e);
            }
        });
    }
    public interface QueryCallBack{
        void done(List<Discuss> list, BmobException e);
    }
    public interface CallBack{
        void done(String objectId, BmobException e);
    }
}
