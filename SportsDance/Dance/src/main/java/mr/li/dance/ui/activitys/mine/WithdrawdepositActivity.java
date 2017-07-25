package mr.li.dance.ui.activitys.mine;

import android.view.View;
import android.widget.Toast;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.base.BaseActivity;

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

    /*@Override
    public void onClick(View view) {
         switch (view.getId()){
             case R.id.mine_zfb_btn:
                 Toast.makeText(mContext, "支付宝", Toast.LENGTH_SHORT).show();
                 break;
             case R.id.mine_tixian_btn:
                 Toast.makeText(mContext, "提现", Toast.LENGTH_SHORT).show();
                 break;
         }
    }*/
    public void btn1(View v){
        Toast.makeText(mContext, "支付宝", Toast.LENGTH_SHORT).show();
    }
    public void btn2(View v){
        Toast.makeText(mContext, "提现", Toast.LENGTH_SHORT).show();
    }
}
