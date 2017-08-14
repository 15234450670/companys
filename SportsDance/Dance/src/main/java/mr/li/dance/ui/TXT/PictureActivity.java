package mr.li.dance.ui.TXT;

import android.support.v7.widget.RecyclerView;

import mr.li.dance.ui.activitys.base.BaseListActivity;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/11 0011
 * 描述:
 * 修订历史:
 */
public class PictureActivity extends BaseListActivity {
    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    public void initViews() {
      setTitle("图文");
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return null;
    }

    @Override
    public void itemClick(int position, Object value) {

    }
}
