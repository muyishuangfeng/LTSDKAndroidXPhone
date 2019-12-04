package com.sdk.ltgame.ltphone;

import android.app.Activity;
import android.content.Context;

import com.sdk.ltgame.core.common.LTGameOptions;
import com.sdk.ltgame.core.common.LTGameSdk;
import com.sdk.ltgame.core.common.Target;
import com.sdk.ltgame.core.impl.OnLoginStateListener;
import com.sdk.ltgame.core.impl.OnRechargeListener;
import com.sdk.ltgame.core.model.LoginObject;
import com.sdk.ltgame.core.model.RechargeObject;
import com.sdk.ltgame.core.platform.AbsPlatform;
import com.sdk.ltgame.core.platform.IPlatform;
import com.sdk.ltgame.core.platform.PlatformFactory;
import com.sdk.ltgame.core.util.LTGameUtil;
import com.sdk.ltgame.ltphone.uikit.PhoneActionActivity;


public class PhonePlatform extends AbsPlatform {


    private PhonePlatform(Context context, String appId, String appKey, boolean isServerTest,
                          String phone, String password, String loginCode, int target) {
        super(context, appId, appKey, isServerTest, phone, password, loginCode, target);
    }


    @Override
    public void login(Activity activity, int target, LoginObject object, OnLoginStateListener listener) {
        PhoneHelper mHelper = new PhoneHelper(activity, object.getmPhone(), object.getmPassword(),
                object.getmAdID(), object.getmLoginCode(), listener);
        mHelper.action();

    }

    @Override
    public void recharge(Activity activity, int target, RechargeObject object, OnRechargeListener listener) {

    }

    /**
     * 工厂类
     */
    public static class Factory implements PlatformFactory {

        @Override
        public IPlatform create(Context context, int target) {
            IPlatform platform = null;
            LTGameOptions options = LTGameSdk.options();
            if (!LTGameUtil.isAnyEmpty(options.getLtAppKey(), options.getLtAppId(),
                    options.getAdID(), options.getmPhone(), options.getmPassword(), options.getmLoginCode())) {
                platform = new PhonePlatform(context, options.getLtAppId(), options.getLtAppKey(),
                        options.getISServerTest(), options.getmPhone(), options.getmPassword(), options.getmLoginCode(), target);
            }
            return platform;
        }

        @Override
        public int getPlatformTarget() {
            return Target.PLATFORM_PHONE;
        }

        @Override
        public boolean checkLoginPlatformTarget(int target) {
            return target == Target.LOGIN_PHONE;
        }

        @Override
        public boolean checkRechargePlatformTarget(int target) {
            return false;
        }
    }

    @Override
    public Class getUIKitClazz() {
        return PhoneActionActivity.class;
    }
}
