package com.xiaoguang.happytoplay.bean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 继承Bmobuser
 * Created by 11655 on 2016/9/27.
 */

public class User extends BmobUser {
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private BmobFile userHead;

    /**
     * 性别
     */
    private String sex;

    /**
     * 收藏的活动的Id
     */
    private List<String> loveGratherIds;


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public BmobFile getUserHead() {
        if (userHead==null){
            userHead = new BmobFile();
        }
        return userHead;
    }

    public void setUserHead(BmobFile userHead) {
        this.userHead = userHead;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<String> getLoveGratherIds() {
        if (loveGratherIds == null) {
            loveGratherIds = new ArrayList<>();
        }
        return loveGratherIds;
    }

    public void setLoveGratherIds(List<String> loveGratherIds) {
        this.loveGratherIds = loveGratherIds;
    }

    @Override
    public String toString() {
        return "User{" +
                "nickName='" + nickName + '\'' +
                ", userHead=" + userHead +
                ", sex='" + sex + '\'' +
                ", loveGratherIds=" + loveGratherIds +
                '}';
    }
}
