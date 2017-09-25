package mr.li.dance.ui.adapters.new_adapter;

import android.content.Context;
import android.view.View;

import mr.li.dance.R;
import mr.li.dance.models.PhotoClassBean;
import mr.li.dance.ui.adapters.BaseRecyclerAdapter;
import mr.li.dance.ui.adapters.RecyclerViewHolder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/14 0014
 * 描述:
 * 修订历史:
 */
public class NewPicAdapter extends BaseRecyclerAdapter<PhotoClassBean> {
    Context mContext;

    public NewPicAdapter(Context ctx) {
        super(ctx);
        mContext = ctx;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.album_items;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, PhotoClassBean item) {
        PhotoClassBean albumInfo = mData.get(position);
        holder.setText(R.id.name, albumInfo.getTitle());

        if (albumInfo.getPhotos()!=0) {
            holder.setText(R.id.num_tv,"共 "+albumInfo.getPhotos()+" 张");
        } else {
            holder.getView(R.id.picnum_tv).setVisibility(View.GONE);
        }
        holder.setImageByUrlOrFilePath(R.id.imageView, albumInfo.getImg_fm(), R.drawable.default_banner);
    }


}
