package mr.li.dance.ui.activitys.music;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.base.BaseActivity;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/18 0018
 * 描述:
 * 修订历史:
 */
public class PlayMusicActivity extends BaseActivity {
    String title;
    @Override
    public int getContentViewId() {
        return R.layout.activity_playmusic;
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        title = mIntentExtras.getString("title");
        Log.e("ml", title + "");
    }

    @Override
    public void initViews() {
          setTitle(title);
    }
    public static void lunch(Context context, String title) {
        Intent intent = new Intent(context, PlayMusicActivity.class);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }
}
