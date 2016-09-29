package com.xiaoguang.happytoplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xiaoguang.happytoplay.R;

import java.util.List;

/**
 * Created by 11655 on 2016/9/28.
 */
public class XListVewAdapter extends BaseAdapter {
    private Context context;
    private List<String> strs;
    private LayoutInflater inflater ;
    public XListVewAdapter(Context context, List<String> strs) {
        this.context = context;
        this.strs = strs;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return strs.size();
    }

    @Override
    public Object getItem(int position) {
        return strs.get(position);
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
