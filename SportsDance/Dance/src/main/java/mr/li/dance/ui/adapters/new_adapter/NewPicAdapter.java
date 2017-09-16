package mr.li.dance.ui.adapters.new_adapter;

import android.content.Context;

import mr.li.dance.R;
import mr.li.dance.models.AlbumInfo;
import mr.li.dance.ui.adapters.BaseRecyclerAdapter;
import mr.li.dance.ui.adapters.RecyclerViewHolder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/14 0014
 * 描述:
 * 修订历史:
 */
public class NewPicAdapter extends BaseRecyclerAdapter<AlbumInfo> {
    Context mContext;
    public NewPicAdapter(Context ctx) {
        super(ctx);
        mContext = ctx;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.new_video_item;
    }

        @Override
        public void bindData(RecyclerViewHolder holder, final int position, final AlbumInfo item) {
            final AlbumInfo albumInfo = mData.get(position);
            holder.setText(R.id.name, albumInfo.getCompete_name());
            holder.setRoundImageByUrlOrFilePath(R.id.imageView, albumInfo.getImg_fm(), R.drawable.default_video);

        }

}
