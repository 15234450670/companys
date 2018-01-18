package mr.li.dance.ui.widget.screenrotate;

/**
 * ====================================================
 * 作    者：DUCK
 * 版    本：
 * 创建日期：2018/1/13 - 21:03
 * 描    述：屏幕旋转回调
 * 修订历史：
 * ====================================================
 */

public interface RotateCallBack {

    /**
     * 屏幕恢复了竖屏
     */
    void portrait();

    /**
     * 屏幕旋转了90度
     */
    void reverseLandscape();

    /**
     * 屏幕旋转了270度
     */
    void landscape();
}
