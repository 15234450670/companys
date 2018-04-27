package mr.li.dance.ui.fragments.secondFragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.HomeIndexResponse;
import mr.li.dance.https.response.HomeResponse;
import mr.li.dance.ui.adapters.second_new_adapter.Second_New_HomeRecyclerAdapter;
import mr.li.dance.ui.fragments.BaseListFragment;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/4/25 0025
 * 描述:
 * 修订历史:
 */
public class SecondHomeFragment extends BaseListFragment {
    Second_New_HomeRecyclerAdapter mAdapter;
    int page = 1;

    @Override
    public void initData() {
        Request<String> request = ParameterUtils.getSingleton().getHomeIndexMap1(String.valueOf(page));
        request(AppConfigs.home_index, request, false);
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public int getContentView() {
        return R.layout.second_home_fragment;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mAdapter = new Second_New_HomeRecyclerAdapter(getActivity());
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
        Request<String> request = ParameterUtils.getSingleton().getHomeIndexMap1(String.valueOf(1));
        request(AppConfigs.home_index, request, false);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        page++;
        Log.e("page---", page + "");
        Request<String> request = ParameterUtils.getSingleton().getHomeIndexMap2(String.valueOf(page));
        request(AppConfigs.home_index_page, request, false);
    }

    @Override
    public void itemClick(int position, Object value) {

    }
}
