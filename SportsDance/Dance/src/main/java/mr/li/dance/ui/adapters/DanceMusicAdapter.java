package mr.li.dance.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/14 0014
 * 描述:
 * 修订历史:
 */
public class DanceMusicAdapter extends DanceBaseAdapter {
    Context context;
    public DanceMusicAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
