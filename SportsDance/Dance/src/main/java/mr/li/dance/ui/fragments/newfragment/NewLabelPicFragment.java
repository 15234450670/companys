package mr.li.dance.ui.fragments.newfragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yolanda.nohttp.rest.Request;

import java.util.ArrayList;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.LabelSeekInfo;
import mr.li.dance.models.TeachInfo;
import mr.li.dance.ui.activitys.album.AlbumActivity;
import mr.li.dance.ui.fragments.BaseListFragment;
import mr.li.dance.ui.fragments.adapter.NewLabeiPicAdapter;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/30 0030
 * 描述:       图片标签选中页
 * 修订历史:
 */
public class NewLabelPicFragment extends BaseListFragment<TeachInfo> {
    int page = 1;
    NewLabeiPicAdapter adapter;
    String             path;

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
        Request<String> homeTabhMap = ParameterUtils.getSingleton().getHomeTabhMap(path, "10905", String.valueOf(page));
        request(AppConfigs.home_zx_screen, homeTabhMap, false);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_list_layout;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new NewLabeiPicAdapter(getActivity());
        adapter.setItemClickListener(this);
        return adapter;
    }

    @Override
    public void loadMore() {
        super.loadMore();
        page++;
        Request<String> homeTabhMap = ParameterUtils.getSingleton().getHomeTabhMap(path, "10905", String.valueOf(page));
        request(AppConfigs.home_zx_screen, homeTabhMap, false);
    }

    @Override
    public void refresh() {
        super.refresh();
        page = 1;
        Request<String> homeTabhMap = ParameterUtils.getSingleton().getHomeTabhMap(path, "10905", String.valueOf(page));
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
    public void itemClick(int position, TeachInfo value) {
        AlbumActivity.lunch(this, value.getId(), value.getTitle());
    }
}