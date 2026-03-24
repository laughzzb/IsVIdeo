package com.is.mediaPlayer.ui.videodetail;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.is.libbase.base.BaseActivity;
import com.is.libbase.config.ARoutePath;
import com.is.libbase.evevntbus.MessageEvent;
import com.is.libbase.utils.StatusBarUtils;
import com.is.mediaPlayer.BR;
import com.is.data_video.bean.ResVideoDetail;
import com.is.mediaPlayer.R;
import com.is.mediaPlayer.databinding.ActivityVideoDetailBinding;
import com.is.mediaPlayer.player.MediaPLayerManager;
import com.is.mediaPlayer.ui.comment.CommentFragment;
import com.is.mediaPlayer.ui.introduce.IntroduceFragment;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhengsr.tablib.view.adapter.TabFlowAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

@Route(path = ARoutePath.Video.ACTIVITY_VIDEODETAIL)
public class VideoDetailActivity extends BaseActivity<ActivityVideoDetailBinding, VideoDetailViewModel> {

    private static final String TAG = "VideoDetailActivity";

    // ✅ 新增：跟踪 EventBus 注册状态
    private boolean isEventBusRegistered = false;

    @Autowired(name = ARoutePath.Video.KEY_VIDEO_ID)
    public int mVideoId;
    private IntroduceFragment mIntroduceFragment;
    private CommentFragment mCommentFragment;
    private MediaPLayerManager mPLayerManager;

    //使用singleTask等,共用一个activity会触发，用来传递数据
    @Override
    public void onNewIntent(@NonNull Intent intent){
        super.onNewIntent(intent);
        //接收到新的视频id后赋值
        mVideoId = intent.getIntExtra(ARoutePath.Video.KEY_VIDEO_ID,0);
        mViewModel.requestDetail(mVideoId);
        //切换视频后，滚动到最顶上。增加用户体验
        mViewDataBinding.nsl.scrollTo(0,0);
    }

    //ui处理
    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mViewDataBinding.getRoot());
        initViewPage();
        initTab();
        initPlayer();

        mViewModel.getAction().observe(this, new Observer<VideoDetailViewModel.Action>() {
            @Override
            public void onChanged(VideoDetailViewModel.Action action) {
                if (action == VideoDetailViewModel.Action.Share){
                    new ShareAction(VideoDetailActivity.this)
                            .setDisplayList(SHARE_MEDIA.WXWORK,SHARE_MEDIA.QQ)//传入平台
                            .withText("hello")//分享内容
                            .setCallback(new UMShareListener() {
                                @Override
                                public void onStart(SHARE_MEDIA shareMedia) {

                                }

                                @Override
                                public void onResult(SHARE_MEDIA shareMedia) {

                                }

                                @Override
                                public void onError(SHARE_MEDIA shareMedia, Throwable throwable) {

                                }

                                @Override
                                public void onCancel(SHARE_MEDIA shareMedia) {

                                }
                            })//回调监听器
                            .open();
                }
            }
        });
    }

    private void initPlayer() {
        //初始化成功。获取播放信息后在开始播放
        mPLayerManager = MediaPLayerManager.getInstance(this);
        mPLayerManager.bindPLayerView(mViewDataBinding.playerView);
    }

    private void initTab() {
        mViewDataBinding.tabLayout.setViewPager(mViewDataBinding.viewPager);

        ArrayList titles = new ArrayList();
        titles.add("简介");
        titles.add("评论");
        mViewDataBinding.tabLayout.setAdapter(new TabFlowAdapter(titles));

    }


    //添加fragment
    private void initViewPage() {
        mIntroduceFragment = (IntroduceFragment) ARouter.getInstance()
                .build(ARoutePath.Video.FRAGMENT_INTRODUCE)
                .navigation();
        mCommentFragment = (CommentFragment) ARouter.getInstance()
                .build(ARoutePath.Video.FRAGMENT_COMMENT)
                .navigation();

        ArrayList<Fragment> fragments = new ArrayList<>();
        // 检查Fragment是否为null
        if (mIntroduceFragment != null) {
            fragments.add(mIntroduceFragment);
        } else {
            // 如果ARouter返回null，直接创建实例
            fragments.add(new IntroduceFragment());
        }

        if (mCommentFragment != null) {
            fragments.add(mCommentFragment);
        } else {
            fragments.add(new CommentFragment());
        }

        mViewDataBinding.viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments == null ? 0 : fragments.size();
            }
        });

        mViewDataBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //再切换fragment的时候计算对应fragment的高度
                if (position == 0){
                    mIntroduceFragment.updateFragmentHeight();
                }else if(position== 1) {
                    mCommentFragment.updateFragmentHeight();
                }
            }
        });
        //指定viewpager预加载的页面数量,不预先加载会跳转到第二个fragment会为空
//        mViewDataBinding.viewPager.setOffscreenPageLimit(fragments.size());
    }

    //数据处理
    @Override
    protected void initData() {
        Log.i(TAG, "initData: mVideoId" + mVideoId);

        mViewModel.requestDetail(mVideoId);
        //获取到播放信息
        mViewModel.getVideoInfo().observe(this, new Observer<ResVideoDetail.ArchivesInfoBean>() {
            @Override
            public void onChanged(ResVideoDetail.ArchivesInfoBean archivesInfoBean) {
                //视频播放地址
                String url = archivesInfoBean.getVideo_file();
                mPLayerManager.play(url);
            }
        });
    }
    /**
     * 通过eventBus订阅 登录状态变化的消息
     *
     * @param event
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent.LoginStatusEvent event) {
        boolean login = event.isLogin();
        Log.i(TAG, "onMessageEvent: event" + login);
        //登录状态如果发生变化，重新请求数据，目的是为了刷新点赞、收藏的状态
        mViewModel.requestDetail(mVideoId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Activity进入前台，恢复播放
        mPLayerManager.setPlayWhenReady(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPLayerManager.setPlayWhenReady(false);

        // ✅ 新增：在 onStop 中也注销 EventBus
        if (isEventBusRegistered) {
            try {
                EventBus.getDefault().unregister(this);
                isEventBusRegistered = false;
                Log.i(TAG, "EventBus unregistered in onStop");
            } catch (Exception e) {
                Log.e(TAG, "Failed to unregister EventBus in onStop: " + e.getMessage());
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // ✅ 修改：安全注销 EventBus
        if (isEventBusRegistered) {
            try {
                EventBus.getDefault().unregister(this);
                isEventBusRegistered = false;
                Log.i(TAG, "EventBus unregistered in onDestroy");
            } catch (Exception e) {
                Log.e(TAG, "Failed to unregister EventBus in onDestroy: " + e.getMessage());
            }
        }

        mPLayerManager.release();
    }

    @Override
    protected VideoDetailViewModel getViewModel() {
        return new ViewModelProvider(this).get(VideoDetailViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_video_detail;
    }

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }
}