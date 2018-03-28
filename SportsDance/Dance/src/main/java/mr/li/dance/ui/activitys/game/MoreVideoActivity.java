package mr.li.dance.ui.activitys.game;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.yolanda.nohttp.rest.Request;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.MoreVideoResponse;
import mr.li.dance.models.Video;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.activitys.video.VideoDetailActivity;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/22 0022
 * 描述:     更多视频
 * 修订历史:
 */
public class MoreVideoActivity extends BaseListActivity {
    MoreVideoAdapter adapter;
    String           mMatchId;
    int page = 1;


    @Override
    public void getIntentData() {
        super.getIntentData();
        mMatchId = mIntentExtras.getString("matchid");
    }

    @Override
    public void initDatas() {
        super.initDatas();
        Request<String> gameMapNew = ParameterUtils.getSingleton().getGameMapVideo(mMatchId, String.valueOf(page));
        request(AppConfigs.home_album, gameMapNew, false);
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        MoreVideoResponse reponseResult = JsonMananger.getReponseResult(response, MoreVideoResponse.class);
        List<Video> data = reponseResult.getData();
        adapter.addList(isRefresh, data);
    }

    @Override
    public void initViews() {
        super.initViews();
        setTitle("比赛视频");

    }


    @Override
    public void itemClick(int position, Object value) {
        Video value1 = (Video) value;
        VideoDetailActivity.lunch(mContext, value1.getId());
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new MoreVideoAdapter(this);
        adapter.setItemClickListener(this);
        return adapter;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_list_layout;
    }

    public static void lunch(Context context, String matchId) {
        Intent intent = new Intent(context, MoreVideoActivity.class);
        intent.putExtra("matchid", matchId);
        context.startActivity(intent);
    }

    @Override
    public void refresh() {
        super.refresh();
        page = 1;
        Request<String> gameMapNew = ParameterUtils.getSingleton().getGameMapVideo(mMatchId, String.valueOf(page));
        request(AppConfigs.home_album, gameMapNew, false);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        page++;
        Request<String> gameMapNew = ParameterUtils.getSingleton().getGameMapVideo(mMatchId, String.valueOf(page));
        request(AppConfigs.home_album, gameMapNew, false);
    }


}
