package mr.li.dance.ui.fragments.main;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
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
import mr.li.dance.ui.activitys.game.GameAdapter;
import mr.li.dance.ui.activitys.game.SearchGameActivity;
import mr.li.dance.ui.activitys.music.PlayMusicActivity;
import mr.li.dance.ui.adapters.BaseRecyclerAdapter;
import mr.li.dance.ui.adapters.ListViewItemClickListener;
import mr.li.dance.ui.adapters.RecyclerViewHolder;
import mr.li.dance.ui.fragments.BaseListFragment;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.DateUtils;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;

import static mr.li.dance.ui.activitys.MainActivity.myBinder;

/**
 * 作者: Lixuewei
 * 版本: 1.0
 * 创建日期: 2017/5/22
 * 描述: 主页-赛事页面
 * 修订历史:
 */
public class MatchFragment extends BaseListFragment implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();
   // MatchPageAdapter mMatchpAdapter;
    GameAdapter      adapter;
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
    int    network;  //选中标签请求网络的判断
    String RANK_TYPE;  // 级别标识码
    String STATE_TYPE;   //状态标识码
    String s;   //判断是哪个链接请求成功
    int    add;// 判断是否加载或刷新


    @Override
    public void initData() {
        network = 0x001;
        mYears = new ArrayList<>();
        rank_list = Arrays.asList(rank);
        state_list = Arrays.asList(state);
        DateUtils dateUtils = new DateUtils();
        int currentYear = dateUtils.getmSelYear();

        for (int i = currentYear; i >= (currentYear - 4); i--) {
            Log.e("xx", i + "");
            mYears.add(String.valueOf(i));
        }
      /*  Request<String> request = ParameterUtils.getSingleton().getMatchMap();
        request(AppConfigs.getMatch_index_code, request, false);*/
        Request<String> request = ParameterUtils.getSingleton().getGameMap(String.valueOf(page));
        request(AppConfigs.getMatch_index_code, request, false);
    }

    @Override
    public void initViews() {
        super.initViews();
        danceViewHolder.getView(R.id.year).setOnClickListener(this);
        danceViewHolder.getView(R.id.rank).setOnClickListener(this);
        danceViewHolder.getView(R.id.state).setOnClickListener(this);
        danceViewHolder.setText(R.id.year_tv, mYears.get(0));
        // 搜索
        danceViewHolder.setClickListener(R.id.search_layout, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // SearchMatchActivity.lunch(getActivity());
                SearchGameActivity.lunch(getActivity());
            }
        });
        danceViewHolder.setClickListener(R.id.btn_music, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myBinder != null && myBinder.binderIsPlaying()) {
                    PlayMusicActivity.lunch(getActivity());
                } else {
                    Toast.makeText(getActivity(), "请去播放音乐", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.game_home;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
     /*   mMatchpAdapter = new MatchPageAdapter(getActivity());
        return mMatchpAdapter;*/
        adapter = new GameAdapter(getActivity());

        return adapter;
    }

    @Override
    public void refresh() {
        super.refresh();
        add = 0x01;
        page = 1;
        if (network == 0x001) {
            Request<String> request = ParameterUtils.getSingleton().getGameMap(String.valueOf(page));
            request(AppConfigs.getMatch_index_code, request, false);
        } else {
            if (!MyStrUtil.isEmpty(time_tv) && MyStrUtil.isEmpty(rank_tv) && MyStrUtil.isEmpty(state_tv)) {
                Log.e(TAG, "time_tv: " + time_tv);
                Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, "", "", "", String.valueOf(page));
                request(AppConfigs.getMatch_search, gameMapSearch, false);
            } else if (!MyStrUtil.isEmpty(rank_tv) && MyStrUtil.isEmpty(time_tv) && MyStrUtil.isEmpty(state_tv)) {
                Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch("", RANK_TYPE, "", "", String.valueOf(page));
                request(AppConfigs.getMatch_search, gameMapSearch, false);
            } else if (!MyStrUtil.isEmpty(state_tv) && MyStrUtil.isEmpty(time_tv) && MyStrUtil.isEmpty(rank_tv)) {
                Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch("", "", STATE_TYPE, "", String.valueOf(page));
                request(AppConfigs.getMatch_search, gameMapSearch, false);
            } else if (!MyStrUtil.isEmpty(time_tv) && !MyStrUtil.isEmpty(rank_tv) && MyStrUtil.isEmpty(state_tv)) {
                Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, RANK_TYPE, "", "", String.valueOf(page));
                request(AppConfigs.getMatch_search, gameMapSearch, false);
            } else if (!MyStrUtil.isEmpty(time_tv) && !MyStrUtil.isEmpty(state_tv) && MyStrUtil.isEmpty(rank_tv)) {
                Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, "", STATE_TYPE, "", String.valueOf(page));
                request(AppConfigs.getMatch_search, gameMapSearch, false);
            } else if (!MyStrUtil.isEmpty(rank_tv) && !MyStrUtil.isEmpty(state_tv) && MyStrUtil.isEmpty(time_tv)) {
                Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch("", RANK_TYPE, STATE_TYPE, "", String.valueOf(page));
                request(AppConfigs.getMatch_search, gameMapSearch, false);
            } else if (!MyStrUtil.isEmpty(time_tv) && !MyStrUtil.isEmpty(rank_tv) && !MyStrUtil.isEmpty(state_tv)) {
                Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, RANK_TYPE, STATE_TYPE, "", String.valueOf(page));
                request(AppConfigs.getMatch_search, gameMapSearch, false);
            }
        }

       /* Request<String> request = ParameterUtils.getSingleton().getMatchMapFromServer();
        request(AppConfigs.getMatch_index_code, request, false);*/
    }

    @Override
    public void loadMore() {
        super.loadMore();
        add = 0;
        page++;
        if (network == 0x001) {
            Request<String> request = ParameterUtils.getSingleton().getGameMap(String.valueOf(page));
            request(AppConfigs.getMatch_index_code, request, false);
        } else {
            if (!MyStrUtil.isEmpty(time_tv) && MyStrUtil.isEmpty(rank_tv) && MyStrUtil.isEmpty(state_tv)) {
                Log.e(TAG, "time_tv: " + time_tv);
                Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, "", "", "", String.valueOf(page));
                request(AppConfigs.getMatch_search, gameMapSearch, false);
            } else if (!MyStrUtil.isEmpty(rank_tv) && MyStrUtil.isEmpty(time_tv) && MyStrUtil.isEmpty(state_tv)) {
                Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch("", RANK_TYPE, "", "", String.valueOf(page));
                request(AppConfigs.getMatch_search, gameMapSearch, false);
            } else if (!MyStrUtil.isEmpty(state_tv) && MyStrUtil.isEmpty(time_tv) && MyStrUtil.isEmpty(rank_tv)) {
                Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch("", "", STATE_TYPE, "", String.valueOf(page));
                request(AppConfigs.getMatch_search, gameMapSearch, false);
            } else if (!MyStrUtil.isEmpty(time_tv) && !MyStrUtil.isEmpty(rank_tv) && MyStrUtil.isEmpty(state_tv)) {
                Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, RANK_TYPE, "", "", String.valueOf(page));
                request(AppConfigs.getMatch_search, gameMapSearch, false);
            } else if (!MyStrUtil.isEmpty(time_tv) && !MyStrUtil.isEmpty(state_tv) && MyStrUtil.isEmpty(rank_tv)) {
                Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, "", STATE_TYPE, "", String.valueOf(page));
                request(AppConfigs.getMatch_search, gameMapSearch, false);
            } else if (!MyStrUtil.isEmpty(rank_tv) && !MyStrUtil.isEmpty(state_tv) && MyStrUtil.isEmpty(time_tv)) {
                Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch("", RANK_TYPE, STATE_TYPE, "", String.valueOf(page));
                request(AppConfigs.getMatch_search, gameMapSearch, false);
            } else if (!MyStrUtil.isEmpty(time_tv) && !MyStrUtil.isEmpty(rank_tv) && !MyStrUtil.isEmpty(state_tv)) {
                Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, RANK_TYPE, STATE_TYPE, "", String.valueOf(page));
                request(AppConfigs.getMatch_search, gameMapSearch, false);
            }
        }

       /* Request<String> request = ParameterUtils.getSingleton().getMatchIndexPageMap(mMatchpAdapter.getNextPage());
        request(AppConfigs.match_indexList, request, false);*/
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e(TAG, response);
        if (what == AppConfigs.getMatch_index_code) {
            s = "1";
            GameHomeResponse reponseResult = JsonMananger.getReponseResult(response, GameHomeResponse.class);
            List<GameHomeResponse.DataBean> data = reponseResult.getData();
            adapter.addList(isRefresh, data, s, add);
        } else if (what == AppConfigs.getMatch_search) {
            s = "5";
            GameHomeResponse gameHomeResponse = JsonMananger.getReponseResult(response, GameHomeResponse.class);
            List<GameHomeResponse.DataBean> gameBean = gameHomeResponse.getData();
            adapter.addList(isRefresh, gameBean, s, add);
        }
        /*if (what == AppConfigs.getMatch_index_code) {
            MatchResponse matchResponse = JsonMananger.getReponseResult(response, MatchResponse.class);
            mMatchpAdapter.refresh(matchResponse);
        } else {
            MatchIndexResponse matchResponse = JsonMananger.getReponseResult(response, MatchIndexResponse.class);
            mMatchpAdapter.loadMore(matchResponse);
        }*/
    }

    @Override
    public void itemClick(int position, Object value) {

    }

    /**
     * @param v
     *         点击标签
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
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

    private void showPop(List<String> mYears, final int type) {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.popwindow_year, null);
        FrameLayout ll = (FrameLayout) view.findViewById(R.id.ll);
        View sanjiaoView = view.findViewById(R.id.sanjiao_icon);
        sanjiaoView.setVisibility(View.GONE);
        popRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        popRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        popYearAdatper = new PopYearAdatper(getActivity());
        popRecyclerView.setAdapter(popYearAdatper);
        popYearAdatper.setItemClickListener(new ListViewItemClickListener<String>() {
            @Override
            public void itemClick(int position, String value) {
                network = 0x002;
                add = 0x01;
                if (type == 0) {
                    danceViewHolder.setText(R.id.year_tv, value);
                    time_tv = danceViewHolder.getTextValue(R.id.year_tv);

                } else if (type == 1) {
                    danceViewHolder.setText(R.id.rank_tv, value);
                    rank_tv = danceViewHolder.getTextValue(R.id.rank_tv);
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
                    danceViewHolder.setText(R.id.state_tv, value);
                    state_tv = danceViewHolder.getTextValue(R.id.state_tv);
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
                    Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, "", "", "", String.valueOf(1));
                    request(AppConfigs.getMatch_search, gameMapSearch, false);
                } else if (!MyStrUtil.isEmpty(rank_tv) && MyStrUtil.isEmpty(time_tv) && MyStrUtil.isEmpty(state_tv)) {
                    Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch("", RANK_TYPE, "", "", String.valueOf(1));
                    request(AppConfigs.getMatch_search, gameMapSearch, false);
                } else if (!MyStrUtil.isEmpty(state_tv) && MyStrUtil.isEmpty(time_tv) && MyStrUtil.isEmpty(rank_tv)) {
                    Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch("", "", STATE_TYPE, "", String.valueOf(1));
                    request(AppConfigs.getMatch_search, gameMapSearch, false);
                } else if (!MyStrUtil.isEmpty(time_tv) && !MyStrUtil.isEmpty(rank_tv) && MyStrUtil.isEmpty(state_tv)) {
                    Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, RANK_TYPE, "", "", String.valueOf(1));
                    request(AppConfigs.getMatch_search, gameMapSearch, false);
                } else if (!MyStrUtil.isEmpty(time_tv) && !MyStrUtil.isEmpty(state_tv) && MyStrUtil.isEmpty(rank_tv)) {
                    Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, "", STATE_TYPE, "", String.valueOf(1));
                    request(AppConfigs.getMatch_search, gameMapSearch, false);
                } else if (!MyStrUtil.isEmpty(rank_tv) && !MyStrUtil.isEmpty(state_tv) && MyStrUtil.isEmpty(time_tv)) {
                    Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch("", RANK_TYPE, STATE_TYPE, "", String.valueOf(1));
                    request(AppConfigs.getMatch_search, gameMapSearch, false);
                } else if (!MyStrUtil.isEmpty(time_tv) && !MyStrUtil.isEmpty(rank_tv) && !MyStrUtil.isEmpty(state_tv)) {
                    Request<String> gameMapSearch = ParameterUtils.getSingleton().getGameMapSearch(time_tv, RANK_TYPE, STATE_TYPE, "", String.valueOf(1));
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
        popupWindow.showAsDropDown(danceViewHolder.getView(R.id.year_tv), 0, 0);
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
