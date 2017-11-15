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

import java.text.SimpleDateFormat;
import java.util.HashMap;

import mr.li.dance.https.CallServer;
import mr.li.dance.https.HttpListener;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.HuoDongInfo;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MD5Utils;
import mr.li.dance.utils.ShareUtils;
import mr.li.dance.utils.UserInfoManager;
import mr.li.dance.utils.updater.SpUtils;

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
                Log.e("sign", sign);

                final SpUtils instance = SpUtils.getInstance(context);

                String huodong_url = instance.getString("huodong_url", "");
                String id = activityId(huodong_url);
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = sDateFormat.format(new java.util.Date());
                Log.e("huodong_url_id", id);
                Log.e("huodong_url_time", date);
                Request<String> moneyEvent = ParameterUtils.getSingleton().getMoneyEvent(userId, sign, id, date);
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

    private String activityId(String url) {
        final String[] m1 = url.split("\\?");
        final String m2 = m1[1];
        final String[] m3 = m2.split("&");
        HashMap<String, String> map = new HashMap<String, String>();

        for (String k : m3) {
            final String[] arr = k.split("=");
            map.put(arr[0], arr[1]);
        }

        String target = map.get("activityId");
        if (TextUtils.isEmpty(target)) {
            return null;
        } else {
            return target;
        }
    }

}
