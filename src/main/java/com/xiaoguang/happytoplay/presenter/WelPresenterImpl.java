package com.xiaoguang.happytoplay.presenter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;

import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.bean.Grather;
import com.xiaoguang.happytoplay.bean.User;
import com.xiaoguang.happytoplay.contract.IWelContract;
import com.xiaoguang.happytoplay.model.GratherModelImpl;
import com.xiaoguang.happytoplay.utils.LogUtils;
import com.xiaoguang.happytoplay.utils.NetWorkStateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by 11655 on 2016/9/27.
 */

public class WelPresenterImpl implements IWelContract.IWelPresenter {
    /**
     * 声明一个动画对象
     */
    private ObjectAnimator animator;
    private IWelContract.IWelView view;
    //当前的网络状态,(默认为false)
    private boolean currentNetworkState = false;
    //声明一个活动id
    private String currentGratherId;
    //声明SharedPreferences对象
    private SharedPreferences perferences;

    public WelPresenterImpl(IWelContract.IWelView view) {
        this.view = view;
        view.setPresenter(this);
    }

    /**
     * 此方法为返回系统设置之后调用
     */
    @Override
    public void isNetWorkState() {
        //开启一个进度条对话框
        view.showLoading();
        LogUtils.i("myTag", "我执行了 网络判断的二次操作 ");
        //使用AsyncTask 进行耗时操作
        AsyncTask<Void, Boolean, Boolean> task = new AsyncTask<Void, Boolean, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                for (int i = 0; i < 5; i++) {
                    try {
                        //线程休眠1秒
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //返回当前的网络情况
                return NetWorkStateUtils.isNetworkAvailable(MyApplitation.context);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                //处理网络的状态
                handleNetWorkState(aBoolean);
            }
        };
        task.execute();
    }

    @Override
    public void handleNetWorkState(boolean netState) {
        //隐藏加载进度条
        view.hiddenLoading();
        //判断当前的网络状态
        if (netState) {
            //网络连接正常
            view.showMsg("网络连接正常");
            //从服务器获取活动数据
//            getGrathersFromServer();
            //判断是否是第一次登陆
            isLoginEd();
            LogUtils.i("myTag", "网络情况正常" + netState);

        } else {
            //网络连接不正常，弹出对话框
            view.showDialog();
            LogUtils.i("myTag", "网络情况不正正常" + netState);
        }
    }

    /**
     * 从服务器上获取活动内容的信息
     * 2016.10.8 更改
     */
    private void getGrathersFromServer() {
        //实例化GratherModel对象
        GratherModelImpl gratherModelImpl = new GratherModelImpl();
        //调用model 层方法查询数据
        gratherModelImpl.queryGrather(100, null, 0, 10, new GratherModelImpl.QueryCallBack<Grather>() {
            @Override
            public void done(final List<Grather> result, BmobException e) {
                if (e == null) {
                    LogUtils.i("从服务器获取活动数据成功");
                    //将服务器返回的活动信息保存到内存中
                    MyApplitation.putDatas("grathers", result);
                    LogUtils.i("数据为：" + result.toString());
                    /*
                      循环根据用户的objectId 获取每个活动的发布人的信息
                     */
                    LogUtils.i("获取数据条数为" + result.size());
                    view.showMsg("获取数据成功");
                    LogUtils.i("查询发布人成功");
                } else {
                    view.showMsg("获取数据失败");
                    LogUtils.i("从服务器获取数据失败,原因为" + e.toString());
                }
            }
        });
    }

    /**
     * 判断用户是否登陆过
     */
    private void isLoginEd() {
        /*
            2016.10.5修改，将直接使用Bmob提供的方法判断用户是否登陆过
         */
        //获取当前User对象
        User currentUser = BmobUser.getCurrentUser(User.class);
        //判断当前user 是否为空，不为空则登陆过，为空则为第一个登陆
        if (currentUser != null) {//不是第一次进行登陆
            //直接将用户信息保存到MyApplication中，方便以后使用
            MyApplitation.user = currentUser;
            //直接跳转到主页面
            view.jumpHome();
        } else {//第一次登陆
            //直接跳转到登陆页面
            view.jumpActivity();
        }
    }

    @Override
    public void startAnimation(View view) {
        //开启一个动画
        animator = ObjectAnimator.ofFloat(view, "alpha", 0.1f, 0.5f, 1f);
        animator.setDuration(5000);
        //为动画的添加监听事件
        AnimationEvent();
        animator.start();
    }

    @Override
    public void start() {

    }

    /**
     * 动画的监听事件
     */
    private void AnimationEvent() {
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                LogUtils.i("动画开始");
                //获取当前的网络状态
                currentNetworkState = NetWorkStateUtils.isNetworkAvailable(MyApplitation.context);
                //从服务器获取数据
                getGrathersFromServer();
                getCitysFromService();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                LogUtils.i("myTag", "当前的网络状态" + currentNetworkState);
                //处理当前的网络状态
                handleNetWorkState(currentNetworkState);
                //判断当前用户是否登陆过
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 获取城市信息
     */
    private void getCitysFromService() {
        //获取编辑器对象
        perferences = MyApplitation.context.getSharedPreferences("citys", Context.MODE_PRIVATE);
        boolean flag = perferences.getBoolean("exist",false);
        if (flag){//本地存在数据
            String result = perferences.getString("citys","");
            parsingJSONCitys(result);
            return;
        }
        //定义请求服务器URL
        final String SERVER_URL = "http://bmob-cdn-6590.b0.upaiyun.com/2016/10/11/54b0a73340c55cc080545797369d1daa.html";
        //实例化OKHttpClient 对象
        OkHttpClient okHttpClient = new OkHttpClient();
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(SERVER_URL)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //打印错误信息
                LogUtils.i(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取服务器返回的信息
                String result = response.body().string();
                LogUtils.i("获取到城市信息为" + result);
                //将城市信息保存到本地
                savaCitysDataToLocation(result);
                //解析城市
                 parsingJSONCitys(result);
            }
        });
    }

    /**
     * 解析城市数据
     * @param result
     */
    private void parsingJSONCitys(String result) {
        List citys = new ArrayList();
        //开始解析
        try {
            JSONObject object = new JSONObject(result);
            JSONArray array = object.getJSONArray("result");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object2 = array.getJSONObject(i);
                JSONArray array2 = object2.getJSONArray("city");
                for (int j = 0; j < array2.length(); j++) {
                    JSONObject object3 = array2.getJSONObject(j);
                    //将获取的城市信息放到List集合中
                    citys.add(object3.get("city"));
                }
            }
            //将城市信息保存到内存中
            MyApplitation.putDatas("cityList",citys);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将城市列表信息保存到本地
     * @param result 城市的Json字符串
     */
    private void savaCitysDataToLocation(String result) {
        //获取编辑器对象
        SharedPreferences.Editor editor = perferences.edit();
        //存放一个标记，用于判断本地是否存在数据
        editor.putBoolean("exist",true);
        //将城市数据存入文件
        editor.putString("citys",result);
        //提交数据
        editor.commit();
    }
}
