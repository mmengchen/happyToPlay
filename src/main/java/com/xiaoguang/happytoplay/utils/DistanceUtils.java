package com.xiaoguang.happytoplay.utils;

/**
 * 根据经纬度计算两地的距离工具类
 * Created by 11655 on 2016/10/9.
 */

public class DistanceUtils {
    /**
     * 地球的半径
     */
    private static final double EARTH_RADIUS = 6378137.0;

    /**
     * 根据两地经纬度，计算两地的距离
     * @param longitude1  活动的经度
     * @param latitude1   活动的纬度
     * @param longitude2  用户的经度
     * @param latitude2   用户的纬度
     * @return distance 返回两地的距离，单位是米
     */
    public static double getDistance(double longitude1, double latitude1,
                                     double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance * 10000) / 10000;
        return distance;
    }
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
}
