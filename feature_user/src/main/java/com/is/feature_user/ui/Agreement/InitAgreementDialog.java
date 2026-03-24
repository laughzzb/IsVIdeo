package com.is.feature_user.ui.Agreement;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.is.feature_user.R;
import com.is.feature_user.databinding.LayoutInitAgreementBinding;

/**
 * 首次进入app时，需要弹窗提示，是否同意相关政策
 */
public class InitAgreementDialog extends DialogFragment {


    private LayoutInitAgreementBinding binding;
    private AgreementCallback mCallback;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.layout_init_agreement, null, false);

        binding.setLifecycleOwner(getActivity());

        //同意
        binding.tvAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onAgreement();
                dismiss();
            }
        });

        //不同意
        binding.tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onExit();
                dismiss();
            }
        });
        View root = binding.getRoot();
        builder.setView(root);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        // 获取对话框的 Window 对象
        Window window = getDialog().getWindow();
        if (window != null) {
            // 设置窗口位置居中
            window.setGravity(Gravity.CENTER);
            // 设置窗口宽高（可选）
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT; // 或具体数值
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
    }

    public void setCallback(AgreementCallback mCallback) {
        this.mCallback = mCallback;
    }

    public interface AgreementCallback{
        /**
         * 同意 可以接着使用
         */
        void onAgreement();
        /**
         * 拒绝 直接退出app
         */
        void onExit();
    }
}
