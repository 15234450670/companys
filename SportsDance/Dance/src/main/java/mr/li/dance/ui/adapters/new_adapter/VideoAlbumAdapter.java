package mr.li.dance.ui.adapters.new_adapter;

import android.content.Context;

import mr.li.dance.R;
import mr.li.dance.models.Video;
import mr.li.dance.ui.adapters.BaseRecyclerAdapter;
import mr.li.dance.ui.adapters.RecyclerViewHolder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/27 0027
 * 描述:
 * 修订历史:
 */
public class VideoAlbumAdapter extends BaseRecyclerAdapter<Video> {
    public VideoAlbumAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.video_detail_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Video item) {
        holder.setImageByUrlOrFilePath(R.id.detail_pic, item.getPicture(), R.drawable.default_banner);
        holder.setText(R.id.detail_title, item.getName());
        holder.setText(R.id.detail_time,item.getStart_time());

    }
}
