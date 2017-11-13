package mr.li.dance.ui.activitys.album;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import mr.li.dance.ui.activitys.MyDanceWebActivity;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.ShareUtils;

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
    public void showToast() {

        ShareUtils shareUtils = new ShareUtils((Activity) context);

        shareUtils.wxFriendShare(AppConfigs.CLICK_EVENT_24, AppConfigs.money_chou, "红包活动", new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {

                Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
                ((Activity) context).finish();
                //跳转
                MyDanceWebActivity.lunch(context, MyDanceWebActivity.OTHERTYPE, "哈哈", AppConfigs.money_chou, false);

            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
            }
        });

    }

}
