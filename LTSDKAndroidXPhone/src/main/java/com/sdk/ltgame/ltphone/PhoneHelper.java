package com.sdk.ltgame.ltphone;

import android.app.Activity;
import android.text.TextUtils;


import com.sdk.ltgame.core.common.Target;
import com.sdk.ltgame.core.impl.OnLoginStateListener;
import com.sdk.ltgame.core.model.LoginResult;
import com.sdk.ltgame.net.manager.LoginRealizeManager;

import java.lang.ref.WeakReference;

class PhoneHelper {

    private String mPhone;
    private String mPassword;
    private String mAdId;
    private String mLoginCode;
    private int mLoginTarget;
    private WeakReference<Activity> mActivityRef;
    private OnLoginStateListener mListener;


    PhoneHelper(Activity activity, String phone, String password, String adID, String isLogin,
                OnLoginStateListener mListener) {
        this.mActivityRef = new WeakReference<>(activity);
        this.mPhone = phone;
        this.mPassword = password;
        this.mAdId = adID;
        this.mLoginCode = isLogin;
        this.mListener = mListener;
        mLoginTarget = Target.LOGIN_PHONE;

    }

    /**
     * 登录
     */
    private void login() {
        if (!TextUtils.isEmpty(mPhone) && !TextUtils.isEmpty(mPassword)) {
            LoginRealizeManager.login(mActivityRef.get(), mPhone, mPassword, mListener);
        }
    }

    /**
     * 注册
     */
    private void register() {
        if (!TextUtils.isEmpty(mPhone)) {
            LoginRealizeManager.getAuthenticationCode(mActivityRef.get(), mPhone,
                    new OnLoginStateListener() {
                        @Override
                        public void onState(Activity activity, LoginResult result) {
                            if (result != null) {
                                if (result.getResultModel() != null) {
                                    if (result.getResultModel().getData() != null) {
                                        if (result.getResultModel().getData().getAuth_code() != null) {
                                            if (!TextUtils.isEmpty(mPassword)) {
                                                LoginRealizeManager.register(mActivityRef.get(), mPhone,
                                                        Integer.valueOf(result.getResultModel().getData()
                                                                .getAuth_code()),
                                                        mPassword, mAdId, mListener);
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    });
        }

    }

    /**
     * 修改密码
     */
    private void changePass() {
        if (!TextUtils.isEmpty(mPhone)) {
            LoginRealizeManager.getAuthenticationCode(mActivityRef.get(), mPhone,
                    new OnLoginStateListener() {
                        @Override
                        public void onState(Activity activity, LoginResult result) {
                            if (result != null) {
                                if (result.getResultModel() != null) {
                                    if (result.getResultModel().getData() != null) {
                                        if (result.getResultModel().getData().getAuth_code() != null) {
                                            if (!TextUtils.isEmpty(mPassword)) {
                                                LoginRealizeManager.updatePassword(mActivityRef.get(), mPhone,
                                                        Integer.valueOf(result.getResultModel().getData()
                                                                .getAuth_code()),
                                                        mPassword, mListener);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
        }
    }

    /**
     * 登录回调
     */
    void action() {
        if (TextUtils.equals(mLoginCode, "1")) {
            register();
        } else if (TextUtils.equals(mLoginCode, "2")) {
            login();
        } else if (TextUtils.equals(mLoginCode, "3")) {
            changePass();
        }
    }
}
