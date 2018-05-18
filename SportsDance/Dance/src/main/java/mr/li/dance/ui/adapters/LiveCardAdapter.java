package mr.li.dance.ui.adapters;

import android.content.Context;

import mr.li.dance.R;
import mr.li.dance.models.LiveCard;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/5/18 0018
 * 描述:
 * 修订历史:
 */
public class LiveCardAdapter extends BaseRecyclerAdapter<LiveCard.DataBean.MenuBean> {
    public LiveCardAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.zhibo_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, LiveCard.DataBean.MenuBean item) {
        holder.setText(R.id.num, item.getStart_time());
        holder.setText(R.id.name, item.getTitle());
    }


}
