package mr.li.dance.ui.activitys.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.yolanda.nohttp.rest.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.GameHomeResponse;
import mr.li.dance.ui.activitys.base.BaseListActivity;
import mr.li.dance.ui.adapters.BaseRecyclerAdapter;
import mr.li.dance.ui.adapters.ListViewItemClickListener;
import mr.li.dance.ui.adapters.RecyclerViewHolder;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.DateUtils;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;

import static mr.li.dance.R.id.search_et;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/26 0026
 * 描述:
 * 修订历史:
 */
public class SearchGameActivity extends BaseListActivity implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    private ArrayList<String> mYears;
    private RecyclerView      popRecyclerView;
    private PopupWindow       popupWindow;
    private PopYearAdatper    popYearAdatper;
    private List<String>      rank_list;
    private List<String>      state_list;
    String[] rank  = new String[]{"WDSF", "CDSF", "地方赛事"};
    String[] state = new String[]{"直播中", "进行中", "报名中", "未开始", "已结束"};
    private String state_tv;
    private String rank_tv;
    private String time_tv;
    int page = 1;
    int    TYPE;     //标签判断标识
    String RANK_TYPE;  // 级别标识码
    String STATE_TYPE;   //状态标识码

    GameAdapter adapter;
    private String textValue;
    int add;// 判断是否加载或刷新
    private boolean isList;

    @Override
    public void itemClick(int position, Object value) {

    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new GameAdapter(this);
        return adapter;
    }

    @Override
    public int getContentViewId() {
        return R.layout.search_game_activity;
    }

    @Override
    public void initDatas() {
        super.initDatas();
        mYears = new ArrayList<>();
        rank_list = Arrays.asList(rank);
        state_list = Arrays.asList(state);
        DateUtils dateUtils = new DateUtils();
        int currentYear = dateUtils.getmSelYear();

        for (int i = currentYear; i >= (currentYear - 4); i--) {
            Log.e("xx", i + "");
            mYears.add(String.valueOf(i));
        }
    }

    @Override
    public void initViews() {
        super.initViews();
        setHeadVisibility(View.GONE);
        mDanceViewHolder.getView(R.id.year).setOnClickListener(this);
        mDanceViewHolder.getView(R.id.rank).setOnClickListener(this);
        mDanceViewHolder.getView(R.id.state).setOnClickListener(this);
        mDanceViewHolder.setText(R.id.year_tv, mYears.get(0));
        mDanceViewHolder.getImageView(R.id.back_icon).setOnClickListener(this);
        mDanceViewHolder.getTextView(R.id.search_btn).setOnClickListener(this);
    }

    @Override
    public void refresh() {
        super.refresh();

        add = 0x01;
        page = 1;

        if (!MyStrUtil.isEmpty(time_tv) && MyStrUtil.isEmpty(rank_tv) && MyStrUtil.isEmpty(state_tv)) {
            Log.e(TAG, "time_tv: " + time_tv);
            Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, "", "", textValue, String.valueOf(page));
            request(AppConfigs.getMatch_search, gameMapSearch, false);
        } else if (!MyStrUtil.isEmpty(rank_tv) && MyStrUtil.isEmpty(time_tv) && MyStrUtil.isEmpty(state_tv)) {
            Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch("", RANK_TYPE, "", textValue, String.valueOf(page));
            request(AppConfigs.getMatch_search, gameMapSearch, false);
        } else if (!MyStrUtil.isEmpty(state_tv) && MyStrUtil.isEmpty(time_tv) && MyStrUtil.isEmpty(rank_tv)) {
            Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch("", "", STATE_TYPE, textValue, String.valueOf(page));
            request(AppConfigs.getMatch_search, gameMapSearch, false);
        } else if (!MyStrUtil.isEmpty(time_tv) && !MyStrUtil.isEmpty(rank_tv) && MyStrUtil.isEmpty(state_tv)) {
            Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, RANK_TYPE, "", textValue, String.valueOf(page));
            request(AppConfigs.getMatch_search, gameMapSearch, false);
        } else if (!MyStrUtil.isEmpty(time_tv) && !MyStrUtil.isEmpty(state_tv) && MyStrUtil.isEmpty(rank_tv)) {
            Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, "", STATE_TYPE, textValue, String.valueOf(page));
            request(AppConfigs.getMatch_search, gameMapSearch, false);
        } else if (!MyStrUtil.isEmpty(rank_tv) && !MyStrUtil.isEmpty(state_tv) && MyStrUtil.isEmpty(time_tv)) {
            Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch("", RANK_TYPE, STATE_TYPE, textValue, String.valueOf(page));
            request(AppConfigs.getMatch_search, gameMapSearch, false);
        } else if (!MyStrUtil.isEmpty(time_tv) && !MyStrUtil.isEmpty(rank_tv) && !MyStrUtil.isEmpty(state_tv)) {
            Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, RANK_TYPE, STATE_TYPE, textValue, String.valueOf(page));
            request(AppConfigs.getMatch_search, gameMapSearch, false);

        }
        isList = false;
       /* Request<String> request = ParameterUtils.getSingleton().getMatchMapFromServer();
        request(AppConfigs.getMatch_index_code, request, false);*/
    }

    @Override
    public void loadMore() {
        super.loadMore();
        add = 0;
        page++;
        if (!MyStrUtil.isEmpty(time_tv) && MyStrUtil.isEmpty(rank_tv) && MyStrUtil.isEmpty(state_tv)) {
            Log.e(TAG, "time_tv: " + time_tv);
            Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, "", "", textValue, String.valueOf(page));
            request(AppConfigs.getMatch_search, gameMapSearch, false);
        } else if (!MyStrUtil.isEmpty(rank_tv) && MyStrUtil.isEmpty(time_tv) && MyStrUtil.isEmpty(state_tv)) {
            Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch("", RANK_TYPE, "", textValue, String.valueOf(page));
            request(AppConfigs.getMatch_search, gameMapSearch, false);
        } else if (!MyStrUtil.isEmpty(state_tv) && MyStrUtil.isEmpty(time_tv) && MyStrUtil.isEmpty(rank_tv)) {
            Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch("", "", STATE_TYPE, textValue, String.valueOf(page));
            request(AppConfigs.getMatch_search, gameMapSearch, false);
        } else if (!MyStrUtil.isEmpty(time_tv) && !MyStrUtil.isEmpty(rank_tv) && MyStrUtil.isEmpty(state_tv)) {
            Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, RANK_TYPE, "", textValue, String.valueOf(page));
            request(AppConfigs.getMatch_search, gameMapSearch, false);
        } else if (!MyStrUtil.isEmpty(time_tv) && !MyStrUtil.isEmpty(state_tv) && MyStrUtil.isEmpty(rank_tv)) {
            Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, "", STATE_TYPE, textValue, String.valueOf(page));
            request(AppConfigs.getMatch_search, gameMapSearch, false);
        } else if (!MyStrUtil.isEmpty(rank_tv) && !MyStrUtil.isEmpty(state_tv) && MyStrUtil.isEmpty(time_tv)) {
            Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch("", RANK_TYPE, STATE_TYPE, textValue, String.valueOf(page));
            request(AppConfigs.getMatch_search, gameMapSearch, false);
        } else if (!MyStrUtil.isEmpty(time_tv) && !MyStrUtil.isEmpty(rank_tv) && !MyStrUtil.isEmpty(state_tv)) {
            Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, RANK_TYPE, STATE_TYPE, textValue, String.valueOf(page));
            request(AppConfigs.getMatch_search, gameMapSearch, false);
        }

        isList = true;
       /* Request<String> request = ParameterUtils.getSingleton().getMatchIndexPageMap(mMatchpAdapter.getNextPage());
        request(AppConfigs.match_indexList, request, false);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_icon:
                hintKbTwo();
                finish();
                break;
            case R.id.search_btn:

                textValue = mDanceViewHolder.getTextValue(search_et);
                Log.e(TAG, "textValue:" + textValue);
                if (MyStrUtil.isEmpty(textValue)) {
                    Toast.makeText(mContext, "请输入要搜索的内容", Toast.LENGTH_SHORT).show();
                } else {
                    Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch("", "", "", textValue, String.valueOf(1));
                    request(AppConfigs.getGame_detail, gameMapSearch, false);
                    mDanceViewHolder.getView(R.id.include).setVisibility(View.VISIBLE);
                }

                break;
            case R.id.year:
                TYPE = 0;
                showPop(mYears, TYPE);
                break;
            case R.id.rank:
                TYPE = 1;
                showPop(rank_list, TYPE);
                break;
            case R.id.state:
                TYPE = 2;
                showPop(state_list, TYPE);
                break;
        }
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e(TAG, response);
        GameHomeResponse gameHomeResponse = JsonMananger.getReponseResult(response, GameHomeResponse.class);
        List<GameHomeResponse.DataBean> gameBean = gameHomeResponse.getData();
        if (MyStrUtil.isEmpty(gameBean) && !isList) {
            mDanceViewHolder.getView(R.id.wu).setVisibility(View.VISIBLE);
            return;
        } else {
            mDanceViewHolder.getView(R.id.wu).setVisibility(View.GONE);
            adapter.addList(isRefresh, gameBean, add);
        }

    }


    public static void lunch(Context context) {
        context.startActivity(new Intent(context, SearchGameActivity.class));
    }


    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    private void showPop(List<String> mYears, final int type) {
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.popwindow_year, null);
        FrameLayout ll = (FrameLayout) view.findViewById(R.id.ll);
        View sanjiaoView = view.findViewById(R.id.sanjiao_icon);
        sanjiaoView.setVisibility(View.GONE);
        popRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        popRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        popYearAdatper = new PopYearAdatper(this);
        popRecyclerView.setAdapter(popYearAdatper);
        popYearAdatper.setItemClickListener(new ListViewItemClickListener<String>() {
            @Override
            public void itemClick(int position, String value) {
                add = 0x01;
                if (type == 0) {
                    mDanceViewHolder.setText(R.id.year_tv, value);
                    time_tv = mDanceViewHolder.getTextValue(R.id.year_tv);

                } else if (type == 1) {
                    mDanceViewHolder.setText(R.id.rank_tv, value);
                    rank_tv = mDanceViewHolder.getTextValue(R.id.rank_tv);
                    switch (rank_tv) {
                        case "WDSF":
                            RANK_TYPE = "10001";
                            break;
                        case "CDSF":
                            RANK_TYPE = "10002";
                            break;
                        case "地方赛事":
                            RANK_TYPE = "10003";
                            break;
                    }
                    Log.e(TAG, "RANK_TYPE: " + RANK_TYPE);
                } else {
                    mDanceViewHolder.setText(R.id.state_tv, value);
                    state_tv = mDanceViewHolder.getTextValue(R.id.state_tv);
                    switch (state_tv) {
                        case "报名中":
                            STATE_TYPE = "1";
                            break;
                        case "进行中":
                            STATE_TYPE = "2";
                            break;
                        case "直播中":
                            STATE_TYPE = "3";
                            break;
                        case "已结束":
                            STATE_TYPE = "4";
                            break;
                        case "未开始":
                            STATE_TYPE = "5";
                            break;

                    }
                    Log.e(TAG, "STATE_TYPE: " + STATE_TYPE);
                }
                if (!MyStrUtil.isEmpty(time_tv) && MyStrUtil.isEmpty(rank_tv) && MyStrUtil.isEmpty(state_tv)) {
                    Log.e(TAG, "time_tv: " + time_tv);
                    Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, "", "", textValue, String.valueOf(1));
                    request(AppConfigs.getMatch_search, gameMapSearch, false);
                } else if (!MyStrUtil.isEmpty(rank_tv) && MyStrUtil.isEmpty(time_tv) && MyStrUtil.isEmpty(state_tv)) {
                    Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch("", RANK_TYPE, "", textValue, String.valueOf(1));
                    request(AppConfigs.getMatch_search, gameMapSearch, false);
                } else if (!MyStrUtil.isEmpty(state_tv) && MyStrUtil.isEmpty(time_tv) && MyStrUtil.isEmpty(rank_tv)) {
                    Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch("", "", STATE_TYPE, textValue, String.valueOf(1));
                    request(AppConfigs.getMatch_search, gameMapSearch, false);
                } else if (!MyStrUtil.isEmpty(time_tv) && !MyStrUtil.isEmpty(rank_tv) && MyStrUtil.isEmpty(state_tv)) {
                    Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, RANK_TYPE, "", textValue, String.valueOf(1));
                    request(AppConfigs.getMatch_search, gameMapSearch, false);
                } else if (!MyStrUtil.isEmpty(time_tv) && !MyStrUtil.isEmpty(state_tv) && MyStrUtil.isEmpty(rank_tv)) {
                    Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, "", STATE_TYPE, textValue, String.valueOf(1));
                    request(AppConfigs.getMatch_search, gameMapSearch, false);
                } else if (!MyStrUtil.isEmpty(rank_tv) && !MyStrUtil.isEmpty(state_tv) && MyStrUtil.isEmpty(time_tv)) {
                    Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch("", RANK_TYPE, STATE_TYPE, textValue, String.valueOf(1));
                    request(AppConfigs.getMatch_search, gameMapSearch, false);
                } else if (!MyStrUtil.isEmpty(time_tv) && !MyStrUtil.isEmpty(rank_tv) && !MyStrUtil.isEmpty(state_tv)) {
                    Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, RANK_TYPE, STATE_TYPE, textValue, String.valueOf(1));
                    request(AppConfigs.getMatch_search, gameMapSearch, false);
                }
                popupWindow.dismiss();

            }

        });
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        popYearAdatper.addList(mYears);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(mDanceViewHolder.getView(R.id.year_tv), 0, 0);
        popYearAdatper.notifyDataSetChanged();
    }

    private class PopYearAdatper extends BaseRecyclerAdapter<String> {
        public PopYearAdatper(Context ctx) {
            super(ctx);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.item_year;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, String item) {
            holder.setText(R.id.year_tv, item);

        }
    }
}
