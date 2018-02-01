package mr.li.dance.ui.activitys.video;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.yolanda.nohttp.rest.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.StringResponse;
import mr.li.dance.https.response.VideoDetailResponse;
import mr.li.dance.models.Video;
import mr.li.dance.ui.activitys.LoginActivity;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.activitys.match.MatchDetailActivity;
import mr.li.dance.ui.activitys.match.MatchVideoActivity;
import mr.li.dance.ui.activitys.newActivitys.SpecialActivity;
import mr.li.dance.ui.adapters.new_adapter.SpecialItemAdapter;
import mr.li.dance.ui.adapters.new_adapter.VideoAlbumAdapter;
import mr.li.dance.ui.widget.screenrotate.MyRotate;
import mr.li.dance.ui.widget.screenrotate.RotateCallBack;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.NToast;
import mr.li.dance.utils.ScreenUtils;
import mr.li.dance.utils.ShareUtils;
import mr.li.dance.utils.UserInfoManager;


/**
 * 作者: Lixuewei
 * 版本: 1.0
 * 创建日期: 2017/6/5
 * 描述: 视频详情页面
 * 修订历史:
 */

public class VideoDetailActivity extends BaseListActivity implements ITXVodPlayListener {
    // BaseItemAdapter mAdapter;
    VideoAlbumAdapter videoAlbumAdapter;
    // private IMediaDataVideoView videoView;
    private String mItemId;
    boolean isCollected;
    boolean isFromCollectpage = false;
    private String shareUrl;
    private String mShareContent;
    private String TAG = getClass().getSimpleName();
    /* LinkedHashMap<String, String> rateMap            = new LinkedHashMap<String, String>();
     VideoViewListener             mVideoViewListener = new VideoViewListener() {


         @Override
         public void onStateResult(int event, Bundle bundle) {
             handleVideoInfoEvent(event, bundle);// 处理视频信息事件
             handlePlayerEvent(event, bundle);// 处理播放器事件
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
     };*/
    private RecyclerView     rv;
    private ArrayList<Video> otherList;
    private ArrayList<Video> album;


    private int             mCurrentRenderMode;
    private int             mCurrentRenderRotation;
    private TXVodPlayConfig mPlayConfig;
    private TXVodPlayer mLivePlayer = null;
    private TXCloudVideoView mPlayerView;
    private LinearLayout     play_progress;
    private ImageView        mLoadingView;

    private boolean mVideoPlay;
    private Button  mBtnPlay;
    private boolean mVideoPause  = false;
    private long    mStartPlayTS = 0;
    private SeekBar  mSeekBar;
    private TextView mTextDuration;
    private TextView mTextStart;
    private boolean mStartSeek = false;
    private MyRotate  rotate;
    private ImageView mBtnRenderRotation;

    private FrameLayout fl;
    private Video       detail;
    private Button      btnvideo;


    /**
     * 初始化播放模式
     */
    @Override
    public void initVideo() {
        super.initVideo();
        startOrientationListener();
        mCurrentRenderMode = TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN;
        mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT;
        mPlayConfig = new TXVodPlayConfig();
    }

    @Override
    public void itemClick(int position, Object value) {
        Video currentInfo = (Video) value;
        mItemId = currentInfo.getId();
        initDatas();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        //   mAdapter = new BaseItemAdapter(mContext, BaseItemAdapterType.CommentType);
        // mAdapter.setItemClickListener(this);
        videoAlbumAdapter = new VideoAlbumAdapter(mContext);
        videoAlbumAdapter.setItemClickListener(this);
        return videoAlbumAdapter;
    }


    @Override
    public int getContentViewId() {
        return R.layout.activity_videodetail;
    }

