package mr.li.dance.ui.activitys.music;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.yolanda.nohttp.rest.Request;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.DanceMusic;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.adapters.DanceMusicAdapter;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/14 0014
 * 描述: 栏目界面
 * 修订历史:
 */
public class DanceMusicActivity extends BaseListActivity {
    DanceMusicAdapter adapter;
    String            mMatchId;
    String            name;

    @Override
    public void itemClick(int position, Object value) {

    }

    @Override
    public void initDatas() {
        super.initDatas();
        setTitle(name);
        refresh();

    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new DanceMusicAdapter(this);
        return adapter;
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        if (mIntentExtras != null) {
            mMatchId = mIntentExtras.getString("matchid");
            name = mIntentExtras.getString("name");
        }
    }

    @Override
    public void initViews() {
        super.initViews();
        mRefreshLayout.setEnableLoadmore(false);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerview.setLayoutManager(layoutManager);
        mRecyclerview.setAdapter(getAdapter());
    }

    @Override
    public int getContentViewId() {
        return R.layout.dancemusic_activity;
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        DanceMusic reponseResult = JsonMananger.getReponseResult(response, DanceMusic.class);
        List<DanceMusic.DataBean> data = reponseResult.getData();

        if (!data.isEmpty()) {
            adapter.addList(isRefresh, data);
        } else {
            Toast.makeText(mContext, "此歌单暂无信息", Toast.LENGTH_SHORT).show();
        }

    }

    public static void lunch(Context context, String matchid, String name) {
        Intent intent = new Intent(context, DanceMusicActivity.class);
        intent.putExtra("matchid", matchid);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }

    public static void lunch(Context context, String matchid) {
        Intent intent = new Intent(context, DanceMusicActivity.class);
        intent.putExtra("matchid", matchid);
        context.startActivity(intent);
    }

    @Override
    public void refresh() {
        super.refresh();
        Request<String> musicInfoMapWuDao = ParameterUtils.getSingleton().getMusicInfoMapWuDao(mMatchId);
        request(AppConfigs.home_music_wudao, musicInfoMapWuDao, false);
    }

    /*@Override
    public void loadMore() {
        super.loadMore();
        Request<String> musicInfoMapWuDao = ParameterUtils.getSingleton().getMusicInfoMapWuDao(mMatchId);
        request(AppConfigs.home_album, musicInfoMapWuDao, false);
    }*/
}
