package com.xiaoguang.happytoplay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.application.MyApplitation;

import java.util.List;

/**
 * 个人中心的界面的适配器
 * Created by 11655 on 2016/9/28.
 */

public class FragPersonGridViewAdapter extends BaseAdapter {
    //设置数据源
    private List<String> listDatas;

    public FragPersonGridViewAdapter(List listDatas) {
        this.listDatas = listDatas;
    }

    @Override
    public int getCount() {
        return listDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return listDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView =  LayoutInflater.from(MyApplitation.context).inflate(R.layout.frag_person_gv_item,null);
        }
        return convertView;
    }
}
