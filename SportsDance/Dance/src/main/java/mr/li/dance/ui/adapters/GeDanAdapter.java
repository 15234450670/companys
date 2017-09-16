package mr.li.dance.ui.adapters;

import android.content.Context;
import android.view.View;

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
    Context context;

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
    public void bindData(final RecyclerViewHolder holder, int position, final GeDanInfo.DataBean.ListBean item) {
        holder.setText(R.id.gd_music, item.getTitle());
        if (item.isFalse) {
            holder.setVisibility(R.id.item_gd_laba, View.VISIBLE);
            holder.setTextAndColor(R.id.gd_music, item.getTitle(), mContext.getResources().getColor(R.color.gedan_item));
            mDanceViewHolder.setText(R.id.gd_txt, item.getTitle());
        } else {
            holder.setVisibility(R.id.item_gd_laba, View.GONE);
            holder.setTextAndColor(R.id.gd_music, item.getTitle(), mContext.getResources().getColor(R.color.black));
        }

    }

    /**
     * 改变歌单状态
     * position 那条数据
     */
    public void selectItem(int position) {
        for (int i = 0; i < mData.size(); i++) {
            GeDanInfo.DataBean.ListBean item = mData.get(i);
            item.isFalse = (position == i ? true : false);
        }
        notifyDataSetChanged();
    }

    public void selectNull(){
        for (int i = 0; i < mData.size(); i++) {
            GeDanInfo.DataBean.ListBean item = mData.get(i);
            item.isFalse = false;
        }
        notifyDataSetChanged();
    }

}
