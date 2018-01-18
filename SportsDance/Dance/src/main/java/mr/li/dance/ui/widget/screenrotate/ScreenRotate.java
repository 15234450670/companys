package mr.li.dance.ui.widget.screenrotate;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.OrientationEventListener;

/**
 * ====================================================
 * 作    者：DUCK
 * 版    本：
 * 创建日期：2018/1/13 - 20:30
 * 描    述：屏幕旋转基础封装类
 * 1.仅提供0度 90度 270度的旋转监听
 * 2.提供一个RotateIntercept用于拦截旋转事件
 * 3.旋转事件通过RotateCallBack来回调
 * 修订历史：
 * ====================================================
 */

public class ScreenRotate extends OrientationEventListener {

    protected int now; //选择标识
    protected final int r0 = 0;
    protected final int r90 = 90;
    protected final int r270 = 270;
    protected final int INTERVAL = 200; //转完手机后 延时多长时间再旋转
    protected boolean isR = true; //是否要旋转 旋转开关

    protected RotateIntercept intercept; //拦截器
    protected RotateCallBack callBack; //旋转回调  仅回调 0度  90度  270度

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            rotate(msg);
        }
    };

    /**
     * 旋转
     *
     * @param msg
     */
    protected void rotate(Message msg) {
        if (msg.what == r90) { //向右转90
            if (callBack != null) {
                callBack.reverseLandscape();
            }
        } else if (msg.what == r270) { //向右转270
            if (callBack != null) {
                callBack.landscape();
            }
        } else if (msg.what == r0) { //竖屏
            if (callBack != null) {
                callBack.portrait();
            }
        } else {
            //nothing
            return;
        }
        now = msg.what;
        isR = true;
    }

    /**
     * 释放资源 请先调用disable后再调用此方法
     */
    public void destory() {
        intercept = null;
        callBack = null;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    public ScreenRotate(Context context) {
        super(context);
    }

    public ScreenRotate(Context context, int rate) {
        super(context, rate);
    }

    public void setIntercept(RotateIntercept intercept) {
        this.intercept = intercept;
    }

    public void setCallBack(RotateCallBack callBack) {
        this.callBack = callBack;
    }

    public void setR(boolean r) {
        isR = r;
    }

    @Override
    public void onOrientationChanged(int orientation) {

        //旋转事件拦截器
        if (intercept != null) {
            if (intercept.isIntercept(orientation)) {
                return;
            }
        }

        //如果有锁定 则不执行以下代码
        if (!isR) {
            return;
        }

        /**
         * now有三个值
         *
         * 0 正常              ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
         * 90 手机向右转90      ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
         * 270 手机向右转了270   ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
         */
        if (now == r0) { //手机现在竖屏
            if (!screenReverseLandscape(orientation)) {
                screenLandscape(orientation);
            }
        } else if (now == r90) { //手机现在向右转了90度
            if (!screenPortrait(orientation)) {
                screenLandscape(orientation);
            }
        } else if (now == r270) { //手机现在向右转了270度
            if (!screenPortrait(orientation)) {
                screenReverseLandscape(orientation);
            }
        }
    }


    /**
     * 手机回正
     *
     * @param orientation 转的角度
     * @return true转了   false没转
     */
    protected boolean screenPortrait(int orientation) {
        if (isRotatePortrait(orientation)) {
            isR = false;
            handler.sendEmptyMessageDelayed(r0, INTERVAL);
            return true;
        }
        return false;
    }


    /**
     * 手机右转270度
     *
     * @param orientation 转的角度
     * @return true转了   false没转
     */
    protected boolean screenLandscape(int orientation) {
        if (isRotateLandscape(orientation)) {
            isR = false;
            handler.sendEmptyMessageDelayed(r270, INTERVAL);
            return true;
        }
        return false;
    }


    /**
     * 手机右转90度
     *
     * @param orientation 转的角度
     * @return true转了   false没转
     */
    protected boolean screenReverseLandscape(int orientation) {
        if (isRotateReverseLandscape(orientation)) {
            isR = false;
            handler.sendEmptyMessageDelayed(r90, INTERVAL);
            return true;
        }
        return false;
    }


    /**
     * 是否要转回竖屏
     *
     * @param orientation 转的角度
     * @return true 转  false 不转
     */
    protected boolean isRotatePortrait(int orientation) {
        return orientation > 335 || orientation < 25 && orientation > 0;
    }


    /**
     * 是否要右转270度
     *
     * @param orientation 转的角度
     * @return true 转  false 不转
     */
    protected boolean isRotateLandscape(int orientation) {
        return orientation < 290 && orientation > 200;
    }


    /**
     * 是否要右转90度
     *
     * @param orientation 转的角度
     * @return true 转  false 不转
     */
    protected boolean isRotateReverseLandscape(int orientation) {
        return orientation > 70 && orientation < 160;
    }

}
