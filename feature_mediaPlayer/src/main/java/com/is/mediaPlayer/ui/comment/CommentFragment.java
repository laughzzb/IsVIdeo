package com.is.mediaPlayer.ui.comment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.is.libbase.base.BaseFragment;
import com.is.libbase.config.ARoutePath;
import com.is.mediaPlayer.BR;
import com.is.mediaPlayer.R;
import com.is.mediaPlayer.adapter.ConmentAdapter;
import com.is.data_video.bean.ResComment;
import com.is.mediaPlayer.databinding.LayoutFragmentCommentBinding;
import com.is.mediaPlayer.ui.comment.delcomment.DeleteCommentDialog;
import com.is.mediaPlayer.ui.videodetail.VideoDetailViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

@Route(path = ARoutePath.Video.FRAGMENT_COMMENT)
public class CommentFragment extends BaseFragment<LayoutFragmentCommentBinding, VideoDetailViewModel>implements ConmentAdapter.onItemClickListenner  {
    private static final String TAG = "CommentFragment";
    private ConmentAdapter mAdapter;
    private Handler mHandler; // ✅ 新增：保存Handler引用
    private OnRefreshLoadMoreListener mRefreshLoadMoreListener;

    private ConmentAdapter.onItemClickListenner mOnItemClickListener; // ✅ 新增：保存监听器引用

    public CommentFragment() {
        // 确保有默认构造函数
    }

    @Override
    protected void initData() {
        // ✅ 修改：使用正确的生命周期所有者
        mViewModel.getIsLoadMore().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoadMore) {
                if (mViewDataBinding != null && mViewDataBinding.smartRefreshLayout != null) {
                    mViewDataBinding.smartRefreshLayout.setEnableLoadMore(isLoadMore);
                }
            }
        });

        mViewModel.getComments().observe(getViewLifecycleOwner(), new Observer<List<ResComment>>() {
            @Override
            public void onChanged(List<ResComment> resComments) {
                if (mAdapter != null) {
                    mAdapter.setList(resComments);
                }
            }
        });

        // 请求评论列表数据
        mViewModel.requestComments(true);
    }

    @Override
    protected void initView() {
        // ✅ 新增：初始化Handler
        mHandler = new Handler(Looper.getMainLooper());

        mViewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ConmentAdapter();
        mViewDataBinding.recyclerView.setAdapter(mAdapter);

        mViewDataBinding.etChat.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                String text = mViewDataBinding.etChat.getText().toString().trim();
                if (text != null && text.length() > 0) {
                    mViewModel.sendComment(text);
                    mViewDataBinding.etChat.getText().clear();
                }
                return true;
            } else {
                return false;
            }
        });

        // ✅ 修改：保存监听器引用
        mOnItemClickListener = new ConmentAdapter.onItemClickListenner() {
            @Override
            public void onItemLongClick(ResComment comment) {
                if (getContext() == null || isDetached()) return;

                DeleteCommentDialog deleteCommentDialog = new DeleteCommentDialog();
                deleteCommentDialog.setComment(comment);
                deleteCommentDialog.show(getChildFragmentManager(), "DeleteCommentDialog");
            }
        };
        mAdapter.setOnItemClickListenner(mOnItemClickListener);

        // ✅ 修改：保存监听器引用
        mRefreshLoadMoreListener = new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mViewDataBinding != null && mViewDataBinding.smartRefreshLayout != null
                        && mViewDataBinding.smartRefreshLayout.isLoading()) {
                    mViewDataBinding.smartRefreshLayout.finishLoadMore();
                }
                mViewModel.requestComments(false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (mViewDataBinding != null && mViewDataBinding.smartRefreshLayout != null
                        && mViewDataBinding.smartRefreshLayout.isRefreshing()) {
                    mViewDataBinding.smartRefreshLayout.finishRefresh();
                }
                if (mViewDataBinding != null && mViewDataBinding.smartRefreshLayout != null) {
                    mViewDataBinding.smartRefreshLayout.setEnableLoadMore(true);
                }
                mViewModel.requestComments(true);
            }
        };
        mViewDataBinding.smartRefreshLayout.setOnRefreshLoadMoreListener(mRefreshLoadMoreListener);

        mViewModel.getErrorCode().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (mViewDataBinding != null && mViewDataBinding.smartRefreshLayout != null
                        && mViewDataBinding.smartRefreshLayout.isRefreshing()) {
                    mViewDataBinding.smartRefreshLayout.finishRefresh();
                }
            }
        });
    }

    @Override
    protected VideoDetailViewModel getViewModel() {
        // ✅ 修改：使用正确的ViewModel获取方式
        return new ViewModelProvider(requireActivity()).get(VideoDetailViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_fragment_comment;
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }

    /**
     * ✅ 修改：修复updateFragmentHeight方法
     * 添加空值检查和Fragment状态检查
     */
    public void updateFragmentHeight() {
        if (!isAdded() || isDetached() || getActivity() == null || getActivity().isFinishing()) {
            Log.w(TAG, "updateFragmentHeight: Fragment is not in valid state");
            return;
        }

        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }

        // 第一次延迟
        mHandler.postDelayed(() -> {
            if (mViewDataBinding == null || mViewDataBinding.getRoot() == null) {
                Log.w(TAG, "updateFragmentHeight: binding or root is null (first delay)");
                return;
            }

            try {
                Log.i(TAG, "updateFragmentHeight: " + mViewDataBinding.getRoot().getHeight());
                mViewDataBinding.getRoot().requestLayout();
            } catch (Exception e) {
                Log.e(TAG, "updateFragmentHeight error (first delay): " + e.getMessage());
            }
        }, 500);

        // 第二次延迟
        mHandler.postDelayed(() -> {
            if (mViewDataBinding == null || mViewDataBinding.getRoot() == null) {
                Log.w(TAG, "updateFragmentHeight: binding or root is null (second delay)");
                return;
            }

            try {
                Log.i(TAG, "updateFragmentHeight: " + mViewDataBinding.getRoot().getHeight());
            } catch (Exception e) {
                Log.e(TAG, "updateFragmentHeight error (second delay): " + e.getMessage());
            }
        }, 1500);
    }

    public void updateComments(List<ResComment> resComments) {
        if (mAdapter != null) {
            mAdapter.setList(resComments);
        }
    }

    // ✅ 新增：onDestroyView清理资源
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: 清理CommentFragment资源");

        // 清理Handler
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    @Override
    public void onItemLongClick(ResComment comment) {

    }
}