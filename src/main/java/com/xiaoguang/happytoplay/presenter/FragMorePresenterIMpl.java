package com.xiaoguang.happytoplay.presenter;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.contract.IFragMoreContract;

import java.util.ArrayList;
import java.util.List;

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
        adapter = new ArrayAdapter<String>(MyApplitation.context, R.layout.frag_more_sp_item,cityList);
        //为Spinner设置适配器
        spCity.setAdapter(adapter);

    }

    @Override
    public void search(String searchText) {

    }

    @Override
    public void start() {
        if (MyApplitation.getDatas("cityList",false)!=null){
            //获取城市信息
            cityList = (List<String>) MyApplitation.getDatas("cityList",false);
        }else {
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
