package com.xiaoguang.happytoplay.presenter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.adapter.PublishGridViewAdapter;
import com.xiaoguang.happytoplay.adapter.PublishSpinnerAdapter;
import com.xiaoguang.happytoplay.application.MyApplitation;
import com.xiaoguang.happytoplay.bean.Grather;
import com.xiaoguang.happytoplay.contract.IPubContract;
import com.xiaoguang.happytoplay.model.GratherModelImpl;
import com.xiaoguang.happytoplay.utils.LogUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

import static com.xiaoguang.happytoplay.application.MyApplitation.context;

/**
 * Created by 11655 on 2016/9/30.
 */

public class PublishPresenterImpl implements IPubContract.IPubPresenter {
    private IPubContract.IPubView view;
    //GradView的数据源
    private ArrayList<String> allSelectedPicture;
    //Spinner的文字数据源
    private List<String> strNames;
    private int [] imgId;
    private GridView mgridView;
    //选择类型的下拉列表
    private Spinner mSp;
    //日期的字符串
    private static String dateStr;
    //时间的字符串
    private static String timeStr;
    //时间选择对话框
    private TimePickerDialog timePickerDialog;
    //图片的资源数组
    private String[] filePaths;

    public PublishPresenterImpl(IPubContract.IPubView view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadingData(Object ...o) {
        start();
        mSp = (Spinner)o[0];
        mgridView = (GridView)o[1];
        //为GradView 添加适配器
        mgridView.setAdapter(new PublishGridViewAdapter(allSelectedPicture, context,this));
        //为Spinner 添加适配器
        mSp.setAdapter(new PublishSpinnerAdapter(strNames,imgId,context));
    }

    @Override
    public void onCreateBottomMenu(View v) {
        //调用View层方法创建弹出式对话框
        view.onCreateBottomMenu(v);
    }

    @Override
    public void setIcoHeader(Context context, Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);

        //

        //将图片资源进行压缩


        LogUtils.i("我在进行添加图片");
        //将资源显示到文本框上
        if(!allSelectedPicture.contains(picturePath)){
            allSelectedPicture.add(picturePath);
        }
        cursor.close();
        //设置适配器
        mgridView.setAdapter(new PublishGridViewAdapter(allSelectedPicture, context,this));
    }

    @Override
    public void setIconHeader() {
        //获取图片的路径
        String uri = Environment.getExternalStorageDirectory() + "/icoImage.jpg";
        if(!allSelectedPicture.contains(uri)){
            allSelectedPicture.add(uri);
        }
        //设置适配器
        mgridView.setAdapter(new PublishGridViewAdapter(allSelectedPicture, context,this));
    }

    @Override
    public void updateUI(ArrayList<String> allPicture) {
        this.allSelectedPicture = allPicture;
        //重新设置适配器,并更新UI
        mgridView.setAdapter(new PublishGridViewAdapter(allPicture,context,this));
    }

    @Override
   public void chooseTime(Context context) {
        //获取当前系统时间
        Calendar calendar = Calendar.getInstance();
        //创建日期选择器
       DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateStr = year+"年"+(month+1)+"月"+dayOfMonth+"日";
                LogUtils.i("我选择的日期为："+dateStr);
                timePickerDialog.show();
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        //显示日期提示框
        datePickerDialog.show();
        //创建时间选择器
        timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeStr = hourOfDay+":"+minute;
                //调用View层，进行时间的显示
                PublishPresenterImpl.this.view.setData(dateStr+""+timeStr);
            }
        },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),true);
    }

    @Override
    public void submit(final Grather data) {
        //声明并实例化GratherModerlImpl对象
        final GratherModelImpl gratherModel = new GratherModelImpl();
        filePaths  = new String [allSelectedPicture.size()];
        //从lsit集合中取出图片资源uri
        for (int i=0;i<allSelectedPicture.size();i++){
            filePaths[i] = allSelectedPicture.get(i);
        }
        gratherModel.Upload(filePaths, new GratherModelImpl.UploadCallBack() {
            private int currentIndex;
            /**
             * @param files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作
             * @param urls-上传文件的完整url地址
             */
            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                //文件需要压缩
                LogUtils.i("上传文件的个数为"+files.size()+"");
                view.showLoading("图片资源上传中","正在上传"+currentIndex+"/"+filePaths.length,false);
                if(urls.size()==filePaths.length){//如果数量相等，则代表文件全部上传完成
                    //do something
                    //将图片资源保存到bean对象中
                    data.setGratherImageFiles(files);
                    view.hiddenLoading();
                    view.showMsg("图片上传成功");
                    LogUtils.i("图片上传成功");
                    view.showLoading(null,"活动发布中",false);
                    //调用Model层进行保存数据
                    gratherModel.Save(data, new GratherModelImpl.SaveCallBack() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e==null){
                                //隐藏进度条
                                view.hiddenLoading();
                                view.showMsg("活动发布成功");
                                LogUtils.i("活动发送成功");
                                //跳转到提示界面
                                view.jumpActivity();
                            } else {
                                view.hiddenLoading();
                                view.showMsg("活动发布失败，请检查原因"+e.toString());
                                LogUtils.i("活动发布失败，原因"+e.toString());
                            }
                        }
                    });
                }
            }

            /**
             *
             * @param curIndex--表示当前第几个文件正在上传
             * @param curPercent--表示当前上传文件的进度值（百分比）
             * @param total--表示总的上传文件数
             * @param totalPercent--表示总的上传进度（百分比）
             */
            @Override
            public void onProcess(int curIndex, int curPercent, int total, int totalPercent) {
//                view.showLoading("图片资源上传中","正在上传"+curIndex+"/"+total,false);
                LogUtils.i("图片资源上传中","正在上传"+curIndex+" / "+total);
                currentIndex = curIndex;
            }

            @Override
            public void onError(int i, String s) {
                LogUtils.i("图片上传失败"+s);
                view.hiddenLoading();
                view.showMsg("图片上传失败请检查，网络设置");
            }
        });
    }

    //进行初始化的操作
    @Override
    public void start() {
        //初始化图片加载器
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(MyApplitation.context)
                .threadPriority(Thread.NORM_PRIORITY - 2)//设置当前线程的优先级
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//使用MD5对UIL进行加密命名
                .diskCacheSize(100 * 1024 * 1024)//50 Mb sd卡(本地)缓存的最大值
                .diskCacheFileCount(300)// 可以缓存的文件数量
                .tasksProcessingOrder(QueueProcessingType.LIFO)//后进先出
                .build();

        //初始化操作
        ImageLoader.getInstance().init(config);
        //初始化数据源
        imgId = new int[] {R.drawable.sort_select,R.drawable.sort_diy,R.drawable.sort_jiangzuo,R.drawable.sort_jieri,
                R.drawable.sort_jucan,R.drawable.sort_meishi,R.drawable.sort_shaoer,
                R.drawable.sort_yanchu,R.drawable.sort_yundong,
                R.drawable.sort_zhanlan,
                R.drawable.sort_zhoubian};
        allSelectedPicture = new ArrayList<String>();
        strNames = new ArrayList<String>();
        strNames.add("类型");
        strNames.add("DIY");
        strNames.add("讲座");
        strNames.add("节日");
        strNames.add("聚餐");
        strNames.add("美食");
        strNames.add("少儿");
        strNames.add("演出");
        strNames.add("运动");
        strNames.add("专栏");
        strNames.add("周边");
    }
}
