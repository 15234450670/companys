package mr.li.dance.ui.fragments.newfragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.HomeVideoIndexResponse;
import mr.li.dance.https.response.HomeVideoResponse;
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
    @Override
    public void initData() {
        Bundle arguments = getArguments();
        path = arguments.getString("path");
        if (Integer.parseInt(path)==0) {
            Request<String> request = ParameterUtils.getSingleton().getHomeDianboMap();
            request(AppConfigs.home_dianbo, request, false);
        } else {
          //Tab变动时的接口

        }
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public int getContentView() {
        return R.layout.list_layout;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mVideoPageAdapter = new NewVideoAdapter(getActivity());
        return mVideoPageAdapter;
    }


    @Override
    public void refresh() {
        super.refresh();
        if (Integer.parseInt(path)==0) {
            Request<String> request = ParameterUtils.getSingleton().getHomeDianboMap();
            request(AppConfigs.home_dianbo, request, false);
        } else {
            //Tab变动时的接口
            page = 1;

        }
    }

    @Override
    public void loadMore() {
        super.loadMore();
        if (Integer.parseInt(path)==0) {
            Request<String> request = ParameterUtils.getSingleton().getHomeDianboPageMap(mVideoPageAdapter.getNextPage());

            request(AppConfigs.home_dianboPage, request, false);
        } else {
             page++;
        }

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
