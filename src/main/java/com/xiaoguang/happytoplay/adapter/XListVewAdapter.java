package com.xiaoguang.happytoplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.bean.Grather;
import com.xiaoguang.happytoplay.bean.User;
import com.xiaoguang.happytoplay.utils.ImageLoaderutils;

import java.util.List;
import java.util.Map;

/**
 * Created by 11655 on 2016/9/28.
 */
public class XListVewAdapter extends BaseAdapter {
    private Context context;
    private List<Grather> grathers;
    private LayoutInflater inflater;
    //设置图片加载器的参数
    private final DisplayImageOptions options;
    //获取图片加载器对象
    private final ImageLoader loader;
    //活动发布人的集合
    private Map<String,User> userMap;


    public XListVewAdapter(Context context, List<Grather> grathers) {
        this.context = context;
        this.grathers = grathers;
        inflater = LayoutInflater.from(context);
        options = ImageLoaderutils.myGetOpt(R.drawable.loading, R.drawable.logding_error);
        loader = ImageLoaderutils.getInstance(MyApplitation.context);
        //获取内存中发布信息人的map集合
        userMap = (Map<String, User>) MyApplitation.getDatas("users",false);
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
        ViewHolder viewHolder;
        if (convertView == null) {
            //填充布局
            convertView = inflater.inflate(R.layout.frag_home_xlv_item, null);
            viewHolder = new ViewHolder();
            //获取item中的控件
            viewHolder.mImageViewHeader = (ImageView) convertView.findViewById(R.id.frag_home_xlv_item_iv_head);
            viewHolder.mImageViewSex = (ImageView) convertView.findViewById(R.id.frag_home_xlv_item_iv_sex);
            viewHolder.mImagePic = (ImageView) convertView.findViewById(R.id.frag_home_xlv_item_iv_img);
            viewHolder.mTextViewNickName = (TextView) convertView.findViewById(R.id.frag_home_xlv_item_tv_nickname);
            viewHolder.mTextViewTime = (TextView) convertView.findViewById(R.id.frag_home_xlv_item_tv_time);
            viewHolder.mTextViewMoney = (TextView) convertView.findViewById(R.id.frag_home_xlv_item_tv_money);
            viewHolder.mTextViewTitle = (TextView) convertView.findViewById(R.id.frag_home_xlv_item_tv_message);
            viewHolder.mTextViewDistance = (TextView) convertView.findViewById(R.id.frag_home_xlv_item_tv_jl);//获取距离控件
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //根据活动Id获取usemap中的user对象
//        User user = userMap.get(userMap.get(grathers.get(position).getObjectId()));
        User user = userMap.get(grathers.get(position).getObjectId());
        /*
            设置数据
         */
       //设置头像
        loader.displayImage(user.getUserHead().getUrl(),viewHolder.mImageViewHeader,options);
        //设置性别图标
        if ("男".equals(user.getSex())){
            viewHolder.mImageViewSex.setImageResource(R.drawable.boy);
        }else{
            viewHolder.mImageViewSex.setImageResource(R.drawable.gril);
        }
        //设置昵称
        viewHolder.mTextViewNickName.setText(user.getNickName());
        //展示图片(默认展示第一张上传的照片)
        loader.displayImage(grathers.get(position).getGratherImageFiles()
                .get(0).getFileUrl(), viewHolder.mImagePic, options);
        viewHolder.mTextViewTime.setText(grathers.get(position).getGraterDataTime());
        viewHolder.mTextViewMoney.setText(String.valueOf(grathers.get(position).getGratherMoney()));
        viewHolder.mTextViewTitle.setText(grathers.get(position).getGratherName());
        //距离由于需要计算，暂时不进行设置
//        viewHolder.mTextViewDistance.setText(grathers.get(position).get);
        return convertView;
    }

    class ViewHolder {
        ImageView mImageViewHeader, mImageViewSex, mImagePic;
        TextView mTextViewNickName, mTextViewTime, mTextViewMoney, mTextViewDistance, mTextViewTitle;
    }
}
