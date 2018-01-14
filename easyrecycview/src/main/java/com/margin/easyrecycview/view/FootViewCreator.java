package com.margin.easyrecycview.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Margin on 2018/1/14.
 * 创建Hearder View
 */

public abstract class FootViewCreator {
    public View create() {
        ViewGroup parent = getParent();
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutResource(), parent, false);
        initView(view);
        return view;
    }

    protected void initView(View view) {

    }

    protected abstract ViewGroup getParent();

    protected abstract int getLayoutResource();

}
