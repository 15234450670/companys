package mr.li.dance.ui.activitys.newActivitys;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.SearchActivity;
import mr.li.dance.ui.activitys.base.BaseActivity;
import mr.li.dance.ui.activitys.music.PlayMusicActivity;
import mr.li.dance.utils.AppConfigs;

import static mr.li.dance.ui.activitys.MainActivity.myBinder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/8 0008
 * 描述:教学界面
 * 修订历史:
 */
public class TeachActivity extends BaseActivity {
    @Override
    public int getContentViewId() {
        return R.layout.new_fragment_list_layout;
    }

    @Override
    public void initDatas() {
        super.initDatas();
        //搜索按钮
        mDanceViewHolder.setClickListener(R.id.search_layout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(TeachActivity.this, AppConfigs.CLICK_EVENT_6);
                SearchActivity.lunch(TeachActivity.this);
            }
        });
        //音乐按钮
        mDanceViewHolder.setClickListener(R.id.btn_music, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myBinder!=null&&myBinder.binderIsPlaying()) {
                    PlayMusicActivity.lunch(TeachActivity.this);
                } else {
                    Toast.makeText(TeachActivity.this, "请去播放音乐", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void initViews() {
        setHeadVisibility(View.GONE);
    }


    public static void lunch(Context context) {
        Intent intent = new Intent(context, TeachActivity.class);
        context.startActivity(intent);
    }
}
