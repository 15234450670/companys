package mr.li.dance.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import mr.li.dance.R;
import mr.li.dance.models.DanceMusic;
import mr.li.dance.ui.activitys.music.SongActivity;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/14 0014
 * 描述:
 * 修订历史:
 */
public class DanceMusicAdapter extends BaseRecyclerAdapter<DanceMusic.DataBean> {
     Context context;
    public DanceMusicAdapter(Context ctx) {
        super(ctx);
        this.context = ctx;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.dance_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, final DanceMusic.DataBean item) {
        holder.setImageByUrlOrFilePath(R.id.wudao_pic, item.getImg_fm(),R.drawable.default_video);
        holder.setText(R.id.wudao_name, item.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        SongActivity.lunch((Activity) context,item.getId(),item.getTitle());
            }
        });
    }
}
