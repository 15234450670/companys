package mr.li.dance.ui.activitys.game;

import android.content.Context;

import mr.li.dance.R;
import mr.li.dance.models.GameGradeSearchResponse;
import mr.li.dance.ui.adapters.BaseRecyclerAdapter;
import mr.li.dance.ui.adapters.RecyclerViewHolder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/26 0026
 * 描述:
 * 修订历史:
 */
public class GradeSearchAdapter extends BaseRecyclerAdapter<GameGradeSearchResponse.DataBean> {
    public GradeSearchAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_scoregroup;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, GameGradeSearchResponse.DataBean item) {
        holder.setText(R.id.groupname_tv, item.getGroup_name());
        holder.setText(R.id.groupnum_tv, item.getNum() + ": ");
    }
}
