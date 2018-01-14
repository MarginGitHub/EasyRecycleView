package com.margin.easyrecycview.manager;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.margin.easyrecycview.adapter.AdapterCreator;
import com.margin.easyrecycview.data.DataObserver;
import com.margin.easyrecycview.data.OnFirstLoadDataListener;
import com.margin.easyrecycview.data.OnMergeDataListener;
import com.margin.easyrecycview.view.EmptyViewCreator;
import com.margin.easyrecycview.view.FootViewCreator;
import com.margin.easyrecycview.view.HeaderViewCreator;
import com.margin.easyrecycview.view.RefreshRecycleView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.List;


/**
 * Created by margin on 2017/12/7.
 * 这个类很重要,它接管了 App 里面的所有列表式页面的创建,Adapter 初始化, 数据初始化加载刷新, 以及各种 item 点击
 * 事件的处理逻辑.
 */

public class RefreshRecycleViewManager<T, K extends BaseViewHolder, A extends BaseQuickAdapter<T, K>> {
    private RefreshRecycleView mRefreshRecycleView;
    private DataObserver<T, K, A> mDataObserver;
    private View mEmptyView;
    private View mHeaderView;
    private View mFootView;
    private BaseQuickAdapter.OnItemClickListener mOnItemClickListener;
    private BaseQuickAdapter.OnItemLongClickListener mOnItemLongClickListener;
    private BaseQuickAdapter.OnItemChildClickListener mOnItemChildClickListener;
    private BaseQuickAdapter.OnItemChildLongClickListener mOnItemChildLongClickListener;
    private OnRefreshListener mOnRefreshListener;
    private OnLoadmoreListener mOnLoadmoreListener;
    private OnRefreshLoadmoreListener mOnRefreshLoadmoreListener;
    private int mStartPage;
    private int mPage;



    public RefreshRecycleViewManager(RefreshRecycleView view, RecyclerView.LayoutManager layoutManager) {
        mRefreshRecycleView = view;
        mRefreshRecycleView.setLayoutManager(layoutManager);
        mDataObserver = new DataObserver<>(this);
    }

    public void setEmptyView(EmptyViewCreator creator) {
        mEmptyView = creator.create();
    }

    public void setHeaderView(HeaderViewCreator creator) {
        mHeaderView = creator.create();
    }

    public void setFootView(FootViewCreator creator) {
        mFootView = creator.create();
    }

    public void setOnItemClickListener(BaseQuickAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(BaseQuickAdapter.OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemChildClickListener(BaseQuickAdapter.OnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    public void setOnItemChildLongClickListener(BaseQuickAdapter.OnItemChildLongClickListener onItemChildLongClickListene) {
        mOnItemChildLongClickListener = onItemChildLongClickListene;
    }

    public void setOnRefreshListener(final com.margin.easyrecycview.data.OnRefreshListener listener) {
        mOnRefreshListener = new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mDataObserver.setRefreshingStatus();
                listener.onRefresh(refreshlayout);
            }
        };
    }
    public void setOnLoadmoreListener(final com.margin.easyrecycview.data.OnLoadmoreListener listener) {
        mOnLoadmoreListener = new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mDataObserver.setLoadingmoreStatus();
                listener.onLoadmore(refreshlayout);
            }
        };
    }


    public void setOnRefreshLoadmoreListener(final com.margin.easyrecycview.data.OnRefreshLoadmoreListener listener) {
        mOnRefreshLoadmoreListener = new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mDataObserver.setLoadingmoreStatus();
                listener.onLoadmore(refreshlayout);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mDataObserver.setRefreshingStatus();
                listener.onRefresh(refreshlayout);
            }
        };
    }

    public void setOnFirstLoadDataListener(OnFirstLoadDataListener<T> onFirstLoadDataListener) {
        mDataObserver.setOnFirstLoadDataListener(onFirstLoadDataListener);
    }

    public void setMergeDataListener(OnMergeDataListener<T> mergeDataListener) {
        mDataObserver.setOnMergeDataListener(mergeDataListener);
    }

    public void setDatas(List<T> data) {
        mDataObserver.setData(data);
    }

    public void setAdapterCreate(AdapterCreator<T, K, A> adapterCreate) {
        mDataObserver.setAdapterCreator(adapterCreate);
    }

    public void setHoldData(boolean isHoldData) {
        mDataObserver.setHoldData(isHoldData);
    }

    public void setStartPage(int startPage) {
        mStartPage = startPage;
        mPage = startPage;
    }

    public int increasePage() {
        int page = mPage;
        mPage++;
        return page;
    }

    public void decreasePage() {
        mPage--;
    }

    public void resetPage() {
        mPage = mStartPage;
    }

    public void initView(A adapter) {
        if (mEmptyView != null) {
            adapter.setEmptyView(mEmptyView);
        }
        if (mHeaderView != null) {
            adapter.setHeaderView(mHeaderView);
        }
        if (mFootView != null) {
            adapter.setFooterView(mFootView);
        }
        if (mOnItemClickListener != null) {
            adapter.setOnItemClickListener(mOnItemClickListener);
        }
        if (mOnItemLongClickListener != null) {
            adapter.setOnItemLongClickListener(mOnItemLongClickListener);
        }
        if (mOnItemChildClickListener != null) {
            adapter.setOnItemChildClickListener(mOnItemChildClickListener);
        }
        if (mOnItemChildLongClickListener != null) {
            adapter.setOnItemChildLongClickListener(mOnItemChildLongClickListener);
        }

        if (mOnRefreshListener != null) {
            mRefreshRecycleView.setOnRefreshListener(mOnRefreshListener);
        }
        if (mOnLoadmoreListener != null) {
            mRefreshRecycleView.setOnLoadmoreListener(mOnLoadmoreListener);
        }
        if (mOnRefreshLoadmoreListener != null) {
            mRefreshRecycleView.setOnRefreshLoadmoreListener(mOnRefreshLoadmoreListener);
        }
        mRefreshRecycleView.setAdapter(adapter);
    }

    public void finishLoadmore(boolean success) {
        mRefreshRecycleView.finishLoadmore(100, success);
    }

    public void finishRefresh(boolean success) {
        mRefreshRecycleView.finishRefresh(100, success);
    }

    public DataObserver<T, K, A> getDataObserver() {
        return mDataObserver;
    }

    /**
     * 数据加载成功回调
     * @param data 新数据
     */
    public void success(List<T> data) {
        mDataObserver.success(data);
    }

    /**
     * 加载成功，但是没有更多数据了
     */
    public void end() {
        mDataObserver.end();
    }

    /**
     * 数据加载失败
     */
    public void error() {
        mDataObserver.error();
    }


}
