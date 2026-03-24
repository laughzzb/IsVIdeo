package com.is.feature_find.ui.categorydetail;

import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.is.data_video.bean.ResFindCategory;

import com.is.feature_find.BR;
import com.is.feature_find.R;
import com.is.feature_find.bean.ResCategoryDetail;
import com.is.feature_find.databinding.ActivityCategoryDetailBinding;
import com.is.libbase.base.BaseActivity;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.config.ARoutePath;
import com.is.libbase.utils.StatusBarUtils;

import java.util.ArrayList;

@Route(path = ARoutePath.Find.ACTIVITY_CATEGORY_DETAIL)
public class CategoryDetailActivity extends BaseActivity<ActivityCategoryDetailBinding,CategoryDetailViewModel> {

    private static final String TAG = "CategoryDetailActivity";

    @Autowired(name = ARoutePath.Find.KEY_CATEGORY_DATA)
    ResFindCategory mDetail;

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mViewDataBinding.getRoot());
        initViewPager();
    }

    private void initViewPager() {
        //热门推荐
        Fragment recommendFragment = (Fragment) ARouter.getInstance()
                .build(ARoutePath.Video.FRAGMENT_CATEGORY_LIST)
                .withInt(ARoutePath.Video.KEY_VIDEO_CATEGORY_TYPE, ARoutePath.Video.CATEGORY_FRAGMENT_RECOMMEND)
                .withInt(ARoutePath.Video.KEY_CATEGORY_ID, mDetail.getId())
                .navigation();
        //最新发布
        Fragment publishFragment = (Fragment) ARouter.getInstance()
                .build(ARoutePath.Video.FRAGMENT_CATEGORY_LIST)
                .withInt(ARoutePath.Video.KEY_VIDEO_CATEGORY_TYPE, ARoutePath.Video.CATEGORY_FRAGMENT_NEWPUBLISH)
                .withInt(ARoutePath.Video.KEY_CATEGORY_ID, mDetail.getId())
                .navigation();

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(recommendFragment);
        fragments.add(publishFragment);

        mViewDataBinding.viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments ==null ?0: fragments.size();
            }
        });

        mViewDataBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                int rbRecommendId = mViewDataBinding.rbRecommend.getId();
                int rbNewId = mViewDataBinding.rbNew.getId();
                if (checkedId == rbRecommendId){
                    mViewDataBinding.viewPager2.setCurrentItem(0);
                    mViewDataBinding.barrier1.setVisibility(View.VISIBLE);
                    mViewDataBinding.barrier2.setVisibility(View.INVISIBLE);
                } else if (checkedId == rbNewId) {
                    mViewDataBinding.viewPager2.setCurrentItem(1);
                    mViewDataBinding.barrier1.setVisibility(View.INVISIBLE);
                    mViewDataBinding.barrier2.setVisibility(View.VISIBLE);
                }
            }
        });
    }





    @Override
    protected void initData() {
        Log.i(TAG, "initData: "+mDetail.getId()+mDetail.getName());
        //虽然mDetail已经有数据了，但是分类详情接口包含了浏览人数，参与人数等新字段
        mViewModel.requestDatas(mDetail.getId());

    }


    @Override
    protected CategoryDetailViewModel getViewModel() {
        return new ViewModelProvider(this).get(CategoryDetailViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_category_detail;
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }
}
