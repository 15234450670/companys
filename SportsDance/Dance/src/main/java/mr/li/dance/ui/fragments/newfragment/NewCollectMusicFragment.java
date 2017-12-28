package mr.li.dance.ui.fragments.newfragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yolanda.nohttp.rest.Request;

import java.util.ArrayList;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.StringResponse;
import mr.li.dance.models.BaseHomeItem;
import mr.li.dance.models.HomeMusicScreen;
import mr.li.dance.models.MusicInfo;
import mr.li.dance.ui.activitys.NewSwipeListFragments;
import mr.li.dance.ui.activitys.music.SongActivity;
import mr.li.dance.ui.fragments.adapter.NewCollectMusic;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.UserInfoManager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/27 0027
 * 描述:收藏音乐
 * 修订历史:
 */
public class NewCollectMusicFragment extends NewSwipeListFragments<BaseHomeItem> {
    NewCollectMusic mAdapter;
    private BaseHomeItem mDelItem;
    private View         viewById;

    @Override
    public void itemClick(int position, BaseHomeItem value) {
        MusicInfo albumInfo = (MusicInfo) value;
        SongActivity.lunch(getActivity(), value.getId(), albumInfo.getTitle(), true);
        getActivity().finish();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        mAdapter = new NewCollectMusic(getActivity(), this);
        mAdapter.Nosee(new NewCollectMusic.see() {
            @Override
            public void NoSee() {
                No();
            }

            @Override
            public void Look() {
                Looks();
            }
        });
        return mAdapter;
    }


    @Override
    public void initViews() {
        super.initViews();
       /* GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerview.setLayoutManager(layoutManager);
        mRecyclerview.setAdapter(mAdapter);*/
        viewById = mView.findViewById(R.id.nodatalayout);
    }

    private void No() {

        viewById.setVisibility(View.VISIBLE);
        viewById.bringToFront();
    }

    private void Looks() {
        viewById.setVisibility(View.GONE);
    }

    @Override
    public void init() {

        danceViewHolder.getImageView(R.id.nodate_icon).setImageResource(R.drawable.no_collect_videolsit);
        danceViewHolder.setText(R.id.nodate_desc, "您还没有收藏音乐");
        super.init();

    }

    @Override
    public int getContentView() {
        return R.layout.swipelist_layout;
    }


    @Override
    public void initData() {
        super.initData();
        refresh();
    }


    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e("music_info:: ", response);
        if (AppConfigs.user_collection == what) {
            StringResponse reponseResult = JsonMananger.getReponseResult(response, StringResponse.class);
            mAdapter.removePosition(mDelItem);
        }
        if (AppConfigs.user_collections == what) {
            HomeMusicScreen reponseResult = JsonMananger.getReponseResult(response, HomeMusicScreen.class);
            ArrayList<MusicInfo> data = reponseResult.getData();
            if (!MyStrUtil.isEmpty(data)) {
                mAdapter.addList(isRefresh, data);
            }
        }
        if (mAdapter.getItemCount() == 0) {
            danceViewHolder.setViewVisibility(R.id.nodatalayout, View.VISIBLE);
        } else {
            danceViewHolder.setViewVisibility(R.id.nodatalayout, View.INVISIBLE);
        }
    }

    private void getData(int index) {
        String userId = UserInfoManager.getSingleton().getUserId(getActivity());
        Log.e("xxx", userId);
        Request<String> request = ParameterUtils.getSingleton().getCollectionListMap2(userId, 10603, index);
        request(AppConfigs.user_collections, request, false);
    }

    @Override
    public void refresh() {
        super.refresh();
        getData(1);
    }

    @Override
    public void loadMore() {
        super.loadMore();
        getData(mAdapter.getNextPage());
    }

    @Override
    public OnSwipeMenuItemClickListener getSwipeMenuItemClickListener() {
        OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
            @Override
            public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
                closeable.smoothCloseMenu();// 关闭被点击的菜单。
                if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                    BaseHomeItem homeItem = mAdapter.getItem(adapterPosition);
                    cancelCollect(homeItem);
                }
            }
        };
        return menuItemClickListener;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void cancelCollect(BaseHomeItem item) {
        mDelItem = item;
        String userId = UserInfoManager.getSingleton().getUserId(getActivity());
        Request<String> request = ParameterUtils.getSingleton().getCollectionMap(userId, item.getId(), 10603, 2);
        request(AppConfigs.user_collection, request, false);
    }

    public SwipeMenuCreator getSwipeMenuCreator() {
        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                int width = getResources().getDimensionPixelSize(R.dimen.spacing_160);
                int height = getResources().getDimensionPixelSize(R.dimen.spacing_199);
                int textsize = getResources().getDimensionPixelSize(R.dimen.textsize12);
                // 添加右侧的，如果不添加，则右侧不会出现菜单。
                {
                    SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity())
                            .setBackgroundDrawable(R.drawable.selector_red)
                            .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                            .setTextColor(Color.WHITE)
                            .setTextSize(textsize)
                            .setWidth(width)
                            .setHeight(height);
                    swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
                }
            }
        };
        return swipeMenuCreator;
    }
}
