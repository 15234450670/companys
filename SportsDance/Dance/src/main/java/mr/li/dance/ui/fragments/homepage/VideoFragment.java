package mr.li.dance.ui.fragments.homepage;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.HomeVideoIndexResponse;
import mr.li.dance.https.response.HomeVideoResponse;
import mr.li.dance.ui.adapters.VideoPageAdapter;
import mr.li.dance.ui.fragments.BaseListFragment;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;

/**
 * 作者: Lixuewei
 * 版本: 1.0
 * 创建日期: 2017/5/22
 * 描述: 主页-首页- 视频页面
 * 修订历史:
 */
public class VideoFragment extends BaseListFragment {
    VideoPageAdapter mVideoPageAdapter;


    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mVideoPageAdapter = new VideoPageAdapter(getActivity());
        return mVideoPageAdapter;
    }

    @Override
    public int getContentView() {
        return R.layout.list_layout;
    }

    @Override
    public void initData() {
        Request<String> request = ParameterUtils.getSingleton().getHomeDianboMap();
        request(AppConfigs.home_dianbo, request, false);

    }

    @Override
    public void refresh() {
        super.refresh();
        Request<String> request = ParameterUtils.getSingleton().getHomeDianboMapFromServer();
        request(AppConfigs.home_dianbo, request, false);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        Request<String> request = ParameterUtils.getSingleton().getHomeDianboPageMap(mVideoPageAdapter.getNextPage());

        request(AppConfigs.home_dianboPage, request, false);
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e("respone", response);
        if (what == AppConfigs.home_dianbo) {
            HomeVideoResponse homeResponse = JsonMananger.getReponseResult(response, HomeVideoResponse.class);
            mVideoPageAdapter.refresh(homeResponse);
        } else {
            HomeVideoIndexResponse reponseResult = JsonMananger.getReponseResult(response, HomeVideoIndexResponse.class);
            mVideoPageAdapter.loadMore(reponseResult);
        }

    }

    @Override
    public void itemClick(int position, Object value) {

    }
}
