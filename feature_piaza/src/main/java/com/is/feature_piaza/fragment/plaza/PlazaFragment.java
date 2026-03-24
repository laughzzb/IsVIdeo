package com.is.feature_piaza.fragment.plaza;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.is.data_video.bean.ResFindCategory;
import com.is.feature_piaza.R;
import com.is.feature_piaza.adaper.PlazaAdapter;
import com.is.feature_piaza.bean.ResPlaza;
import com.is.feature_piaza.databinding.LayoutFragmentPlazaBinding;
import com.is.libbase.base.BaseFragment;
import com.is.libbase.config.ARoutePath;
import com.is.libbase.utils.StatusBarUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

@Route(path = ARoutePath.Plaza.FRAGMENT_PLAZA)
public class PlazaFragment extends BaseFragment<LayoutFragmentPlazaBinding, PlazaViewModel> {

    private PlazaAdapter mAdapter;



    @Override
    protected void initData() {

        mViewModel.getDates().observe(getViewLifecycleOwner(), new Observer<List<ResPlaza>>() {
            @Override
            public void onChanged(List<ResPlaza> data) {
                if (mViewDataBinding.layoutRecycler.smartRefreshLayout.isRefreshing()){
                    mViewDataBinding.layoutRecycler.smartRefreshLayout.finishRefresh();
                }
                //数据请求成功
                mAdapter.setDatas(data);
            }
        });
        mViewModel.getErrorCode().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (mViewDataBinding.layoutRecycler.smartRefreshLayout.isRefreshing()){
                    mViewDataBinding.layoutRecycler.smartRefreshLayout.finishRefresh();
                }
            }
        });
    mViewModel.requestDates();//请求数据

    }

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mViewDataBinding.getRoot());

        RecyclerView recyclerView = mViewDataBinding.layoutRecycler.recyclerView;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        //每一行怎么占据空间
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 ){
                    return 2; //如果是第一行，那就独占1行
                }else {
                    return 1;//如果是普通的item类型，那就2个time占一行
                }

            }
        });
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new PlazaAdapter();
        recyclerView.setAdapter(mAdapter);
        SmartRefreshLayout smartRefreshLayout = mViewDataBinding.layoutRecycler.smartRefreshLayout;
        //不需要加载更多
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.requestDates();//请求数据
            }
        });


        mAdapter.setListener(new PlazaAdapter.PlazaItemListener() {
            @Override
            public void onBannerClick(ResPlaza.PlazaDetail detail) {
                if (getContext() == null || isDetached()) return;

                ResFindCategory category = new ResFindCategory();
                //最好做非判断
                //Activity_CATEGORY_DETAIL页面需要ResFindCategory类型，因此在这里做数据类型转换
                category.setId(detail.getId());
                category.setDescription(detail.getDescription());
                category.setFullurl(detail.getFullurl());
                category.setIcon(detail.getIcon());
                category.setName(detail.getName());
                category.setImage(detail.getImage());
                category.setUrl(detail.getUrl());
                //跳转到分类详情，并传递对应的分类id
                ARouter.getInstance().build(ARoutePath.Find.ACTIVITY_CATEGORY_DETAIL)
                        .withParcelable(ARoutePath.Find.KEY_CATEGORY_DATA,category)
                        .navigation();

            }

            @Override
            public void onImageClick(ResPlaza.PlazaDetail detail) {

            }
        });

    }



    @Override
    protected PlazaViewModel getViewModel() {
        return new ViewModelProvider(this).get(PlazaViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_fragment_plaza;
    }

    @Override
    protected int getBindingVariableId() {
        return 0;
    }

}
