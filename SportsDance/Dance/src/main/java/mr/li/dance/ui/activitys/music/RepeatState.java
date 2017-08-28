package mr.li.dance.ui.activitys.music;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import mr.li.dance.R;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/16 0016
 * 描述:播放状态控制
 * 修订历史:
 */

public class RepeatState implements OnClickListener {

    //ImageView
    private ImageView          tv_repeatMode;
    //播放模式集合
    private ArrayList<Integer> list;
    private MusicStatus        ms;
    /**
     * 现在选中的状态
     * 0，单曲循环
     * 1，列表循环
     * 2，随机播放
     */
    public int state = 1;
    Context mContext;

    public RepeatState(ImageView tv_repeatMode, Context context) {
        this.tv_repeatMode = tv_repeatMode;
        this.tv_repeatMode.setOnClickListener(this);
        this.mContext = context;
        list = new ArrayList<Integer>();
        list.add(R.drawable.one_circulation);
        list.add(R.drawable.item_circulation);
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
        if (state == 0) {
            Toast.makeText(mContext, "单曲循环", Toast.LENGTH_SHORT).show();
        } else if (state == 1) {
            Toast.makeText(mContext, "列表循环", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "随机播放", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        state++;
        changeState();
        if (ms != null) {
            ms.onStatus(state);
        }
    }

    public void setChange(int status) {
        state = status;
        tv_repeatMode.setImageResource(list.get(state));
    }

    public interface MusicStatus {
        void onStatus(int status);
    }

    public void setStatus(MusicStatus musicStatus) {
        this.ms = musicStatus;
    }

}
