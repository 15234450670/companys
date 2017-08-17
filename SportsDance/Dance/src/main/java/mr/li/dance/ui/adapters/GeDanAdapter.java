package mr.li.dance.ui.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import mr.li.dance.R;
import mr.li.dance.models.GeDanInfo;
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
        holder.itemView.setTag(position);
        is = true;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is) {
                    Toast.makeText(context, "dd" + position, Toast.LENGTH_SHORT).show();
                    holder.setVisibility(R.id.gd_bo, View.VISIBLE);
                    holder.setTextAndColor(R.id.gd_music, item.getTitle(), mContext.getResources().getColor(R.color.gedan_item));
                    mDanceViewHolder.setText(R.id.gd_txt, item.getTitle());
                    is = false;

                } else {
                    Toast.makeText(context, "dd" + position, Toast.LENGTH_SHORT).show();
                    holder.setVisibility(R.id.gd_bo, View.INVISIBLE);
                    holder.setTextAndColor(R.id.gd_music, item.getTitle(), mContext.getResources().getColor(R.color.white));
                    is = true;

                }


            }
        });


    }
}
