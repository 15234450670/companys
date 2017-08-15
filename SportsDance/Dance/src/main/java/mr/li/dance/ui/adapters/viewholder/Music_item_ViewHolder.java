package mr.li.dance.ui.adapters.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import mr.li.dance.R;
import mr.li.dance.models.BaseHomeItem;
import mr.li.dance.utils.DanceViewHolder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/15 0015
 * 描述:
 * 修订历史:
 */
public class Music_item_ViewHolder  extends RecyclerView.ViewHolder{
    DanceViewHolder danceViewHolder;
    Context mContext;
    public Music_item_ViewHolder(Context context, View view) {
        super(view);

        mContext = context;
        danceViewHolder = new DanceViewHolder(mContext, view);
    }

    public void setViewDatas(BaseHomeItem baseHomeItem) {
        int type = baseHomeItem.getType();
        switch (type){
            case 10101://直播
                bindZhibo(baseHomeItem);
                break;
            case 10102://点播
                bindVideo(baseHomeItem);
                break;
            default:
                break;
        }
    }
    private void bindZhibo(BaseHomeItem zhibo) {
        danceViewHolder.setRoundImageByUrlOrFilePath(R.id.item_pic, zhibo.getPicture_app(), R.drawable.default_video);

    }
    private void bindVideo(BaseHomeItem video) {
        danceViewHolder.setRoundImageByUrlOrFilePath(R.id.item_pic, video.getPicture_app(), R.drawable.default_video);
    }
}
