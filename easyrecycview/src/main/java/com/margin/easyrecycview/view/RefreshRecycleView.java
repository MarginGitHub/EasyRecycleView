package com.margin.easyrecycview.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

/**
 * Created by margin on 2017/12/8.
 * 可刷新的RecyclerView
 * 在RecyclerView外面包了一层SmartRefreshLayout
 * 从而实现了可以上拉加载更多，下拉刷新的效果
 */

public class RefreshRecycleView extends SmartRefreshLayout {
    private RecyclerView mRecyclerView;

    public RefreshRecycleView(Context context) {
        super(context);
        init(context);
    }

    public RefreshRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshRecycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mRecyclerView = new RecyclerView(context);
        SmartRefreshLayout.LayoutParams layoutParams = new SmartRefreshLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mRecyclerView.setLayoutParams(layoutParams);
        addView(mRecyclerView);
    }

    @Override
    public SmartRefreshLayout setOnLoadmoreListener(OnLoadmoreListener listener) {
        setRefreshFooter(new ClassicsFooter(getContext()));
        setEnableLoadmore(true);
        return super.setOnLoadmoreListener(listener);
    }

    @Override
    public SmartRefreshLayout setOnRefreshListener(OnRefreshListener listener) {
        setRefreshHeader(new ClassicsHeader(getContext()));
        setEnableRefresh(true);
        return super.setOnRefreshListener(listener);
    }

    @Override
    public SmartRefreshLayout setOnRefreshLoadmoreListener(OnRefreshLoadmoreListener listener) {
        setRefreshHeader(new ClassicsHeader(getContext()));
        setRefreshFooter(new ClassicsFooter(getContext()));
        setEnableRefresh(true);
        setEnableLoadmore(true);
        return super.setOnRefreshLoadmoreListener(listener);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public RecyclerView getRecycleView() {
        return mRecyclerView;
    }
}
