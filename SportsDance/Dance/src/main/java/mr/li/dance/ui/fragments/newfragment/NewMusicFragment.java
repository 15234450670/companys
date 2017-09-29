package mr.li.dance.ui.fragments.newfragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.MusicResponse;
import mr.li.dance.models.LabelSelectMusicInfo;
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
    String          path;
    int page = 1;
    private String tag = "NewMusicFragment";


    @Override
    public void itemClick(int position, Object value) {

    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            Log.d(tag, "arguments = null");
            return;
        }
        path = arguments.getString("path");
        Log.e("xxx", path);
        if (TextUtils.isEmpty(path)) {
            return;
        }
        if (path.equals("0")) {
            Request<String> request = ParameterUtils.getSingleton().getMusicInfo2Map(String.valueOf(page));
            request(AppConfigs.home_music, request, false);
        } else {
            Request<String> request = ParameterUtils.getSingleton().getHomeTabhMap(path, "10904", String.valueOf(page));
            request(AppConfigs.home_music, request, false);
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_list_layout;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new NewMusicAdapter(getActivity());
        return adapter;
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e("music",response);

        if (what == AppConfigs.home_music) {
            MusicResponse reponseResult = JsonMananger.getReponseResult(response, MusicResponse.class);
            adapter.refresh(reponseResult);
            if (reponseResult.getData().getMusic_class() == null) {
                LabelSelectMusicInfo reponseResults = JsonMananger.getReponseResult(response, LabelSelectMusicInfo.class);
                adapter.refresh1(reponseResults);
            }
        } else {
            MusicResponse reponseResult = JsonMananger.getReponseResult(response, MusicResponse.class);
            adapter.loadMore(reponseResult);
            if (reponseResult.getData().getMusic_class() == null) {
                LabelSelectMusicInfo reponseResults = JsonMananger.getReponseResult(response, LabelSelectMusicInfo.class);
                adapter.loadMore1(reponseResults);
            }
        }
    }

    @Override
    public void initViews() {
        super.initViews();
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerview.setLayoutManager(layoutManager);
        mRecyclerview.setAdapter(adapter);
    }

    @Override
    public void refresh() {
        super.refresh();
        page = 1;
        if (path.equals("0")) {
            Request<String> musicInfoMap = ParameterUtils.getSingleton().getMusicInfo2Map(String.valueOf(page));
            request(AppConfigs.home_music, musicInfoMap, false);
        } else {
            Request<String> request = ParameterUtils.getSingleton().getHomeTabhMap(path, "10904", String.valueOf(page));
            request(AppConfigs.home_music, request, false);
        }
    }

    @Override
    public void loadMore() {
        super.loadMore();
        page++;
        if (path.equals("0")) {
            Request<String> musicInfoMap = ParameterUtils.getSingleton().getMusicInfo2Map(String.valueOf(page));
            request(AppConfigs.home_music_page, musicInfoMap, false);
        } else {
            Request<String> request = ParameterUtils.getSingleton().getHomeTabhMap(path, "10904", String.valueOf(page));
            request(AppConfigs.home_music_page, request, false);
        }

    }

}
