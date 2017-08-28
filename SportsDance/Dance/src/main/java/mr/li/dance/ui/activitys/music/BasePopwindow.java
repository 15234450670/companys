package mr.li.dance.ui.activitys.music;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/20
 * 描述:    Popwindow基类
 * 修订历史:
 */
public abstract class BasePopwindow implements View.OnClickListener {

    protected PopupWindow ppw;
    protected Activity    context;
    protected Object      object;

    //确定按钮被点击
    public static final int BUTTON_SURE = 10022;

    //取消按钮被点击
    public static final int BUTTON_CANCEL = 10023;

    //popwindow消失的时候
    public static final int POP_DISMISS = 10024;

    //popwindow的主布局
    private View view;

    private PopAction pa;

    public interface PopAction {
        void onAction(int type, Object o);
    }

    public void setAction(PopAction pa) {
        this.pa = pa;
    }

    public BasePopwindow(Activity context) {
        this.context = context;
        int viewId = getPopwindowViewId();

        if (viewId < 1) {
            throw new IllegalStateException("请设置popwindow的布局！！");
        }

        view = LayoutInflater.from(context).inflate(viewId, null);
        this.ppw = createPop(view);

        if (ppw == null) {
            throw new IllegalStateException("请创建popwindow！！");
        }

        //popwindow消失时的监听
        this.ppw.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpaha(1.0f);
                transportAction(POP_DISMISS, object);
                object = null;
            }
        });

        init();
        initPopwindowView();
        initPopwindowData();
    }

    protected PopupWindow createPop(View view) {
        return new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    //显示popwindow在某个view的中间
    public void showPopwindowMiddle(View parent) {
        showPopwindow(parent, Gravity.CENTER);
    }

    //显示popwindow
    public void showPopwindow(View parent, int gravity) {
        showPopwindow(parent, gravity, 0, 0);
    }

    public void showBottom() {
        View view = context.findViewById(android.R.id.content);
        showPopwindow(view, Gravity.BOTTOM, 0, 0);
    }

    //在那个控件下显示
    public void showPopwindowBelow(View view) {
        ppw.showAsDropDown(view);
    }

    //显示popwindow
    public void showPopwindow(View parent, int gravity, int x, int y) {
        initDataBeforeShow();
        backgroundAlpaha(0.5f);

        //关闭软键盘
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);

        ppw.showAtLocation(parent, gravity, x, y);
    }

    //显示popwindow在屏幕中间
    public void showPopwindowInScreenMiddle() {
        View view = context.findViewById(android.R.id.content);
        showPopwindowMiddle(view);
    }

    //隐藏popwindow
    public void dimissPopwindow() {
        ppw.dismiss();
    }

    //初始化
    private void init() {
        ppw.setOutsideTouchable(true);
        ppw.setFocusable(true);
        //透明的背景
        ColorDrawable dw = new ColorDrawable();
        ppw.setBackgroundDrawable(dw);
    }

    //找寻控件
    protected View findPopViewById(int resourceId) {
        return view.findViewById(resourceId);
    }

    //给控件添加监听
    protected void addClickListener(View view) {
        view.setOnClickListener(this);
    }

    //接口回调
    protected void transportAction(int type, Object o) {
        if (pa != null) {
            pa.onAction(type, o);
        }
    }

    //结束释放资源
    public void destroy() {
        context = null;
        ppw = null;
        pa = null;
        view = null;
        object = null;
    }

    //改变屏幕背景的透明度
    protected void backgroundAlpaha(float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        click(v.getId());
    }

    //获取popwindow主界面布局的资源id
    protected abstract int getPopwindowViewId();

    //初始化Popwindow视图
    protected abstract void initPopwindowView();

    //初始化Popwindow的固定数据
    protected abstract void initPopwindowData();

    //点击事件
    protected abstract void click(int viewId);

    //显示之前动态设定数据
    protected abstract void initDataBeforeShow();

}
