package mr.li.dance.ui.adapters;

import android.content.Context;
import android.view.View;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.models.AlbumInfo;
import mr.li.dance.models.BaseHomeItem;
import mr.li.dance.models.BaseItemAdapterType;
import mr.li.dance.models.TeachDetailInfo;
import mr.li.dance.models.Video;
import mr.li.dance.models.ZhiBoInfo;
import mr.li.dance.models.ZiXunInfo;
import mr.li.dance.utils.MyStrUtil;

/**
 * Created by Lixuewei on 2017/5/29.
 */

public class BaseItemAdapter extends BaseRecyclerAdapter<BaseHomeItem> {

    private final int viewType1 = 0x001;//正常模式
    private final int viewType2 = 0x002;//视频左右模式,资讯单图模式
    private final int viewType3 = 0x003; //教学模式
    private final int TYPE_ZIXUNONPIC = 5;//资讯1张图
    private final int TYPE_ZIXU_THREE_PIC = 6;//资讯3张图
    private final int TYPE_MUSIC = 7;

    private BaseItemAdapterType mAdatperType;

    public BaseItemAdapter(Context ctx, BaseItemAdapterType adapterType) {
        super(ctx);
        mAdatperType = adapterType;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        switch (viewType) {
            case viewType1:
                return R.layout.item_base;
            case viewType2:
                return R.layout.item_base_type2;
            case TYPE_ZIXUNONPIC:
                return R.layout.item_consultation_type1;
            case TYPE_ZIXU_THREE_PIC:
                return R.layout.consultation_item_type4;
            case TYPE_MUSIC:
                return R.layout.dance_item;
            case viewType3:
                //

                return R.layout.teach_item_more;

        }
        return R.layout.item_base;
    }

    @Override
    public int getItemViewType(int position) {
        switch (mAdatperType) {
            case CommentType:
                return viewType1;
            case VIDEOTYPE2:
                return viewType2;
            case ZIXUN:
                ZiXunInfo ziXunInfo = (ZiXunInfo) mData.get(position);
                if ("1".equals(ziXunInfo.getImg_num())) {
                    return TYPE_ZIXUNONPIC;
                } else {
                    return TYPE_ZIXU_THREE_PIC;
                }
            case MUSIC:
                return TYPE_MUSIC;
            case TEACHER:
                return viewType3;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, BaseHomeItem item) {
        if (item instanceof ZhiBoInfo) {
            bindMatch(holder, (ZhiBoInfo) item);
        } else if (item instanceof Video) {
            bindVideo(holder, (Video) item);
        } else if (item instanceof ZiXunInfo) {
            bindInfomation(holder, (ZiXunInfo) item);
        } else if (item instanceof AlbumInfo) {
            bindImageInfo(holder, (AlbumInfo) item);
        } else if (item instanceof TeachDetailInfo.DataBean.OtherListBean) {
            bindMore(holder, (TeachDetailInfo.DataBean.OtherListBean) item);
        }
    }

    private void bindMore(RecyclerViewHolder holder, TeachDetailInfo.DataBean.OtherListBean item) {
        holder.setImageByUrlOrFilePath(R.id.more_pic, item.getPicture(), R.drawable.default_video);
    }

    private void bindMatch(RecyclerViewHolder holder, ZhiBoInfo match) {
        holder.setImageByUrlOrFilePath(R.id.imageView, match.getPicture(), R.drawable.default_video);
        holder.setText(R.id.name, match.getName());
        holder.setText(R.id.time_tv, match.getStarttime());
        holder.setVisibility(R.id.typeicon_tv, View.VISIBLE);
        holder.setImageResDrawable(R.id.typeicon_tv, R.drawable.home_icon_008);
    }

    private void bindVideo(RecyclerViewHolder holder, Video video) {
        holder.setRoundImageByUrlOrFilePath(R.id.imageView, video.getPicture(), R.drawable.default_video);
        holder.setText(R.id.name, video.getName());
        holder.setText(R.id.time_tv, video.getInserttime());
        holder.setVisibility(R.id.typeicon_tv, View.VISIBLE);
        holder.setImageResDrawable(R.id.typeicon_tv, R.drawable.home_icon_005);

    }

    private void bindInfomation(RecyclerViewHolder holder, ZiXunInfo information) {
        String title = information.getName();
        if (MyStrUtil.isEmpty(title)) {
            title = information.getTitle();
        }
        holder.setText(R.id.name, title);
        holder.setText(R.id.time_tv, information.getInserttime());
        holder.setText(R.id.from_tv, "来源: "+information.getWriter());
        if ("1".equals(information.getImg_num())) {
            holder.setImageByUrlOrFilePath(R.id.imageView, information.getPicture(), R.drawable.default_video);
        } else {
            holder.setImageByUrlOrFilePath(R.id.pic1_iv, information.getPicture(), R.drawable.default_video);
            holder.setImageByUrlOrFilePath(R.id.pic2_iv, information.getPicture_2(), R.drawable.default_video);
            holder.setImageByUrlOrFilePath(R.id.pic3_iv, information.getPicture_3(), R.drawable.default_video);
        }
    }

    private void bindImageInfo(RecyclerViewHolder holder, AlbumInfo albumInfo) {
        holder.setImageByUrlOrFilePath(R.id.imageView, albumInfo.getPicture(), R.drawable.default_video);
        holder.setText(R.id.name, albumInfo.getClass_name());
        holder.setText(R.id.time_tv, albumInfo.getInserttime());
        holder.setVisibility(R.id.typeicon_tv, View.INVISIBLE);
        holder.setImageResDrawable(R.id.typeicon_tv, R.drawable.home_icon_005);
        holder.setVisibility(R.id.picnum_tv, View.VISIBLE);
    }

    public void refresh(List list) {
        addList(true, list);
    }

    public void loadMore(List list) {

        addList(list);
    }

}
