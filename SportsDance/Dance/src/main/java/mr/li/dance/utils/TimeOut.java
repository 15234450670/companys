package mr.li.dance.utils;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/10 0010
 * 描述:
 * 修订历史:
 */
public class TimeOut {

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 2000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {

            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
