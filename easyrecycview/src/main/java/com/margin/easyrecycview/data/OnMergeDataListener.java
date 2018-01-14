package com.margin.easyrecycview.data;

import java.util.List;

/**
 * Created by Margin on 2018/1/14.
 * 这个监听器是用作当Adapter需要进行数据合并时候的回调
 */

public interface OnMergeDataListener<T> {
    int onMerge(List<T> origin, List<T> data);
}
