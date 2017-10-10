package mr.li.dance.ui.fragments.adapter;

import android.content.Context;
import android.view.View;

import mr.li.dance.R;
import mr.li.dance.models.TeachInfo;
import mr.li.dance.ui.adapters.BaseRecyclerAdapter;
import mr.li.dance.ui.adapters.RecyclerViewHolder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/30 0030
 * 描述:   视频标签选中适配器
 * 修订历史:
 */
public class NewLabelVideoAdapter extends BaseRecyclerAdapter<TeachInfo> {
    public NewLabelVideoAdapter(Context ctx) {
        super(ctx);
    }


    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.home_video_fragment;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, TeachInfo item) {
        holder.setText(R.id.name, item.name);
        holder.setImageByUrlOrFilePath(R.id.imageView, item.getPicture(), R.drawable.default_banner);
        holder.setText(R.id.time_tv,item.getDescribed());
        holder.getView(R.id.time_tv).setVisibility(View.GONE);
    }
}
