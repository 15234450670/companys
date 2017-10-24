package mr.li.dance.ui.activitys.newActivitys;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;

import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.SwipeListActivity;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/24 0024
 * 描述:
 * 修订历史:
 */
public class Shequ_look extends SwipeListActivity {

    @Override
    public void initViews() {
        super.initViews();
        setTitle("关注");
    }

    @Override
    public void itemClick(int position, Object value) {

    }

    @Override
    public OnSwipeMenuItemClickListener getSwipeMenuItemClickListener() {

        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
        };
        return swipeMenuCreator;
    }


    @Override
    public RecyclerView.Adapter getAdapter() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.swipelist_layout;
    }
}
