package mr.li.dance.ui.fragments.newfragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.HomeVideoResponse;
import mr.li.dance.models.LabelSeekInfoVideo;
import mr.li.dance.ui.adapters.new_adapter.NewVideoAdapter;
import mr.li.dance.ui.fragments.BaseListFragment;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/12 0012
 * 描述:
 * 修订历史:
 */
public class NewVideoFragment extends BaseListFragment {
    NewVideoAdapter mVideoPageAdapter;
    private String path;
    int page = 1;
    private final String tag = "NewVideoFragment";

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
            Request<String> request = ParameterUtils.getSingleton().getHomeDianbo2Map(String.valueOf(page));
            request(AppConfigs.home_dianbo, request, false);
        } else {
            Request<String> request = ParameterUtils.getSingleton().getHomeTabhMap(path, "10901", String.valueOf(page));
            request(AppConfigs.home_dianbo, request, false);
        }
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
        mVideoPageAdapter = new NewVideoAdapter(getActivity());
        return mVideoPageAdapter;
    }


    @Override
    public void refresh() {
        super.refresh();
        page = 1;
        if (path.equals("0")) {
            Request<String> request = ParameterUtils.getSingleton().getHomeDianbo2Map(String.valueOf(page));
            request(AppConfigs.home_dianbo, request, false);
        } else {
            Request<String> request = ParameterUtils.getSingleton().getHomeTabhMap(path, "10901", String.valueOf(page));
            request(AppConfigs.home_dianbo, request, false);
        }
    }

    @Override
    public void loadMore() {
        page++;
        super.loadMore();
        if (path.equals("0")) {
            Request<String> request = ParameterUtils.getSingleton().getHomeDianbo2Map(String.valueOf(page));
            request(AppConfigs.home_dianboPage, request, false);
        } else {
            Request<String> request = ParameterUtils.getSingleton().getHomeTabhMap(path, "10901", String.valueOf(page));
            request(AppConfigs.home_dianboPage, request, false);
        }

    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e("respone", response);
        if (what == AppConfigs.home_dianbo) {
            HomeVideoResponse homeResponse = JsonMananger.getReponseResult(response, HomeVideoResponse.class);
            mVideoPageAdapter.refresh(homeResponse);
            if (homeResponse.getData().getDb_rec() == null) {
                LabelSeekInfoVideo reponseResults = JsonMananger.getReponseResult(response, LabelSeekInfoVideo.class);
                mVideoPageAdapter.refresh1(reponseResults);
            }

        } else {
            HomeVideoResponse homeResponse = JsonMananger.getReponseResult(response, HomeVideoResponse.class);
            mVideoPageAdapter.loadMore(homeResponse);
            if (homeResponse.getData().getDb_rec() == null) {
                LabelSeekInfoVideo reponseResults = JsonMananger.getReponseResult(response, LabelSeekInfoVideo.class);
                mVideoPageAdapter.loadMore1(reponseResults);
            }
        }

    }

    @Override
    public void itemClick(int position, Object value) {

    }
}
