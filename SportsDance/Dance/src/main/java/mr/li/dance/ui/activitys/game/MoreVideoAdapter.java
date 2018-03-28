package mr.li.dance.ui.activitys.game;

import android.content.Context;
import android.view.View;

import mr.li.dance.R;
import mr.li.dance.models.Video;
import mr.li.dance.ui.adapters.BaseRecyclerAdapter;
import mr.li.dance.ui.adapters.RecyclerViewHolder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/22 0022
 * 描述:       视频更多适配器
 * 修订历史:
 */
public class MoreVideoAdapter extends BaseRecyclerAdapter<Video> {
    public MoreVideoAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.home_video_fragment;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Video item) {
        holder.getImageView(R.id.tubiao).setVisibility(View.VISIBLE);
        holder.setImageByUrlOrFilePath(R.id.imageView, item.getPicture(), R.drawable.default_video);
        holder.setText(R.id.name, item.getName()) ;


    }
}
