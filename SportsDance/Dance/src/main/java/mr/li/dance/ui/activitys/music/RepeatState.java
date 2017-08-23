package mr.li.dance.ui.activitys.music;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import java.util.ArrayList;

import mr.li.dance.R;

/**
 * 控制播放状态的类
 */

public class RepeatState implements OnClickListener {

    //ImageView
    private ImageView          tv_repeatMode;
    //播放模式集合
    private ArrayList<Integer> list;
    private MusicStatus ms;
    /**
     * 现在选中的状态
     * 0，单曲循环
     * 1，列表循环
     * 2，随机播放
     */
    public int state = 1;

    public RepeatState(ImageView tv_repeatMode) {
        this.tv_repeatMode = tv_repeatMode;
        this.tv_repeatMode.setOnClickListener(this);
        list = new ArrayList<Integer>();
        list.add(R.drawable.item_circulation);
        list.add(R.drawable.one_circulation);
        list.add(R.drawable.match_circulation);
        tv_repeatMode.setImageResource(list.get(state));
    }

    public int getState() {
        return state;
    }

    public void setState(int states) {
        this.state = states;
    }

    public void changeState() {
        if (state > list.size() - 1) {
            state = 0;
        }
        tv_repeatMode.setImageResource(list.get(state));
    }

    @Override
    public void onClick(View v) {
        state++;
        changeState();
        if(ms != null){
            ms.onStatus(state);
        }
    }

    public void setChange(int status){
        state = status;
        tv_repeatMode.setImageResource(list.get(state));
    }

    public interface MusicStatus{
        void onStatus(int status);
    }

    public void setStatus(MusicStatus musicStatus){
        this.ms = musicStatus;
    }

}
