package mr.li.dance.ui.fragments.video;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mr.li.dance.R;
import mr.li.dance.models.ZhiBoBean;
import mr.li.dance.ui.activitys.game.GameDetailActivity;
import mr.li.dance.ui.activitys.video.ZhiBoDetailActivity;
import mr.li.dance.ui.adapters.LiveImageAdapter;
import mr.li.dance.ui.fragments.BaseFragment;
import mr.li.dance.ui.widget.FullyLinearLayoutManager;
import mr.li.dance.utils.MyStrUtil;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/5/23 0023
 * 描述:
 * 修订历史:
 */
public class IntroduceFragment extends BaseFragment implements ITXVodPlayListener, View.OnClickListener {
    private ZhiBoBean.DataBean.AdVideoBean            adVideo;
    private ArrayList<ZhiBoBean.DataBean.AdWlinkBean> adWlink;

    private String brief;
    private String title;
    private Bundle arguments;
    boolean isFlag;
    private TextView         t;
    private TextView         tv;
    private int              isLive;
    private int              mCurrentRenderMode;
    private int              mCurrentRenderRotation;
    private TXVodPlayConfig  mPlayConfig;
    private TXCloudVideoView mPlayerView;
    private LinearLayout     play_progress;
    private Button           btnvideo;
    private TXVodPlayer mLivePlayer = null;
    private ImageView mLoadingView;
    private Button    mBtnPlay;
    private SeekBar   mSeekBar;
    private TextView  mTextStart;
    private TextView  mTextDuration;
    private boolean mStartSeek = false;
    private FrameLayout frame;
    private long mStartPlayTS = 0;
    private boolean mVideoPlay;
    private boolean mVideoPause = false;
    private ImageView    p;
    private RecyclerView rv;
    private String       mId;
    private String       name;

    @Override
    public int getContentView() {
        return R.layout.introduce_zhibo;
    }

    @Override
    public void initVideo() {
        super.initVideo();
        mCurrentRenderMode = TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN;
        mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT;
        mPlayConfig = new TXVodPlayConfig();
    }

    @Override
    public void initData() {
        arguments = getArguments();
        if (arguments == null) {
            return;
        }
        isLive = arguments.getInt("isLive");
        mId = arguments.getString("mId");
        name = arguments.getString("name");
        adVideo = (ZhiBoBean.DataBean.AdVideoBean) arguments.getSerializable("adVideo");
        adWlink = arguments.getParcelableArrayList("adWlink");
        brief = arguments.getString("brief");
        title = arguments.getString("title");
        Log.e("isLive->", isLive + "**" + mId + "--" + name + "///" + brief + "..." + title);
    }

