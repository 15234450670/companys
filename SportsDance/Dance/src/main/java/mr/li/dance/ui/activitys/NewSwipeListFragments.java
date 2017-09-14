package mr.li.dance.ui.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import mr.li.dance.R;
import mr.li.dance.ui.fragments.BaseListFragment;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/6 0006
 * 描述:
 * 修订历史:
 */
public abstract class NewSwipeListFragments<T> extends BaseListFragment<T> {
    protected SwipeMenuRecyclerView        mSwipeMenuRecyclerView;
    protected OnSwipeMenuItemClickListener menuItemClickListener;

    @Override
    public void initData() {
        menuItemClickListener = getSwipeMenuItemClickListener();

    }

    public abstract OnSwipeMenuItemClickListener getSwipeMenuItemClickListener();
    public abstract SwipeMenuCreator getSwipeMenuCreator();

    @Override
    public void initViews() {
        initRefreshLayout();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }
    public void init(){

        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) getActivity().findViewById(R.id.recyclerview);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mSwipeMenuRecyclerView.setLayoutManager(layoutManager);
        mSwipeMenuRecyclerView.setSwipeMenuCreator(getSwipeMenuCreator());
        // 设置菜单Item点击监听。
        mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        mSwipeMenuRecyclerView.setAdapter(getAdapter());
    }
}
