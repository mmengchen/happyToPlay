package com.xiaoguang.happytoplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoguang.happytoplay.R;

import java.util.List;

/**
 * SpinnerAdapter 下拉列表的适配器
 * Created by 11655 on 2016/9/30.
 */

public class PublishSpinnerAdapter extends BaseAdapter {
    /**
     * 定义数据源
     */
    private List<String> strNames;
    /**
     * 图标资源ID
     */
    private int[] imgIds;
    /**
     * 上下文对象
     */
    private Context context;

    public PublishSpinnerAdapter(List<String> strNames, int[] imgIds, Context context) {
        this.strNames = strNames;
        this.imgIds = imgIds;
        this.context = context;
    }

    @Override
    public int getCount() {
        return strNames.size();
    }

    @Override
    public Object getItem(int position) {
        return strNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder vh;
        if (convertView == null) {
            vh = new ViewHoder();
            convertView = LayoutInflater.from(context).inflate(R.layout.act_pub_sp_item, null);
            vh.imageView = (ImageView) convertView.findViewById(R.id.act_pub_sp_item_iv);
            vh.textView = (TextView) convertView.findViewById(R.id.act_pub_sp_item_tv);
            convertView.setTag(vh);
        } else {
            vh = (ViewHoder) convertView.getTag();
        }
        //设置图片和文字
        vh.imageView.setImageResource(imgIds[position]);
        vh.textView.setText(strNames.get(position));
        return convertView;
    }

    class ViewHoder {
        private ImageView imageView;
        private TextView textView;
    }
}
