package com.xiaoguang.happytoplay.presenter;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.baidu.location.BDLocation;
import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.bean.Grather;
import com.xiaoguang.happytoplay.contract.IFragMoreContract;
import com.xiaoguang.happytoplay.model.GratherModelImpl;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by 11655 on 2016/9/28.
 */

public class FragMorePresenterImpl implements IFragMoreContract.IFragMorePresenter {
    //声明一个View接口
    private IFragMoreContract.IFragMoreView view;
    //声明一个适配器对象
    private ArrayAdapter<String> adapter;
    //声明一个Spinner数据源
    private List<String> cityList;
    private Spinner spCity;

    public FragMorePresenterImpl(IFragMoreContract.IFragMoreView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadingData(Object... o) {
        //获取Spinner对象
        spCity = (Spinner) o[0];
        //进行数据源的初始化操作
        start();
        //为控件添加适配器
        adapter = new ArrayAdapter<String>(MyApplitation.context, R.layout.frag_more_sp_item, cityList);
        //为Spinner设置适配器
        spCity.setAdapter(adapter);

    }

    @Override
    public void query(String datas, final int type) {
        //显示一个进度条
        view.showLoading();
        //实例化Model层
        GratherModelImpl gratherModel = new GratherModelImpl();
        //初始化参数
        int queryType = 0;
        String dataType = null;
        int skip = 0;
        int counts = 10;
        BmobGeoPoint bmobGeoPoint = null;
        switch (type) {
            case 0://模糊查询,(付费用户)
                break;
            case 1://根据活动金额进行查询,免费用户金额为0
                queryType = 700;
                break;
            case 2://根据点赞数进行查询
                queryType = 600;
                break;
            case 3://根据距离进行查询
                queryType = 500;
                //获取当前位置信息
                BDLocation loaction = (BDLocation) MyApplitation.getDatas("currentLocation",false);
                bmobGeoPoint = new BmobGeoPoint(loaction.getLongitude(),loaction.getLatitude());
                break;
            case 4://根据类型查询数据
                queryType = 400;
                dataType = datas;
                break;
        }
        gratherModel.queryGrather(queryType, dataType, skip, counts, bmobGeoPoint, new GratherModelImpl.QueryCallBack<Grather>() {
            @Override
            public void done(List<Grather> result, BmobException e) {
                //隐藏进度条
                view.hiddenLoading();
                if (e == null) {
                    //查询成功
                    view.jumpActivity(result);
                } else {
                    //查询失败
                    view.showMsg("数据查询失败，原因" + e.toString());
                }
            }
        });
    }

    @Override
    public void start() {
        if (MyApplitation.getDatas("cityList", false) != null) {
            //获取城市信息
            cityList = (List<String>) MyApplitation.getDatas("cityList", false);
        } else {
            cityList = new ArrayList<>();
            //设置默认数据
            cityList.add("北京");
            cityList.add("上海");
            cityList.add("广州");
            cityList.add("杭州");
            cityList.add("深圳");
        }
    }

}
