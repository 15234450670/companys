package mr.li.dance.ui.activitys.music;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

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
    Context mContext;
    /**
     * 现在选中的状态
     * 0，单曲循环
     * 1，列表循环
     * 2，随机播放
     */
    int state = 1;

    public RepeatState(ImageView tv_repeatMode, Context context) {
        this.mContext = context;
        this.tv_repeatMode = tv_repeatMode;
        this.tv_repeatMode.setOnClickListener(this);
        list = new ArrayList<Integer>();
        list.add(R.drawable.one_circulation);
        list.add(R.drawable.item_circulation);
        list.add(R.drawable.match_circulation);
        tv_repeatMode.setImageResource(list.get(state));
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void changeState() {
        if (state > list.size() - 1) {
            state = 0;
        }
        tv_repeatMode.setImageResource(list.get(state));
        if (state == 0) {
            Toast.makeText(mContext, "单曲循环", Toast.LENGTH_SHORT).show();
        }else if (state == 1){
            Toast.makeText(mContext, "列表循环", Toast.LENGTH_SHORT).show();
        } else if (state == 2) {
            Toast.makeText(mContext, "随机播放", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        state++;
        changeState();
    }

}
