package com.login.tencent;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.login.Listener;
import com.login.iface.ILogin;
import com.utils.LogUtil;

/**
 * com.tencent
 * 2019/1/21 12:56
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class TencentManager {
    private static final String TAG = "TencentManager";
    protected com.tencent.tauth.Tencent mTencent;
    private static TencentManager mManager;
    private String mAppId;
    private Context mContext;
    private ILogin mLogin;

    public static TencentManager getTencentManager() {
        return mManager;
    }

    public static TencentManager init(Context context, String appId) {
        if (mManager == null)
            synchronized (TAG) {
                if (mManager == null)
                    mManager = new TencentManager(context, appId);
            }
        return mManager;
    }

    private TencentManager(Context context, String appId) {
        mContext = context.getApplicationContext();
        mAppId = appId;
        mTencent = com.tencent.tauth.Tencent.createInstance(mAppId, mContext);
    }

    public void login(FragmentActivity activity, Listener loginListener) {
        if (mTencent.isSessionValid()) {
            LogUtil.e(TAG, "is session valid");
            return;
        }
        if (!mTencent.isQQInstalled(mContext)) {
            LogUtil.e(TAG, "没有安装qq应用将通过页面授权");
        }
        addFragment(activity,activity.getSupportFragmentManager());
        mLogin.login(loginListener);
    }

    public void login(Fragment fragment, Listener loginListener) {
        if (mTencent.isSessionValid()) {
            LogUtil.e(TAG, "is session valid");
            return;
        }
        if (!mTencent.isQQInstalled(mContext)) {
            LogUtil.e(TAG, "没有安装qq应用将通过页面授权");
        }
        addFragment(fragment.getActivity(),fragment.getChildFragmentManager());
        mLogin.login(loginListener);
    }

    private void addFragment(Activity activity,android.support.v4.app.FragmentManager manager) {
        android.support.v4.app.Fragment fragment = manager.findFragmentByTag(TAG);
        boolean isNewInstance = fragment == null;
        if (isNewInstance) {
            mLogin = new TencentImpl(activity);
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(fragment, TAG);
            if (android.os.Build.VERSION.SDK_INT >= 24)
                transaction.commitNow();
            else {
                transaction.commitAllowingStateLoss();
                manager.executePendingTransactions();
            }
        }
        mLogin = (ILogin) fragment;
    }
}
