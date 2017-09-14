package mr.li.dance.ui.activitys.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yolanda.nohttp.rest.Request;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.CallServer;
import mr.li.dance.https.HttpListener;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.Bound_ZFB_state;
import mr.li.dance.models.Mine_itemInfo;
import mr.li.dance.models.TiXianStateInfo;
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
    int page = 1;
    private TiXianStateInfo state;
    private int             start;
    private Mine_itemInfo   reponseResult;
    // private List<Mine_itemInfo.DataBean.DetailBean> detail;
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
        Bound();
        TiXianState();


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

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        Bound();
        TiXianState();
    }

    /**
     * 点击右边按钮
     */
    @Override
    public void onHeadRightButtonClick(View v) {
        super.onHeadRightButtonClick(v);
        Delivery();
    }
    private void Delivery() {
        if (state.getData().getStart() == 1) {
            Intent intent = new Intent(this, TiXianZhongActivity.class);
            intent.putExtra("money", state.getData().getMoney());
            intent.putExtra("time", state.getData().getTime());
            startActivity(intent);

        } else {
            String remaining_sum = reponseResult.getData().getRemaining_sum();
            if (!MyStrUtil.isEmpty(remaining_sum)) {
                Intent intent = new Intent(this, WithdrawdepositActivity.class);
                intent.putExtra("back_money", reponseResult.getData().getRemaining_sum());
                intent.putExtra("start", start);
                startActivity(intent);
                        finish();
            }
        }
    }

    public static void lunch(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    /**
     * 请求
     */
    public void MineInfo() {
        String userId = UserInfoManager.getSingleton().getUserId(mContext);
        Log.e("mine_userid:", userId);
        Request<String> request = ParameterUtils.getSingleton().getTiXianInfoMap(userId, page + "");
        Log.e("page", page + "");
        request(AppConfigs.item_tx, request, false);
    }


    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e("明细+++++++:", response);
        switch (what) {
            case AppConfigs.item_tx:
                reponseResult = JsonMananger.getReponseResult(response, Mine_itemInfo.class);
                Log.e("ErrorCode", reponseResult.getErrorCode() + "");
                mDanceViewHolder.setText(R.id.mines_money, reponseResult.getData().getRemaining_sum());
                List<Mine_itemInfo.DataBean.DetailBean> detail = reponseResult.getData().getDetail();

                if (!detail.isEmpty()) {
                    adapters.addList(isRefresh, detail);
                } else {
                    Toast.makeText(mContext, "暂无更多信息", Toast.LENGTH_SHORT).show();
                }

                break;
            case AppConfigs.Bound_state:

                Bound_ZFB_state reponseResults = JsonMananger.getReponseResult(response, Bound_ZFB_state.class);
                start = reponseResults.getData().getStart();
                break;
        }

    }

    /**
     * 提现状态
     */
    private void TiXianState() {
        String userId = UserInfoManager.getSingleton().getUserId(this);
        Request<String> tiXian_state = ParameterUtils.getSingleton().getTiXian_state(userId);
        CallServer.getRequestInstance().add(this, 0, tiXian_state, new HttpListener() {
            @Override
            public void onSucceed(int what, String response) {

                state = JsonMananger.getReponseResult(response, TiXianStateInfo.class);
            }

            @Override
            public void onFailed(int what, int responseCode, String response) {

            }
        }, true, true);

    }

    /**
     * 绑定状态
     */
    public void Bound() {
        String userId = UserInfoManager.getSingleton().getUserId(mContext);
        Request<String> bound_state = ParameterUtils.getSingleton().getBound_state(userId);
        request(AppConfigs.Bound_state, bound_state, false);
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
     * 下拉刷新
     */
    @Override
    public void refresh() {
        super.refresh();
        page = 1;
        MineInfo();

    }

    /**
     * 条目点击事件
     */

    @Override
    public void itemClick(int position, Object value) {
    }
}
