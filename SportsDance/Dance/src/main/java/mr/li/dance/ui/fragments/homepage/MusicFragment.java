package mr.li.dance.ui.fragments.homepage;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.MusicResponse;
import mr.li.dance.models.MusicIndexPesponse;
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
        return R.layout.fragment_list_layout;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mAdapter = new MusicAdapter(getActivity());
        return mAdapter;
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e("音乐",response);
        if (what==AppConfigs.home_music) {
            MusicResponse reponseResult = JsonMananger.getReponseResult(response, MusicResponse.class);
            mAdapter.refresh(reponseResult);
        } else if (what==AppConfigs.home_music_page) {
            MusicIndexPesponse reponseResultIndex = JsonMananger.getReponseResult(response, MusicIndexPesponse.class);
            mAdapter.loadMore(reponseResultIndex);
        }
    }

    @Override
    public void refresh() {
        super.refresh();
        Request<String> musicInfoMap = ParameterUtils.getSingleton().getMusicInfoMap();
        request(AppConfigs.home_music, musicInfoMap, false);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        Request<String> musicInfoMapIndex = ParameterUtils.getSingleton().getMusicInfoMapIndex(String.valueOf(mAdapter.getNextPage()));
        Log.e("valueOf",String.valueOf(mAdapter.getNextPage())) ;
        request(AppConfigs.home_music_page, musicInfoMapIndex, false);
    }

    @Override
    public void itemClick(int position, Object value) {

    }
}
