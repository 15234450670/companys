package mr.li.dance.ui.activitys.mine;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.base.BaseActivity;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/2 0002
 * 描述:
 * 修订历史:
 */
public class TiXianZhongActivity extends BaseActivity {

    private String money;
    private String time;

    @Override
    public int getContentViewId() {
        return R.layout.activity_tixian_zhong;
    }

    @Override
    public void initViews() {
        setTitle("提现");
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        money = mIntentExtras.getString("money");
        time = mIntentExtras.getString("time");
    }


    @Override
    public void initDatas() {
        super.initDatas();
        mDanceViewHolder.setText(R.id.tixian_money,money);
        mDanceViewHolder.setText(R.id.tixian_time,"申请日期："+time);
    }
}
