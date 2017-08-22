package mr.li.dance.ui.activitys.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

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

    private MediaPlayer mp;
    private onPlayerCompletion opc;
    private List<GeDanInfo.DataBean.ListBean> musicList;
    private int position = -1;
    private int totalTime;
    private int currentPosition;
    private MpStarted ms;


    public interface MpStarted{
        void onStart(int totalT);
    }

    @Override
    public IBinder onBind(Intent intent) {
        if(mp==null){
            mp = new MediaPlayer();
        }
        MyBinder mb = new MyBinder();
        //给MediaPlayer添加结束后的监听
        mb.playerCompletion();
        return mb;
    }

    //播放结束后的接口回调
    public interface onPlayerCompletion{
        void onCompletion();
    }

    //播放音乐 返回总时长
    public int playMusic(String path, int position){
        this.position = position;
        if(mp!=null&&path!=null){
            mp.reset();
            try {
                mp.setDataSource(path);
                mp.prepareAsync();
                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                        totalTime = mediaPlayer.getDuration();
                        if(ms != null){
                            ms.onStart(totalTime);
                        }
                    }
                });
            } catch (Exception e) {
                return -2;
            }
            return mp.getDuration();
        }
        return -1;
    }

    //播放或暂停
    public boolean startOrPause(){
        if(mp!=null){
            if(mp.isPlaying()){
                currentPosition = mp.getCurrentPosition();
                mp.pause();
            }else{
                mp.start();
            }
            return mp.isPlaying();
        }
        return false;
    }

    //停止播放
    public void stop(){
        if(mp!=null){
            mp.stop();
        }
    }
    //暂停播放
    public void Pauses(){
        if(mp!=null){
            mp.pause();
        }
    }

    public int getSDuration(){
        if(mp!=null){
            return mp.getDuration();
        }
        return -1;
    }

    //获得已播放的时长
    public int getCurrentPosition(){
        if(mp!=null){
            if(mp.isPlaying()){
                return mp.getCurrentPosition();
            }else{
                return -1;
            }
        }
        return -2;
    }

    //选择播放位置
    public void playerSeekTo(int porgress){
        if(mp!=null){
            mp.seekTo(porgress);
        }
    }

    //释放资源
    public void release(){
        if(mp.isPlaying()){
            mp.stop();
        }
        mp.release();
        mp = null;
    }

    //获取播放状态
    public boolean isPlaying(){
        if(mp!=null){
            return mp.isPlaying();
        }
        return false;
    }

    //重置播放器
    public void playerReset(){
        if(mp!=null){
            mp.reset();
        }
    }

    //开始播放
    public void startPlay(){
        if(mp!=null){
            mp.start();

        }
    }

    public List<GeDanInfo.DataBean.ListBean> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<GeDanInfo.DataBean.ListBean> musicList) {
        this.musicList = musicList;
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

    public int getCurrentTime(){
        return currentPosition;
    }

    public boolean firstPlay(){
        if(position < 0){
            playMusic(musicList.get(0).getMusic_address(), 0);
            position = 0;
            return true;
        } else {
            return startOrPause();
        }
    }

    public String getTitle(){
        if(position < 0){
            return "";
        } else {
            return musicList.get(position).getTitle();
        }
    }

    /**
     * 上一首
     */
    public void UpMusic(String path, int position){
        // stop();
        Pauses();
            if(position==0){
                playMusic(path,musicList.size()-1);
            }else{
                playMusic(path,position-1);
            }
    }

    /**
     * 下一首
     */
    public void NextMusic(String path, int position){
        // stop();
        Pauses();
        if(position==musicList.size()-1){
            playMusic(path,0);
        }else{
            playMusic(path,position+1);
        }
    }


    public class MyBinder extends Binder {
        public void mPause(){
            Pauses();
        }

        public void mUpMusic(String path, int position){
            UpMusic(path,position);
        }
        public void mNextMusic(String path, int position){
            NextMusic(path,position);
        }

        public void setMs(MpStarted st){
            ms = st;
        }

        public String mGetTitle(){
            return getTitle();
        }

        public boolean mFirstPlay(){
            return firstPlay();
        }

        public int mGetTotalTime() {
            return getTotalTime();
        }

        public int mGetCurrentTime(){
            return getCurrentTime();
        }

        public void mSetMusicList(List<GeDanInfo.DataBean.ListBean> musicList){
            setMusicList(musicList);
        }

        public List<GeDanInfo.DataBean.ListBean> mGetMusicList(){
            return getMusicList();
        }

        public int mGetPosition(){
            return getPosition();
        }

        public void mSetPosition(int position){
            setPosition(position);
        }

        //播放结束后的选择
        public void getPlayerCompletion(onPlayerCompletion bopc){
            opc = bopc;
        }

        public void playerCompletion(){

            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    if(opc!=null){
                        opc.onCompletion();
                    }
                }
            });
        }

        //开始播放
        public void binderStartPlay(){
            startPlay();
        }

        //重置播放器
        public void binderPlayerReset(){
            playerReset();
        }

        //获取播放状态
        public boolean binderIsPlaying(){
            return isPlaying();
        }

        //释放资源
        public void binderRelease(){
            release();
        }

        //播放 返回总长
        public int binderPlay(String path, int pos){
            return playMusic(path, pos);
        }

        //停止
        public void binderStop(){
            stop();
        }

        //暂停或开始 返回当前状态
        public boolean binderStartOrPause(){
            return startOrPause();
        }

        //获得已播放时长
        public int binderGetCurrentPosition(){
            return getCurrentPosition();
        }

        //拉动
        public void binderPlayerSeekTo(int porgress){
            playerSeekTo(porgress);
        }

        public int getBduration(){
            return getSDuration();
        }

    }
}
