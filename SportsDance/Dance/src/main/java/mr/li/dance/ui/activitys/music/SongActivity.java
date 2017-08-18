package mr.li.dance.ui.activitys.music;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yolanda.nohttp.rest.Request;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.GeDanInfo;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.adapters.GeDanAdapter;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.glide.ImageLoaderManager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/16 0016
 * 描述:歌单界面
 * 修订历史:
 */
public class SongActivity extends BaseListActivity {
    int page = 1;
    String       mItemId;
    GeDanAdapter mAdapter;

    @Override
    public void itemClick(int position, Object value) {

    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mAdapter = new GeDanAdapter(this, mDanceViewHolder);
        mAdapter.setItemClickListener(this);
        return mAdapter;
    }

    @Override
    public void initDatas() {
        super.initDatas();
        setTitle("歌单");
        Request<String> musicInfoGeDanMap = ParameterUtils.getSingleton().getMusicInfoGeDanMap(mItemId, String.valueOf(page));
        request(AppConfigs.home_music_gedan, musicInfoGeDanMap, false);

    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        GeDanInfo reponseResult = JsonMananger.getReponseResult(response, GeDanInfo.class);
        if (!MyStrUtil.isEmpty(reponseResult.getData().getTitle())) {
            mDanceViewHolder.setText(R.id.text_title, reponseResult.getData().getTitle());
        } else {
            mDanceViewHolder.setText(R.id.text_title, "");
        }
        if (!MyStrUtil.isEmpty(reponseResult.getData().getClick_sum())) {
            mDanceViewHolder.setText(R.id.text_count, reponseResult.getData().getClick_sum());
        } else {
            mDanceViewHolder.setText(R.id.text_count, 0 + "");
        }
        if (!MyStrUtil.isEmpty(reponseResult.getData().getImg_fm())) {
            mDanceViewHolder.setImageByUrlOrFilePaths(R.id.img_head, reponseResult.getData().getImg_fm());
            ImageLoaderManager.getSingleton().LoadMoHu_gedan(this, reponseResult.getData().getImg_fm(), mDanceViewHolder.getImageView(R.id.gd_bg));
            ImageLoaderManager.getSingleton().LoadCircle(this, reponseResult.getData().getImg_fm(), mDanceViewHolder.getImageView(R.id.gd_pic), R.drawable.icon_mydefault);
        } else {
            mDanceViewHolder.setImageByUrlOrFilePaths1(R.id.img_head, R.drawable.kaoji_defaulticon);
            ImageLoaderManager.getSingleton().LoadMoHu(this, "", mDanceViewHolder.getImageView(R.id.gd_bg), R.drawable.kaoji_defaulticon);
        }
        List<GeDanInfo.DataBean.ListBean> list = reponseResult.getData().getList();
        if (!list.isEmpty()) {
            mAdapter.addList(isRefresh, list);
            mDanceViewHolder.setText(R.id.gd_txt,list.get(0).getTitle());
        } else {
            mDanceViewHolder.setViewVisibility(R.id.gd_black, View.INVISIBLE);
            Toast.makeText(mContext, "暂无更多信息", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void initViews() {
        super.initViews();

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_gedan;
    }

    public static void lunch(Context context, String mItemId) {
        Intent intent = new Intent(context, SongActivity.class);
        intent.putExtra("itemid", mItemId);
        context.startActivity(intent);
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        mItemId = mIntentExtras.getString("itemid");
        Log.e("ml", mItemId + "");
    }

    @Override
    public void loadMore() {
        super.loadMore();
        page++;
        Request<String> musicInfoGeDanMap = ParameterUtils.getSingleton().getMusicInfoGeDanMap(mItemId, String.valueOf(page));
        request(AppConfigs.home_music_gedan, musicInfoGeDanMap, false);
    }

    @Override
    public void refresh() {
        super.refresh();
        page = 1;
        Request<String> musicInfoGeDanMap = ParameterUtils.getSingleton().getMusicInfoGeDanMap(mItemId, String.valueOf(page));
        request(AppConfigs.home_music_gedan, musicInfoGeDanMap, false);
    }
}
