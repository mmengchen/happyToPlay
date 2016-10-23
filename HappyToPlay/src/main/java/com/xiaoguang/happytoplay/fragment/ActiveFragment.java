package com.xiaoguang.happytoplay.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.xiaoguang.happytoplay.R;
import com.xiaoguang.happytoplay.base.BaseFragment;

/**
 * 活动的Fragment
 * Created by 11655 on 2016/9/29.
 */

public class ActiveFragment extends BaseFragment {
    @Override
    protected View initLayout(LayoutInflater inflater) {
        return inflater.inflate(R.layout.frag_act, null);
    }
}
