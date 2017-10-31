package mr.li.dance.ui.adapters.new_adapter;

import android.content.Context;
import android.util.Log;

import mr.li.dance.R;
import mr.li.dance.ui.adapters.BaseRecyclerAdapter;
import mr.li.dance.ui.adapters.RecyclerViewHolder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/27 0027
 * 描述:    社区详情图文适配器
 * 修订历史:
 */
public class DetailsListAdapter extends BaseRecyclerAdapter<String> {


    public DetailsListAdapter(Context ctx) {
        super(ctx);

    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.shequ_details_pic_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, String item) {
        Log.e("item", item);
        holder.setImageByUrlOrFilePath(R.id.pic_item, item, R.drawable.default_banner);
    }


}
