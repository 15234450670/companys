package mr.li.dance.ui.activitys.video;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lecloud.sdk.constant.PlayerEvent;
import com.lecloud.sdk.constant.PlayerParams;
import com.lecloud.sdk.constant.StatusCode;
import com.lecloud.sdk.videoview.IMediaDataVideoView;
import com.lecloud.sdk.videoview.VideoViewListener;
import com.lecloud.skin.ui.utils.TimerUtils;
import com.lecloud.skin.videoview.live.UIActionLiveVideoView;
import com.umeng.analytics.MobclickAgent;
import com.yolanda.nohttp.rest.Request;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.MenuBean;
import mr.li.dance.models.ZhiBo;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.activitys.match.MatchDetailActivity;
import mr.li.dance.ui.adapters.DirectseedSpeedAdapter;
import mr.li.dance.ui.widget.VideoLayoutParams;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.ShareUtils;

/**
 * 作者: Lixuewei
 * 版本: 1.0
 * 创建日期: 2017/6/5
 * 描述: 直播详情
 * 修订历史:
 */

public class ZhiBoDetailActivity extends BaseListActivity {
    private TextView timeText;
    private long     beginTime;
    DirectseedSpeedAdapter mAdapter;
    private IMediaDataVideoView videoView;
    private String              mItemId;
    private String              mMatchId;
          int page = 1;
    LinkedHashMap<String, String> rateMap            = new LinkedHashMap<String, String>();
    VideoViewListener             mVideoViewListener = new VideoViewListener() {
        @Override
        public void onStateResult(int event, Bundle bundle) {
            handleVideoInfoEvent(event, bundle);// 处理视频信息事件
            handlePlayerEvent(event, bundle);// 处理播放器事件
            handleLiveEvent(event, bundle);// 处理直播类事件,如果是点播，则这些事件不会回调
        }

        @Override
        public String onGetVideoRateList(LinkedHashMap<String, String> map) {
            rateMap = map;
            for (Map.Entry<String, String> rates : map.entrySet()) {
                if (rates.getValue().equals("高清")) {
                    return rates.getKey();
                }
            }
            return "";
        }
    };


    @Override
    public void itemClick(int position, Object value) {
       /* QuickZhiboInfo currentInfo = (QuickZhiboInfo) value;
        mItemId = currentInfo.getId();
        initDatas();*/
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mAdapter = new DirectseedSpeedAdapter(mContext);
        return mAdapter;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_zhibodetail;
    }

