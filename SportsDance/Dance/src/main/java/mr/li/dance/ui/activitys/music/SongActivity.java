package mr.li.dance.ui.activitys.music;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
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
import mr.li.dance.utils.ShareUtils;
import mr.li.dance.utils.glide.ImageLoaderManager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/16 0016
 * 描述:歌单界面
 * 修订历史:
 */
public class SongActivity extends BaseListActivity<GeDanInfo.DataBean.ListBean> {
    int page = 1;
    public static String mItemId, name;
    GeDanAdapter          mAdapter;
    MusicService.MyBinder myBinder;
    private       ImageView   iv;
    private       TextView    gd_txt;
    public static String      allTitle;
    public static String      imageUrl;
    private       ServiceConn conn;

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
        conn = new ServiceConn();
        conn.getMyBinder(new ServiceConn.binderCreateFinish() {
            @Override
            public void binderHasCreated(MusicService.MyBinder mb) {
                myBinder = mb;
                int a = myBinder.mGetPosition();
                if (a > -1) {
                    iv.setSelected(myBinder.binderIsPlaying());
                    gd_txt.setText(myBinder.mGetTitle());
                    //mAdapter.selectItem(myBinder.mGetPosition());
                }
                //myBinder.mSetMusicList(mAdapter.getmList());
                myBinder.setMs(new MusicService.MpStarted() {
                    @Override
                    public void onStart(int totalT) {
                        iv.setSelected(true);
                        gd_txt.setText(myBinder.mGetTitle());
                        mAdapter.selectItem(myBinder.mGetPosition());

                    }
                });

            }
        });
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (myBinder != null && mItemId != null && myBinder.mIsSameList(mItemId)) {
            mAdapter.selectItem(myBinder.mGetPosition());
            gd_txt.setText(myBinder.mGetTitle());
            iv.setSelected(myBinder.binderIsPlaying());

        }
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        GeDanInfo reponseResult = JsonMananger.getReponseResult(response, GeDanInfo.class);
        imageUrl = reponseResult.getData().getImg_fm();
        if (!MyStrUtil.isEmpty(reponseResult.getData().getTitle())) {
            mDanceViewHolder.setText(R.id.text_title, reponseResult.getData().getTitle());
            allTitle = reponseResult.getData().getTitle();
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
            mDanceViewHolder.setImageByUrlOrFilePaths1(R.id.img_head, R.drawable.default_video);
            ImageLoaderManager.getSingleton().LoadMoHu(this, "", mDanceViewHolder.getImageView(R.id.gd_bg), R.drawable.default_video);
        }
        findViewById(R.id.imageView4).setVisibility(View.VISIBLE);
        List<GeDanInfo.DataBean.ListBean> list = reponseResult.getData().getList();
        if (!list.isEmpty()) {
            mAdapter.addList(isRefresh, list);
            myBinder.mSetMusicList(mAdapter.getmList(), mItemId);
            //  myBinder.mSetList(mAdapter.getmList(), mItemId);
            int a = myBinder.mGetPosition();
            if (a > -1) {
                if (myBinder.mIsSameList(mItemId)) {
                    mAdapter.selectItem(myBinder.mGetPosition());
                    mRecyclerview.smoothScrollToPosition(a);
                }
            }

        } else {
            mDanceViewHolder.setViewVisibility(R.id.gd_black, View.INVISIBLE);
            Toast.makeText(mContext, "暂无信息", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void initViews() {
        super.initViews();
        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setEnableRefresh(false);
        setRightImage(R.drawable.share_icon_001);
        iv = (ImageView) findViewById(R.id.gd_bo);

        /**
         * 播放
         */
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv.setSelected(myBinder.mFirstPlay());
            }
        });

        gd_txt = mDanceViewHolder.getTextView(R.id.gd_txt);

        /**
         * 跳转到播放器
         */
        mDanceViewHolder.setClickListener(R.id.gd_tiao, new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (myBinder != null && myBinder.mGetPosition() > -1) {
                    PlayMusicActivity.lunch(SongActivity.this);
                } else {
                    Toast.makeText(mContext, "请选择要播放的歌曲", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_gedan;
    }

    public static void lunch(Context context, String mItemId, String name) {
        Intent intent = new Intent(context, SongActivity.class);
        intent.putExtra("itemid", mItemId);
        intent.putExtra("name", name);
        context.startActivity(intent);
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        if (!MyStrUtil.isEmpty(mIntentExtras)) {
            mItemId = mIntentExtras.getString("itemid");
            name = mIntentExtras.getString("name");
        }
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

    /**
     * 点击条目播放音乐
     */
    @Override
    public void itemClick(int position, GeDanInfo.DataBean.ListBean value) {
        mAdapter.selectItem(position);
        if (myBinder != null) {
            Toast.makeText(mContext, "加载中,请稍候...", Toast.LENGTH_SHORT).show();
            if (!myBinder.mIsSameList(mItemId)) {
                myBinder.mSetList(mAdapter.getmList(), mItemId);
            }
            myBinder.binderPlay(position);
            iv.setSelected(true);
        }
    }

    @Override
    public void onHeadRightButtonClick(View v) {
        super.onHeadRightButtonClick(v);
        showShareDialog();
    }

    ShareUtils mShareUtils;

    private void showShareDialog() {
        MobclickAgent.onEvent(this, AppConfigs.CLICK_EVENT_29);
        if (mShareUtils == null) {
            mShareUtils = new ShareUtils(this);
        }

        mShareUtils.showShareDilaog(AppConfigs.CLICK_EVENT_29, AppConfigs.SHAREMUSIC + mItemId, name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);

    }

}