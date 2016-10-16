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
import com.xiaoguang.happytoplay.bean.Discuss;
import com.xiaoguang.happytoplay.bean.User;
import com.xiaoguang.happytoplay.contract.IGratherDetailsContract;
import com.xiaoguang.happytoplay.model.UserModelImpl;
import com.xiaoguang.happytoplay.utils.ImageLoaderutils;
import com.xiaoguang.happytoplay.view.CircleImageView;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by 11655 on 2016/10/14.
 */

public class DiscussXlvAdapter extends BaseAdapter {
    //定义一个数据源
    private List<Discuss> discussList;
    private Context context;
    //    private LayoutInflater inflater;
    //定义p层对象
    private IGratherDetailsContract.IGratherDetailsPresenter presenter;
    //设置图片加载器的参数
    private final DisplayImageOptions options;
    //获取图片加载器对象
    private final ImageLoader loader;
    //头像默认的Url
    private String URI_DEFAULT = "http://bmob-cdn-6590.b0.upaiyun.com/2016/10/16/f8bd4e9c40174c49805921fbe68b6745.png";
    public DiscussXlvAdapter(List<Discuss> discussList, Context context,
                             IGratherDetailsContract.IGratherDetailsPresenter presenter) {
        this.discussList = discussList;
        this.context = context;
//        this.inflater = LayoutInflater.from(context);
        this.presenter = presenter;
        options = ImageLoaderutils.myGetOpt(R.drawable.loading, R.drawable.logding_error);
        loader = ImageLoaderutils.getInstance(MyApplitation.context);
    }

    @Override
    public int getCount() {
        return discussList.size();
    }

    @Override
    public Object getItem(int position) {
        return discussList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.act_details_xlv_item, null);
            convertView.setTag(viewHolder);
            //获取控件
            viewHolder.circleImageViewHead = (CircleImageView) convertView.findViewById(R.id.act_detials_xlv_item_civ_head);
            viewHolder.imgSex = (ImageView) convertView.findViewById(R.id.act_detials_xlv_item_iv_sex);
            viewHolder.tvDiscussText = (TextView) convertView.findViewById(R.id.act_detials_xlv_item_tv_discuss_text);
            viewHolder.tvUname = (TextView) convertView.findViewById(R.id.act_detials_xlv_item_tv_usename);
            viewHolder.tvDiscusTime = (TextView) convertView.findViewById(R.id.act_detials_xlv_item_tv_discuss_time);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        viewHolder.tvDiscussText.setText(discussList.get(position).getDiscussText());
        viewHolder.tvDiscusTime.setText(discussList.get(position).getDiscussTime());
        UserModelImpl userModel = new UserModelImpl();
        presenter.queryUserSuccess();
        //查询头像信息
        final ViewHolder finalViewHolder = viewHolder;
        userModel.queryUsers(discussList.get(position).getDiscussUserId(), new UserModelImpl.QueryUserCallBack() {
            @Override
            public void onDone(User user, BmobException e) {
                if (e == null) {
                    if (user.getUserHead().getUrl()==null){//头像为空,则显示默认头像
                        loader.displayImage(URI_DEFAULT, finalViewHolder.circleImageViewHead, options);
                    }else {
                        //查询成功,设置头像//使用ImageLoader
                        loader.displayImage(user.getUserHead().getUrl(), finalViewHolder.circleImageViewHead, options);
                    }
                    //设置用户名
                    finalViewHolder.tvUname.setText(user.getNickName());
                    //查询信息成功
                    presenter.queryUserSuccess();
                } else {
                    //查询失败
                    presenter.queryUseError(e);
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        CircleImageView circleImageViewHead;
        ImageView imgSex;
        TextView tvUname, tvDiscussText, tvDiscusTime;
    }
}
