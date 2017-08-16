package mr.li.dance.ui.activitys.music;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.base.BaseListActivity;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/16 0016
 * 描述:歌单界面
 * 修订历史:
 */
public class SongActivity extends BaseListActivity {
    String mItemId;
    @Override
    public void itemClick(int position, Object value) {

    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return null;
    }

    @Override
    public void initDatas() {
        super.initDatas();

    }

    @Override
    public void initViews() {
        super.initViews();
        setTitle("歌单");
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_gedan;
    }
    public static void lunch(Context context, String mItemId) {
        Intent intent = new Intent(context, SongActivity.class);
        intent.putExtra("itemid", mItemId);
        context.startActivity(intent);
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        mItemId = mIntentExtras.getString("itemid");
        Log.e("ml",mItemId+"");
    }
}
