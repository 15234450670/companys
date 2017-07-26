package mr.li.dance.ui.activitys.mine;

import android.view.View;
import android.widget.Toast;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.base.BaseActivity;
import mr.li.dance.utils.MyStrUtil;

/**
 *  描述: 提现页面
 * Author     : yjd
 * Date       : 2017/7/24
 */

public class WithdrawdepositActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.tixian_activity;
    }

    @Override
    public void initViews() {
       setTitle("提现");
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        String back_money = mIntentExtras.getString("back_money");
        if (!MyStrUtil.isEmpty(back_money)) {
            mDanceViewHolder.setText(R.id.tixian_money,back_money);
        }


    }

    public void btn1(View v){
        Toast.makeText(mContext, "支付宝", Toast.LENGTH_SHORT).show();
    }
    public void btn2(View v){
        Toast.makeText(mContext, "提现", Toast.LENGTH_SHORT).show();
    }
}
