package com.margin.easyrecycview.data;

import java.util.List;

/**
 * Created by Margin on 2018/1/14.
 * 这个监听器是用作当Adapter第一次加载数据时候的回调
 */

public interface OnFirstLoadDataListener<T> {
    void onFirstLoad(List<T> datas);
}
