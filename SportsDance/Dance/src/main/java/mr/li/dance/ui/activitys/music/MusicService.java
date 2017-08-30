package mr.li.dance.ui.activitys.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

import mr.li.dance.models.GeDanInfo;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/18
 * 描述:   服务控制类
 * 修订历史:
 */
public class MusicService extends Service {

    private MediaPlayer                       mp;
    private List<GeDanInfo.DataBean.ListBean> musicList;      //数据集合
    private int position = -1;           //当前播放位置
    private int       totalTime;                 //总进度
    private int       currentPosition;         //暂停后保存进度
    private MpStarted ms;

    private int STATE = 1;

    public static final int RANDOM   = 2;
    public static final int SINGLE   = 0;
    public static final int PLAYLIST = 1;
    public static boolean isPlay;    //播放状态
    public        String  id;             //记录歌单ID

    public interface MpStarted {
        void onStart(int totalT);

    }

    public void playStatus() {
        switch (STATE) {
            case RANDOM:
                int i = PlayerUtiles.random(musicList.size(), position);
                position = i;
                playMusic(position);
                break;
            case SINGLE:
                mp.start();
                break;
            case PLAYLIST:
                NextMusic();
                break;
        }
    }

    public void Random() {
        int i = PlayerUtiles.random(musicList.size(), position);
        position = i;
        playMusic(position);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mp == null) {
            mp = new MediaPlayer();
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    totalTime = mediaPlayer.getDuration();
                    mediaPlayer.start();
                    if (ms != null) {
                        ms.onStart(totalTime);
                    }
                }
            });
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    playStatus();
                }
            });
            mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    //     playerReset();
                    return false;
                }
            });
        }
    }

    public void mpReset() {
        mp.reset();
    }

    @Override
    public IBinder onBind(Intent intent) {
        MyBinder mb = new MyBinder();
        return mb;
    }

    //播放音乐 返回总时长
    public int playMusic(int position) {
        this.position = position;
        if (mp != null) {
            mp.reset();
            try {
                isPlay = true;
                Log.e("xxx", musicList.toString());
                mp.setDataSource(musicList.get(position).getMusic_address());
                mp.prepareAsync();

            } catch (Exception e) {
                return -2;
            }
            return 1;
        }
        return -1;
    }

    //播放或暂停
    public boolean startOrPause() {
        if (mp != null) {
            if (mp.isPlaying()) {
                isPlay = false;
                currentPosition = mp.getCurrentPosition();
                mp.pause();
            } else {
                isPlay = true;
                mp.start();
            }
            return mp.isPlaying();
        }
        return false;
    }

    //停止播放
    public void stop() {
        if (mp != null) {
            isPlay = false;
            mp.stop();

        }
    }

    //暂停
    public void Pause() {
        if (mp != null) {
            currentPosition = mp.getCurrentPosition();
            isPlay = false;
            mp.pause();
        }
    }


    public int getSDuration() {
        if (mp != null) {
            return mp.getDuration();
        }
        return -1;
    }

    //获得已播放的时长
    public int getCurrentPosition() {
        if (mp != null) {
            if (mp.isPlaying()) {
                return mp.getCurrentPosition();
            } else {
                return -1;
            }
        }
        return -2;
    }

    //选择播放位置
    public void playerSeekTo(int porgress) {
        if (mp != null) {
            mp.seekTo(porgress);
        }
    }

    //释放资源
    public void release() {
        if (mp.isPlaying()) {
            mp.stop();
        }
        mp.release();
        mp = null;
    }

    //获取播放状态
    public boolean isPlaying() {
        if (mp != null) {
            return mp.isPlaying();
        }
        return false;
    }

    //重置播放器
    public void playerReset() {
        if (mp != null) {
            mp.reset();
        }
    }

    //开始播放
    public void startPlay() {
        if (mp != null) {
            mp.start();

        }
    }

    public List<GeDanInfo.DataBean.ListBean> getMusicList() {
        return musicList;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getCurrentTime() {
        return currentPosition;
    }

    public boolean firstPlay() {
        if (position < 0) {
            playMusic(0);
            position = 0;
            return true;
        } else {
            return startOrPause();
        }
    }

    public String getTitle() {
        if (position < 0) {
            return "";
        } else {
            return musicList.get(position).getTitle();
        }
    }

    /**
     * 上一首
     */
    public void UpMusic() {
        if (position == 0) {
            position = musicList.size() - 1;
        } else {
            position--;
        }
        upAndNext(position);
    }

    /**
     * 播放
     * @param position
     */
    public void upAndNext(int position) {
        switch (STATE) {
            case SINGLE:
            case PLAYLIST:
                playMusic(position);
                break;
            case RANDOM:
                Random();
                break;
        }
    }

    /**
     * 下一首
     */
    public void NextMusic() {
        position++;
        if (position == musicList.size()) {
            position = 0;
        }
        upAndNext(position);
    }

    public void setMusicList(List<GeDanInfo.DataBean.ListBean> musicList, String listId) {
        if (id == null) {
            id = listId;
            this.musicList = musicList;
        }
    }

    public void setList(List<GeDanInfo.DataBean.ListBean> musicList, String id) {
        this.id = id;
        this.musicList = musicList;
    }

    public boolean isSameList(String listId) {
        return id == null ? false : id.equals(listId);
    }

    public class MyBinder extends Binder {


        public void mRandom() {
            Random();
        }

        public void setStatus(int status) {
            STATE = status;
        }

        public int getStatus() {
            return STATE;
        }

        public boolean mIsSameList(String id) {
            return isSameList(id);
        }

        public void mSetList(List<GeDanInfo.DataBean.ListBean> musicList, String id) {
            setList(musicList, id);
        }

        public void mSetMusicList(List<GeDanInfo.DataBean.ListBean> musicList, String listId) {
            setMusicList(musicList, listId);
        }

        public void mUpMusic() {
            UpMusic();
        }

        public void mNextMusic() {
            NextMusic();
        }

        public void setMs(MpStarted st) {
            ms = st;
        }

        public String mGetTitle() {
            return getTitle();
        }

        public boolean mFirstPlay() {
            return firstPlay();
        }

        public int mGetTotalTime() {
            return getTotalTime();
        }

        public int mGetCurrentTime() {
            return getCurrentTime();
        }


        public void mReset() {
            mpReset();
        }

        public List<GeDanInfo.DataBean.ListBean> mGetMusicList() {
            return getMusicList();
        }

        public int mGetPosition() {
            return getPosition();
        }

        public void mSetPosition(int position) {
            setPosition(position);
        }

        //开始播放
        public void binderStartPlay() {
            startPlay();
        }

        //重置播放器
        public void binderPlayerReset() {
            playerReset();
        }

        //获取播放状态
        public boolean binderIsPlaying() {
            return isPlaying();
        }

        //释放资源
        public void binderRelease() {
            release();
        }

        //播放 返回总长
        public int binderPlay(int pos) {
            return playMusic(pos);
        }

        //停止
        public void binderStop() {
            stop();
        }

        public void binderPause() {
            Pause();
        }

        //暂停或开始 返回当前状态
        public boolean binderStartOrPause() {
            return startOrPause();
        }

        //获得已播放时长
        public int binderGetCurrentPosition() {
            return getCurrentPosition();
        }

        //拉动
        public void binderPlayerSeekTo(int porgress) {
            playerSeekTo(porgress);
        }

        public int getBduration() {
            return getSDuration();
        }

    }
}
