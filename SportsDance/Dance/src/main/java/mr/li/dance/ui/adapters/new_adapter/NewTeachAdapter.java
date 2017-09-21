package mr.li.dance.ui.adapters.new_adapter;

import android.content.Context;
import android.view.View;

import mr.li.dance.R;
import mr.li.dance.models.TeachInfo;
import mr.li.dance.ui.adapters.BaseRecyclerAdapter;
import mr.li.dance.ui.adapters.RecyclerViewHolder;
import mr.li.dance.utils.MyStrUtil;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/20 0020
 * 描述:
 * 修订历史:
 */
public class NewTeachAdapter extends BaseRecyclerAdapter<TeachInfo> {

    private final String tag = getClass().getSimpleName();

    public NewTeachAdapter(Context ctx) {
        super(ctx);
    }


    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_albumnotitle;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, final TeachInfo item) {
        TeachInfo ziXunInfo = mData.get(position);
        if (!MyStrUtil.isEmpty(ziXunInfo)) {
            holder.setText(R.id.name, ziXunInfo.getTitle());
            if (MyStrUtil.isEmpty(ziXunInfo.getDescribed())) {
                holder.getView(R.id.time_tv).setVisibility(View.GONE);
            } else {
                holder.getView(R.id.time_tv).setVisibility(View.VISIBLE);
                holder.setText(R.id.time_tv, ziXunInfo.getDescribed());
            }
            holder.setImageByUrlOrFilePath(R.id.imageView, ziXunInfo.getImg(), R.drawable.default_video);


        }


    }
}
