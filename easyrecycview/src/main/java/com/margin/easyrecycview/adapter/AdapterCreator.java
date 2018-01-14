package com.margin.easyrecycview.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Margin on 2018/1/14.
 * 适配器创造接口
 */

public interface AdapterCreator<T, K extends BaseViewHolder, A extends BaseQuickAdapter<T, K>> {
    A create(List<T> data);
}
