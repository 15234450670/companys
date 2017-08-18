package mr.li.dance.ui.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import mr.li.dance.R;
import mr.li.dance.models.GeDanInfo;
import mr.li.dance.ui.activitys.music.PlayMusicActivity;
import mr.li.dance.utils.DanceViewHolder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/17 0017
 * 描述:
 * 修订历史:
 */
public class GeDanAdapter extends BaseRecyclerAdapter<GeDanInfo.DataBean.ListBean> {


    DanceViewHolder mDanceViewHolder;
    Context         context;
    boolean         is;

    public GeDanAdapter(Context context, DanceViewHolder mDanceViewHolder) {
        super(context);
        this.context = context;
        this.mDanceViewHolder = mDanceViewHolder;

    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.gedan_item;
    }

    @Override
    public void bindData(final RecyclerViewHolder holder, final int position, final GeDanInfo.DataBean.ListBean item) {
        holder.setText(R.id.gd_music, item.getTitle());
       final ImageView gd_bo = mDanceViewHolder.getImageView(R.id.gd_bo);
        final ImageView gd_stop = mDanceViewHolder.getImageView(R.id.gd_stop);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is) {
                    holder.setVisibility(R.id.gd_bo, View.VISIBLE);
                    holder.setTextAndColor(R.id.gd_music, item.getTitle(), mContext.getResources().getColor(R.color.gedan_item));
                    mDanceViewHolder.setText(R.id.gd_txt, item.getTitle());
                    gd_bo.setVisibility(View.INVISIBLE);
                    gd_stop.setVisibility(View.VISIBLE);
                    is = false;

                } else {
                    holder.setVisibility(R.id.gd_bo, View.INVISIBLE);
                    holder.setTextAndColor(R.id.gd_music, item.getTitle(), mContext.getResources().getColor(R.color.white));
                    gd_bo.setVisibility(View.VISIBLE);
                    gd_stop.setVisibility(View.INVISIBLE);
                    is = true;
                }


            }
        });

        /**
         * 跳转到播放器
         */
        mDanceViewHolder.setClickListener(R.id.gd_tiao, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textValue = mDanceViewHolder.getTextValue(R.id.gd_txt);
                PlayMusicActivity.lunch(context,textValue);
            }
        });
        /**
         * 播放
         */
        mDanceViewHolder.setClickListener(R.id.gd_bo, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
