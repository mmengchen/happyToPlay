package com.xiaoguang.happytoplay.model;

import com.xiaoguang.happytoplay.bean.Grather;
import com.xiaoguang.happytoplay.utils.LogUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * 活动操作的model
 * Created by 11655 on 2016/10/8.
 */

public class GratherModelImpl {
    /**
     * 文件批量上传
     * @param filePaths  文件的路径的数组
     * @param uploadCallBack  回调方法
     */
    public void Upload(String []filePaths, final UploadCallBack uploadCallBack){
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> list1) {
                uploadCallBack.onSuccess(list,list1);
            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {
                uploadCallBack.onProcess(i,i1,i2,i3);
            }

            @Override
            public void onError(int i, String s) {
                uploadCallBack.onError(i,s);
            }
        });
    }

    /**
     * 保存数据到服务器
     * @param grather  活动bean对象
     * @param saveCallBack  回调方法
     */
    public void Save(Grather grather, final SaveCallBack saveCallBack){
        grather.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                saveCallBack.done(objectId,e);
            }
        });

    }

    /**
     * 查询活动的
     * @param queryType 查询类型，值为 100时，为默认查询10条数据；值为200时 ，值为300，值为400时
     * @param dataType
     * @param skip
     * @param counts
     * @param queryCallBack  查询的回调方法
     */
    public void queryGrather(int queryType, String dataType, int skip, int counts, final QueryCallBack<Grather> queryCallBack){
        BmobQuery<Grather> query = new BmobQuery<>();
        switch (queryType){
            case 100:
                //设置默认查询10条
                query.setLimit(counts);
                break;
            case 200:
                query.setLimit(counts);
                //设置跳过skip条数据
                query.setSkip(skip);
                break;
            case 300:
                //添加查询条件，相当于where objectId  = dataType
                query.addWhereEqualTo("objectId",dataType);
                break;
            case 400:
                //添加查询条件，相当于where objectId  = dataType
                query.addWhereEqualTo("gratherType",dataType);
                break;
        }
        query.findObjects(new FindListener<Grather>() {
            @Override
            public void done(List<Grather> list, BmobException e) {
                queryCallBack.done(list,e);
            }
        });
    }
    /**
     * 批量上传的回调接口
     */
    public interface UploadCallBack{
        void onSuccess(List<BmobFile> list, List<String> list1);
        void onProcess(int i, int i1, int i2, int i3);
        void onError(int i, String s);
    }

    /**
     * 保存用户数据的回调接口
     */
    public interface SaveCallBack{
        void done(String objectId, BmobException e);
    }

    /**
     * 查询的回调接口
     * @param <T>
     */
    public interface QueryCallBack<T>{
        void done(List<T> result,BmobException e);
    }
}
