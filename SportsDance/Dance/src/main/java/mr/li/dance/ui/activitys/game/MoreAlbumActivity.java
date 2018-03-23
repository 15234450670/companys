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
import mr.li.dance.ui.activitys.album.AlbumActivity;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.fragments.adapter.NewLabeiPicAdapter;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/22 0022
 * 描述:
 * 修订历史:
 */
public class MoreAlbumActivity extends BaseListActivity {
    String mMatchId;
    int page = 1;
    NewLabeiPicAdapter adapter;

    @Override
    public void getIntentData() {
        super.getIntentData();
        mMatchId = mIntentExtras.getString("matchid");
    }

    @Override
    public void initDatas() {
        super.initDatas();
        Request<String> gameMapPic = ParameterUtils.getSingleton().getGameMapPic(mMatchId, String.valueOf(page));
        request(AppConfigs.match_share_cj, gameMapPic, false);
    }

    @Override
    public void initViews() {
        super.initViews();
        setTitle("精彩图片");
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        MoreNewResponse reponseResult = JsonMananger.getReponseResult(response, MoreNewResponse.class);
        List<TeachInfo> data = reponseResult.getData();
        adapter.addList(isRefresh, data);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        page++;
        Request<String> gameMapPic = ParameterUtils.getSingleton().getGameMapPic(mMatchId, String.valueOf(page));
        request(AppConfigs.match_share_cj, gameMapPic, false);
    }

    @Override
    public void refresh() {
        super.refresh();
        page = 1;
        Request<String> gameMapPic = ParameterUtils.getSingleton().getGameMapPic(mMatchId, String.valueOf(page));
        request(AppConfigs.match_share_cj, gameMapPic, false);
    }

    @Override
    public void itemClick(int position, Object value) {
        TeachInfo value1 = (TeachInfo) value;
        AlbumActivity.lunch(this, value1.getId(), value1.getTitle());
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new NewLabeiPicAdapter(this);
        adapter.setItemClickListener(this);
        return adapter;
    }

    public static void lunch(Context context, String matchId) {
        Intent intent = new Intent(context, MoreAlbumActivity.class);
        intent.putExtra("matchid", matchId);
        context.startActivity(intent);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_list_layout;
    }
}