    @Override
    public void initViews() {
        TextView textView = danceViewHolder.getTextView(R.id.text1);
        textView.setOnClickListener(this);
        frame = (FrameLayout) danceViewHolder.getView(R.id.video_frame);
        if (!MyStrUtil.isEmpty(name)) {
            View view = danceViewHolder.getView(R.id.class_jieshao);
            view.setVisibility(View.VISIBLE);
            danceViewHolder.setText(R.id.matchname_tv, name);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GameDetailActivity.lunch(getActivity(), mId);
                }
            });

        }
        rv = (RecyclerView) danceViewHolder.getView(R.id.rv);
        //  rv.setNestedScrollingEnabled(false);
        FullyLinearLayoutManager manager1 = new FullyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager1);
        LiveImageAdapter adapter = new LiveImageAdapter(getActivity());
        if (!MyStrUtil.isEmpty(adWlink)) {
            adapter.addList(adWlink);
            rv.setAdapter(adapter);
        }
        t = danceViewHolder.getTextView(R.id.t);
        tv = danceViewHolder.getTextView(R.id.tv);
        p = danceViewHolder.getImageView(R.id.p);
        t.setText(title);
        t.setMaxLines(1);
        t.setEllipsize(TextUtils.TruncateAt.END);
        tv.setVisibility(View.GONE);
        if (!MyStrUtil.isEmpty(brief)) {
            p.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isFlag) {
                        p.setBackgroundResource(R.drawable.up_live);
                        tv.setText(brief);
                        t.setEllipsize(null);// 展开
                        t.setSingleLine(isFlag);
                        tv.setVisibility(View.VISIBLE);
                        isFlag = true;
                    } else {
                        isFlag = false;
                        p.setBackgroundResource(R.drawable.down_live);
                        t.setMaxLines(1);
                        t.setEllipsize(TextUtils.TruncateAt.END);
                        tv.setVisibility(View.GONE);
                    }
                }
            });
        }

        mPlayerView = (TXCloudVideoView) danceViewHolder.getView(R.id.video_view);
        play_progress = (LinearLayout) danceViewHolder.getView(R.id.play_progress);
        btnvideo = danceViewHolder.getButton(R.id.btnvideo);
        registerForContextMenu(getActivity().findViewById(R.id.btnPlay));

        if (mLivePlayer == null) {
            mLivePlayer = new TXVodPlayer(getActivity());
        }
        mLoadingView = danceViewHolder.getImageView(R.id.loadingImageView);
        mBtnPlay = danceViewHolder.getButton(R.id.btnPlay);

        mTextStart = danceViewHolder.getTextView(R.id.play_start);
        mTextDuration = danceViewHolder.getTextView(R.id.duration);
        mTextDuration.setTextColor(Color.rgb(255, 255, 255));
        mTextStart.setTextColor(Color.rgb(255, 255, 255));


        /**
         * 进度条
         */

        mSeekBar = (SeekBar) danceViewHolder.getView(R.id.seekbar);
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
        /**
         * 判断是否直播中
         * @param startTime
         * @param endTime
         * @return -1 直播未开始 0 正在直播  -1 直播一结束
         */

        if (!MyStrUtil.isEmpty(adVideo) && isLive != 0) {
            frame.setVisibility(View.VISIBLE);
            startPlayRtmp(adVideo.getUrl());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text1:
                ZhiBoDetailActivity.viewPager.setCurrentItem(1);
                break;
        }
    }

    private boolean startPlayRtmp(String playUrl) {
        if (TextUtils.isEmpty(playUrl)) {
            Toast.makeText(getActivity(), "无播放地址", Toast.LENGTH_SHORT).show();
            return false;
        }

        mBtnPlay.setBackgroundResource(R.drawable.video_pause);
        frame.setBackgroundColor(0xff000000);
        mLivePlayer.setPlayerView(mPlayerView);
        mLivePlayer.setVodListener(this);
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
            frame.setBackgroundResource(R.drawable.default_banner);
            return false;
        }
        Log.w("video render", "timetrack start play");

        startLoadingAnimation();

        mStartPlayTS = System.currentTimeMillis();

        /**
         * 视频点击隐藏状态栏
         */
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int m = play_progress.getVisibility();
                //                danceViewHolder.getView(R.id.video_top).setVisibility(m == View.VISIBLE ? View.GONE : View.VISIBLE);
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
        frame.setBackgroundColor(0xff000000);
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
    public void onDestroyView() {
        super.onDestroyView();
        if (mLivePlayer != null) {
            mLivePlayer.stopPlay(true);
            mLivePlayer = null;
        }
        if (mPlayerView != null) {
            mPlayerView.onDestroy();
            mPlayerView = null;
        }


        mPlayConfig = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("pause", "pause----->");
        pauseStatus();
    }


    @Override
    public void onResume() {
        super.onResume();

        //pauseStatus();
    }


    @Override
    public void onPlayEvent(TXVodPlayer player, int event, Bundle param) {
        String playEventLog = "receive event: " + event + ", " + param.getString(TXLiveConstants.EVT_DESCRIPTION);
        // Log.d(TAG, playEventLog);

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
                //  Log.d(TAG, player.toString() + " progress " + progress + " secondary progress " + playable);
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
            danceViewHolder.getView(R.id.play_progress).setVisibility(View.GONE);
            //   danceViewHolder.getView(R.id.video_top).setVisibility(View.GONE);
            frame.setOnClickListener(null);
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
                    startPlayRtmp(adVideo.getUrl());
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
            Toast.makeText(getActivity(), "信号不好，请重试", Toast.LENGTH_SHORT).show();
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            stopLoadingAnimation();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("isVisibleToUser", isVisibleToUser + "");
        if (!isVisibleToUser) {
            if (mLivePlayer != null) {

                pauseStatus();
            }
        }
    }


    @Override
    public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

    }


}
