package mr.li.dance.ui.adapters.new_adapter;

import android.content.Context;

import mr.li.dance.R;
import mr.li.dance.models.SpecialInfo;
import mr.li.dance.ui.adapters.BaseRecyclerAdapter;
import mr.li.dance.ui.adapters.RecyclerViewHolder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/28 0028
 * 描述:  视频相关专辑适配
 * 修订历史:
 */
public class SpecialAdapter extends BaseRecyclerAdapter<SpecialInfo> {

    public SpecialAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_albumnotitle;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, SpecialInfo item) {
         holder.setImageByUrlOrFilePath(R.id.imageView,item.getPicture(),R.drawable.default_banner);
        holder.setText(R.id.name,item.getName());
        holder.setText(R.id.time_tv,item.getStart_time());
    }
}
