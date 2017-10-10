package mr.li.dance.ui.fragments.adapter;

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
 * 创建日期:2017/9/28 0028
 * 描述:   资讯选中标签适配器
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
        if (MyStrUtil.isEmpty(item.getWriter())) {
            holder.getView(R.id.laiyuan).setVisibility(View.GONE);
        } else {
            holder.setText(R.id.from_tv, item.getWriter());
        }
    }
}
