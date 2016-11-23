package com.xiaoguang.happytoplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.contract.IGratherDetailsContract;
import com.xiaoguang.happytoplay.utils.ImageLoaderutils;

import java.util.ArrayList;

/**
 * 活动详情中展示图片的GridView的适配器
 * Created by 11655 on 2016/10.14
 */
public class GratherDetailsGridViewAdapter extends BaseAdapter {
    //存放所有选择的照片
    private ArrayList<String> allSelectedPicture = new ArrayList<String>();
    private Context context;
    public LayoutInflater layoutInflater;
    //设置图片加载器的参数
    private final DisplayImageOptions options = ImageLoaderutils.myGetOpt(R.drawable.loading,
            R.drawable.logding_error);
    //获取图片加载器对象
    private final ImageLoader loader  = ImageLoader.getInstance();
    //实例化对象
    private IGratherDetailsContract.IGratherDetailsPresenter presenter ;

    public GratherDetailsGridViewAdapter(ArrayList<String> allSelectedPicture, Context context,
                                         IGratherDetailsContract.IGratherDetailsPresenter presenter) {
        this.allSelectedPicture = allSelectedPicture;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return allSelectedPicture.size();
    }

    @Override
    public Object getItem(int position) {
        return allSelectedPicture.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.details_gv_item,null);
            viewHolder = new ViewHolder();
            //获取图片控件
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.details_gv_item_iv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //显示图片
        loader.displayImage(allSelectedPicture.get(position),viewHolder.imageView,options);
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
    }
}
