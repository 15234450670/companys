package mr.li.dance.ui.activitys.video;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.umeng.analytics.MobclickAgent;
import com.yolanda.nohttp.rest.Request;

import java.util.ArrayList;
import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.MenuBean;
import mr.li.dance.models.ZhiBo;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.activitys.match.MatchDetailActivity;
import mr.li.dance.ui.adapters.DirectseedSpeedAdapter;
import mr.li.dance.ui.widget.screenrotate.MyRotate;
import mr.li.dance.ui.widget.screenrotate.RotateCallBack;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.ShareUtils;
import mr.li.dance.utils.TimerUtils;

import static mr.li.dance.R.id.video_top;

/**
 * 作者: Lixuewei
 * 版本: 1.0
 * 创建日期: 2017/6/5
 * 描述: 直播详情
 * 修订历史:
 */

public class ZhiBoDetailActivity extends BaseListActivity implements ITXLivePlayListener, View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    private TextView timeText;
    private long     beginTime;
    DirectseedSpeedAdapter mAdapter;

    private String mItemId;
    private String mMatchId;
    int page = 1;

    private MyRotate rotate;
    private TXLivePlayer mLivePlayer = null;
    private boolean          mIsPlaying;
    private TXCloudVideoView mPlayerView;
    private ImageView        mLoadingView;
    private Button           mBtnPlay;
    private ImageView        mBtnRenderRotation;


    public static final int ACTIVITY_TYPE_LIVE_PLAY     = 2;
    public static final int ACTIVITY_TYPE_REALTIME_PLAY = 5;


    private int mCurrentRenderMode;
    private int mCurrentRenderRotation;

    private int mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
    private TXLivePlayConfig mPlayConfig;
    private long mStartPlayTS = 0;
    protected int                                   mActivityType;
    private   LinearLayout                          play_progress;
    private   ArrayList<ZhiBo.DataBean.CompeteBean> compete;
    private   FrameLayout                           ff;

    @Override
    public void itemClick(int position, Object value) {

    }

    @Override
    public void initVideo() {
        super.initVideo();
        startOrientationListener();
        mCurrentRenderMode = TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN;
        mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT;
        mPlayConfig = new TXLivePlayConfig();
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
        //应用运行时，保持屏幕高亮，不锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setTitle("直播");
        registerForContextMenu(findViewById(R.id.btnPlay));
        ff = (FrameLayout) findViewById(R.id.video_frame);

        ff.setOnClickListener(this);
        if (mLivePlayer == null) {
            mLivePlayer = new TXLivePlayer(this);
        }

        mPlayerView = (TXCloudVideoView) findViewById(R.id.video_view);
        play_progress = (LinearLayout) mDanceViewHolder.getView(R.id.play_progress);
        mPlayerView.setLogMargin(12, 12, 110, 60);
        mPlayerView.showLog(false);
        mActivityType = ACTIVITY_TYPE_LIVE_PLAY;
        mLoadingView = (ImageView) findViewById(R.id.loadingImageView);
        /**
         * 播放按钮
         */
        mBtnPlay = (Button) findViewById(R.id.btnPlay);
        mBtnPlay.setOnClickListener(this);
        mBtnRenderRotation = (ImageView) findViewById(R.id.btnOrientation);
        mBtnRenderRotation.setOnClickListener(this);

    }

    @Override
    public void initDatas() {
        super.initDatas();
        Request<String> request = ParameterUtils.getSingleton().getHZhiboDetailMap(mItemId, String.valueOf(page));
        request(AppConfigs.home_zhiboDetailL, request, true);
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        mItemId = mIntentExtras.getString("itemid");
        Log.e("itemidssss", mItemId);

    }


    @Override
    public void onSucceed(int what, String responseStr) {
        super.onSucceed(what, responseStr);
        Log.e(TAG, responseStr);
        ZhiBo reponseResult = JsonMananger.getReponseResult(responseStr, ZhiBo.class);
        compete = reponseResult.getData().getCompete();
        startPlay(compete);
        ArrayList<MenuBean> menu = reponseResult.getData().getMenu();
        if (!MyStrUtil.isEmpty(menu)) {
            mDanceViewHolder.getView(R.id.program).setVisibility(View.VISIBLE);
            mAdapter.addList(isRefresh, menu);
        }
    }

    @Override
    public void refresh() {
        super.refresh();
        page = 1;
        Request<String> request = ParameterUtils.getSingleton().getHZhiboDetailMap(mItemId, String.valueOf(page));
        request(AppConfigs.home_zhiboDetailL, request, true);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        page++;
        Request<String> request = ParameterUtils.getSingleton().getHZhiboDetailMap(mItemId, String.valueOf(page));
        request(AppConfigs.home_zhiboDetailL, request, true);
    }


    public static void lunch(Context context, String id) {
        Intent intent = new Intent(context, ZhiBoDetailActivity.class);

        intent.putExtra("itemid", id);
        context.startActivity(intent);
    }

    public static void lunchs(Context context, String id) {
        Intent intent = new Intent(context, ZhiBoDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

    /**
     * 检查直播 链接
     * @param playUrl
     * @return
     */
    private boolean checkPlayUrl(final String playUrl) {
        if (TextUtils.isEmpty(playUrl) || (!playUrl.startsWith("http://") && !playUrl.startsWith("https://") && !playUrl.startsWith("rtmp://") && !playUrl.startsWith("/"))) {
            Toast.makeText(getApplicationContext(), "播放地址不合法1，直播目前仅支持rtmp,flv播放方式!", Toast.LENGTH_SHORT).show();
            return false;
        }

        switch (mActivityType) {
            case ACTIVITY_TYPE_LIVE_PLAY: {
                if (playUrl.startsWith("rtmp://")) {
                    mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
                } else if ((playUrl.startsWith("http://") || playUrl.startsWith("https://")) && playUrl.contains(".flv")) {
                    mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_FLV;
                } else {
                    Toast.makeText(getApplicationContext(), "播放地址不合法2，直播目前仅支持rtmp,flv播放方式!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            break;
            case ACTIVITY_TYPE_REALTIME_PLAY:
                mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC;
                break;
            default:
                Toast.makeText(getApplicationContext(), "播放地址不合法3，目前仅支持rtmp,flv播放方式!", Toast.LENGTH_SHORT).show();
                return false;
        }
        return true;
    }

    /**
     * 开始播放
     */
    private boolean startPlay(final List<ZhiBo.DataBean.CompeteBean> zhiBoInfo) {
        mMatchId = zhiBoInfo.get(0).getCompete_id();
        mDanceViewHolder.setText(R.id.video_title, zhiBoInfo.get(0).getCompete_name());//视频名称
        setRightImage(R.drawable.share_icon_001);
        mShareContent = zhiBoInfo.get(0).getName();
        if (!TextUtils.isEmpty(mShareContent)) {
            View view = mDanceViewHolder.getView(R.id.class_jieshao);
            view.setVisibility(View.VISIBLE);
            mDanceViewHolder.getView(R.id.v).setVisibility(View.VISIBLE);
            mDanceViewHolder.setText(R.id.matchname_tv, zhiBoInfo.get(0).getCompete_name());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MatchDetailActivity.lunch(mContext, zhiBoInfo.get(0).getCompete_id());
                }
            });

        } else {
            mDanceViewHolder.getView(R.id.class_jieshao).setVisibility(View.GONE);
        }
        mDanceViewHolder.setText(R.id.compete_trailer, zhiBoInfo.get(0).getCompete_trailer());
        int playStatus = -1;
        if (!MyStrUtil.isEmpty(zhiBoInfo.get(0).getBegin_time()) && !MyStrUtil.isEmpty(zhiBoInfo.get(0).getEnd_time())) {
            playStatus = TimerUtils.isPlaying(zhiBoInfo.get(0).getBegin_time(), zhiBoInfo.get(0).getEnd_time());
        } else {
            playStatus = -1;
        }
        Log.e(TAG, playStatus + "");
        switch (playStatus) {

            case -1:
                ff.setClickable(false);
                mDanceViewHolder.getView(R.id.play_progress).setVisibility(View.INVISIBLE);
                mDanceViewHolder.getView(R.id.video_top).setVisibility(View.INVISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.video_view, View.INVISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.playstatus_layout, View.VISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.stop_layout, View.INVISIBLE);
                mDanceViewHolder.setText(R.id.starttime, "开始时间:" + zhiBoInfo.get(0).getBegin_time());
                mDanceViewHolder.setText(R.id.endtime, "结束时间:" + zhiBoInfo.get(0).getEnd_time());
                break;
            case 0:
                ff.setClickable(true);
                mDanceViewHolder.getView(R.id.play_progress).setVisibility(View.VISIBLE);
                mDanceViewHolder.getView(R.id.video_top).setVisibility(View.VISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.stop_layout, View.INVISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.video_view, View.VISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.playstatus_layout, View.GONE);
                break;
            case 1:
                ff.setClickable(false);
                mDanceViewHolder.getView(R.id.play_progress).setVisibility(View.INVISIBLE);
                mDanceViewHolder.getView(R.id.video_top).setVisibility(View.INVISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.video_view, View.INVISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.stop_layout, View.VISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.playstatus_layout, View.INVISIBLE);
                break;

        }
        if (playStatus != 0) {
            return false;
        }

        String playUrl = "rtmp://live.hkstv.hk.lxdns.com/live/hks";

        if (!checkPlayUrl(playUrl)) {
            return false;
        }
        mBtnPlay.setBackgroundResource(R.drawable.video_pause);

        mLivePlayer.setPlayerView(mPlayerView);

        mLivePlayer.setPlayListener(this);
        // 硬件加速在1080p解码场景下效果显著，但细节之处并不如想象的那么美好：
        // (1) 只有 4.3 以上android系统才支持
        // (2) 兼容性我们目前还仅过了小米华为等常见机型，故这里的返回值您先不要太当真
        //  mLivePlayer.enableHardwareDecode(mHWDecode);
        mLivePlayer.setRenderRotation(mCurrentRenderRotation);
        mLivePlayer.setRenderMode(mCurrentRenderMode);
        //设置播放器缓存策略
        //这里将播放器的策略设置为自动调整，调整的范围设定为1到4s，您也可以通过setCacheTime将播放器策略设置为采用
        //固定缓存时间。如果您什么都不调用，播放器将采用默认的策略（默认策略为自动调整，调整范围为1到4s）
        //mLivePlayer.setCacheTime(5);

        mLivePlayer.setConfig(mPlayConfig);

        int result = mLivePlayer.startPlay(playUrl, mPlayType); // result返回值：0 success;  -1 empty url; -2 invalid url; -3 invalid playType;
        if (result != 0) {
            mBtnPlay.setBackgroundResource(R.drawable.video_resume);

            return false;
        }

        Log.w("video render", "timetrack start play");

        startLoadingAnimation();


        mStartPlayTS = System.currentTimeMillis();

        return true;
    }

    /**
     * 结束播放
     */
    private void stopPlay() {
        mBtnPlay.setBackgroundResource(R.drawable.video_resume);
        stopLoadingAnimation();
        if (mLivePlayer != null) {
            mLivePlayer.stopRecord();
            mLivePlayer.setPlayListener(null);
            mLivePlayer.stopPlay(true);
        }
        mIsPlaying = false;
    }

    /**
     * 开始加载动画
     */
    private void startLoadingAnimation() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
            ((AnimationDrawable) mLoadingView.getDrawable()).start();
        }
    }

    /**
     * 结束或者链接错误加载动画
     */
    private void stopLoadingAnimation() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
            ((AnimationDrawable) mLoadingView.getDrawable()).stop();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_frame:
                final int m = play_progress.getVisibility();
                mDanceViewHolder.getView(video_top).setVisibility(m == View.VISIBLE ? View.GONE : View.VISIBLE);
                play_progress.setVisibility(m == View.VISIBLE ? View.GONE : View.VISIBLE);
                play_progress.bringToFront();
                break;
            case R.id.btnPlay:
                Log.d(TAG, "click playbtn isplay:" + mIsPlaying + " playtype:" + mPlayType);
                if (mIsPlaying) {
                    stopPlay();
                } else {
                    mIsPlaying = startPlay(compete);
                }
                break;
            case R.id.btnOrientation:
                if (mLivePlayer == null) {
                    return;
                }
                buttonClick();
                break;
        }
    }


    /**
     * 启用手机旋转监听
     */
    private void startOrientationListener() {
        rotate = new MyRotate(this);
        rotate.setCallBack(new RotateCallBack() {
            @Override
            public void portrait() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                showOrHideView(true);
            }

            @Override
            public void reverseLandscape() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                showOrHideView(false);
            }

            @Override
            public void landscape() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                showOrHideView(false);
            }
        });

        rotate.enable();
    }

    /**
     * 旋转的点击事件
     */
    private void buttonClick() {
        rotate.userPress(this);
    }

    /**
     * 横竖屏切换时的View变化
     * @param flag
     *         true竖屏  false横屏
     */
    private void showOrHideView(boolean flag) {
        Log.e("flag", flag + "");
        mDanceViewHolder.getView(R.id.scroll).setVisibility(flag ? View.VISIBLE : View.GONE);
        mBtnRenderRotation.setImageDrawable(flag ? getResources().getDrawable(R.drawable.video_unfold) : getResources().getDrawable(R.drawable.video_packup));
        if (flag) {
            setHeadVisibility(View.VISIBLE);
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            setHeadVisibility(View.GONE);
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

    }


    @Override
    public void onPlayEvent(int event, Bundle param) {
        String playEventLog = "receive event: " + event + ", " + param.getString(TXLiveConstants.EVT_DESCRIPTION);
        Log.d(TAG, playEventLog);

        if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            stopLoadingAnimation();
            Log.d("AutoMonitor", "PlayFirstRender,cost=" + (System.currentTimeMillis() - mStartPlayTS));
        } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT || event == TXLiveConstants.PLAY_EVT_PLAY_END) {
            stopPlay();
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_LOADING) {
            startLoadingAnimation();
        } else if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {
            stopLoadingAnimation();
        } else if (event == TXLiveConstants.PLAY_EVT_CHANGE_RESOLUTION) {
        }

        if (event < 0) {
            Toast.makeText(getApplicationContext(), "信号不好，请重试", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNetStatus(Bundle bundle) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLivePlayer != null) {
            mLivePlayer.stopPlay(true);
            mLivePlayer = null;
        }
        if (mPlayerView != null) {
            mPlayerView.onDestroy();
            mPlayerView = null;
        }

        if (rotate != null) {
            rotate.disable();
            rotate.destory();
            rotate = null;
        }

        mPlayConfig = null;

        Log.d(TAG, "vrender onDestroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLivePlayer != null) {
            mLivePlayer.pause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mLivePlayer != null) {
            mLivePlayer.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");


    }


}
