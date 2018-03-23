package mr.li.dance.ui.activitys.game;

import android.content.Context;

import mr.li.dance.R;
import mr.li.dance.ui.adapters.BaseRecyclerAdapter;
import mr.li.dance.ui.adapters.RecyclerViewHolder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/23 0023
 * 描述:
 * 修订历史:
 */
public class AssistAdapter extends BaseRecyclerAdapter<String> {
    public AssistAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.game_sponsor_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, String item) {
        String[] split = item.split(",");
        for (int i = 0; i < split.length; i++) {
            holder.setText(R.id.tv, split[i]);
        }
    }
}
