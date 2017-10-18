package mr.li.dance.ui.fragments.newfragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yolanda.nohttp.rest.Request;

import java.util.ArrayList;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.LabelSeekInfo;
import mr.li.dance.models.TeachInfo;
import mr.li.dance.ui.activitys.video.VideoDetailActivity;
import mr.li.dance.ui.fragments.BaseListFragment;
import mr.li.dance.ui.fragments.adapter.NewLabelVideoAdapter;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/30 0030
 * 描述:      视频标签选中页
 * 修订历史:
 */
public class NewLabelVideoFragment  extends BaseListFragment<TeachInfo> {
    int page = 1;
    NewLabelVideoAdapter adapter;
    String               path;

    private final String tag = getClass().getSimpleName();

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
        Log.d(tag, path);
        Request<String> homeTabhMap = ParameterUtils.getSingleton().getHomeTabhMap(path, "10901", String.valueOf(page));
        request(AppConfigs.home_zx_screen, homeTabhMap, false);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_list_layout;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new NewLabelVideoAdapter(getActivity());
        adapter.setItemClickListener(this);
        return adapter;
    }

    @Override
    public void loadMore() {
        super.loadMore();
        page++;
        Request<String> homeTabhMap = ParameterUtils.getSingleton().getHomeTabhMap(path, "10901", String.valueOf(page));
        request(AppConfigs.home_zx_screen, homeTabhMap, false);
    }

    @Override
    public void refresh() {
        super.refresh();
        page = 1;
        Request<String> homeTabhMap = ParameterUtils.getSingleton().getHomeTabhMap(path, "10901", String.valueOf(page));
        request(AppConfigs.home_zx_screen, homeTabhMap, false);
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e("re::",response);
        LabelSeekInfo reponseResult = JsonMananger.getReponseResult(response, LabelSeekInfo.class);
        ArrayList<TeachInfo> arr = reponseResult.getData().getArr();
        if (MyStrUtil.isEmpty(arr)) {
            danceViewHolder.getView(R.id.wu).setVisibility(View.VISIBLE);
            return;
        } else {
            danceViewHolder.getView(R.id.wu).setVisibility(View.GONE);
            adapter.addList(isRefresh, arr);
        }

    }

    @Override
    public void itemClick(int position, TeachInfo ziXunInfo) {
        if (!TextUtils.isEmpty(ziXunInfo.getId())) {
            VideoDetailActivity.lunch(getActivity(), ziXunInfo.getId());
        } else {
            Toast.makeText(getActivity(), "暂时没有信息..", Toast.LENGTH_SHORT).show();
        }

    }
}