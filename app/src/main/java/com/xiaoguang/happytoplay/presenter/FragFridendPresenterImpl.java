package com.xiaoguang.happytoplay.presenter;

import android.widget.ArrayAdapter;

import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.contract.IFagFriendContract;
import com.xiaoguang.happytoplay.view.XListView;

import java.util.ArrayList;

/**
 * 好友的处理类
 * Created by 11655 on 2016/10/17.
 */

public class FragFridendPresenterImpl implements IFagFriendContract.IFagFriendPresenter {
    private IFagFriendContract.IIFagFriendView view;

    public FragFridendPresenterImpl(IFagFriendContract.IIFagFriendView view) {
        this.view = view;
        view.setPresenter(this);
    }

    /**
     * 进行数据的初始化操作
     */
    @Override
    public void start() {
        //获取控件,并设置适配器
        ArrayList datas = new ArrayList();
        /*以下数据进行测试*/
        datas.add("添加");
        datas.add("添加");
        datas.add("添加");
        datas.add("添加");
        datas.add("添加");
        XListView xListView = view.getmFragFriendXlv();
        xListView.setAdapter(new ArrayAdapter<String>(MyApplitation.context, android.R.layout.simple_list_item_1, datas));

    }

    @Override
    public void destroy() {
    }
}
