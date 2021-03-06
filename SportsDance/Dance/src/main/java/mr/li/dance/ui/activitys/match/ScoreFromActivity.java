package mr.li.dance.ui.activitys.match;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.yolanda.nohttp.rest.Request;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.StringResponse;
import mr.li.dance.models.ScoreInfo;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.adapters.ScoreFromAdapter;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;

/**
 * 作者: Lixuewei
 * 版本: 1.0
 * 创建日期: 2017/6/5
 * 描述:  成绩查询组别对数列表页面
 * 修订历史:
 */

public class ScoreFromActivity extends BaseListActivity<ScoreInfo> {
    ScoreFromAdapter mScorefromAdapter;
    private String mGroupName;
    private String mMatchId;

    @Override
    public int getContentViewId() {
        return R.layout.list_layout;
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        mGroupName = mIntentExtras.getString("groupname");
        mMatchId = mIntentExtras.getString("matchid");
    }

    @Override
    public void initViews() {
        super.initViews();
        setTitle(mGroupName);

        Request<String> request = ParameterUtils.getSingleton().getmscoreQueryMMap(mMatchId, mGroupName, mScorefromAdapter.getNextPage());
        request(AppConfigs.match_scoreQueryM, request, true);
    }

    @Override
    public void itemClick(int position, ScoreInfo value) {

    }

    private void getData(int index) {
        Request<String> request = ParameterUtils.getSingleton().getmscoreQueryMMap(mMatchId, mGroupName, index);
        request(AppConfigs.match_scoreQueryM, request, false);
    }

    @Override
    public void refresh() {
        super.refresh();
        getData(1);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        getData(mScorefromAdapter.getNextPage());
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mScorefromAdapter = new ScoreFromAdapter(this);
        mScorefromAdapter.setItemClickListener(this);
        return mScorefromAdapter;
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e("response", response);
        StringResponse stringResponse = JsonMananger.getReponseResult(response, StringResponse.class);
        String dataStr = stringResponse.getData();
        List<ScoreInfo> list = JsonMananger.jsonToList(dataStr, ScoreInfo.class);
        mScorefromAdapter.addList(isRefresh, list);
    }


    public static void lunch(Context context, String mMatchId, String groupname) {
        Intent intent = new Intent(context, ScoreFromActivity.class);
        intent.putExtra("groupname", groupname);
        intent.putExtra("matchid", mMatchId);
        context.startActivity(intent);
    }

}
