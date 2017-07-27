package mr.li.dance.ui.activitys.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.Mine_itemInfo;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.adapters.Mine_item_adapter;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.UserInfoManager;

/**
 * 作者: yjd
 * 版本: 1.0
 * 创建日期: 2017/7/24
 * 描述: 首页-我的-我的账户
 * 修订历史:
 */

public class AccountActivity extends BaseListActivity {
    Mine_item_adapter adapters;
    private Mine_itemInfo reponseResult;
    int page = 0;

    public void initViewss() {
        setTitle("账单明细");
        mRightIv.setVisibility(View.VISIBLE);
        mRightIv.setBackgroundResource(R.drawable.mine_tixian_btn);
    }

    @Override
    public void initDatas() {

        super.initDatas();
        initViewss();
        MineInfo();
    }
    @Override
    public RecyclerView.Adapter getAdapter() {
        adapters = new Mine_item_adapter(this);
        return adapters;
    }


    @Override
    public int getContentViewId() {

        return R.layout.activity_account;
    }
    /**
     * 点击右边按钮
     */
    @Override
    public void onHeadRightButtonClick(View v) {
        super.onHeadRightButtonClick(v);
        Intent intent=new Intent(this,WithdrawdepositActivity.class);
        String remaining_sum = reponseResult.getData().getRemaining_sum();
        if (!MyStrUtil.isEmpty(remaining_sum)) {
            intent.putExtra("back_money",reponseResult.getData().getRemaining_sum());
            startActivity(intent);

        }
    }

    public static void lunch(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }


    /**
     * 请求
     */
    public void MineInfo(){
        String userId = UserInfoManager.getSingleton().getUserId(mContext);
        Log.e("mine_userid:",userId);
        Request<String> request = ParameterUtils.getSingleton().getTiXianInfoMap(userId, page+"");
        request(AppConfigs.item_tx,request,false);

}

    @Override
    public void onSucceed(int what, String response) {

        Log.e("明细",response);
        if (what==AppConfigs.item_tx&&!MyStrUtil.isEmpty(response)) {
            reponseResult = JsonMananger.getReponseResult(response, Mine_itemInfo.class);
            Log.e("url",reponseResult.getErrorCode()+"");
            mDanceViewHolder.setText(R.id.mines_money, reponseResult.getData().getRemaining_sum());
            adapters.add(reponseResult);
        } else{
            Toast.makeText(mContext, "您未参与活动", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 上啦加载
     */
    @Override
    public void loadMore() {
        super.loadMore();
          page++;
        MineInfo();
    }

    /**
     * 刷新
     */
    @Override
    public void refresh() {
        super.refresh();

    }

    /**
     * 条目点击事件
     */

    @Override
    public void itemClick(int position, Object value) {

    }
}