    @Override
    public void initViews() {
        super.initViews();
        setTitle("直播");
        videoView = new UIActionLiveVideoView(this);
        setActionLiveParameter(false);
        if (videoView instanceof UIActionLiveVideoView) {
            ((UIActionLiveVideoView) videoView).setVideoAutoPlay(true);
        }
        videoView.setVideoViewListener(mVideoViewListener);
        final RelativeLayout videoContainer = (RelativeLayout) findViewById(R.id.videoContainer);
        videoContainer.addView((View) videoView, VideoLayoutParams.computeContainerSize(this, 16, 9));

        mDanceViewHolder.setClickListener(R.id.querydetail_tv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatchDetailActivity.lunch(ZhiBoDetailActivity.this, mMatchId);
            }
        });
    }

    @Override
    public void initDatas() {
        super.initDatas();
        Request<String> request = ParameterUtils.getSingleton().getHZhiboDetailMap(mItemId,String.valueOf(page));
        request(AppConfigs.home_zhiboDetailL, request, true);
    }

    private void setZhiboDetail(final List<ZhiBo.DataBean.CompeteBean> zhiBoInfo) {
        mMatchId = zhiBoInfo.get(0).getCompete_id();
        setRightImage(R.drawable.share_icon_001);
        mShareContent = zhiBoInfo.get(0).getName();
        if (!TextUtils.isEmpty(mShareContent)) {
            View view = mDanceViewHolder.getView(R.id.class_jieshao);
            view.setVisibility(View.VISIBLE);
            mDanceViewHolder.setText(R.id.matchname_tv, zhiBoInfo.get(0).getName());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MatchDetailActivity.lunch(mContext, zhiBoInfo.get(0).getCompete_id());
                }
            });

        } else {
            mDanceViewHolder.getView(R.id.class_jieshao).setVisibility(View.GONE);
        }
       /*
        mDanceViewHolder.setText(R.id.brief_tv, zhiBoInfo.getBrief());
        mDanceViewHolder.setText(R.id.compete_trailer, zhiBoInfo.getCompete_trailer());*/

        shareUrl = String.format(AppConfigs.SHARELIVE, mItemId);
        int playStatus = -1;
        if (!MyStrUtil.isEmpty(zhiBoInfo.get(0).getBegin_time()) && !MyStrUtil.isEmpty(zhiBoInfo.get(0).getEnd_time())) {
            playStatus = TimerUtils.isPlaying(zhiBoInfo.get(0).getBegin_time(), zhiBoInfo.get(0).getEnd_time());
        } else {
            playStatus = -1;
        }

        switch (playStatus) {
            case -1:
                mDanceViewHolder.setViewVisibility(R.id.videoContainer, View.INVISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.playstatus_layout, View.VISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.stop_layout, View.INVISIBLE);
                mDanceViewHolder.setText(R.id.starttime, "开始时间:" + zhiBoInfo.get(0).getBegin_time());
                mDanceViewHolder.setText(R.id.endtime, "结束时间:" + zhiBoInfo.get(0).getEnd_time());
                break;
            case 0:
                mDanceViewHolder.setViewVisibility(R.id.stop_layout, View.INVISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.videoContainer, View.VISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.playstatus_layout, View.GONE);
                break;
            case 1:
                mDanceViewHolder.setViewVisibility(R.id.videoContainer, View.INVISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.stop_layout, View.VISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.playstatus_layout, View.INVISIBLE);
                break;

        }
        videoView.resetPlayer();
        if (playStatus != 0) {
            return;
        }
        Bundle mBundle = new Bundle();
        mBundle.putInt(PlayerParams.KEY_PLAY_MODE, PlayerParams.VALUE_PLAYER_ACTION_LIVE);
        mBundle.putString(PlayerParams.KEY_PLAY_ACTIONID, zhiBoInfo.get(0).getActivity_id());
        mBundle.putBoolean(PlayerParams.KEY_PLAY_USEHLS, false);
        mBundle.putString(PlayerParams.KEY_CUID, "");
        mBundle.putString(PlayerParams.KEY_UTOKEN, "");
        mBundle.putString(PlayerParams.KEY_PLAY_CUSTOMERID, "");
        mBundle.putBoolean("pano", true);
        mBundle.putBoolean("hasSkin", true);
        videoView.setDataSource(mBundle);
        timeText = (TextView) findViewById(R.id.time_text);
        beginTime = System.currentTimeMillis();
    }


    @Override
    public void getIntentData() {
        super.getIntentData();
        mItemId = mIntentExtras.getString("itemid");
        Log.e("itemidssss",mItemId);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoView != null) {
            videoView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoView != null) {
            videoView.onPause();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.onDestroy();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
            setHeadVisibility(View.GONE);
        } else {
            setHeadVisibility(View.VISIBLE);
        }
        if (videoView != null) {
            videoView.onConfigurationChanged(newConfig);
        }
    }

    /**
     * 处理播放器本身事件，具体事件可以参见IPlayer类
     */
    private void handlePlayerEvent(int state, Bundle bundle) {
        switch (state) {
            case PlayerEvent.ACTION_LIVE_PLAY_PROTOCOL:
                setActionLiveParameter(bundle.getBoolean(PlayerParams.KEY_PLAY_USEHLS));
                break;
            case PlayerEvent.PLAY_VIDEOSIZE_CHANGED:
                /**
                 * 获取到视频的宽高的时候，此时可以通过视频的宽高计算出比例，进而设置视频view的显示大小。
                 * 如果不按照视频的比例进行显示的话，(以surfaceView为例子)内容会填充整个surfaceView。
                 * 意味着你的surfaceView显示的内容有可能是拉伸的
                 */
                break;

            case PlayerEvent.PLAY_PREPARED:
                // 播放器准备完成，此刻调用start()就可以进行播放了
                if (videoView != null) {
                    videoView.onStart();
                }
                break;
            case PlayerEvent.PLAY_INFO:
                int code = bundle.getInt(PlayerParams.KEY_RESULT_STATUS_CODE);
                if (code == StatusCode.PLAY_INFO_VIDEO_RENDERING_START) {
                    long startPlayTime = (System.currentTimeMillis() - beginTime);
                    float num = (float) startPlayTime / 1000;
                    if (timeText != null) {
                        timeText.setText("起播耗时：" + num + "秒");
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 处理直播类事件
     */
    private void handleLiveEvent(int state, Bundle bundle) {
    }

    /**
     * 处理视频信息类事件
     */
    private void handleVideoInfoEvent(int state, Bundle bundle) {
    }

    private void setActionLiveParameter(boolean hls) {
        if (hls) {
            videoView.setCacheWatermark(1000, 100);
            videoView.setMaxDelayTime(50000);
            videoView.setCachePreSize(1000);
            videoView.setCacheMaxSize(40000);
        } else {
            //rtmp
            videoView.setCacheWatermark(500, 100);
            videoView.setMaxDelayTime(1000);
            videoView.setCachePreSize(200);
            videoView.setCacheMaxSize(10000);
        }
    }

    @Override
    public void onSucceed(int what, String responseStr) {
        super.onSucceed(what, responseStr);

        ZhiBo reponseResult = JsonMananger.getReponseResult(responseStr, ZhiBo.class);
        ArrayList<ZhiBo.DataBean.CompeteBean> compete = reponseResult.getData().getCompete();
        setZhiboDetail(compete);
        ArrayList<MenuBean> menu = reponseResult.getData().getMenu();
        if (!MyStrUtil.isEmpty(menu)) {
            mDanceViewHolder.getView(R.id.program).setVisibility(View.VISIBLE);
            mAdapter.addList(isRefresh,menu);
        }
    }
   /* @Override
    public void onSucceed(int what, String responseStr) {
        //        super.onSucceed(what, responseStr);
        // ZhiboDetailResponse detailResponse = JsonMananger.getReponseResult(responseStr, ZhiboDetailResponse.class);
        //mAdapter.addList(true, detailResponse.getData().getOtherList());


    }*/
    @Override
    public void refresh() {
        super.refresh();
        page = 1;
        Request<String> request = ParameterUtils.getSingleton().getHZhiboDetailMap(mItemId,String.valueOf(page));
        request(AppConfigs.home_zhiboDetailL, request, true);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        page++;
        Request<String> request = ParameterUtils.getSingleton().getHZhiboDetailMap(mItemId,String.valueOf(page));
        request(AppConfigs.home_zhiboDetailL, request, true);
    }

    public static void lunch(Context context, String id) {
        Intent intent = new Intent(context, ZhiBoDetailActivity.class);
        intent.putExtra("itemid", id);
        context.startActivity(intent);
    }

    String shareUrl;
    private String mShareContent;
    ShareUtils mShareUtils;

    private void showShareDialog() {
        MobclickAgent.onEvent(this, AppConfigs.CLICK_EVENT_19);
        if (mShareUtils == null) {
            mShareUtils = new ShareUtils(this);
        }
        mShareUtils.showShareDilaog(AppConfigs.CLICK_EVENT_19, shareUrl, mShareContent);
    }


    @Override
    public void onHeadRightButtonClick(View v) {
        showShareDialog();
    }

}
