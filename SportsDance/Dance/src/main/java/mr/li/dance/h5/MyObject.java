package mr.li.dance.h5;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yolanda.nohttp.rest.Request;

import mr.li.dance.https.CallServer;
import mr.li.dance.https.HttpListener;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.HuoDongInfo;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MD5Utils;
import mr.li.dance.utils.ShareUtils;
import mr.li.dance.utils.UserInfoManager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/11/13 0013
 * 描述:
 * 修订历史:
 */
public class MyObject {
    private Context context;

    public MyObject(Context context) {
        this.context = context;
    }

    //将显示Toast和对话框的方法暴露给JS脚本调用
    @JavascriptInterface
    public void H5share(String Title, String Url) {
        Log.e("H5share", Title + "---?" + Url);
        ShareUtils shareUtils = new ShareUtils((Activity) context);
        shareUtils.wxFriendShare(AppConfigs.CLICK_EVENT_24, Url, Title, new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {

                String userId = UserInfoManager.getSingleton().getUserId(context);
                String appsecret = "32dae2ac34079322325d28cfa0825w3aa";
                String joint = userId + appsecret;
                String s = MD5Utils.md5Utils(joint);
                String sign = s.toUpperCase();
                Request<String> moneyEvent = ParameterUtils.getSingleton().getMoneyEvent(userId, sign);
                CallServer.getRequestInstance().add(context, 0, moneyEvent, new HttpListener() {
                    @Override
                    public void onSucceed(int what, String response) {
                        HuoDongInfo reponseResult = JsonMananger.getReponseResult(response, HuoDongInfo.class);
                        if (!TextUtils.isEmpty(reponseResult.getData())) {
                            Log.e("response", reponseResult.getData());
                        }
                    }

                    @Override
                    public void onFailed(int what, int responseCode, String response) {
                        Log.e("responseCode", responseCode + response);
                    }
                }, false, false);
                Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
