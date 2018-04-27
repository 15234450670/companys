package mr.li.dance.ui.activitys.game;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.yolanda.nohttp.rest.Request;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.MoreNewResponse;
import mr.li.dance.models.TeachInfo;
import mr.li.dance.ui.activitys.MyDanceWebActivity;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.fragments.adapter.NewLabeiAdapter;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/22 0022
 * 描述:      更多新闻
 * 修订历史:
 */
public class MoreNewActivity extends BaseListActivity {
    String mMatchId;
    int page = 1;
    NewLabeiAdapter adapter;

    @Override
    public void getIntentData() {
        super.getIntentData();
        mMatchId = mIntentExtras.getString("matchid");
    }

    @Override
    public void initDatas() {
        super.initDatas();
        Request<String> gameMapNew = ParameterUtils.getSingleton().getGameMapNew(mMatchId, String.valueOf(page));
        request(AppConfigs.home_album, gameMapNew, false);
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        MoreNewResponse reponseResult = JsonMananger.getReponseResult(response, MoreNewResponse.class);
        List<TeachInfo> data = reponseResult.getData();
        adapter.addList(isRefresh, data);
    }

    @Override
    public void initViews() {
        super.initViews();
        setTitle("相关新闻");
    }

    @Override
    public void itemClick(int position, Object value) {
        TeachInfo value1 = (TeachInfo) value;
        MyDanceWebActivity.lunch(this, MyDanceWebActivity.ZIXUNTYPE, value1.getTitle(), AppConfigs.ZixunShareUrl3 + value1.getId(), AppConfigs.ZixunShareUrl2 + value1.getId(), -1);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new NewLabeiAdapter(this);
        adapter.setItemClickListener(this);
        return adapter;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_list_layout;
    }

    public static void lunch(Context context, String matchId) {
        Intent intent = new Intent(context, MoreNewActivity.class);
        intent.putExtra("matchid", matchId);
        context.startActivity(intent);
    }

    @Override
    public void refresh() {
        super.refresh();
        page = 1;
        Request<String> gameMapNew = ParameterUtils.getSingleton().getGameMapNew(mMatchId, String.valueOf(page));
        request(AppConfigs.home_album, gameMapNew, false);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        page++;
        Request<String> gameMapNew = ParameterUtils.getSingleton().getGameMapNew(mMatchId, String.valueOf(page));
        request(AppConfigs.home_album, gameMapNew, false);

    }
}
