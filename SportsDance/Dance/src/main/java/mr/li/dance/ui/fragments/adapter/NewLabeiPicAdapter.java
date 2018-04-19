package mr.li.dance.ui.fragments.adapter;

import android.content.Context;

import mr.li.dance.R;
import mr.li.dance.models.TeachInfo;
import mr.li.dance.ui.adapters.BaseRecyclerAdapter;
import mr.li.dance.ui.adapters.RecyclerViewHolder;
import mr.li.dance.utils.MyStrUtil;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/30 0030
 * 描述:     图片标签选中适配器
 * 修订历史:
 */
public class NewLabeiPicAdapter extends BaseRecyclerAdapter<TeachInfo> {
    public NewLabeiPicAdapter(Context ctx) {
        super(ctx);
    }


    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.album_items;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, TeachInfo item) {
        holder.setText(R.id.name, item.getTitle());
        holder.setImageByUrlOrFilePath(R.id.imageView, item.getPicture(), R.drawable.default_banner);
        if (!MyStrUtil.isEmpty(item.getPhotos())) {
            holder.setText(R.id.num_tv, "共 " + item.getPhotos() + " 张");
        } else {
            holder.setText(R.id.num_tv, "共 " + item.sum + " 张");
        }
    }
}