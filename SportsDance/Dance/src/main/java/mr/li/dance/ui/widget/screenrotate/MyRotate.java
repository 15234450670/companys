package mr.li.dance.ui.widget.screenrotate;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;

/**
 * ====================================================
 * 作    者：DUCK
 * 版    本：
 * 创建日期：2018/1/13 - 21:15
 * 描    述：杨珺达项目专用
 * 修订历史：
 * ====================================================
 */

public final class MyRotate extends ScreenRotate {

    private boolean userClick = false; //是否是用户点击旋转

    public MyRotate(Context context) {
        super(context);

        //该项目需要这样的拦截器
        intercept = new RotateIntercept() {
            @Override
            public boolean isIntercept(int orientation) {
                if (userClick) {
                    if (now == r270) {
                        userClick = !isRotateLandscape(orientation) && !isRotateReverseLandscape(orientation);
                    } else if (now == r0) {
                        userClick = !isRotatePortrait(orientation);
                    }
                    return userClick;
                }
                return userClick;
            }
        };
    }

    /**
     * 项目中的 横竖屏切换
     *
     * @param activity
     */
    public void userPress(Activity activity) {
        if (now == r0) {
            userClick = true;
            now = r270;
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            if (callBack != null) {
                callBack.landscape();
            }
        } else {
            userClick = true;
            now = r0;
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            if (callBack != null) {
                callBack.portrait();
            }
        }
    }

}
