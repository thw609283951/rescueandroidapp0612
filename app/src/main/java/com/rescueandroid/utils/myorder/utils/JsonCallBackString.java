package com.rescueandroid.utils.myorder.utils;

/**
 * Created by CC on 2017/5/29.
 */

public interface JsonCallBackString {
    /**
     * @param requestCode 判断回调
     * @param resultCode 回调结果：0失败 1成功
     * @param reply 返回的String
     * */
    void resultData(int requestCode, int resultCode, String reply);
}
