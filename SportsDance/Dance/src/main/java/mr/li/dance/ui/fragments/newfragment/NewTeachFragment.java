package mr.li.dance.ui.fragments.newfragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;

import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.HomeTeachResponse;
import mr.li.dance.models.LabelSeekInfo;
import mr.li.dance.models.TeachInfo;
import mr.li.dance.ui.activitys.newActivitys.TeachDetailsActivity;
import mr.li.dance.ui.adapters.new_adapter.NewTeachAdapter;
import mr.li.dance.ui.fragments.BaseListFragment;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/20 0020
 * 描述:
 * 修订历史:
 */
public class NewTeachFragment extends BaseListFragment {
    NewTeachAdapter mPageAdapter;

    private String path;
    private final String tag = "NewTeachFragment";
    private LinearLayout wu;
    private LinearLayout you;
    int page = 1;

    @Override
    public void initViews() {
        super.initViews();
        wu = (LinearLayout) mView.findViewById(R.id.wu);
        you = (LinearLayout) mView.findViewById(R.id.rec);
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
            Request<String> request = ParameterUtils.getSingleton().getHomeTeachMap(String.valueOf(page));
            request(AppConfigs.home_tab_teach, request, false);
        } else {
            Request<String> request = ParameterUtils.getSingleton().getHomeTeacTabhMap(path,"10903",String.valueOf(page));
            request(AppConfigs.home_tab_teachs, request, false);
        }
        //refresh();

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_list_layout;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mPageAdapter = new NewTeachAdapter(getActivity());
        mPageAdapter.setItemClickListener(this);
        return mPageAdapter;
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e("xxx",response);

        if (what == AppConfigs.home_tab_teach) {
            HomeTeachResponse reponseResult = JsonMananger.getReponseResult(response, HomeTeachResponse.class);
            if (reponseResult==null) {
                return;

            } else {
                mPageAdapter.addList(isRefresh, reponseResult.getData().getTeach());
            }
            if (reponseResult.getData().getTeach()==null) {
                LabelSeekInfo reponseResults = JsonMananger.getReponseResult(response, LabelSeekInfo.class);
                mPageAdapter.addList(isRefresh, reponseResults.getData().getArr());
            }
        }
    }

    @Override
    public void refresh() {
        super.refresh();
        page = 1;
        if (path.equals("0")) {
            Request<String> request = ParameterUtils.getSingleton().getHomeTeachMap(String.valueOf(page));
            request(AppConfigs.home_tab_teach, request, false);
        } else {
            Request<String> request = ParameterUtils.getSingleton().getHomeTeacTabhMap(path, "10903", String.valueOf(page));
            request(AppConfigs.home_tab_teachs, request, false);
        }
    }

    @Override
    public void loadMore() {
        super.loadMore();
        page++;
        if (path.equals("0")) {
            Request<String> request = ParameterUtils.getSingleton().getHomeTeachMap(String.valueOf(page));
            request(AppConfigs.home_tab_teach, request, false);
        } else {

            Request<String> request = ParameterUtils.getSingleton().getHomeTeacTabhMap(path, "10903", String.valueOf(page));
            request(AppConfigs.home_tab_teachs, request, false);
        }
    }

    @Override
    public void itemClick(int position, Object values) {
        TeachInfo item = (TeachInfo) values;
        TeachDetailsActivity.lunch(this, item.getId(),item.getImg(),item.getTitle(),item.getDescribed());


    }
}
