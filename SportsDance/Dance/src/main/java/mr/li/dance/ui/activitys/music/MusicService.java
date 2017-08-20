package mr.li.dance.ui.activitys.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

/**
 * 类的用途：音乐服务
 * Created by ：杨珺达
 * date：2017/8/19
 */

public class MusicService extends Service {

    private MediaPlayer mp;
    private onPlayerCompletion opc;

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
    public int playMusic(String path){
        if(mp!=null&&path!=null){
            mp.reset();
            try {
                mp.setDataSource(path);
                mp.prepareAsync();
                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
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

    public class MyBinder extends Binder {

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
        public int binderPlay(String path){
            return playMusic(path);
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
