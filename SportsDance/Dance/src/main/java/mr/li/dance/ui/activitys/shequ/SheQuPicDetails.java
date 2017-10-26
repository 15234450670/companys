package mr.li.dance.ui.activitys.shequ;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.base.BaseActivity;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/26 0026
 * 描述:       社区图片详情
 * 修订历史:
 */
public class SheQuPicDetails extends BaseActivity {
    private String TAG = getClass().getSimpleName();

    @Override
    public int getContentViewId() {
        return R.layout.pic_details_activity;
    }

    @Override
    public void initViews() {
        setTitle("图片详情");
    }

    public static void lunch(Context context, String id, String uid) {
        Intent intent = new Intent(context, SheQuPicDetails.class);
        intent.putExtra("itemId", id);
        intent.putExtra("uid", uid);
        context.startActivity(intent);

    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        String itemId = mIntentExtras.getString("itemId");
        String uid = mIntentExtras.getString("uid");
        Log.e(TAG, itemId);
    }
}
