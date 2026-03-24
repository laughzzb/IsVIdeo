package com.is.libbase.base.list;

import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.is.libbase.R;
import com.is.libbase.base.BaseFragment;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.databinding.LayoutStrRecyclerviewBinding;
import com.is.network.bean.ResList;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

public abstract class BaseListFragment<T> extends BaseFragment<LayoutStrRecyclerviewBinding, BaseListViewModel> {

    private RecyclerView.Adapter mAdapter;


    @Override
    protected void initData() {
        mViewModel.requestDates(true);

        //观察到数据发生变化
        mViewModel.getDates().observe(getViewLifecycleOwner(), new Observer<ResList<T>>() {
            @Override
            public void onChanged(ResList<T> dates ) {
                //停止刷新，加载的状态
                SmartRefreshLayout smartRefreshLayout = mViewDataBinding.smartRefreshLayout;
                //告知刷新完毕，可结束刷新动画
                if (smartRefreshLayout.isRefreshing()) {
                    smartRefreshLayout.finishRefresh();
                }
                //告知加载已完成，无需加载了
                if (smartRefreshLayout.isLoading()) {
                    smartRefreshLayout.finishLoadMore();
                }
                List<T> lists = dates.getList();
                onDatesRequestSuccess(lists);
//                mAdapter.setVideos(resVideos);

            }
        });
        mViewModel.getIsLoadMore().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoadMore) {
                //是否允许接着加载
                mViewDataBinding.smartRefreshLayout.setEnableLoadMore(isLoadMore);
                if (!isLoadMore) {
                    Toast.makeText(getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void initView() {
        //初始化recyclerView
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        mViewDataBinding.recyclerView.setLayoutManager(layoutManager);
        //初始化适配器，由外部创建传进来
        mAdapter = getAdapter();
        mViewDataBinding.recyclerView.setAdapter(mAdapter);

        mViewDataBinding.smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //下拉触发这个方法
//                mViewModel.requestData(mPageType, false);
//                onLoadMoreDates(false);
                mViewModel.requestDates(false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //刷新
//                mViewModel.requestData(mPageType, true);
//                onLoadMoreDates(true);
                mViewModel.requestDates(true);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_str_recyclerview;
    }

    @Override
    protected int getBindingVariableId() {
        return 0;
    }

    protected abstract RecyclerView.Adapter getAdapter();

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    protected abstract  void onDatesRequestSuccess(List<T> list);
}
