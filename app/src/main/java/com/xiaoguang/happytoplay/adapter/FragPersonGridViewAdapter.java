package com.xiaoguang.happytoplay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
    //设置图片的数据源
    private int [] imgIds = {R.drawable.my_youhui,R.drawable.my_shoucang,
            R.drawable.my_send,R.drawable.my_order,R.drawable.my_gather,R.drawable.my_shezhi};

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            convertView =  LayoutInflater.from(MyApplitation.context).inflate(R.layout.frag_person_gv_item,null);
            viewHolder = new ViewHolder();
            viewHolder.imageViewIco = (ImageView) convertView.findViewById(R.id.frag_person_gv_item_iv);
            viewHolder.textViewContent = (TextView) convertView.findViewById(R.id.frag_person_gv_item_tv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        viewHolder.imageViewIco.setImageResource(imgIds[position]);
        viewHolder.textViewContent.setText(listDatas.get(position));
        return convertView;
    }
    class ViewHolder{
        ImageView imageViewIco;
        TextView textViewContent;
    }
}
