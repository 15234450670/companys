package mr.li.dance.ui.activitys.game;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.yolanda.nohttp.rest.Request;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.GameGradeSearchResponse;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.activitys.match.ScoreFromActivity;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/23 0023
 * 描述:       成绩搜索
 * 修订历史:
 */
public class GradeSearchActivity extends BaseListActivity implements View.OnClickListener {
    String mMatchId;
    int page = 1;
    private String content;
    GradeSearchAdapter adapter;
    private View view;
    boolean isList;

    @Override
    public void getIntentData() {
        super.getIntentData();
        mMatchId = mIntentExtras.getString("matchid");
    }

    @Override
    public void itemClick(int position, Object value) {
        GameGradeSearchResponse.DataBean value1 = (GameGradeSearchResponse.DataBean) value;
        ScoreFromActivity.lunch(this, mMatchId, value1.getGroup_name());
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new GradeSearchAdapter(this);
        adapter.setItemClickListener(this);
        return adapter;
    }


    @Override
    public void initViews() {
        super.initViews();
        setHeadVisibility(View.GONE);
        mDanceViewHolder.setClickListener(R.id.back_icon, this);
        mDanceViewHolder.setClickListener(R.id.search_btn, this);
        view = mDanceViewHolder.getView(R.id.wu);
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
        switch (v.getId()) {
            case R.id.back_icon:
                hintKbTwo();
                finish();
                break;
            case R.id.search_btn:
                content = mDanceViewHolder.getTextValue(R.id.search_et);
                if (MyStrUtil.isEmpty(content)) {
                    Toast.makeText(mContext, "请输入要查询的内容", Toast.LENGTH_SHORT).show();
                } else {
                    Request<String> gameMapGradeSearch = ParameterUtils.getSingleton().getGameMapGradeSearch(mMatchId, content, String.valueOf(page));
                    request(AppConfigs.home_music, gameMapGradeSearch, false);
                }

                hintKbTwo();
                break;

        }
    }


    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e("what", response);
        GameGradeSearchResponse reponseResult = JsonMananger.getReponseResult(response, GameGradeSearchResponse.class);
        List<GameGradeSearchResponse.DataBean> data = reponseResult.getData();
        if (MyStrUtil.isEmpty(data) && !isList) {
            view.setVisibility(View.VISIBLE);
            return;
        } else {
            view.setVisibility(View.GONE);
            adapter.addList(isRefresh, data);
        }


    }

    @Override
    public void refresh() {
        super.refresh();
        isList = false;
        page = 1;
        Request<String> gameMapGradeSearch = ParameterUtils.getSingleton().getGameMapGradeSearch(mMatchId, content, String.valueOf(page));
        request(AppConfigs.home_music, gameMapGradeSearch, false);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        isList = true;
        page++;
        Request<String> gameMapGradeSearch = ParameterUtils.getSingleton().getGameMapGradeSearch(mMatchId, content, String.valueOf(page));
        request(AppConfigs.home_music, gameMapGradeSearch, false);
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
