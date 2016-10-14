package com.xiaoguang.happytoplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.bean.Grather;
import com.xiaoguang.happytoplay.bean.User;
import com.xiaoguang.happytoplay.model.UserModelImpl;
import com.xiaoguang.happytoplay.utils.DistanceUtils;
import com.xiaoguang.happytoplay.utils.ImageLoaderutils;
import com.xiaoguang.happytoplay.utils.LogUtils;
import com.xiaoguang.happytoplay.utils.ToastUtils;
import com.xiaoguang.happytoplay.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 11655 on 2016/9/28.
 */
public class XListVewAdapter extends BaseAdapter {
    private Context context;
    /**
     * 数据源
     */
    private List<Grather> grathers = new ArrayList<>();
    private LayoutInflater inflater;
    //设置图片加载器的参数
    private final DisplayImageOptions options;
    //获取图片加载器对象
    private final ImageLoader loader;

    public XListVewAdapter(Context context, List<Grather> grathers) {
        this.context = context;
        this.grathers = grathers;
        inflater = LayoutInflater.from(context);
        options = ImageLoaderutils.myGetOpt(R.drawable.loading, R.drawable.logding_error);
        loader = ImageLoaderutils.getInstance(MyApplitation.context);

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        final Holder holder = new Holder();
        if (convertView == null) {
            //填充布局
            convertView = inflater.inflate(R.layout.frag_home_xlv_item, null);
            viewHolder = new ViewHolder();
            //获取item中的控件
            viewHolder.mcircleImageViewHead = (CircleImageView) convertView.findViewById(R.id.frag_home_xlv_item_iv_head);
            viewHolder.mImageViewSex = (ImageView) convertView.findViewById(R.id.frag_home_xlv_item_iv_sex);
            viewHolder.mImagePic = (ImageView) convertView.findViewById(R.id.frag_home_xlv_item_iv_img);
            viewHolder.mImageViewLove = (ImageView) convertView.findViewById(R.id.frag_home_xlv_item_iv_bottom_love);
            viewHolder.mTextViewNickName = (TextView) convertView.findViewById(R.id.frag_home_xlv_item_tv_nickname);
            viewHolder.mTextViewTime = (TextView) convertView.findViewById(R.id.frag_home_xlv_item_tv_time);
            viewHolder.mTextViewMoney = (TextView) convertView.findViewById(R.id.frag_home_xlv_item_tv_money);
            viewHolder.mTextViewTitle = (TextView) convertView.findViewById(R.id.frag_home_xlv_item_tv_message);
            viewHolder.mTextViewDistance = (TextView) convertView.findViewById(R.id.frag_home_xlv_item_tv_jl);//获取距离控件
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /*
            设置数据
         */
        UserModelImpl u = new UserModelImpl();
        //查询并设置用户数据
        u.queryUsers(grathers.get(position).getGratherOriginator(), new UserModelImpl.QueryUserCallBack() {
            @Override
            public void onDone(User user, BmobException e) {
                loader.displayImage(user.getUserHead().getUrl(), viewHolder.mcircleImageViewHead, options);
                //设置性别图标
                if ("男".equals(user.getSex())) {
                    viewHolder.mImageViewSex.setImageResource(R.drawable.boy);
                } else {
                    viewHolder.mImageViewSex.setImageResource(R.drawable.gril);
                }
                //设置昵称
                viewHolder.mTextViewNickName.setText(user.getNickName());
            }
        });
        //------------
        //展示图片(默认展示第一张上传的照片)
        loader.displayImage(grathers.get(position).getGratherImageFiles()
                .get(0).getFileUrl(), viewHolder.mImagePic, options);
        viewHolder.mTextViewTime.setText(grathers.get(position).getGraterDataTime());
        viewHolder.mTextViewMoney.setText(String.valueOf(grathers.get(position).getGratherMoney()) + " 元");
        viewHolder.mTextViewTitle.setText(grathers.get(position).getGratherName());
        //用于显示距离
        StringBuffer sbDistance = new StringBuffer();
        //获取活动发布的位置
        BmobGeoPoint gratherLocation = grathers.get(position).getGratherGeoPoint();
        //从内存中获取用户当前的位置
        BDLocation currentLocation = (BDLocation) MyApplitation.getDatas("currentLocation", false);
        if (currentLocation != null) {
            //计算距离
            double distance = DistanceUtils.getDistance(currentLocation.getLongitude(), currentLocation.getLatitude(),
                    gratherLocation.getLongitude(), gratherLocation.getLatitude());
            sbDistance.append("距离你 ");
            if (distance >= 1000) {
                sbDistance.append(Math.round(distance) / 1000);
                sbDistance.append(" Km");
            } else {
                sbDistance.append(distance);
                sbDistance.append(" m");
            }

        } else {
            sbDistance.append("未知");
        }
        //设置距离
        viewHolder.mTextViewDistance.setText(sbDistance.toString());
        //用于标记是否点击过
        holder.flag = grathers.get(position).getLoveUserIds().contains(MyApplitation.user.getObjectId());
        //获取当前活动收藏的用户,判断是否出现过
        LogUtils.i("收藏活动的用户信息id" + grathers.get(position).getLoveUserIds().toString());
        LogUtils.i("当前的用户id为" + MyApplitation.user.getObjectId());
        LogUtils.i("从服务器获取的点赞为" + grathers.get(position).getLoveUserIds().contains(MyApplitation.user.getObjectId()));
        if (holder.flag) {//点赞了
            //显示红心效果
            viewHolder.mImageViewLove.setImageResource(R.drawable.love_press);
        } else {
            //显示灰心效果
            viewHolder.mImageViewLove.setImageResource(R.drawable.love);
        }
        //为点赞按钮添加点击事件
        viewHolder.mImageViewLove.setOnClickListener(new View.OnClickListener() {
            //用于标记是否点击过
            @Override
            public void onClick(View v) {
                if (holder.flag) {//为true为点赞过,取消点赞
                    LogUtils.i("我正在执行取消点赞 " + position);
                    final Grather grather = new Grather();
                    final ArrayList<String> uids = new ArrayList<String>();
                    uids.add(MyApplitation.user.getObjectId());
                    grather.removeAll("loveUserIds", uids);
                    //更新服务器数据
                    grather.update(grathers.get(position).getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                LogUtils.i("当前活动中移除用户成功");
                                //将user表移除
                                User user = new User();
                                ArrayList<String> gratherIds = new ArrayList<String>();
                                gratherIds.add(grathers.get(position).getObjectId());
                                user.removeAll("loveGratherIds", gratherIds);
                                user.update(MyApplitation.user.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            LogUtils.i("当前用户移除当前活动成功");
                                            ToastUtils.toastShort("取消点赞成功");
                                            //设置图片
                                            viewHolder.mImageViewLove.setImageResource(R.drawable.love);
                                        } else {
                                            LogUtils.i("当前用户移除当前活动失败" + e.toString());
                                            ToastUtils.toastShort("取消点赞失败");

                                        }
                                    }
                                });
                            } else {
                                LogUtils.i("当前活动中移除用户成功" + e.toString());
                                ToastUtils.toastShort("取消点赞失败，原因是" + e.toString());
                            }
                        }
                    });
                } else {//点赞
                    LogUtils.i("我正在进行点赞操作 " + position);
                    final Grather grather = new Grather();
                    //将当前用户Id保存
                    grather.add("loveUserIds", MyApplitation.user.getObjectId());
                    //更新数据到服务器
                    grather.update(grathers.get(position).getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                LogUtils.i("当前活动添加用户成功");
                                //向user表添加活动
                                User user = new User();
                                //将当前的活动的Id添加到用户表中
                                user.add("loveGratherIds", grathers.get(position).getObjectId());
                                user.update(MyApplitation.user.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            LogUtils.i("当前用户添加当前活动成功");
                                            ToastUtils.toastShort("点赞成功");
                                            //设置图片
                                            viewHolder.mImageViewLove.setImageResource(R.drawable.love_press);
                                        } else {
                                            LogUtils.i("当前用户添加当前活动失败" + e.toString());
                                            ToastUtils.toastShort("点赞失败");
                                        }
                                    }
                                });
                            } else {
                                LogUtils.i("当前活动添加当前用户失败，原因 " + e.toString());
                                ToastUtils.toastShort("点赞失败，原因是" + e.toString());
                            }
                        }
                    });
                }
                holder.flag = !holder.flag;
            }
        });
        return convertView;
    }

    class ViewHolder {
        CircleImageView mcircleImageViewHead;
        ImageView mImageViewSex, mImagePic, mImageViewLove;
        TextView mTextViewNickName, mTextViewTime, mTextViewMoney, mTextViewDistance, mTextViewTitle;
    }

    class Holder {
        /**
         * 用于标记当前活动的状态，false 为没有点赞过，true 为点赞过，默认为false
         */
        boolean flag = false;
    }
}
