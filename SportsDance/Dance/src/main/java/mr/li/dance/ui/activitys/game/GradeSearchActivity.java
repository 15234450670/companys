package mr.li.dance.ui.activitys.game;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.MyStrUtil;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/23 0023
 * 描述:       成绩搜索
 * 修订历史:
 */
public class GradeSearchActivity extends BaseListActivity implements View.OnClickListener{
    String mMatchId;
    int page = 1;

    @Override
    public void getIntentData() {
        super.getIntentData();
        mMatchId = mIntentExtras.getString("matchid");
    }

    @Override
    public void itemClick(int position, Object value) {

    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return null;
    }


    @Override
    public void initViews() {
        super.initViews();
        mDanceViewHolder.setClickListener(R.id.back_icon, this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.grade_activity;
    }

    public static void lunch(Context context, String mMatchId) {
        Intent intent = new Intent(context, GradeSearchActivity.class);
        intent.putExtra("matchid", mMatchId);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_icon:
                hintKbTwo();
                finish();
                break;
            case R.id.search_btn:
                String content = mDanceViewHolder.getTextValue(R.id.search_et);
                if (MyStrUtil.isEmpty(content)) {
                    Toast.makeText(mContext, "请输入要查询的内容", Toast.LENGTH_SHORT).show();
                } else {
                    Request<String> gameMapGradeSearch = ParameterUtils.getSingleton().getGameMapGradeSearch(mMatchId, content, String.valueOf(page));
                    request(AppConfigs.home_music,gameMapGradeSearch,false);
                }

                hintKbTwo();
                break;

        }
    }


    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);

    }

    /**
     * 关闭软键盘
     */
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
