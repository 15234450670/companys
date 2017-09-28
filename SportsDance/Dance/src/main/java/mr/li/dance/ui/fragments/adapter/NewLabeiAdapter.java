package mr.li.dance.ui.fragments.adapter;

import android.content.Context;

import mr.li.dance.R;
import mr.li.dance.models.TeachInfo;
import mr.li.dance.ui.adapters.BaseRecyclerAdapter;
import mr.li.dance.ui.adapters.RecyclerViewHolder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/28 0028
 * 描述:
 * 修订历史:
 */
public class NewLabeiAdapter extends BaseRecyclerAdapter<TeachInfo> {
    public NewLabeiAdapter(Context ctx) {
        super(ctx);
    }


    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_consultation_type1;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, TeachInfo item) {
        holder.setText(R.id.name, item.getTitle());
        holder.setImageByUrlOrFilePath(R.id.imageView, item.getPicture(), R.drawable.default_banner);
    }
}
