package mr.li.dance.ui.fragments.newfragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.yolanda.nohttp.rest.Request;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.HomeAlbumInfo;
import mr.li.dance.models.LabelPicInfo;
import mr.li.dance.models.PhotoClassBean;
import mr.li.dance.ui.activitys.album.AlbumActivity;
import mr.li.dance.ui.adapters.new_adapter.NewPicAdapter;
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
public class NewPicFragment extends BaseListFragment<PhotoClassBean> {
    NewPicAdapter adapter;
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
        adapter = new NewPicAdapter(getActivity());
        adapter.setItemClickListener(this);
        return adapter;
    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        path = arguments.getString("path");
        if (arguments == null) {
            return;
        }
        path = arguments.getString("path");
        Log.e("xxx", path);
        if (TextUtils.isEmpty(path)) {
            return;
        }
        if (path.equals("0")) {
            Request<String> request = ParameterUtils.getSingleton().getHomeAlbum2Map(page);
            request(AppConfigs.home_album, request, false);
        } else {
            Request<String> request = ParameterUtils.getSingleton().getHomeTabhMap(path, "10905", String.valueOf(page));
            request(AppConfigs.home_dianbo, request, false);
        }
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        HomeAlbumInfo reponseResult = JsonMananger.getReponseResult(response, HomeAlbumInfo.class);
        if (reponseResult.getData().getPhotoClass() != null) {
            adapter.addList(isRefresh, reponseResult.getData().getPhotoClass());
        } else {
            LabelPicInfo reponseResult1 = JsonMananger.getReponseResult(response, LabelPicInfo.class);
            adapter.addList(isRefresh, reponseResult1.getData().getArr());
        }

    }


    @Override
    public void refresh() {
        super.refresh();
        page = 1;
        if (path.equals("0")) {
            Request<String> request = ParameterUtils.getSingleton().getHomeAlbum2Map(page);
            request(AppConfigs.home_album, request, false);
        } else {
            Request<String> request = ParameterUtils.getSingleton().getHomeTabhMap(path, "10905", String.valueOf(page));
            request(AppConfigs.home_dianbo, request, false);
        }
    }

    @Override
    public void loadMore() {
        super.loadMore();
        page++;
        if (path.equals("0")) {
            Request<String> request = ParameterUtils.getSingleton().getHomeAlbum2Map(page);
            request(AppConfigs.home_album, request, false);
        } else {
            Request<String> request = ParameterUtils.getSingleton().getHomeTabhMap(path, "10905", String.valueOf(page));
            request(AppConfigs.home_album, request, false);
        }

    }

    @Override
    public void itemClick(int position, PhotoClassBean value) {
        AlbumActivity.lunch(this, value.getId(), value.getTitle());
    }
}
