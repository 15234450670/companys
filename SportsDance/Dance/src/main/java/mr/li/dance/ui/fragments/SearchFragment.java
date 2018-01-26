package mr.li.dance.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.yolanda.nohttp.rest.Request;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.HomeAlbumResponse;
import mr.li.dance.https.response.HomeVideoIndexResponse;
import mr.li.dance.https.response.TeacherRespone;
import mr.li.dance.https.response.ZiXunIndexResponse;
import mr.li.dance.models.AlbumInfo;
import mr.li.dance.models.BaseHomeItem;
import mr.li.dance.models.BaseItemAdapterType;
import mr.li.dance.models.HomeMusicScreen;
import mr.li.dance.models.ZiXunInfo;
import mr.li.dance.ui.activitys.MyDanceWebActivity;
import mr.li.dance.ui.activitys.album.AlbumActivity;
import mr.li.dance.ui.activitys.music.SongActivity;
import mr.li.dance.ui.activitys.newActivitys.TeachDetailsActivity;
import mr.li.dance.ui.activitys.video.VideoDetailActivity;
import mr.li.dance.ui.adapters.BaseItemAdapter;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;

/**
 * Created by Lixuewei on 2017/5/29.
 */

public class SearchFragment extends BaseListFragment<BaseHomeItem> {

    private String mType;
    BaseItemAdapter mBaseItemAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Bundle bundle = getArguments();
        mType = bundle.getString("type");
    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentView() {
        return R.layout.list_layout;
    }

    @Override
    public void itemClick(int position, BaseHomeItem value) {
        if (TextUtils.equals("teach", mType)) {
            // ZhiBoDetailActivity.lunch(getActivity(), value.getId());
            TeachDetailsActivity.lunch(this, value.getId(), value.getImg_fm(), value.getTitle(), value.getDescribed());
        } else if (TextUtils.equals("video", mType)) {
            VideoDetailActivity.lunch(getActivity(), value.getId());
        } else if (TextUtils.equals("article", mType)) {
            ZiXunInfo xunInfo = (ZiXunInfo) value;
            String title = xunInfo.getName();
            if (MyStrUtil.isEmpty(title)) {
                title = xunInfo.getTitle();
            }
            String url = String.format(AppConfigs.ZixunShareUrl, String.valueOf(value.getId()));
            MyDanceWebActivity.lunch(getActivity(), MyDanceWebActivity.ZIXUNTYPE, title, url, true);
        } else if (TextUtils.equals("photo_class", mType)) {
            AlbumInfo albumInfo = (AlbumInfo) value;
            AlbumActivity.lunch(getActivity(), value.getId(), albumInfo.getClass_name());
        } else if (TextUtils.equals("music_class", mType)) {
            SongActivity.lunch(getActivity(), value.getId(), value.getTitle());
        }
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        if (TextUtils.equals("article", mType)) {
            mBaseItemAdapter = new BaseItemAdapter(getActivity(), BaseItemAdapterType.ZIXUN);
        } else if (TextUtils.equals("teach", mType)) {
            mBaseItemAdapter = new BaseItemAdapter(getActivity(), BaseItemAdapterType.ALBUM);
        } else if (TextUtils.equals("video", mType)) {
            mBaseItemAdapter = new BaseItemAdapter(getActivity(), BaseItemAdapterType.ALBUM);
        } else if (TextUtils.equals("music_class", mType)) {
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
            mRecyclerview.setLayoutManager(manager);
            mBaseItemAdapter = new BaseItemAdapter(getActivity(), BaseItemAdapterType.MUSIC);
        } else if (TextUtils.equals("photo_class", mType)) {
            mBaseItemAdapter = new BaseItemAdapter(getActivity(), BaseItemAdapterType.ALBUM);
        }
        mBaseItemAdapter.setItemClickListener(this);
        return mBaseItemAdapter;

      /*  if (TextUtils.equals("video_live", mType)) {
            mBaseItemAdapter = new BaseItemAdapter(getActivity(), BaseItemAdapterType.ALBUM);
        } else if (TextUtils.equals("video", mType)) {
            mBaseItemAdapter = new BaseItemAdapter(getActivity(), BaseItemAdapterType.ALBUM);
        } else if (TextUtils.equals("article", mType)) {
            mBaseItemAdapter = new BaseItemAdapter(getActivity(), BaseItemAdapterType.ZIXUN);
        } else if (TextUtils.equals("photo_class", mType)) {
            mBaseItemAdapter = new BaseItemAdapter(getActivity(), BaseItemAdapterType.ALBUM);
        } else if (TextUtils.equals("music_class", mType)) {
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
            mRecyclerview.setLayoutManager(manager);
            mBaseItemAdapter = new BaseItemAdapter(getActivity(), BaseItemAdapterType.MUSIC);
        }
        mBaseItemAdapter.setItemClickListener(this);
        return mBaseItemAdapter;*/
    }


    @Override
    public void refresh() {
        super.refresh();
        Request<String> request = ParameterUtils.getSingleton().getHomeSearchMap(mType, searchContent, 1);
        request(AppConfigs.home_Search, request, false);
    }

    String searchContent;

    @Override
    public void loadMore() {
        super.loadMore();
        Request<String> request = ParameterUtils.getSingleton().getHomeSearchMap(mType, searchContent, mBaseItemAdapter.getNextPage());
        request(AppConfigs.home_Search, request, false);
    }

    public void refresh(String content, String type) {
        if (content.equals(searchContent)) {
            return;
        }
        mType = type;
        searchContent = content;
        refresh();
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e("xxx", response);
        List list = null;
        if (TextUtils.equals("teach", mType)) {
            TeacherRespone indexResponse = JsonMananger.getReponseResult(response, TeacherRespone.class);

            list = indexResponse.getData();
        } else if (TextUtils.equals("video", mType)) {
            HomeVideoIndexResponse indexResponse = JsonMananger.getReponseResult(response, HomeVideoIndexResponse.class);

            list = indexResponse.getData();

        } else if (TextUtils.equals("article", mType)) {
            ZiXunIndexResponse indexResponse = JsonMananger.getReponseResult(response, ZiXunIndexResponse.class);

            list = indexResponse.getData();
        } else if (TextUtils.equals("photo_class", mType)) {
            HomeAlbumResponse indexResponse = JsonMananger.getReponseResult(response, HomeAlbumResponse.class);

            list = indexResponse.getData();
        } else if (TextUtils.equals("music_class", mType)) {
            HomeMusicScreen indexResponse = JsonMananger.getReponseResult(response, HomeMusicScreen.class);
            list = indexResponse.getData();
        }
        if (MyStrUtil.isEmpty(list)) {
            danceViewHolder.getView(R.id.wu).setVisibility(View.VISIBLE);
        } else {
            danceViewHolder.getView(R.id.wu).setVisibility(View.GONE);
            mBaseItemAdapter.addList(isRefresh, list);
        }


    }
}
