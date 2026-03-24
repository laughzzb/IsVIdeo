package com.is.mediaPlayer.ui.introduce;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.is.libbase.config.ARoutePath;
import com.is.mediaPlayer.BR;
import com.is.mediaPlayer.R;
import com.is.libbase.base.BaseFragment;
import com.is.data_video.bean.ResVideoDetail;
import com.is.mediaPlayer.databinding.LayoutFragmentIntroduceBinding;
import com.is.mediaPlayer.ui.comment.report.CommentReportPopupWindow;
import com.is.mediaPlayer.ui.videodetail.VideoDetailViewModel;
import com.is.mediaPlayer.ui.videolist.VideoListFragment;

@Route(path = ARoutePath.Video.FRAGMENT_INTRODUCE)
public class IntroduceFragment extends BaseFragment<LayoutFragmentIntroduceBinding, VideoDetailViewModel> {
    private static final String TAG = "IntroduceFragment";
    private CommentReportPopupWindow mPopupWindow;

    public IntroduceFragment() {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
         //关联fragment
        VideoListFragment fragment = (VideoListFragment) ARouter.getInstance().build(ARoutePath.Video.FRAGMENT_VIDEO_LIST)
                .withInt(ARoutePath.Video.KEY_VIDEO_LIST_TYPE, ARoutePath.Video.VIDEO_LIST_FRAGMENT_RECOMMEND)
                .withBoolean(ARoutePath.Video.KEY_VIDEO_LIST_STYLE,true)
                .navigation();

        mViewDataBinding.ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomPopupWindow();
            }
        });

        getChildFragmentManager().beginTransaction().add(mViewDataBinding.fcv.getId(), fragment).commit();

    }

    private void showBottomPopupWindow() {
        if (mPopupWindow ==null){
            AppCompatActivity activity = (AppCompatActivity) getActivity();//获取activity
            //创建实例类
            mPopupWindow = new CommentReportPopupWindow(activity);
            mPopupWindow.setOnPopupInteractionListen(new CommentReportPopupWindow.OnPopupInteractionListener() {
                @Override
                public void onSendMessage(String message) {
                    mViewModel.sendComment(message);
                }
            });
        }
        mPopupWindow.showPopup(mViewDataBinding.getRoot());//关联
    }

    @Override
    protected VideoDetailViewModel getViewModel() {
        //ViewModel 的生命周期、类型与 Activity 相同  共享viewmodel的数据
        return new ViewModelProvider(requireActivity()).get(VideoDetailViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_fragment_introduce;
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }

    public void updateFragmentHeight() {

        new Handler().postDelayed(() -> {
            Log.i(TAG, "updateFragmentHeight: " + mViewDataBinding.getRoot().getHeight());
            //重新计算当前根布局的所有父布局的大小和位置
            mViewDataBinding.getRoot().requestLayout();
        }, 500);
    }

}