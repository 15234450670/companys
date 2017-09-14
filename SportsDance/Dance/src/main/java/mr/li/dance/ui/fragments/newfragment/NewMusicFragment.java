package mr.li.dance.ui.fragments.newfragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.MusicResponse;
import mr.li.dance.models.MusicIndexPesponse;
import mr.li.dance.ui.adapters.new_adapter.NewMusicAdapter;
import mr.li.dance.ui.fragments.BaseListFragment;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/14 0014
 * 描述:
 * 修订历史:
 */
public class NewMusicFragment extends BaseListFragment {
    NewMusicAdapter adapter;
    String path;
    int page = 1;
    @Override
    public void itemClick(int position, Object value) {

    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        path = arguments.getString("path");
        if (Integer.parseInt(path)==0) {
            Request<String> musicInfoMap = ParameterUtils.getSingleton().getMusicInfoMap();
            request(AppConfigs.home_music, musicInfoMap, false);
        } else {

        }
    }

    @Override
    public int getContentView() {
        return  R.layout.fragment_list_layout;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new NewMusicAdapter(getActivity());
        return adapter;
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        if (what==AppConfigs.home_music) {
            MusicResponse reponseResult = JsonMananger.getReponseResult(response, MusicResponse.class);
            adapter.refresh(reponseResult);
        } else if (what==AppConfigs.home_music_page) {
            MusicIndexPesponse reponseResultIndex = JsonMananger.getReponseResult(response, MusicIndexPesponse.class);
            adapter.loadMore(reponseResultIndex);
        }
    }

    @Override
    public void initViews() {
        super.initViews();
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerview.setLayoutManager(layoutManager);
        mRecyclerview.setAdapter(getAdapter());
    }
    @Override
    public void refresh() {
        super.refresh();
        if (Integer.parseInt(path)==0) {
            Request<String> musicInfoMap = ParameterUtils.getSingleton().getMusicInfoMap();
            request(AppConfigs.home_music, musicInfoMap, false);
        } else {
            page=1;

        }
    }

    @Override
    public void loadMore() {
        super.loadMore();

        if (Integer.parseInt(path)==0) {
      Request<String> musicInfoMapIndex = ParameterUtils.getSingleton().getMusicInfoMapIndex(String.valueOf(adapter.getNextPage()));
        request(AppConfigs.home_music_page, musicInfoMapIndex, false);
        } else {
            page++;

        }

    }
}
