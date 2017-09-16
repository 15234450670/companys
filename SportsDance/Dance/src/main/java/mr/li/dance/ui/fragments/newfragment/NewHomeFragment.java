package mr.li.dance.ui.fragments.newfragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.HomeIndexResponse;
import mr.li.dance.https.response.HomeResponse;
import mr.li.dance.ui.activitys.SearchActivity;
import mr.li.dance.ui.activitys.music.PlayMusicActivity;
import mr.li.dance.ui.adapters.new_adapter.New_HomeRecyclerAdapter;
import mr.li.dance.ui.fragments.BaseListFragment;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;

import static mr.li.dance.ui.activitys.MainActivity.myBinder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/1 0001
 * 描述:     新的首页
 * 修订历史:
 */
public class NewHomeFragment extends BaseListFragment {
    New_HomeRecyclerAdapter mAdapter;

    @Override
    public void initData() {
        Request<String> request = ParameterUtils.getSingleton().getHomeIndexMap();
        request(AppConfigs.home_index, request, false);
    }

    @Override
    public void initViews() {
        super.initViews();
        //搜索按钮
        danceViewHolder.setClickListener(R.id.search_layout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getActivity(), AppConfigs.CLICK_EVENT_6);
                SearchActivity.lunch(getActivity());
            }
        });
        //音乐按钮
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
        mAdapter = new New_HomeRecyclerAdapter(getActivity());
        return mAdapter;
    }


    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e("首页轮播图", response);
        if (what == AppConfigs.home_index) {
            HomeResponse homeResponse = JsonMananger.getReponseResult(response, HomeResponse.class);
            mAdapter.refresh(homeResponse);
        } else {
            HomeIndexResponse homeResponse = JsonMananger.getReponseResult(response, HomeIndexResponse.class);
            mAdapter.loadMore(homeResponse);
        }
    }

    @Override
    public void refresh() {
        super.refresh();
        Request<String> request = ParameterUtils.getSingleton().getHomeIndexMap();
        request(AppConfigs.home_index, request, false);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        Request<String> request = ParameterUtils.getSingleton().getHomeIndexPageMap(mAdapter.getNextPage());
        request(AppConfigs.home_index_page, request, false);
    }

    @Override
    public void itemClick(int position, Object value) {

    }
}
