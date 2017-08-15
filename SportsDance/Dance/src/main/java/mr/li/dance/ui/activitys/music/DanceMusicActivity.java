package mr.li.dance.ui.activitys.music;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.adapters.DanceMusicAdapter;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/14 0014
 * 描述: 舞蹈界面
 * 修订历史:
 */
public class DanceMusicActivity extends BaseListActivity {
    DanceMusicAdapter adapter;

    @Override
    public void itemClick(int position, Object value) {
    }


    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new DanceMusicAdapter(this);
        return adapter;
    }

    @Override
    public void initViews() {
        super.initViews();
        setTitle("舞蹈");
        mRecyclerview.setLayoutManager(new GridLayoutManager(this,3));

    }

    @Override
    public int getContentViewId() {
        return R.layout.dancemusic_activity;
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
    }

    public static  void lunch(Context context) {
        Intent intent = new Intent(context,DanceMusicActivity.class);
        context.startActivity(intent);
    }

}
