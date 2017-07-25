package mr.li.dance.ui.activitys.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.Mine_itemInfo;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.adapters.Mine_item_adapter;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.UserInfoManager;

/**
 * 作者: yjd
 * 版本: 1.0
 * 创建日期: 2017/7/24
 * 描述: 首页-我的-我的账户
 * 修订历史:
 */

public class AccountActivity extends BaseListActivity<Mine_itemInfo> {
    Mine_item_adapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_account;
    }

    @Override
    public void initViews() {
        setTitle("账单明细");
        mRightIv.setVisibility(View.VISIBLE);
        mRightIv.setBackgroundResource(R.drawable.mine_tixian_btn);
    }

    /**
     * 点击右边按钮
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
    public RecyclerView.Adapter getAdapter() {
        adapter = new Mine_item_adapter(this);
        return adapter;
    }

    @Override
    public void initDatas() {
        super.initDatas();
        MineInfo();
    }
    public void MineInfo(){
        String userId = UserInfoManager.getSingleton().getUserInfo(this).getUserid();
        Log.e("mine_userid:",userId);
        Request<String> request = ParameterUtils.getSingleton().getTiXianInfoMap(userId, "1");
        request(AppConfigs.item_tx,request,false);
}

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e("明细",response);
        if (what==AppConfigs.item_tx) {
            Mine_itemInfo reponseResult = JsonMananger.getReponseResult(response, Mine_itemInfo.class);
              adapter.add(reponseResult);


        }
    }
    /**
     * 上啦加载
     */
    @Override
    public void loadMore() {
        super.loadMore();
    }
    /**
     * 条目点击事件
     */

    @Override
    public void itemClick(int position, Mine_itemInfo value) {

    }
}
