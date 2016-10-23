package com.xiaoguang.happytoplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.contract.IPubContract;
import com.xiaoguang.happytoplay.utils.LogUtils;
import com.xiaoguang.happytoplay.view.BottomPopView;

import java.util.ArrayList;

/**
 * publishActivity中展示图片的GridView的适配器
 * Created by 11655 on 2016/9/30.
 */
public class PublishGridViewAdapter extends BaseAdapter {
    //存放所有选择的照片
    private ArrayList<String> allSelectedPicture = new ArrayList<String>();
    private Context context;
    public LayoutInflater layoutInflater;
    private BottomPopView bottomPopView;
    //实例化对象
    private IPubContract.IPubPresenter presenter;

    public PublishGridViewAdapter(ArrayList<String> allSelectedPicture, Context context, IPubContract.IPubPresenter presenter) {
        this.allSelectedPicture = allSelectedPicture;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.presenter = presenter;
    }

    @Override
    public int getCount() {
        return allSelectedPicture.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.act_pub_grid_item, null);

            holder.image = (ImageView) convertView.findViewById(R.id.child_iv);
            holder.btn = (Button) convertView.findViewById(R.id.child_delete);

            holder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == allSelectedPicture.size()) {
            holder.image.setBackgroundResource(R.drawable.icon_addpic);
            holder.btn.setVisibility(View.GONE);

            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //去图库加载图片，并重新设置适配器
                    selectClick(v);
                    LogUtils.i("我现在要添加照片");
                }
            });
            if (position == 3) {
                holder.image.setVisibility(View.GONE);
            }
        } else {
            ImageLoader.getInstance().displayImage("file://" + allSelectedPicture.get(position),
                    holder.image);

            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击后移除图片
                    allSelectedPicture.remove(position);

//                    //更新UI
                    presenter.updateUI(allSelectedPicture);

                }
            });

        }
        return convertView;
    }

    /**
     * 弹出对话框，进行图片的选择
     */
    private void selectClick(View view) {
        presenter.onCreateBottomMenu(view);
    }
    
    /**
     * 自定义的一个类用来缓存convertview
     */
    public class ViewHolder {
        public ImageView image;
        public Button btn;
    }
}
