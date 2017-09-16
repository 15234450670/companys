package mr.li.dance.ui.fragments.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.MatchIndexResponse;
import mr.li.dance.https.response.MatchResponse;
import mr.li.dance.ui.activitys.match.SearchMatchActivity;
import mr.li.dance.ui.activitys.music.PlayMusicActivity;
import mr.li.dance.ui.adapters.MatchPageAdapter;
import mr.li.dance.ui.fragments.BaseListFragment;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;

import static mr.li.dance.ui.activitys.MainActivity.myBinder;

/**
 * 作者: Lixuewei
 * 版本: 1.0
 * 创建日期: 2017/5/22
 * 描述: 主页-赛事页面
 * 修订历史:
 */
public class MatchFragment extends BaseListFragment {
    MatchPageAdapter mMatchpAdapter;

    @Override
    public void initData() {
        Request<String> request = ParameterUtils.getSingleton().getMatchMap();
        request(AppConfigs.getMatch_index_code, request, false);
    }

    @Override
    public void initViews() {
        super.initViews();
        danceViewHolder.setClickListener(R.id.search_layout, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchMatchActivity.lunch(getActivity());
            }
        });
        danceViewHolder.setClickListener(R.id.btn_music, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myBinder!=null&&myBinder.binderIsPlaying()) {
                    PlayMusicActivity.lunch(getActivity());
                } else {
                    Toast.makeText(getActivity(), "请去播放音乐", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.new_fragment_list_layout;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mMatchpAdapter = new MatchPageAdapter(getActivity());
        return mMatchpAdapter;
    }

    @Override
    public void refresh() {
        super.refresh();
        Request<String> request = ParameterUtils.getSingleton().getMatchMapFromServer();
        request(AppConfigs.getMatch_index_code, request, false);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        Request<String> request = ParameterUtils.getSingleton().getMatchIndexPageMap(mMatchpAdapter.getNextPage());
        request(AppConfigs.match_indexList, request, false);
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        if (what == AppConfigs.getMatch_index_code) {
            MatchResponse matchResponse = JsonMananger.getReponseResult(response, MatchResponse.class);
            mMatchpAdapter.refresh(matchResponse);
        } else {
            MatchIndexResponse matchResponse = JsonMananger.getReponseResult(response, MatchIndexResponse.class);
            mMatchpAdapter.loadMore(matchResponse);
        }
    }

    @Override
    public void itemClick(int position, Object value) {

    }
}
