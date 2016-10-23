package com.xiaoguang.happytoplay.model;

/**
 * 最基本的模型接口
 * Created by 11655 on 2016/9/26.
 */

public interface IBaseModel {
    int STATUS_FAIL = -1;// 验证失败
    int STATUS_SUCCESS = 0;// 成功
    int STATUS_NORMAL = 1;// 正常状态
    int STATUS_VERIFY_ING = 2;// 正常状态
}
