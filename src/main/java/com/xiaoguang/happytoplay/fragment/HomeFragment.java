package com.xiaoguang.happytoplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.base.BaseFragment;
import com.xiaoguang.happytoplay.contract.IFragHomeContract;
import com.xiaoguang.happytoplay.presenter.FragHomePresenterImpl;
import com.xiaoguang.happytoplay.utils.ToastUtils;
import com.xiaoguang.happytoplay.view.XListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 11655 on 2016/9/28.
 */

public class HomeFragment extends BaseFragment implements IFragHomeContract.IFragHomeView {

    //获取控件
    @BindView(R.id.frag_home_ib_back)
    ImageButton mIbBack;
    @BindView(R.id.frag_home_et_search)
    EditText mEtSearch;
    @BindView(R.id.frag_home_ib_search)
    ImageButton mbSearch;
    @BindView(R.id.frag_home_ib_menu)
    ImageButton mIbMenu;
    @BindView(R.id.frag_home_xlv)
    XListView mXlv;
    private IFragHomeContract.IFragHomePresenter presenter;
    //声明一个标记，用于判断点击事件,默认为false 表示第一次点击
    private boolean flag = false;
    private long exitTime = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FragHomePresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDatas();
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        getData();
    }

    @Override
    public void showMsg(String msg) {
        ToastUtils.toastShort(msg);
    }

    @Override
    public void showLoading() {
        super.showProcessDialog("", "", true);
    }

    @Override
    public void hiddenLoading() {
        super.dismissProcessDialog();
    }

    @Override
    public void setPresenter(IFragHomeContract.IFragHomePresenter presenter) {
        this.presenter = presenter;

    }

    @Override
    public void jumpActivity() {

    }

    @Override
    public boolean back() {
        return false;
    }

    @Override
    public void setData(Object... object) {

    }

    @Override
    public void getData() {
        //为listview 添加数据
        presenter.loadingData(mXlv, this);
    }

    /**
     * 为控件添加监听事件
     *
     * @param view
     */
    @OnClick({R.id.frag_home_ib_back, R.id.frag_home_ib_search, R.id.frag_home_ib_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.frag_home_ib_back:
                isExitAPP();
                break;
            case R.id.frag_home_ib_search:
                if (flag) {//如果使第二次点击,进行搜索相关
                    Toast.makeText(getContext(), "我在进行搜索功能", Toast.LENGTH_SHORT).show();
                    //隐藏输入框
                    mEtSearch.setVisibility(View.GONE);
                    //显示返回按钮
                    mIbBack.setVisibility(View.VISIBLE);
                    flag = false;
                    //获取文本框的内容
                    String searchText = mEtSearch.getText().toString();
                    //调用p层进行处理
                    presenter.search(searchText);

                } else {
                    //显示搜索控件
                    mEtSearch.setVisibility(View.VISIBLE);
                    //隐藏控件
                    mIbBack.setVisibility(View.GONE);
                    flag = true;
                }
                break;
            case R.id.frag_home_ib_menu:
                break;
        }
    }

    /**
     * 双击退出,计算时间差实现
     */
    private void isExitAPP() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.toastShort("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            //退出APP
            getActivity().finish();
            System.exit(0);
        }
    }
}
