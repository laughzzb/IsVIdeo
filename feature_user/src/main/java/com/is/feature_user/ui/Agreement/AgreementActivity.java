package com.is.feature_user.ui.Agreement;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.is.feature_user.R;
import com.is.feature_user.config.UserConfig;
import com.is.feature_user.databinding.ActivityAgreementBinding;
import com.is.libbase.base.BaseActivity;
import com.is.libbase.base.BaseViewModel;
import com.is.libbase.config.ARoutePath;
import com.is.libbase.utils.StatusBarUtils;

@Route(path = ARoutePath.User.ACTIVITY_AGREEMENT)
public class AgreementActivity extends BaseActivity<ActivityAgreementBinding, BaseViewModel> {
    private final String BASE_URL = "https://titok.fzqq.fun/";
    private final String PRIVATE_URL = BASE_URL + "agreement.html";//隐私政策，隐私概要

    private final String AGREEMENT_URL = BASE_URL + "UserAgreement.html";//用户协议
    private final String USER_INFO_URL = BASE_URL + "userinfomenus.html";//隐私政策，隐私概要

    @Autowired(name = UserConfig.AgreementType.KEY_AGREEMENT_TYPE)
    public int mType;


    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mViewDataBinding.getRoot());


    }

    @Override
    protected void initData() {
        mViewModel.showLoading(true);
        mViewDataBinding.webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //页面加载结束，会触发
                mViewModel.showLoading(false);
            }
        });
        String url = AGREEMENT_URL;
        switch (mType) {
            case UserConfig.AgreementType.VALUE_AGREEMENT:
                url = AGREEMENT_URL;
                mViewDataBinding.tvTitle.setText("用户协议");
                break;
            case UserConfig.AgreementType.VALUE_PRIVATE:
            case UserConfig.AgreementType.VALUE_SIMPLE_PRIVATE:
                mViewDataBinding.tvTitle.setText("隐私政策");
                url = PRIVATE_URL;
                break;
            case UserConfig.AgreementType.VALUE_USER_INFO:
                mViewDataBinding.tvTitle.setText("个人信息收集清单");
                url = USER_INFO_URL;
                break;
        }

        mViewDataBinding.webView.loadUrl(url);

    }

    @Override
    protected BaseViewModel getViewModel() {
        return new ViewModelProvider(this).get(BaseViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_agreement;
    }

    @Override
    protected int getBindingVariableId() {
        return 0;
    }
}