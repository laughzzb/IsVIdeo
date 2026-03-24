package com.is.feature_user.api;

import com.is.feature_user.bean.ReqMobileLogin;
import com.is.feature_user.bean.ReqUpdateUserProfile;
import com.is.feature_user.bean.ResLogin;
import com.is.feature_user.bean.ResResetPwd;
import com.is.feature_user.bean.ResSendSmsCode;
import com.is.feature_user.bean.ResUpload;
import com.is.libbase.manager.ResUser;
import com.is.network.bean.ResBase;

import java.io.File;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserApiService {
    /**
     * 请求获取验证码
     *
     * @param code
     * @return
     */
    @POST("addons/cms/api.sms/send")
    Call<ResBase<ResBase>> sendSmsCode(@Body ResSendSmsCode code);

    /**
     * 通过手机号登录
     *
     * @return
     */
    @POST("addons/cms/api.login/mobilelogin")
    Call<ResBase<ResLogin>> mobileLogin(@Body ReqMobileLogin login);

    /**
     * 获取用户信息
     * @return
     */
    @GET("addons/cms/api.user/userInfo")
    Call<ResBase<ResUser>> getUserInfo(@Query("user_id") String userId, @Query("type") String type);
    /**
     * 修改用户信息
     * @return
     */
    @POST("addons/cms/api.user/profile")
    Call<ResBase<Void>> updateUserProfile(@Header("token") String token, @Body ReqUpdateUserProfile profile);

    /**
     * 重置密码
     *
     * @param code
     * @return
     */
    @POST("addons/cms/api.login/resetpwd")
    Call<ResBase<ResBase>> resetPassword(@Header("token")String token, @Body ResResetPwd code);

    /**
     * 退出登录
     * @param token
     */
    @POST("addons/cms/api.user/logout")
    Call<ResBase<ResBase>> logout(@Header("token")String token);

    /**
     * 上传头像
     * @param token
     * @param file
     * @return
     */
    @Multipart //标识这个请求是一个Multipart/form-data 表单提交请求
    @POST("api/common/upload")
    Call<ResBase<ResUpload>> uploadFile(@Header("token")String token,@Part MultipartBody.Part file);


}
