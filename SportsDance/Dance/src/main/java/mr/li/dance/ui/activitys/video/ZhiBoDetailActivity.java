package mr.li.dance.ui.activitys.video;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.ZhiBoBean;
import mr.li.dance.ui.activitys.base.BaseActivity;
import mr.li.dance.ui.adapters.ZhiBoPagerAdapter;
import mr.li.dance.ui.fragments.video.CardFrag;
import mr.li.dance.ui.fragments.video.CardFragment;
import mr.li.dance.ui.fragments.video.IntroduceFragment;
import mr.li.dance.ui.fragments.video.SynopsisFragment;
import mr.li.dance.ui.widget.screenrotate.MyRotate;
import mr.li.dance.ui.widget.screenrotate.RotateCallBack;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.ScreenUtils;
import mr.li.dance.utils.ShareUtils;
import mr.li.dance.utils.TimerUtils;
import mr.li.dance.utils.util.IndexViewPager;


/**
 * 作者: Lixuewei
 * 版本: 1.0
 * 创建日期: 2017/6/5
 * 描述: 直播详情
 * 修订历史:
 */

public class ZhiBoDetailActivity extends BaseActivity implements ITXLivePlayListener, View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    private TextView timeText;
    private long     beginTime;

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


    public static final int ACTIVITY_TYPE_LIVE_PLAY = 2;


    private int mCurrentRenderMode;
    private int mCurrentRenderRotation;

    private int mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
    private TXLivePlayConfig mPlayConfig;
    private long mStartPlayTS = 0;
    protected int          mActivityType;
    private   LinearLayout play_progress;

    private       FrameLayout                     ff;
    private       ZhiBoBean.DataBean.LiveInfoBean compete;
    // private String[] titles = {"简介", "赛程表"};
    //private TabLayout           tab;
    public static IndexViewPager                       viewPager;
    private       ArrayList<Fragment>             list;

    private Bundle            synopsisBundle;
    private SynopsisFragment  synopsisFragment;
    private CardFragment      cardFragment;
    private Bundle            cardBundle;
    private IntroduceFragment introduceFragment;
    private CardFrag          cardFrag;
    int playStatus = -1;

    @Override
    public void initVideo() {
        super.initVideo();
        // startOrientationListener();
        mCurrentRenderMode = TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN;
        mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT;
        mPlayConfig = new TXLivePlayConfig();
    }


    @Override
    public int getContentViewId() {
        return R.layout.activity_zhibodetail;
    }

    @Override
    public void initViews() {
        //  super.initViews();
        //应用运行时，保持屏幕高亮，不锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setTitle("直播");
        registerForContextMenu(findViewById(R.id.btnPlay));
        ff = (FrameLayout) findViewById(R.id.video_frame);
        // tab = (TabLayout) mDanceViewHolder.getView(R.id.tab);
        viewPager = (IndexViewPager) mDanceViewHolder.getView(R.id.vp);
        //  tab.setTabMode(TabLayout.MODE_FIXED);
        list = new ArrayList<>();

        ff.setOnClickListener(this);
        if (mLivePlayer == null) {
            mLivePlayer = new TXLivePlayer(this);
        }
        mDanceViewHolder.getView(R.id.video_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        synopsisBundle = new Bundle();
        cardBundle = new Bundle();
      /*  synopsisFragment = new SynopsisFragment();
        cardFragment = new CardFragment();*/
        introduceFragment = new IntroduceFragment();
        cardFrag = new CardFrag();
        list.add(introduceFragment);
        list.add(cardFrag);

    }

    @Override
    public void initDatas() {
        super.initDatas();
        Request<String> request = ParameterUtils.getSingleton().getHZhiboDetailMap(mItemId, String.valueOf(page));
        Log.e(TAG + "id:", mItemId);
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
        ZhiBoBean reponseResult = JsonMananger.getReponseResult(responseStr, ZhiBoBean.class);
        ZhiBoBean.DataBean.AdVideoBean adVideo = reponseResult.getData().getAdVideo();
        ArrayList<ZhiBoBean.DataBean.AdWlinkBean> adWlink = reponseResult.getData().getAdWlink();
        compete = reponseResult.getData().getLiveInfo();
        startPlay(compete);
        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final boolean isPlaying = mLivePlayer.isPlaying();

                if (isPlaying) {
                    pauseStatus();
                } else {
                    playStatus();
                }

            }
        });


        if (!MyStrUtil.isEmpty(adVideo)) {
            synopsisBundle.putSerializable("adVideo", adVideo);
        }
        if (!MyStrUtil.isEmpty(adWlink)) {
            synopsisBundle.putParcelableArrayList("adWlink", adWlink);
        }
        if (!MyStrUtil.isEmpty(compete.getBrief())) {
            synopsisBundle.putString("brief", compete.getBrief());
        }
        if (!MyStrUtil.isEmpty(compete.getLive_title())) {
            synopsisBundle.putString("title", compete.getLive_title());
        }
        synopsisBundle.putString("mId", compete.getCompete_id());
        synopsisBundle.putString("name", compete.getCompete_name());
        synopsisBundle.putInt("isLive", playStatus);
        introduceFragment.setArguments(synopsisBundle);


        cardBundle.putString("id", mItemId);
        cardBundle.putString("mId", compete.getCompete_id());
        cardBundle.putString("name", compete.getCompete_name());

        cardFrag.setArguments(cardBundle);

        ZhiBoPagerAdapter adapter = new ZhiBoPagerAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
    }

    /* @Override
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
 */
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
            Toast.makeText(getApplicationContext(), "播放地址不合法，直播目前仅支持rtmp,flv播放方式!", Toast.LENGTH_SHORT).show();
            return false;
        }

        switch (mActivityType) {
            case ACTIVITY_TYPE_LIVE_PLAY: {
                if (playUrl.startsWith("rtmp://")) {
                    mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
                } else if ((playUrl.startsWith("http://") || playUrl.startsWith("https://")) && playUrl.contains(".flv")) {
                    mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_FLV;
                } else {
                    Toast.makeText(getApplicationContext(), "播放地址不合法，直播目前仅支持rtmp,flv播放方式!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            break;

            default:
                Toast.makeText(getApplicationContext(), "播放地址不合法，目前仅支持rtmp,flv播放方式!", Toast.LENGTH_SHORT).show();
                return false;
        }
        return true;
    }

    /**
     * 一切就绪 开始播放
     */
    public void playStatus() {
        mLivePlayer.resume();
        mBtnPlay.setBackgroundResource(R.drawable.video_pause);
        ff.setBackgroundColor(0xff000000);
        mIsPlaying = true;
    }

    /**
     * 一切就绪 停止播放
     */
    public void pauseStatus() {
        mLivePlayer.pause();
        mBtnPlay.setBackgroundResource(R.drawable.video_resume);
        mIsPlaying = false;
    }

    /**
     * 开始播放
     */
    private boolean startPlay(final ZhiBoBean.DataBean.LiveInfoBean zhiBoInfo) {
        mMatchId = zhiBoInfo.getCompete_id();
        mDanceViewHolder.setText(R.id.video_title, zhiBoInfo.getLive_title());//视频名称
        setRightImage(R.drawable.share_icon_001);
        mShareContent = zhiBoInfo.getLive_title();
        shareUrl = String.format(AppConfigs.SHARELIVE, mItemId);
      /*  if (!MyStrUtil.isEmpty(zhiBoInfo.getCompete_name())) {
            View view = mDanceViewHolder.getView(R.id.class_jieshao);
            view.setVisibility(View.VISIBLE);
            mDanceViewHolder.setText(R.id.matchname_tv, zhiBoInfo.getCompete_name());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GameDetailActivity.lunch(mContext, zhiBoInfo.getCompete_id());
                }
            });

        } else {
            mDanceViewHolder.getView(R.id.class_jieshao).setVisibility(View.GONE);
        }*/
        //mDanceViewHolder.setText(R.id.compete_trailer, zhiBoInfo.get(0).getCompete_trailer());

        if (!MyStrUtil.isEmpty(zhiBoInfo.getTime()) && !MyStrUtil.isEmpty(zhiBoInfo.getBegin_time()) && !MyStrUtil.isEmpty(zhiBoInfo.getEnd_time())) {
            playStatus = TimerUtils.isPlaying(zhiBoInfo.getTime(), zhiBoInfo.getBegin_time(), zhiBoInfo.getEnd_time());
        } else {
            playStatus = -1;
        }
        Log.e(TAG, playStatus + "");
        switch (playStatus) {

            case -1:

                ff.setClickable(false);
                mDanceViewHolder.getView(R.id.play_progress).setVisibility(View.INVISIBLE);   //视频底部
                mDanceViewHolder.getView(R.id.video_top).setVisibility(View.INVISIBLE);        //视频顶部
                mDanceViewHolder.setViewVisibility(R.id.video_view, View.INVISIBLE);  //视频
                mDanceViewHolder.setViewVisibility(R.id.stop_layout, View.INVISIBLE);    //视频结束
                mDanceViewHolder.setViewVisibility(R.id.playstatus_layout, View.VISIBLE);//未开始
                mDanceViewHolder.setImageByUrlOrFilePaths(R.id.prepare_img, zhiBoInfo.getPicture_begin());
                break;
            case 0:
                startOrientationListener();
                ff.setClickable(true);
                mDanceViewHolder.getView(R.id.play_progress).setVisibility(View.VISIBLE);
                mDanceViewHolder.getView(R.id.video_top).setVisibility(View.VISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.stop_layout, View.INVISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.video_view, View.VISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.playstatus_layout, View.INVISIBLE);
                break;
            case 1:

                ff.setClickable(false);
                mDanceViewHolder.getView(R.id.play_progress).setVisibility(View.INVISIBLE);
                mDanceViewHolder.getView(R.id.video_top).setVisibility(View.INVISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.video_view, View.INVISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.stop_layout, View.VISIBLE);
                mDanceViewHolder.setViewVisibility(R.id.playstatus_layout, View.INVISIBLE);
                mDanceViewHolder.setImageByUrlOrFilePaths(R.id.end_img, zhiBoInfo.getPicture_end());
                break;

        }
        if (playStatus != 0) {

            return false;
        }

        String playUrl = AppConfigs.VIDEO_ZHIBO + zhiBoInfo.getTencent_streamId() + ".flv";
        Log.e(TAG, playUrl);
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
                mDanceViewHolder.getView(R.id.video_top).setVisibility(m == View.VISIBLE ? View.GONE : View.VISIBLE);
                play_progress.setVisibility(m == View.VISIBLE ? View.GONE : View.VISIBLE);
                play_progress.bringToFront();
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
        ViewGroup.LayoutParams layoutParams = ff.getLayoutParams();
        Log.e("flag", flag + "");
        mDanceViewHolder.getView(R.id.vp).setVisibility(flag ? View.VISIBLE : View.GONE);
      /*  mDanceViewHolder.getView(R.id.scroll).setVisibility(flag ? View.VISIBLE : View.GONE);
        if (!MyStrUtil.isEmpty(compete.getCompete_name())) {
            mDanceViewHolder.getView(R.id.class_jieshao).setVisibility(flag ? View.VISIBLE : View.GONE);
        } else {
            mDanceViewHolder.getView(R.id.class_jieshao).setVisibility(View.GONE);
        }     */

        //  mDanceViewHolder.getView(R.id.jiemu).setVisibility(flag ? View.VISIBLE : View.GONE);
        boolean utils = ScreenUtils.hasNavBar(this);
        mBtnRenderRotation.setImageDrawable(flag ? getResources().getDrawable(R.drawable.video_unfold) : getResources().getDrawable(R.drawable.video_packup));
        if (flag) {
            ScreenUtils.showFullScreen(this, false);
            setHeadVisibility(View.VISIBLE);
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            ScreenUtils.showFullScreen(this, utils);
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
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {
            stopPlay();

        } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
            stopPlay();
            Toast.makeText(getApplicationContext(), "信号不好，请退出重试", Toast.LENGTH_SHORT).show();
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
