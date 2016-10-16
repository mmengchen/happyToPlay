package com.xiaoguang.happytoplay.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.base.BaseActivity;
import com.xiaoguang.happytoplay.contract.IMapContract;
import com.xiaoguang.happytoplay.utils.PoiOverlay;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 地图的Activity
 * Created by 11655 on 2016/10/6.
 */

public class MapActivity extends BaseActivity implements IMapContract.IMapView {

    //获取控件
    @BindView(R.id.act_map_et)
    EditText mEt;
    @BindView(R.id.act_map_iv_search)
    ImageView mIvSearch;
    @BindView(R.id.act_map_mapView)
    MapView mMapView;
    private PoiSearch mPoiSearch;
    private BaiduMap mBaiduMap;

    //检索结果集
    private List<PoiInfo> pois;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_map);
        ButterKnife.bind(this);
        // 获取到地图的操作对象
        mBaiduMap = mMapView.getMap();
        // 第一步，创建POI检索实例
        mPoiSearch = PoiSearch.newInstance();
        // 第三步，设置POI检索监听者
        mPoiSearch.setOnGetPoiSearchResultListener(new MyPOIResult());
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hiddenLoading() {

    }

    @Override
    public void setPresenter(IMapContract.IMapPresenter presenter) {

    }

    @Override
    public void jumpActivity() {

    }

    @Override
    public boolean back() {
        return false;
    }

    @Override
    public void setData(Object object) {

    }

    @Override
    public void getData() {

    }

    /**
     * 点击事件
     */
    @OnClick(R.id.act_map_iv_search)
    public void onClick() {
        //获取搜索框内容
        String searchContent = mEt.getText().toString();
        //进行Poi检索
        // 第四步，发起检索请求；
        mPoiSearch.searchInCity((new PoiCitySearchOption()).city("北京")
                .keyword(searchContent).pageNum(0));// 获取页数的数据(获取第十页的检索数据)//每页10条
    }

    // 第二步，创建POI检索监听者；
    class MyPOIResult implements OnGetPoiSearchResultListener {


        // 获取Place详情页检索结果
        @Override
        public void onGetPoiDetailResult(PoiDetailResult arg0) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult arg0) {

        }

        // 获取POI检索结果
        @Override
        public void onGetPoiResult(PoiResult result) {
            pois = result.getAllPoi();
            //将用户搜索到的结果显示到地图上(检索结果覆盖物 )
            // 检索结果覆盖物 第二步，在POI检索回调接口中添加自定义的PoiOverlay；
            if (result != null) {
                //清除覆盖物
                mBaiduMap.clear();
                //创建PoiOverlay
                PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
                //设置overlay可以处理标注点击事件
                mBaiduMap.setOnMarkerClickListener(overlay);
                //设置PoiOverlay数据
                overlay.setData(result);
                //添加PoiOverlay到地图中
                overlay.addToMap();
                overlay.zoomToSpan();
                return;
            }
        }

    }

    // 检索结果覆盖物 第一步，构造自定义 PoiOverlay 类；
    private class MyPoiOverlay extends PoiOverlay {
        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        /**
         * 点击结构覆盖物的回调方法
         */
        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            final PoiInfo p = pois.get(index);
            //创建InfoWindow展示的view
            Button button = new Button(getApplicationContext());
            button.setBackgroundResource(R.drawable.map_bg);
            button.setTextColor(Color.RED);
            button.setText(p.city + "\r\n" +
                    p.name + "\r\n" +
                    p.address + "\r\n" +
                    p.phoneNum + "\r\n" +
                    p.postCode);

            //点击地图上小图标
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //将位置信息保存到内存中
                    MyApplitation.putDatas("location",p);
                    MapActivity.this.setResult(5);
                    finish();
                }
            });
            //定义用于显示该InfoWindow的坐标点
//			LatLng pt = new LatLng(39.86923, 116.397428);
            //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
            /**
             * 参数一:弹出的视图view
             * 参数二:弹出的视图view所在位置
             * 参数三:y轴偏移量
             */

            InfoWindow mInfoWindow = new InfoWindow(button, p.location, -120);
            //显示InfoWindow
            mBaiduMap.showInfoWindow(mInfoWindow);
            return true;
        }
    }
}
