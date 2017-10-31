package mr.li.dance.ui.activitys.shequ;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import mr.li.dance.R;
import mr.li.dance.ui.TXT.PostVideoActivity;
import mr.li.dance.ui.activitys.base.BaseActivity;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/30 0030
 * 描述:录制
 * 修订历史:
 */
public class TranscribeVideo extends BaseActivity implements View.OnClickListener {
    @Override
    public int getContentViewId() {
        return R.layout.transcribe_activity;
    }

    @Override
    public void initViews() {
        setTitle("视频");
    }

    @Override
    public void initDatas() {
        super.initDatas();
        mDanceViewHolder.getButton(R.id.nect_btn).setOnClickListener(this);

    }

    public static void lunch(Context context) {
        Intent intent = new Intent(context, TranscribeVideo.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nect_btn:
                PostVideoActivity.lunch(this);
                break;
        }
    }
}
