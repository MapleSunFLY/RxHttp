package com.fly.postop.httplibrary.sign;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.fly.postop.httplibrary.R;
import com.fly.postop.httplibrary.RxHttpUtils;
import com.shangyi.android.utils.LogUtils;
import com.shangyi.android.utils.NetUtils;

import java.util.UUID;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.fly.postop.core.http.sign
 * 作    者 : FLY
 * 创建时间 : 2018/10/8
 * 描述: 获取设备信息
 */
public class DeviceInfoConfig {

    // 设备系统标示 Android 0, IOS 1
    public static String OS_TYPE_ANDROID = "android";
    public static final String TAG = "Device";

    public int appType; // 0医生 1患者 2运动

    public String appVersion;  // 10

    public String deviceModel = android.os.Build.MODEL; // 设备号 htc

    public String mobileOs = OS_TYPE_ANDROID; // 操作系统

    public String mobileOsVersion = android.os.Build.VERSION.RELEASE;// 操作系统版本号

    public String uuid; // iphone为uuid，android为mac地址

    public String carrier = "NA";//运营商（中国电信，中国移动，等）0 1 2

    public String resolution; // 分辨率  720*1280

    private static DeviceInfoConfig mDeviceInfoConfig;

    public static DeviceInfoConfig getInstance() {
        if (mDeviceInfoConfig == null) {
            mDeviceInfoConfig = new DeviceInfoConfig();
            mDeviceInfoConfig.init(RxHttpUtils.getInstance().getContext());
        }
        return mDeviceInfoConfig;
    }

    public void init(Context context) {
        this.appType = RxHttpUtils.getInstance().getContext().getResources().getInteger(R.integer.app_type);
        this.appVersion = String.valueOf(getVersionName(context));
        this.uuid = NetUtils.getMac(context.getApplicationContext());
        setDeviceId(context);
    }

    private void setDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice, tmSerial, androidId;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.carrier = tm.getSubscriberId();
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = ""
                + android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());

    }

    /**
     * @return 当前渠道 不能取纯数字的渠道号
     */

    public String getChannelName(Context context) {
        try {

            ApplicationInfo appInfo = context.getApplicationContext().getPackageManager()
                    .getApplicationInfo(context.getApplicationContext().getPackageName(),
                            PackageManager.GET_META_DATA);
            String msg = appInfo.metaData.getString("BaiduMobAd_CHANNEL");

            LogUtils.d(TAG, " CHANNEL == " + msg);
            return msg;
        } catch (Exception e) {
            e.printStackTrace();
            return "shangyichannel";
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersionName(Context context) {
        try {
            PackageManager manager = context.getApplicationContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getApplicationContext().getPackageName(),
                    0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "0.0.0.1";
        }
    }

}