    @Override
    public void initViews() {
        super.initViews();
        //应用运行时，保持屏幕高亮，不锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setTitle("视频详情");
        mPlayerView = (TXCloudVideoView) mDanceViewHolder.getView(R.id.video_view);
        play_progress = (LinearLayout) mDanceViewHolder.getView(R.id.play_progress);
        btnvideo = mDanceViewHolder.getButton(R.id.btnvideo);
        mDanceViewHolder.getView(R.id.video_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        registerForContextMenu(findViewById(R.id.btnPlay));
        mBtnRenderRotation = mDanceViewHolder.getImageView(R.id.btnOrientation);
        //创建player对象
        if (mLivePlayer == null) {
            mLivePlayer = new TXVodPlayer(this);
        }
        fl = (FrameLayout) findViewById(R.id.video_frame);
        mLoadingView = mDanceViewHolder.getImageView(R.id.loadingImageView);
        mBtnPlay = (Button) findViewById(R.id.btnPlay);

        /**
         * 点击切换横竖屏
         */
        mBtnRenderRotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLivePlayer == null) {
                    return;
                }
                buttonClick();
            }
        });


        mSeekBar = (SeekBar) mDanceViewHolder.getView(R.id.seekbar);
        /**
         * 进度条
         */
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean bFromUser) {
                mTextStart.setText(String.format("%02d:%02d", progress / 1000 / 60, progress / 1000 % 60));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mStartSeek = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mLivePlayer != null) {
                    mLivePlayer.seek(seekBar.getProgress() / 1000.f);
                }
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mStartSeek = false;
                    }
                }, 500);
            }
        });
        mTextDuration = mDanceViewHolder.getTextView(R.id.duration); //总时长
        mTextStart = mDanceViewHolder.getTextView(R.id.play_start);   //当前进度
        mTextDuration.setTextColor(Color.rgb(255, 255, 255));
        mTextStart.setTextColor(Color.rgb(255, 255, 255));

        setRightImage(R.drawable.collect_icon, R.drawable.share_icon_001);
        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setEnableRefresh(false);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerview.setLayoutManager(manager);
        mRecyclerview.setAdapter(getAdapter());
        mRefreshLayout.setEnableLoadmore(false);
       /* videoView = new UIVodVideoView(this);
        ((UIVodVideoView) videoView).setVideoAutoPlay(true);
        videoView.setVideoViewListener(mVideoViewListener);*/
        rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager manager1 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(manager1);
       /* final RelativeLayout videoContainer = (RelativeLayout) findViewById(R.id.videoContainer);
        videoContainer.addView((View) videoView, VideoLayoutParams.computeContainerSize(this, 16, 9));*/

    }

    @Override
    public void initDatas() {
        super.initDatas();
        String userId = UserInfoManager.getSingleton().getUserId(this);
        Request<String> request = ParameterUtils.getSingleton().getVideoDetailMap(mItemId, userId);
        Log.e("mId", mItemId);
        request(AppConfigs.home_dianboDetailL, request, true);
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        mItemId = mIntentExtras.getString("itemid");
        isFromCollectpage = mIntentExtras.getBoolean("isfromcollectpage");
    }

    @Override
    public void onSucceed(int what, String responseStr) {
        super.onSucceed(what, responseStr);
        Log.e(TAG, responseStr);
        if (AppConfigs.home_dianboDetailL == what) {
            final VideoDetailResponse detailResponse = JsonMananger.getReponseResult(responseStr, VideoDetailResponse.class);
            if (!TextUtils.isEmpty(detailResponse.getData().getDetail().getCompete_name())) {
                mDanceViewHolder.getView(R.id.class_jieshao).setVisibility(View.VISIBLE);
                mDanceViewHolder.setText(R.id.jieshao, detailResponse.getData().getDetail().getCompete_name());
                View view = mDanceViewHolder.getView(R.id.tiao);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MatchDetailActivity.lunch(mContext, detailResponse.getData().getDetail().getCompete_id());
                    }
                });
            } else {
                mDanceViewHolder.getView(R.id.class_jieshao).setVisibility(View.GONE);
            }
            final String compete_id = detailResponse.getData().getDetail().getCompete_id();
            isCollected = (0 != detailResponse.getData().getCollection_id());
            otherList = detailResponse.getData().getOtherList();
            if (!MyStrUtil.isEmpty(otherList)) {
                videoAlbumAdapter.addList(otherList);
                View view = mDanceViewHolder.getView(R.id.ll);
                view.setVisibility(View.VISIBLE);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MatchVideoActivity.lunch(mContext, compete_id);
                    }
                });
            } else {
                mDanceViewHolder.getView(R.id.ll).setVisibility(View.GONE);
            }
            album = detailResponse.getData().getAlbum();
            if (!MyStrUtil.isEmpty(album)) {
                View view = mDanceViewHolder.getView(R.id.zhuanji);
                view.setVisibility(View.VISIBLE);
                SpecialItemAdapter itemAdapter = new SpecialItemAdapter(this);
                itemAdapter.addList(album);
                itemAdapter.setItemClickListener(this);
                rv.setAdapter(itemAdapter);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SpecialActivity.lunch(mContext, mItemId);
                    }
                });
            } else {
                mDanceViewHolder.getView(R.id.zhuanji).setVisibility(View.GONE);
            }
            mDanceViewHolder.setText(R.id.matchname_tv, detailResponse.getData().getDetail().getName());//视频名称
            mDanceViewHolder.setText(R.id.video_title, detailResponse.getData().getDetail().getName());//视频名称
            // setVideoDetail(detailResponse.getData().getDetail());
            detail = detailResponse.getData().getDetail();
            startPlayRtmp(detail);


        } else {
            StringResponse stringResponse = JsonMananger.getReponseResult(responseStr, StringResponse.class);
            NToast.shortToast(this, stringResponse.getData());
            isCollected = !isCollected;
        }
        if (isCollected) {
            mRightIv.setImageResource(R.drawable.collect_icon_002);
        } else {
            mRightIv.setImageResource(R.drawable.collect_icon);
        }
    }

    /**
     * 开始播放的方法
     * @return
     */
    private boolean startPlayRtmp(Video video) {
        String playUrl = video.getVideo();
        mShareContent = video.getName();
        shareUrl = String.format(AppConfigs.SHAREMOV, mItemId);
        Log.e("playUrl", "-->" + playUrl);
        //  String playUrl = "http://200024424.vod.myqcloud.com/200024424_709ae516bdf811e6ad39991f76a4df69.f20.mp4";
        if (TextUtils.isEmpty(playUrl)) {
            Toast.makeText(getApplicationContext(), "无播放地址", Toast.LENGTH_SHORT).show();
            return false;
        }

        mBtnPlay.setBackgroundResource(R.drawable.video_pause);
        fl.setBackgroundColor(0xff000000);
        mLivePlayer.setPlayerView(mPlayerView);

        mLivePlayer.setVodListener(this);
        //        mLivePlayer.setRate(1.5f);
        // 硬件加速在1080p解码场景下效果显著，但细节之处并不如想象的那么美好：
        // (1) 只有 4.3 以上android系统才支持
        // (2) 兼容性我们目前还仅过了小米华为等常见机型，故这里的返回值您先不要太当真
        //    mLivePlayer.enableHardwareDecode(mHWDecode);
        mLivePlayer.setRenderRotation(mCurrentRenderRotation);
        mLivePlayer.setRenderMode(mCurrentRenderMode);

        //        mPlayConfig.setPlayerType(TXVodPlayer.PLAYER_TYPE_EXO);
        Map<String, String> header = new HashMap<>();
        mPlayConfig.setHeaders(header);
        mLivePlayer.setConfig(mPlayConfig);
        mLivePlayer.setAutoPlay(true);
        int result = mLivePlayer.startPlay(playUrl); // result返回值：0 success;  -1 empty url;
        if (result != 0) {
            mBtnPlay.setBackgroundResource(R.drawable.video_resume);
            fl.setBackgroundResource(R.drawable.default_banner);
            return false;
        }

        Log.w("video render", "timetrack start play");

        startLoadingAnimation();

        mStartPlayTS = System.currentTimeMillis();

        /**
         * 视频点击隐藏状态栏
         */
        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int m = play_progress.getVisibility();
                mDanceViewHolder.getView(R.id.video_top).setVisibility(m == View.VISIBLE ? View.GONE : View.VISIBLE);
                play_progress.setVisibility(m == View.VISIBLE ? View.GONE : View.VISIBLE);
                play_progress.bringToFront();

            }
        });

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
        return true;
    }

    /**
     * 一切就绪 开始播放
     */
    public void playStatus() {

        mLivePlayer.resume();
        mBtnPlay.setBackgroundResource(R.drawable.video_pause);
        fl.setBackgroundColor(0xff000000);
        mVideoPlay = true;

    }

    /**
     * 一切就绪 停止播放
     */
    public void pauseStatus() {
        mLivePlayer.pause();
        mBtnPlay.setBackgroundResource(R.drawable.video_resume);
        mVideoPlay = false;

    }


    /**
     * 结束播放
     */
    private void stopPlayRtmp() {
        mBtnPlay.setBackgroundResource(R.drawable.video_resume);
        //  mRootView.setBackgroundResource(R.drawable.main_bkg);
        stopLoadingAnimation();
        if (mLivePlayer != null) {
            mLivePlayer.setVodListener(null);
            mLivePlayer.stopPlay(false);
        }
        mVideoPause = false;
        mVideoPlay = false;


    }

    /**
     * 开始加载的动画
     */
    private void startLoadingAnimation() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.VISIBLE);
            ((AnimationDrawable) mLoadingView.getDrawable()).start();
        }
    }

    /**
     * 结束加载的动画
     */
    private void stopLoadingAnimation() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
            ((AnimationDrawable) mLoadingView.getDrawable()).stop();
        }
    }


    @Override
    public void refresh() {
        super.refresh();
        initDatas();
    }

    @Override
    public void onHeadRightButtonClick(View v) {
        if (!UserInfoManager.getSingleton().isLoading(this)) {
            LoginActivity.lunch(this, 0x001);
        } else {
            String userId = UserInfoManager.getSingleton().getUserId(this);
            int operation = isCollected ? 1 : 2;
            Log.e("operation:v:", operation + "");
            Request<String> request = ParameterUtils.getSingleton().getCollectionMap(userId, mItemId, 10602, operation);
            request(AppConfigs.user_collection, request, false);
        }
    }

    @Override
    public void onBackPressed() {
       /* if (isFromCollectpage && !isCollected) {
            MyCollectActivity.lunch(this, true, mItemId);
            finish();
        } else {
            super.onBackPressed();
        }*/
        finish();
        super.onBackPressed();
    }

    @Override
    public void onHeadLeftButtonClick(View v) {
        onBackPressed();
    }

    public void onHeadRightButtonClick2(View v) {
        showShareDialog();
    }

    ShareUtils mShareUtils;

    private void showShareDialog() {

        if (mShareUtils == null) {
            mShareUtils = new ShareUtils(this);
        }
        mShareUtils.showShareDilaog(AppConfigs.CLICK_EVENT_18, shareUrl, mShareContent);
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
        Log.d(TAG, "onPause");
        if (mLivePlayer != null) {
            mLivePlayer.pause();
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (mLivePlayer != null) {
            mLivePlayer.resume();

        }
    }


    public static void lunch(Context context, String id) {
        lunch(context, id, false);

    }

    public static void lunchs(Context context, String id) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("itemid", id);
        context.startActivity(intent);
    }

    public static void lunch(Context context, String id, boolean isfromCollectPage) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        intent.putExtra("itemid", id);
        intent.putExtra("isfromcollectpage", isfromCollectPage);
        context.startActivity(intent);

    }

    @Override
    public void onPlayEvent(TXVodPlayer player, int event, Bundle param) {
        String playEventLog = "receive event: " + event + ", " + param.getString(TXLiveConstants.EVT_DESCRIPTION);
        Log.d(TAG, playEventLog);

        if (event == TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {
            if (mStartSeek) {
                return;
            }
            int progress = param.getInt(TXLiveConstants.EVT_PLAY_PROGRESS_MS);
            int duration = param.getInt(TXLiveConstants.EVT_PLAY_DURATION_MS);
            int playable = param.getInt(TXLiveConstants.EVT_PLAYABLE_DURATION_MS);

            if (mSeekBar != null) {
                mSeekBar.setProgress(progress);
                mSeekBar.setSecondaryProgress(playable);
                Log.d(TAG, player.toString() + " progress " + progress + " secondary progress " + playable);
            }
            if (mTextStart != null) {
                mTextStart.setText(String.format("%02d:%02d", progress / 1000 / 60, progress / 1000 % 60));
            }
            if (mTextDuration != null) {
                mTextDuration.setText(String.format("%02d:%02d", duration / 1000 / 60, duration / 1000 % 60));
            }
            if (mSeekBar != null) {
                mSeekBar.setMax(duration);
            }
            return;
        } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT || event == TXLiveConstants.PLAY_EVT_PLAY_END || event == TXLiveConstants.PLAY_ERR_FILE_NOT_FOUND) {
            mDanceViewHolder.getView(R.id.play_progress).setVisibility(View.GONE);
            mDanceViewHolder.getView(R.id.video_top).setVisibility(View.GONE);
            fl.setOnClickListener(null);
            btnvideo.setVisibility(View.VISIBLE);
            stopPlayRtmp();
            mVideoPlay = false;
            mVideoPause = false;
            if (mTextStart != null) {
                mTextStart.setText("00:00");
            }
            if (mSeekBar != null) {
                mSeekBar.setProgress(0);
            }
            btnvideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnvideo.setVisibility(View.GONE);
                    startPlayRtmp(detail);
                }
            });


        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_LOADING) {
            startLoadingAnimation();
        } else if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {
            stopLoadingAnimation();
        } else if (event == TXLiveConstants.PLAY_EVT_CHANGE_RESOLUTION) {
        } else if (event == TXLiveConstants.PLAY_ERR_HLS_KEY) {
            stopPlayRtmp();
        } else if (event == TXLiveConstants.PLAY_WARNING_RECONNECT) {
            startLoadingAnimation();
        }

        if (event < 0) {
            Toast.makeText(getApplicationContext(), "信号不好，请重试", Toast.LENGTH_SHORT).show();
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            stopLoadingAnimation();
        }
    }

    @Override
    public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

    }


    // TODO: 2018/1/13 这里开始是旋转逻辑 =========================

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
     * @param b
     *         true竖屏  false横屏
     */
    private void showOrHideView(boolean b) {

        mDanceViewHolder.getView(R.id.scroll).setVisibility(b ? View.VISIBLE : View.GONE);

        mDanceViewHolder.getTextView(R.id.matchname_tv).setVisibility(b ? View.VISIBLE : View.GONE);
        mBtnRenderRotation.setImageDrawable(b ? getResources().getDrawable(R.drawable.video_unfold) : getResources().getDrawable(R.drawable.video_packup));
        boolean utils = ScreenUtils.hasNavBar(this);
        if (b) {
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


    // TODO: 2018/1/25 0025 暂时没用

     /*   private void setVideoDetail(Video video) {

        videoView.resetPlayer();

        String Compete_name = video.getCompete_name();

        if (MyStrUtil.isEmpty(Compete_name)) {
            mDanceViewHolder.setViewVisibility(R.id.brief_tv, View.GONE);
        } else {
            mDanceViewHolder.setText(R.id.brief_tv, video.getCompete_name());
        }
        mDanceViewHolder.setText(R.id.type_tv, "赛事相关视频");
        Bundle mBundle = new Bundle();
        mBundle.putString(PlayerParams.KEY_PLAY_UUID, AppConfigs.KEY_PLAY_UUID);
        mBundle.putString(PlayerParams.KEY_PLAY_VUID, video.getVideo_unique());
        mBundle.putString(PlayerParams.KEY_PLAY_PU, AppConfigs.KEY_PLAY_PU);

        videoView.setPanorama(true);
        videoView.setDataSource(mBundle);
    }
*/

       /* @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
            setHeadVisibility(View.GONE);
        } else {
            setHeadVisibility(View.VISIBLE);
        }
       *//* if (videoView != null) {
            videoView.onConfigurationChanged(newConfig);
        }*//*
    }*/

    /**
     * 处理播放器本身事件，具体事件可以参见IPlayer类
     */
   /* private void handlePlayerEvent(int state, Bundle bundle) {
        switch (state) {
            case PlayerEvent.PLAY_VIDEOSIZE_CHANGED:
                *//**
     * 获取到视频的宽高的时候，此时可以通过视频的宽高计算出比例，进而设置视频view的显示大小。
     * 如果不按照视频的比例进行显示的话，(以surfaceView为例子)内容会填充整个surfaceView。
     * 意味着你的surfaceView显示的内容有可能是拉伸的
     *//*
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
                    //播放第一帧
                }
                break;
            default:
                break;
        }
    }*/

    /**
     * 处理视频信息类事件
     */
    /*private void handleVideoInfoEvent(int state, Bundle bundle) {
    }
*/
}
