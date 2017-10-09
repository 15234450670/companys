package mr.li.dance.ui.activitys.mine;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.LoginActivity;
import mr.li.dance.ui.activitys.MyDanceWebActivity;
import mr.li.dance.ui.activitys.base.BaseActivity;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.UserInfoManager;
import mr.li.dance.utils.Utils;
import mr.li.dance.utils.glide.GlideCatchUtil;


/**
 * 作者: Lixuewei
 * 版本: 1.0
 * 创建日期: 2017/6/5
 * 描述: 首页-我的-设置
 * 修订历史:
 */


public class SettingActivity extends BaseActivity implements View.OnClickListener {


    @Override
    public int getContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initViews() {
        setTitle("设置");
        mDanceViewHolder.setText(R.id.vername_tv, "V " + Utils.getVersionName(this));
    }

    public static void lunch(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }

    @Override
    public void initDatas() {
        super.initDatas();
        registFinishBooadCast();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear_layout:
                clearCach();
                break;
            case R.id.updatepwd_layout:
                UpdatePwdActivity.lunch(this);
                break;
            case R.id.user_agreement:
                MyDanceWebActivity.lunch(this, MyDanceWebActivity.USER, "用户协议", AppConfigs.USER, true);
                break;
        }
    }

    public void toQuit(View view) {
        MobclickAgent.onEvent(this, AppConfigs.CLICK_EVENT_16);
        UserInfoManager.getSingleton().clearInfo(this);
        LoginActivity.lunch(this);
        finish();
    }

    /**
     * 清除缓存
     */
    private void clearCach(){
        final String cacheSize = GlideCatchUtil.getInstance().getCacheSize();
          AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("确定清除" + cacheSize + "缓存文件?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GlideCatchUtil.getInstance().cleanCatchDisk();
                        GlideCatchUtil.getInstance().clearCacheDiskSelf();
                        GlideCatchUtil.getInstance().clearCacheMemory();
                        Toast.makeText(mContext, "清除成功", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.setCanceledOnTouchOutside(false);
    }
}
