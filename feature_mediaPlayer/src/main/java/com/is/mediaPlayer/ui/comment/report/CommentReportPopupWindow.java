package com.is.mediaPlayer.ui.comment.report;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.is.mediaPlayer.R;
import com.is.mediaPlayer.databinding.LayoutCommentReportBinding;
import com.is.mediaPlayer.databinding.LayoutFragmentCommentBinding;
import com.is.mediaPlayer.ui.videodetail.VideoDetailViewModel;

/**
 * 回复窗口
 * 只显示弹窗和UI的交互
 */
public class CommentReportPopupWindow extends PopupWindow {


    private LayoutCommentReportBinding binding;
    private OnPopupInteractionListener listener;

    public CommentReportPopupWindow(AppCompatActivity activity) {
        super(activity);
        init(activity);
    }

    public void init(AppCompatActivity activity) {
        VideoDetailViewModel viewModel = new ViewModelProvider(activity).get(VideoDetailViewModel.class);
        //使用DataBinding 方式加载布局
        binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.layout_comment_report,
                null, false);
        binding.setLifecycleOwner(activity);
        binding.setViewModel(viewModel);//设置viewModel

        //设置PopupWindow内容视图
        setContentView(binding.getRoot());
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //获取焦点，接收用户的输入事件
        setFocusable(true);
        //允许用户在PopupWindow外部触摸屏幕，触摸外部后直接关闭弹窗
        setOutsideTouchable(true);
        //自动调整弹窗的大小，以确保能完整显示
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //避免默认背景导致PopupWindow大小受限，影响沾满屏幕效果
        setBackgroundDrawable(null);

//        //绑定事件 点赞
//        binding.ivLike.setOnClickListener(v -> {
//            if (listener != null) listener.onLikeClicked();
//        });
//        //收藏
//        binding.ivCollect.setOnClickListener(v -> {
//            if (listener != null) listener.onCollectionClicked();
//        });

        binding.etChat.setOnEditorActionListener((v, actionId, event) -> {
            //如果用户按下输入法上的发送
            if (actionId == EditorInfo.IME_ACTION_SEND){
                String text = binding.etChat.getText().toString().trim();
                if (text!=null && text.length()>0){
                    if (listener !=null)listener.onSendMessage(binding.etChat.getText().toString());//丢给外部

                    binding.etChat.getText().clear();//清空输入框
                }
                return true;
            }
            return false;
        });


    }
    /**
     * 显示PopupWindow
     */
    public void showPopup(View anchor) {
        //在整个布局的底部显示
        showAtLocation(anchor, android.view.Gravity.BOTTOM, 0, 0);
    }

    /**
     * 设置监听器
     *
     * @param listener
     */
    public void setOnPopupInteractionListen(OnPopupInteractionListener listener) {
        this.listener = listener;
    }

    /**
     * 回调交互
     */
    public interface OnPopupInteractionListener {
        //点赞
//        void onLikeClicked();
//
//        //收藏
//        void onCollectionClicked();

        //发送评论
        void onSendMessage(String message);
    }

}
