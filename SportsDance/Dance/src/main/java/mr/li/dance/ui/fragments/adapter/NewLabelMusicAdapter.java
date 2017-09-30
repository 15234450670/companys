package mr.li.dance.ui.fragments.adapter;

import android.content.Context;

import mr.li.dance.R;
import mr.li.dance.models.TeachInfo;
import mr.li.dance.ui.adapters.BaseRecyclerAdapter;
import mr.li.dance.ui.adapters.RecyclerViewHolder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/30 0030
 * 描述:    音乐标签选中适配器
 * 修订历史:
 */
public class NewLabelMusicAdapter extends BaseRecyclerAdapter<TeachInfo> {
    public NewLabelMusicAdapter(Context ctx) {
        super(ctx);
    }


    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.music_rec_type_2;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, TeachInfo item) {
        holder.setText(R.id.title, item.getTitle());
        holder.setImageByUrlOrFilePath(R.id.item_pic, item.img_fm, R.drawable.default_banner);
    }
}
