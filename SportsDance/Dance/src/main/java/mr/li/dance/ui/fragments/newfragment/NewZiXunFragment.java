package mr.li.dance.ui.fragments.newfragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.HomeZxResponse;
import mr.li.dance.https.response.ZiXunIndexResponse;
import mr.li.dance.models.ZiXunInfo;
import mr.li.dance.ui.activitys.MyDanceWebActivity;
import mr.li.dance.ui.adapters.new_adapter.NewMessageAdapter;
import mr.li.dance.ui.fragments.BaseListFragment;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/8 0008
 * 描述:     资讯FrameLayout页面
 * 修订历史:
 */
public class NewZiXunFragment extends BaseListFragment {
    NewMessageAdapter mPageAdapter;
    int page = 1;
    private String path;

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
        mPageAdapter = new NewMessageAdapter(getActivity(), this);
        return mPageAdapter;
    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        path = arguments.getString("path");
        if (Integer.parseInt(path)==0) {
            Request<String> request = ParameterUtils.getSingleton().getHomeZxMap();
            request(AppConfigs.home_zx, request, false);
        } else {
            Request<String> request = ParameterUtils.getSingleton().getHomeZxMapTab(String.valueOf(page), path);
            request(AppConfigs.home_zx, request, false);
        }
    }

    @Override
    public void refresh() {
        super.refresh();
        if (Integer.parseInt(path)==0) {
            Request<String> request = ParameterUtils.getSingleton().getHomeZxMap();
            request(AppConfigs.home_zx, request, false);
        } else {
            page=1;
            Request<String> request = ParameterUtils.getSingleton().getHomeZxMapTab(String.valueOf(page), path);
            request(AppConfigs.home_zx, request, false);
        }
    }

    @Override
    public void loadMore() {
        super.loadMore();
        if (Integer.parseInt(path)==0) {
            Request<String> request = ParameterUtils.getSingleton().getHomeZxPageMap(mPageAdapter.getNextPage());
            request(AppConfigs.home_zxPage, request, false);
        } else {
            page++;
            Request<String> request = ParameterUtils.getSingleton().getHomeZxMapTab(String.valueOf(page), path);
            request(AppConfigs.home_zxPage, request, false);
        }
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        if (what == AppConfigs.home_zx) {
            HomeZxResponse reponseResult = JsonMananger.getReponseResult(response, HomeZxResponse.class);
            mPageAdapter.refresh(reponseResult);
        } else{
            ZiXunIndexResponse indexResponse = JsonMananger.getReponseResult(response, ZiXunIndexResponse.class);
            mPageAdapter.loadMore(indexResponse);
        }

    }

    @Override
    public void itemClick(int position, Object value) {
        ZiXunInfo ziXunInfo = (ZiXunInfo) value;
        String url = String.format(AppConfigs.ZixunShareUrl, String.valueOf(ziXunInfo.getId()));
        MyDanceWebActivity.lunch(getActivity(), MyDanceWebActivity.ZIXUNTYPE, ziXunInfo.getTitle(), url, true);

    }

}
