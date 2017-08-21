package mr.li.dance.ui.activitys.music;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yolanda.nohttp.error.ServerError;

import java.text.SimpleDateFormat;
import java.util.List;

import mr.li.dance.R;
import mr.li.dance.models.GeDanInfo;
import mr.li.dance.ui.activitys.base.BaseActivity;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.glide.ImageLoaderManager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/18
 * 描述:    播放界面
 * 修订历史:
 */
public class PlayMusicActivity extends BaseActivity implements BasePopwindow.PopAction {
    SingListBean          bean;
    MusicService.MyBinder myBinder;
    private SeekBar seek;

    private int mPosition;
    SimpleDateFormat sdf     = new SimpleDateFormat("m:ss");
    Handler          handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what != 0) {
                int totalTime = myBinder.getBduration();
                seek.setMax(totalTime);
                tv_right.setText(sdf.format(totalTime));
                handler.sendEmptyMessageDelayed(0, 500);
            } else {
                int k = myBinder.binderGetCurrentPosition();
                if (k > 0) {
                    seek.setProgress(k);
                }
                handler.sendEmptyMessageDelayed(0, 500);
            }

            return true;
        }
    });
    private TextView                          tv_left;
    private TextView                          tv_right;
    private List<GeDanInfo.DataBean.ListBean> list;
    private ImageView                         playing_play;
    private SingListPop                       singPop;
    private TextView                          music_title;
    private int                               totalTime;
    private ObjectAnimator                    anim1;
    private ObjectAnimator                    anim2;

    @Override
    public int getContentViewId() {
        return R.layout.activity_playmusic;
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        String json = mIntentExtras.getString("title");
        try {
            bean = JsonMananger.jsonToBean(json, SingListBean.class);
        } catch (ServerError serverError) {
            serverError.printStackTrace();
        }
        Log.e("ml", json + "");
    }

    @Override
    public void initDatas() {
        super.initDatas();
        Rotate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void initViews() {

        ServiceConn conn = new ServiceConn();

        if(SongActivity.imageUrl != null){
            ImageLoaderManager.getSingleton().LoadCircle(PlayMusicActivity.this, SongActivity.imageUrl, mDanceViewHolder.getImageView(R.id.image_pic), R.drawable.icon_mydefault);
        }else {

        }

        seek = (SeekBar) findViewById(R.id.seekBar);

        tv_left = (TextView) findViewById(R.id.time_left);
        tv_right = (TextView) findViewById(R.id.time_right);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            boolean user = false;

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (user) {
                    myBinder.binderPlayerSeekTo(seekBar.getProgress());
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacksAndMessages(null);
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                user = fromUser;
                tv_left.setText(sdf.format(progress));
            }
        });

        playing_play = (ImageView) findViewById(R.id.playing_play);
        playing_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playing_play.setSelected(myBinder.binderStartOrPause());
                Log.e("xxx", myBinder.binderStartOrPause() + "");
                if (myBinder.binderStartOrPause()) {
                    anim1.start();
                    anim2.start();

                } else {
                    anim1.end();
                    anim2.end();

                }
            }
        });

        conn.getMyBinder(new ServiceConn.binderCreateFinish() {
            @Override
            public void binderHasCreated(MusicService.MyBinder mb) {
                myBinder = mb;
                if(myBinder.mGetPosition() > -1){
                    totalTime = myBinder.getBduration();
                    seek.setMax(totalTime);
                    music_title.setText(myBinder.mGetTitle());
                    tv_right.setText(sdf.format(totalTime));
                    playing_play.setSelected(myBinder.binderIsPlaying());
                    Log.e("xxxxxxx", myBinder.binderIsPlaying() + "");
                    if (myBinder.binderIsPlaying()) {
                        anim1.start();
                        anim2.start();
                    }
                    if(myBinder.binderIsPlaying()){
                        int l = myBinder.binderGetCurrentPosition();
                        seek.setProgress(l);
                        handler.sendEmptyMessage(0);
                    } else {
                        seek.setProgress(myBinder.mGetCurrentTime());
                        handler.sendEmptyMessage(0);
                    }
                }
                myBinder.setMs(new MusicService.MpStarted() {
                    @Override
                    public void onStart(int totalT) {
                        totalTime = myBinder.getBduration();
                        seek.setMax(totalTime);
                        tv_right.setText(sdf.format(totalTime));
                        music_title.setText(myBinder.mGetTitle());
                        playing_play.setSelected(true);
                    }
                });
            }
        });
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);

        setTitle(SongActivity.allTitle);
        music_title = (TextView) findViewById(R.id.title);
        findViewById(R.id.playing_playlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singPop.showBottom();
            }
        });
        /**
         * 上一首
         */
        findViewById(R.id.playing_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        /**
         * 下一首
         */
        findViewById(R.id.playing_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        /**
         * 控制播放类型
         */
        findViewById(R.id.playing_mode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        singPop = new SingListPop(this);
        singPop.list = list;
        singPop.setAction(this);

    }

    private void Rotate() {
        ImageView imageView = (ImageView) findViewById(R.id.imageView6);
        ImageView image_pic = (ImageView) findViewById(R.id.image_pic);
        anim1 = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f);
        anim2 = ObjectAnimator.ofFloat(image_pic, "rotation", 0f, 360f);
        anim1.setDuration(3500);
        anim1.setInterpolator(new LinearInterpolator());
        anim1.setRepeatCount(ValueAnimator.INFINITE);
        anim2.setDuration(3500);
        anim2.setInterpolator(new LinearInterpolator());
        anim2.setRepeatCount(ValueAnimator.INFINITE);

    }


    public static void lunch(Context context, String title) {
        Intent intent = new Intent(context, PlayMusicActivity.class);
        intent.putExtra("title", title);
        Activity a = (Activity) context;
        a.startActivityForResult(intent, 750);
    }
    public static void lunch(Context context) {
        Intent intent = new Intent(context, PlayMusicActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onAction(int type, Object o) {
        if (type != BasePopwindow.POP_DISMISS && type != BasePopwindow.BUTTON_CANCEL) {
            mPosition = type;
            myBinder.binderPlay(list.get(type).getMusic_address(), type);
            playing_play.setSelected(true);
            music_title.setText(list.get(type).getTitle());
            handler.sendEmptyMessageDelayed(1, 3000);
        }
    }

    @Override
    public void onHeadLeftButtonClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("position", mPosition);
        setResult(500, intent);
        super.onHeadLeftButtonClick(v);
    }
}
