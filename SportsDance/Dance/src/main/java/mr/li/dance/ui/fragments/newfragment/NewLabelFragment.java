package mr.li.dance.ui.fragments.newfragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.yolanda.nohttp.rest.Request;

import java.util.ArrayList;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.LabelSeekInfo;
import mr.li.dance.models.TeachInfo;
import mr.li.dance.ui.activitys.MyDanceWebActivity;
import mr.li.dance.ui.fragments.BaseListFragment;
import mr.li.dance.ui.fragments.adapter.NewLabeiAdapter;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/28 0028
 * 描述:
 * 修订历史:
 */
public class NewLabelFragment extends BaseListFragment<TeachInfo> {
    int page = 1;
    NewLabeiAdapter adapter;
    String          path;
    private String id;

    @Override
    public void initViews() {
        super.initViews();
    }


    @Override
    public void initData() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            return;
        }
        path = arguments.getString("path");
        id = arguments.getString("id");
        Log.e("path :: ", path);
        if (TextUtils.isEmpty(path)&&TextUtils.isEmpty(id)) {
           danceViewHolder.getView(R.id.wu).setVisibility(View.VISIBLE);
            return;
        } else {
            danceViewHolder.getView(R.id.wu).setVisibility(View.GONE);
        }
        Request<String> homeTabhMap = ParameterUtils.getSingleton().getHomeTabhMap(path, id, String.valueOf(page));
        request(AppConfigs.home_zx_screen, homeTabhMap, false);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_list_layout;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new NewLabeiAdapter(getActivity());
        return adapter;
    }

    @Override
    public void loadMore() {
        super.loadMore();
        page++;
        Request<String> homeTabhMap = ParameterUtils.getSingleton().getHomeTabhMap(path, "10902", String.valueOf(page));
        request(AppConfigs.home_zx_screen, homeTabhMap, false);
    }

    @Override
    public void refresh() {
        super.refresh();
        page = 1;
        Request<String> homeTabhMap = ParameterUtils.getSingleton().getHomeTabhMap(path, "10902", String.valueOf(page));
        request(AppConfigs.home_zx_screen, homeTabhMap, false);
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        LabelSeekInfo reponseResult = JsonMananger.getReponseResult(response, LabelSeekInfo.class);
        ArrayList<TeachInfo> arr = reponseResult.getData().getArr();
        if (MyStrUtil.isEmpty(arr)) {
            return;
        }
        adapter.addList(isRefresh, arr);
    }

    @Override
    public void itemClick(int position, TeachInfo ziXunInfo) {
        MyDanceWebActivity.lunch(getActivity(), MyDanceWebActivity.ZIXUNTYPE, ziXunInfo.getTitle(), AppConfigs.ZixunShareUrl2 + ziXunInfo.getId(), true);
    }
}
