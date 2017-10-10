package mr.li.dance.ui.adapters;

import android.content.Context;

import mr.li.dance.R;

/**
 * 作者: Lixuewei
 * 版本: 1.0
 * 创建日期: 2017/5/22
 * 描述: 直播或者视频控件下边的类似进度的适配器
 * 修订历史:
 */

public class DirectseedSpeedAdapter extends BaseRecyclerAdapter<String> {
    public DirectseedSpeedAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.zhibo_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, String item) {
        holder.setText(R.id.num, position + "1" + ". ");
        holder.setText(R.id.name, item);
    }
}
