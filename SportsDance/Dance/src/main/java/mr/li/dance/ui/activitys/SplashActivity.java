package mr.li.dance.ui.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;

import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.CallServer;
import mr.li.dance.https.HttpListener;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.CheckLogin;
import mr.li.dance.ui.activitys.base.DanceApplication;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.NToast;
import mr.li.dance.utils.UserInfoManager;
import mr.li.dance.utils.Utils;
import mr.li.dance.utils.isNetworkAvailable;


/**
 * 作者: Lixuewei
 * 版本: 1.0
 * 创建日期: 2017/5/16
 * 描述: 启动页面
 * 修订历史:
 */
public class SplashActivity extends Activity implements HttpListener {
    private String             TAG     = getClass().getSimpleName();
    private android.os.Handler handler = new android.os.Handler();
    private isNetworkAvailable is;

    public void initDatas() {
        is = new isNetworkAvailable();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (is.isNetwork(SplashActivity.this)) {
                    goToMain();
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    NToast.longToast(SplashActivity.this, "当前没有可用网络！");
                    finish();

                }
            }
        }, 3000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        initDatas();


    }

    private void goToMain() {
        String userId = UserInfoManager.getSingleton().getUserId(this);
        String phone_xh = Utils.getSystemModel();
        DanceApplication instance = DanceApplication.getInstance();
        String deviceToken = instance.getDeviceToken();
        String loginTime = UserInfoManager.getSingleton().getLoginTime(this);
        Log.e(TAG, "userId:" + userId);
        Log.e(TAG, "版本：" + Utils.getVersionName(this) + "Token:" + deviceToken + "型号:" + phone_xh + "时间:" + loginTime);
        if (!TextUtils.isEmpty(userId)) {
            Request<String> stringRequest = ParameterUtils.getSingleton().checkLogin(userId, Utils.getVersionName(this), "1", phone_xh, deviceToken, loginTime);
            CallServer.getRequestInstance().add(this, 0, stringRequest, this, true, true);
        } else {
            Request<String> stringRequest = ParameterUtils.getSingleton().checkLogin("", Utils.getVersionName(this), "1", phone_xh, deviceToken, "");
            CallServer.getRequestInstance().add(this, 0, stringRequest, this, true, true);
        }
    }

    @Override
    public void onSucceed(int what, String response) {
       /* Log.e(TAG, "走了");
        CheckLogin reponseResult = JsonMananger.getReponseResult(response, CheckLogin.class);
        int errorCode = reponseResult.getErrorCode();
        switch (errorCode) {
            case 101:
                Log.e(TAG, "走了101");
                break;
            case 102:
                Log.e(TAG, "走了102");
                UserInfoManager.getSingleton().saveTime(this, reponseResult.getData());
                break;
            case 103:
                Log.e(TAG, "走了103");
                UserInfoManager.getSingleton().clearInfo(this);
                break;
        }
        CheckLogin.StartPageBean startPage = reponseResult.getStartPage();
        if (!MyStrUtil.isEmpty(startPage)) {
            startActivity(new Intent(this, AdvertisingActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }*/


    }

    @Override
    public void onFailed(int what, int responseCode, String response) {
        Log.e(TAG, "走了-----" + response);
        CheckLogin reponseResult = JsonMananger.getReponseResult(response, CheckLogin.class);
        int errorCode = reponseResult.getErrorCode();
        switch (errorCode) {
            case 101:
                Log.e(TAG, "走了101");
                break;
            case 102:
                Log.e(TAG, "走了102");
                UserInfoManager.getSingleton().saveTime(this, reponseResult.getData());
                break;
            case 103:
                Log.e(TAG, "走了103");
                UserInfoManager.getSingleton().clearInfo(this);
                break;

        }
        CheckLogin.StartPageBean startPage = reponseResult.getStartPage();
        if (!MyStrUtil.isEmpty(startPage)) {
            Intent intent = new Intent(SplashActivity.this, AdvertisingActivity.class);
            intent.putExtra("type", startPage.getType());
            intent.putExtra("number", startPage.getNumber());
            intent.putExtra("title", startPage.getTitle());
            intent.putExtra("img", startPage.getImg());
            intent.putExtra("appid", startPage.getAppid());
            intent.putExtra("appsecret", startPage.getAppsecret());
            intent.putExtra("url", startPage.getUrl());

            startActivity(intent);
            finish();

        } else {

            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            finish();
        }


    }

}

