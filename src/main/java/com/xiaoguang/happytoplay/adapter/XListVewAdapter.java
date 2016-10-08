package com.xiaoguang.happytoplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.bean.Grather;

import java.util.List;

/**
 * Created by 11655 on 2016/9/28.
 */
public class XListVewAdapter extends BaseAdapter {
    private Context context;
    private List<Grather> grathers;
    private LayoutInflater inflater ;
    public XListVewAdapter(Context context, List<Grather> grathers) {
        this.context = context;
        this.grathers = grathers;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return grathers.size();
    }

    @Override
    public Object getItem(int position) {
        return grathers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            //填充布局
            convertView = inflater.inflate(R.layout.frag_home_xlv_item,null);
        }
        return convertView;
    }
}
