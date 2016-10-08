package com.xiaoguang.happytoplay.bean;

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
    private String graherName;
    //活动类型
    private String gratherType;
    //活动内容
    private String graherContent;
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

    public Grather() {
    }

    public String getGraherName() {
        return graherName;
    }

    public void setGraherName(String graherName) {
        this.graherName = graherName;
    }

    public String getGraherType() {
        return gratherType;
    }

    public void setGraherType(String getGraherType) {
        this.gratherType = gratherType;
    }

    public String getGraherContent() {
        return graherContent;
    }

    public void setGraherContent(String graherContent) {
        this.graherContent = graherContent;
    }

    public List<BmobFile> getGratherImageFiles() {
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
}
