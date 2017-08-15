package mr.li.dance.ui.fragments.homepage;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.HomeIndexResponse;
import mr.li.dance.models.MusicInfo;
import mr.li.dance.ui.adapters.MusicAdapter;
import mr.li.dance.ui.fragments.BaseListFragment;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/14 0014
 * 描述:   音乐界面
 * 修订历史:
 */
public class MusicFragment extends BaseListFragment {

    MusicAdapter mAdapter;

    @Override
    public void initData() {
        Request<String> musicInfoMap = ParameterUtils.getSingleton().getMusicInfoMap();
        request(AppConfigs.home_music, musicInfoMap, false);
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public int getContentView() {
        return R.layout.music_zong;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mAdapter = new MusicAdapter(getActivity());
        return mAdapter;
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e("轮播图", response);
        if (what == AppConfigs.home_music) {
            MusicInfo reponseResult = JsonMananger.getReponseResult(response, MusicInfo.class);
            MusicInfo.DataBean data = reponseResult.getData();

            mAdapter.refresh(data);
        }  else {
            HomeIndexResponse homeResponse = JsonMananger.getReponseResult(response, HomeIndexResponse.class);
            mAdapter.loadMore(homeResponse);
        }

    }

    @Override
    public void refresh() {
        super.refresh();
        Request<String> request = ParameterUtils.getSingleton().getHomeIndexMap();
        request(AppConfigs.home_music, request, false);
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
