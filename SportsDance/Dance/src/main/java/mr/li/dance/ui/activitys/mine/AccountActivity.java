package mr.li.dance.ui.activitys.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.base.BaseListActivity;


/**
 * 作者: yjd
 * 版本: 1.0
 * 创建日期: 2017/7/24
 * 描述: 首页-我的-我的账户
 * 修订历史:
 */

public class AccountActivity extends BaseListActivity {


    @Override
    public int getContentViewId() {
        return R.layout.activity_account;
    }

    @Override
    public void initViews() {
        setTitle("账单明细");
        mHeadRightText.setVisibility(View.VISIBLE);
        mHeadRightText.setBackgroundResource(R.drawable.mine_withdrawdeposit_ref);
        mHeadRightText.setText("提现");
    }

    /**
     * 点击右边按钮
     * @param v
     */
    @Override
    public void onHeadRightButtonClick(View v) {
        super.onHeadRightButtonClick(v);
        startActivity(new Intent(this,WithdrawdepositActivity.class));
    }

    public static void lunch(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }


    @Override
    public void itemClick(int position, Object value) {

    }
    @Override
    public RecyclerView.Adapter getAdapter() {
        return null;
    }

}
