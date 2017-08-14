package mr.li.dance.ui.fragments.homepage;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mr.li.dance.R;
import mr.li.dance.ui.adapters.MusicAdapter;
import mr.li.dance.ui.fragments.BaseListFragment;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/14 0014
 * 描述:音乐界面
 * 修订历史:
 */
public class MusicFragment extends BaseListFragment {
    MusicAdapter adapter;
      List<Integer>list = new ArrayList<>();
    @Override
    public void initData() {
        for (int i = 0; i < 10; i++) {
            list.add(R.drawable.ic_launcher);
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_list_layout;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        adapter = new MusicAdapter(getActivity());
        return adapter;
    }
    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        adapter.refresh(list);

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
    public void itemClick(int position, Object value) {

    }
}
