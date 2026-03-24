package com.is.feature_find.ui.find;


import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.is.data_video.bean.ResFindCategory;
import com.is.feature_find.BR;
import com.is.feature_find.R;
import com.is.feature_find.bean.ResFindAnchor;
import com.is.feature_find.databinding.LayoutFragmentFindBinding;
import com.is.libbase.base.BaseFragment;
import com.is.libbase.config.ARoutePath;
import com.is.libbase.utils.StatusBarUtils;

import java.util.List;

@Route(path = ARoutePath.Find.FRAGMENT_FIND)
public class FindFragment extends BaseFragment<LayoutFragmentFindBinding,FindViewModel> implements CategoryAdapter.CategoryListenner {
    private static final String TAG = "FindFragment";
    private CategoryAdapter mCategoryAdapter;
    private AnchorAdapter mAnchorAdapter;


    @Override
    protected void initData() {
        mViewModel.loadFindData();//请求数据

        mViewModel.getCategory().observe(getViewLifecycleOwner(), new Observer<List<ResFindCategory>>() {
            @Override
            public void onChanged(List<ResFindCategory> category) {
                mCategoryAdapter.setDatas(category);
            }
        });

        mViewModel.getAnchor().observe(getViewLifecycleOwner(), new Observer<List<ResFindAnchor>>() {
            @Override
            public void onChanged(List<ResFindAnchor> anchors) {
                mAnchorAdapter.setDatas(anchors);
            }
        });

    }

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mViewDataBinding.getRoot());
        Log.i(TAG, "initView: ");

        //横向排布，一行三个
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        mViewDataBinding.rvCategory.setLayoutManager(layoutManager);

        mCategoryAdapter = new CategoryAdapter();
        mCategoryAdapter.setListenner(this);


        mViewDataBinding.rvCategory.setAdapter(mCategoryAdapter);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);//设置横向
        mViewDataBinding.rvAnchor.setLayoutManager(linearLayoutManager);
        mAnchorAdapter = new AnchorAdapter();
        mViewDataBinding.rvAnchor.setAdapter(mAnchorAdapter);

        mViewModel.getAction().observe(getViewLifecycleOwner(), action -> {
            if (action == FindViewModel.FindAction.NAVIGATION_TO_THEME_LIST) {
                //跳转到主题播单
                ARouter.getInstance().build(ARoutePath.Find.ACTIVITY_THEME_LIST).navigation();
            } else if (action == FindViewModel.FindAction.NAVIGATION_TO_TOPIC) {
                //跳转到话题播单
                ARouter.getInstance().build(ARoutePath.Find.ACTIVITY_TOPIC).navigation();
            }

        });
        mViewDataBinding.etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到搜索页
                ARouter.getInstance().build(ARoutePath.Video.ACTIVITY_SEARCH).navigation();
            }
        });

    }

    /**
     * 分类点击
     *
     * @param category 被点击的分类数据
     */
    public void onCategroyItemClick(ResFindCategory category) {
        //跳转到分类详情，并传递对应的分类id
        ARouter.getInstance().build(ARoutePath.Find.ACTIVITY_CATEGORY_DETAIL)
                .withParcelable(ARoutePath.Find.KEY_CATEGORY_DATA, category)
                .navigation();
    }

    @Override
    protected FindViewModel getViewModel() {
        return new ViewModelProvider(this).get(FindViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_fragment_find;
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }

}
