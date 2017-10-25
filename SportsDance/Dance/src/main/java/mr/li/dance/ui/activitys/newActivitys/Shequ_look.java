package mr.li.dance.ui.activitys.newActivitys;

import android.content.Context;
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

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.MyFansInfo;
import mr.li.dance.models.MyFansInfo.DataBean;
import mr.li.dance.ui.activitys.SwipeListActivity;
import mr.li.dance.ui.adapters.new_adapter.PersonLookAdapter;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.UserInfoManager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/24 0024
 * 描述:      我的关注
 * 修订历史:
 */
public class Shequ_look extends SwipeListActivity {
    String            userid;
    PersonLookAdapter adapter;
    private DataBean mDelItem;

    @Override
    public void initViews() {
        super.initViews();
        setTitle("关注");
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        userid = mIntentExtras.getString("itemId");
        Log.e("idididid", userid);
    }

    @Override
    public void initDatas() {
        super.initDatas();
        Request<String> personLook = ParameterUtils.getSingleton().getPersonLook(userid);
        request(AppConfigs.person_look, personLook, false);
    }

    @Override
    public void itemClick(int position, Object value) {

    }


    @Override
    public RecyclerView.Adapter getAdapter() {
        String userId = UserInfoManager.getSingleton().getUserId(this);
        adapter = new PersonLookAdapter(this, userId);
        return adapter;
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        Log.e("TAG:: ", response);
        if (AppConfigs.user_collection == what) {
            adapter.removePosition(mDelItem);
        }

        if (AppConfigs.person_look == what) {
            MyFansInfo reponseResult = JsonMananger.getReponseResult(response, MyFansInfo.class);
            List<DataBean> data = reponseResult.getData();
            if (MyStrUtil.isEmpty(data)) {
                mDanceViewHolder.setViewVisibility(R.id.nodatalayout, View.VISIBLE);
                mDanceViewHolder.setText(R.id.nodate_desc, "暂无关注");
                mDanceViewHolder.setViewVisibility(R.id.refresh, View.GONE);
            } else {
                mDanceViewHolder.setViewVisibility(R.id.nodatalayout, View.GONE);
                mDanceViewHolder.setViewVisibility(R.id.refresh, View.VISIBLE);
                adapter.addList(isRefresh, data);
            }
        }

    }

    @Override
    public int getContentViewId() {
        return R.layout.swipelist_layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void refresh() {
        super.refresh();
    }

    @Override
    public void loadMore() {
        super.loadMore();
    }

    @Override
    public OnSwipeMenuItemClickListener getSwipeMenuItemClickListener() {
        OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
            @Override
            public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
                closeable.smoothCloseMenu();// 关闭被点击的菜单。
                if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                    DataBean homeItem = adapter.getItem(adapterPosition);
                    cancelCollect(homeItem);
                }
            }
        };
        return menuItemClickListener;
    }

    private void cancelCollect(DataBean homeItem) {
        mDelItem = homeItem;
        String userId = UserInfoManager.getSingleton().getUserId(Shequ_look.this);
        Request<String> request = ParameterUtils.getSingleton().getPersonCancelLook(userId, homeItem.getUserid(), 1);
        request(AppConfigs.user_collection, request, false);


    }


    public SwipeMenuCreator getSwipeMenuCreator() {
        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                String userId = UserInfoManager.getSingleton().getUserId(Shequ_look.this);
                if (userid.equals(userId)) {
                    int width = getResources().getDimensionPixelSize(R.dimen.spacing_160);
                    int height = getResources().getDimensionPixelSize(R.dimen.spacing_199);
                    int textsize = getResources().getDimensionPixelSize(R.dimen.textsize12);
                    // 添加右侧的，如果不添加，则右侧不会出现菜单。
                    {
                        SwipeMenuItem deleteItem = new SwipeMenuItem(Shequ_look.this)
                                .setBackgroundDrawable(R.drawable.selector_red)
                                .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                                .setTextColor(Color.WHITE)
                                .setTextSize(textsize)
                                .setWidth(width)
                                .setHeight(height);
                        swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
                    }
                }

            }
        };
        return swipeMenuCreator;
    }


    public static void lunch(Context context, String id) {
        Intent intent = new Intent(context, Shequ_look.class);
        intent.putExtra("itemId", id);
        context.startActivity(intent);
    }
}
