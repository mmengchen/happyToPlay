package com.xiaoguang.happytoplay.bean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * 活动的Bean对象
 * Created by 11655 on 2016/10/6.
 */

public class Grather extends BmobObject {
    //活动名称
    private String gratherName;
    //活动类型
    private String gratherType;
    //活动内容
    private String gratherContent;
    //活动图片
    private List<BmobFile> gratherImageFiles;
    //活动时间
    private String graterDataTime;
    //活动地点
    private BmobGeoPoint gratherGeoPoint;
    //活动金额
    private float gratherMoney;
    //活动的发起人
    private String gratherOriginator;
    //活动开启状态
    private boolean flag;
    //活动的地址
    private String address;
    //收藏活动的人
    private List<String> loveUserIds;
    //参加活动的人
    private List<String> joinUsesId;
    public String getGratherName() {
        return gratherName;
    }

    public void setGratherName(String gratherName) {
        this.gratherName = gratherName;
    }

    public String getGratherType() {
        return gratherType;
    }

    public void setGratherType(String gratherType) {
        this.gratherType = gratherType;
    }

    public String getGratherContent() {
        return gratherContent;
    }

    public void setGratherContent(String gratherContent) {
        this.gratherContent = gratherContent;
    }

    public List<BmobFile> getGratherImageFiles() {
        if (gratherImageFiles == null) {
            gratherImageFiles = new ArrayList<>();
        }
        return gratherImageFiles;
    }

    public void setGratherImageFiles(List<BmobFile> gratherImageFiles) {
        this.gratherImageFiles = gratherImageFiles;
    }

    public String getGraterDataTime() {
        return graterDataTime;
    }

    public void setGraterDataTime(String graterDataTime) {
        this.graterDataTime = graterDataTime;
    }

    public BmobGeoPoint getGratherGeoPoint() {
        return gratherGeoPoint;
    }

    public void setGratherGeoPoint(BmobGeoPoint gratherGeoPoint) {
        this.gratherGeoPoint = gratherGeoPoint;
    }

    public float getGratherMoney() {
        return gratherMoney;
    }

    public void setGratherMoney(float gratherMoney) {
        this.gratherMoney = gratherMoney;
    }

    public String getGratherOriginator() {
        return gratherOriginator;
    }

    public void setGratherOriginator(String gratherOriginator) {
        this.gratherOriginator = gratherOriginator;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getLoveUserIds() {
        if (loveUserIds == null) {
            loveUserIds = new ArrayList<>();
        }
        return loveUserIds;
    }

    public void setLoveUserIds(List<String> loveUserIds) {
        this.loveUserIds = loveUserIds;
    }

    public List<String> getJoinUsesId() {
        if (joinUsesId==null){
            joinUsesId = new ArrayList<>();
        }
        return joinUsesId;
    }

    public void setJoinUsesId(List<String> joinUsesId) {
        this.joinUsesId = joinUsesId;
    }

    @Override
    public String toString() {
        return "Grather{" +
                "gratherName='" + gratherName + '\'' +
                ", gratherType='" + gratherType + '\'' +
                ", gratherContent='" + gratherContent + '\'' +
                ", gratherImageFiles=" + gratherImageFiles +
                ", graterDataTime='" + graterDataTime + '\'' +
                ", gratherGeoPoint=" + gratherGeoPoint +
                ", gratherMoney=" + gratherMoney +
                ", gratherOriginator='" + gratherOriginator + '\'' +
                ", flag=" + flag +
                ", address='" + address + '\'' +
                ", loveUserIds=" + loveUserIds +
                '}';
    }
}
