package com.is.feature_home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.is.feature_home.databinding.LayoutFragmentHomeBinding;
import com.is.libbase.base.BaseFragment;
import com.is.libbase.config.ARoutePath;
import com.is.libbase.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Route(path = ARoutePath.Home.FRAGMENT_HOME)
public class HomeFragment extends BaseFragment<LayoutFragmentHomeBinding, HomeViewModel> {
    private static final String TAG = "HomeFragment";

    // Fragment引用
    private Fragment mRecommendFragment;
    private Fragment mDailyFragment;

    // 适配器和监听器引用
    private FragmentStateAdapter mFragmentAdapter;
    private ViewPager2.OnPageChangeCallback mOnPageChangeCallback;
    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener;

    // ✅ 修改：将ArrayList改为非null的List，并添加同步控制
    private List<Fragment> mFragments = Collections.synchronizedList(new ArrayList<>());

    @Override
    protected void initData() {
        // 可以在这里进行数据初始化
    }

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mViewDataBinding.getRoot());

        // 通过ARouter获取Fragment
        mRecommendFragment = (Fragment) ARouter.getInstance()
                .build(ARoutePath.Video.FRAGMENT_VIDEO_LIST)
                .withInt(ARoutePath.Video.KEY_VIDEO_LIST_TYPE, ARoutePath.Video.VIDEO_LIST_FRAGMENT_RECOMMEND)
                .navigation();

        mDailyFragment = (Fragment) ARouter.getInstance()
                .build(ARoutePath.Video.FRAGMENT_VIDEO_LIST)
                .withInt(ARoutePath.Video.KEY_VIDEO_LIST_TYPE, ARoutePath.Video.VIDEO_LIST_FRAGMENT_DAILY)
                .navigation();

        // 修改：添加null检查，确保Fragment不为null
        if (mRecommendFragment == null) {
            Log.e(TAG, "RecommendFragment is null, creating placeholder");
            mRecommendFragment = new PlaceholderFragment();
        }

        if (mDailyFragment == null) {
            Log.e(TAG, "DailyFragment is null, creating placeholder");
            mDailyFragment = new PlaceholderFragment();
        }

        // 修改：清空并重新添加Fragment
        synchronized (mFragments) {
            mFragments.clear();
            mFragments.add(mRecommendFragment);
            mFragments.add(mDailyFragment);
        }

        //  修改：使用getChildFragmentManager()而不是getActivity()
        mFragmentAdapter = new FragmentStateAdapter(getChildFragmentManager(), getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                // 关键修复：添加同步控制和null检查
                synchronized (mFragments) {
                    if (position >= 0 && position < mFragments.size()) {
                        Fragment fragment = mFragments.get(position);
                        if (fragment != null) {
                            return fragment;
                        }
                    }
                    Log.w(TAG, "createFragment: position " + position + " is invalid, returning placeholder");
                    return new PlaceholderFragment();
                }
            }

            @Override
            public int getItemCount() {
                //  关键修复：确保mFragments不为null
                if (mFragments == null) {
                    Log.w(TAG, "getItemCount: mFragments is null, returning 0");
                    return 0;
                }

                synchronized (mFragments) {
                    int count = mFragments.size();
                    Log.d(TAG, "getItemCount: returning " + count);
                    return count;
                }
            }
        };

        mViewDataBinding.viewPager2.setAdapter(mFragmentAdapter);

        // 保存ViewPager2回调引用
        mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (mViewDataBinding == null) return;

                switch (position) {
                    case 0:
                        mViewDataBinding.rgRecommend.setChecked(true);
                        break;
                    case 1:
                        mViewDataBinding.rbDaily.setChecked(true);
                        break;
                }
            }
        };
        mViewDataBinding.viewPager2.registerOnPageChangeCallback(mOnPageChangeCallback);

        // ✅ 保存RadioGroup监听器引用
        mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                if (mViewDataBinding == null || mViewDataBinding.viewPager2 == null) return;

                if (checkedId == mViewDataBinding.rgRecommend.getId()) {
                    mViewDataBinding.viewPager2.setCurrentItem(0);
                } else if (checkedId == mViewDataBinding.rbDaily.getId()) {
                    mViewDataBinding.viewPager2.setCurrentItem(1);
                }
            }
        };
        mViewDataBinding.rgIndicator.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    public static class PlaceholderFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = new View(requireContext());
            view.setBackgroundColor(Color.WHITE);
            return view;
        }
    }

    @Override
    protected HomeViewModel getViewModel() {
        return new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_fragment_home;
    }

    @Override
    protected int getBindingVariableId() {
        return 0;
    }
}