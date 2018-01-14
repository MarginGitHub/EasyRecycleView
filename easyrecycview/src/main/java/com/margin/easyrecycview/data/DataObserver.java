package com.margin.easyrecycview.data;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.margin.easyrecycview.adapter.AdapterCreator;
import com.margin.easyrecycview.manager.RefreshRecycleViewManager;
import com.margin.easyrecycview.view.RefreshRecycleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Margin on 2018/1/14.
 * Adapter数据观察者
 */

public class DataObserver<T, K extends BaseViewHolder, A extends BaseQuickAdapter<T, K>> {
//    数据还未初始化状态
    private static final int UNINITIALIZED = 0;
//    数据已初始化
    private static final int INITIALIZED = 1;
//    正在加载更多
    private static final int LOADINGMORE = 2;
//    正在下拉刷新
    private static final int REFRESHING = 3;

    private RefreshRecycleViewManager<T, K, A> mManager;
    private A mAdapter;
    private List<T> mData;
    private AdapterCreator<T, K, A> mAdapterCreator;
    private OnFirstLoadDataListener<T> mOnFirstLoadDataListener;
    private OnMergeDataListener<T> mOnMergeDataListener;
//    默认状态是未初始化状态
    private int mCurrentStatus = UNINITIALIZED;
//    标记当下拉刷新没有获取到数据时是否保持原有数据， 默认是清空的。
    private boolean isHoldData;

    public DataObserver(RefreshRecycleViewManager<T, K, A> manager) {
        mManager = manager;
    }

    public void setAdapterCreator(AdapterCreator<T, K, A> mAdapterCreator) {
        this.mAdapterCreator = mAdapterCreator;
    }

    public void setOnFirstLoadDataListener(OnFirstLoadDataListener<T> mOnFirstLoadDataListener) {
        this.mOnFirstLoadDataListener = mOnFirstLoadDataListener;
    }

    public void setOnMergeDataListener(OnMergeDataListener<T> mOnMergeDataListener) {
        this.mOnMergeDataListener = mOnMergeDataListener;
    }

    public void setHoldData(boolean isHoldData) {
        this.isHoldData = isHoldData;
    }

    public void setLoadingmoreStatus() {
        mCurrentStatus = LOADINGMORE;
    }

    public void setRefreshingStatus() {
        mCurrentStatus = REFRESHING;
    }

    public void setData(List<T> data) {
        if (mCurrentStatus == UNINITIALIZED) {
            init(data);
        } else {
            replace(data);
        }
    }

    /**
     * 数据加载成功回调
     * @param data 新数据
     */
    public void success(List<T> data) {
        switch (mCurrentStatus) {
            case UNINITIALIZED:
            case INITIALIZED:
                init(data);
                break;
            case LOADINGMORE:
                mManager.finishLoadmore(true);
                append(data);
                break;
            case REFRESHING:
                mManager.finishRefresh(true);
                replace(data);
                mManager.resetPage();
                break;
        }
    }

    /**
     * 加载成功，但是没有更多数据了
     */
    public void end() {
        switch (mCurrentStatus) {
            case UNINITIALIZED:
            case INITIALIZED:
                mManager.decreasePage();
                break;
            case LOADINGMORE:
                mManager.finishLoadmore(true);
                mManager.decreasePage();
                break;
            case REFRESHING:
                mManager.finishRefresh(true);
                if (!isHoldData) {
                    replace(new ArrayList<T>());
                    mManager.resetPage();
                }
                break;
        }
    }


    /**
     * 数据加载失败
     */
    public void error() {
        switch (mCurrentStatus) {
            case UNINITIALIZED:
            case INITIALIZED:
                mManager.decreasePage();
                break;
            case LOADINGMORE:
                mManager.finishLoadmore(false);
                mManager.decreasePage();
                break;
            case REFRESHING:
                mManager.finishRefresh(false);
                break;
        }
    }

    private void init(List<T> data) {
        if (mAdapterCreator != null && data != null) {
            if (mData == null) {
                mData = data;
            } else {
                mData.clear();
                mData.addAll(data);
            }
            if (mOnFirstLoadDataListener != null) {
                mOnFirstLoadDataListener.onFirstLoad(mData);
            }
            if (mAdapter == null) {
                mAdapter = mAdapterCreator.create(mData);
                mManager.initView(mAdapter);
            }
            mAdapter.notifyDataSetChanged();
            mCurrentStatus = INITIALIZED;
        }
    }

    private void append(List<T> data) {
        if (mData != null && data != null) {
            int start = mData.size() - 1;
            if (mOnMergeDataListener != null) {
                start = mOnMergeDataListener.onMerge(mData, data);
            } else {
                mData.addAll(data);
            }
            mAdapter.notifyItemRangeInserted(start, data.size());
        }
        mCurrentStatus = INITIALIZED;
    }

    private void replace(List<T> data) {
        if (data == null) {
            return;
        }
        if (mData != null) {
            mData.clear();
            mData.addAll(data);
            mAdapter.notifyDataSetChanged();
            mCurrentStatus = INITIALIZED;
        } else {
            init(data);
        }
    }


}
