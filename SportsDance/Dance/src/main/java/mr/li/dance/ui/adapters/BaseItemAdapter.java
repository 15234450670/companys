package mr.li.dance.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.models.AlbumInfo;
import mr.li.dance.models.BaseHomeItem;
import mr.li.dance.models.BaseItemAdapterType;
import mr.li.dance.models.MusicInfo;
import mr.li.dance.models.TeachDetailInfo;
import mr.li.dance.models.TeachInfo;
import mr.li.dance.models.Video;
import mr.li.dance.models.ZiXunInfo;
import mr.li.dance.utils.MyStrUtil;

import static mr.li.dance.R.layout.item_base;

/**
 * Created by Lixuewei on 2017/5/29.
 */

public class BaseItemAdapter extends BaseRecyclerAdapter<BaseHomeItem> {

    private final int viewType1           = 0x001;//正常模式
    private final int viewType2           = 0x002;//视频左右模式,资讯单图模式
    private final int viewType3           = 0x003; //教学模式
    private final int TYPE_ZIXUNONPIC     = 5;//资讯1张图
    private final int TYPE_ZIXU_THREE_PIC = 6;//资讯3张图
    private final int TYPE_MUSIC          = 7;
    private final int viewType4           = 0x004;   //图片
    private BaseItemAdapterType mAdatperType;

    public BaseItemAdapter(Context ctx, BaseItemAdapterType adapterType) {
        super(ctx);
        mAdatperType = adapterType;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        switch (viewType) {
            case viewType1:
                return R.layout.video_detail_item;
            case viewType2:
                return R.layout.item_base_type2;
            case TYPE_ZIXUNONPIC:
                return R.layout.item_consultation_type1;
            case TYPE_ZIXU_THREE_PIC:
                return R.layout.consultation_item_type4;
            case TYPE_MUSIC:
                return R.layout.dance_item;
            case viewType3:
                return R.layout.teach_item_more;
            case viewType4:
                return R.layout.item_base;
        }
        return item_base;
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
            case ALBUM:
                return viewType4;

        }
        return super.getItemViewType(position);
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, BaseHomeItem item) {
        if (item instanceof TeachInfo) {
            bindMatch(holder, (TeachInfo) item);
        } else if (item instanceof Video) {
            bindVideo(holder, (Video) item);
        } else if (item instanceof ZiXunInfo) {
            bindInfomation(holder, (ZiXunInfo) item);
        } else if (item instanceof AlbumInfo) {
            bindImageInfo(holder, (AlbumInfo) item);
        } else if (item instanceof TeachDetailInfo.DataBean.OtherListBean) {
            bindMore(holder, (TeachDetailInfo.DataBean.OtherListBean) item, position);
        } else if (item instanceof MusicInfo) {
            bindMusic(holder, (MusicInfo) item);
        }
    }

    private void bindMusic(RecyclerViewHolder holder, MusicInfo item) {
        holder.setRoundImageByUrlOrFilePath(R.id.wudao_pic, item.getImg_fm(), R.drawable.default_video);
        holder.setText(R.id.wudao_name, item.getTitle());
    }

    private void bindMore(final RecyclerViewHolder holder, TeachDetailInfo.DataBean.OtherListBean item, final int position) {
        holder.setText(R.id.sort_tv, position + 1 + "");
        holder.setImageByUrlOrFilePath(R.id.pic, item.getPicture(), R.drawable.default_video);
        holder.setText(R.id.name, item.getName());
        if (item.isClick){
            holder.getView(R.id.start_item).setVisibility(View.VISIBLE);
            holder.getView(R.id.time_tv).setVisibility(View.GONE);
        }else {
            holder.getView(R.id.start_item).setVisibility(View.GONE);
            holder.getView(R.id.time_tv).setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(item.getVideo_duration())) {

                holder.setText(R.id.time_tv, "时长： " + item.getVideo_duration());
            }
        }
    }

    private void bindMatch(RecyclerViewHolder holder, TeachInfo match) {
        holder.setImageByUrlOrFilePath(R.id.imageView, match.getImg_fm(), R.drawable.default_video);
        holder.setText(R.id.name, match.getTitle());
        if (!MyStrUtil.isEmpty(match.getDescribed())) {
            holder.getView(R.id.time_tv).setVisibility(View.VISIBLE);
            holder.setText(R.id.time_tv, match.getDescribed());
        }

        holder.setImageResDrawable(R.id.typeicon_tv, R.drawable.home_icon_008);
    }

    private void bindVideo(RecyclerViewHolder holder, Video video) {
        holder.setRoundImageByUrlOrFilePath(R.id.imageView, video.getPicture(), R.drawable.default_video);
        holder.setVisibility(R.id.typeicon_tv, View.VISIBLE);
        holder.setImageResDrawable(R.id.typeicon_tv, R.drawable.home_icon_008);
        holder.setText(R.id.name, video.getName());
        //  holder.setText(R.id.time_tv, video.getInserttime());
        // holder.setVisibility(R.id.typeicon_tv, View.VISIBLE);
        //holder.setImageResDrawable(R.id.typeicon_tv, R.drawable.home_icon_005);

    }

    private void bindInfomation(RecyclerViewHolder holder, ZiXunInfo information) {
        String title = information.getName();
        if (MyStrUtil.isEmpty(title)) {
            title = information.getTitle();
        }
        holder.setText(R.id.name, title);
        if (TextUtils.isEmpty(information.getWriter())) {
//            holder.getView(R.id.laiyuan).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.laiyuan).setVisibility(View.VISIBLE);
            holder.setText(R.id.from_tv, "来源 ："+information.getWriter());
        }

        if ("1".equals(information.getImg_num())) {
            holder.setImageByUrlOrFilePath(R.id.imageView, information.getPicture(), R.drawable.default_video);
        } else {
            holder.setImageByUrlOrFilePath(R.id.pic1_iv, information.getPicture(), R.drawable.default_video);
            holder.setImageByUrlOrFilePath(R.id.pic2_iv, information.getPicture_2(), R.drawable.default_video);
            holder.setImageByUrlOrFilePath(R.id.pic3_iv, information.getPicture_3(), R.drawable.default_video);
        }
    }

    private void bindImageInfo(RecyclerViewHolder holder, AlbumInfo albumInfo) {
        holder.setImageByUrlOrFilePath(R.id.imageView, albumInfo.getImg_fm(), R.drawable.default_video);
        holder.setText(R.id.name, albumInfo.getTitle());
        // holder.setText(R.id.time_tv, albumInfo.getInserttime());
        holder.setVisibility(R.id.typeicon_tv, View.INVISIBLE);
        holder.setVisibility(R.id.picnum_tv, View.VISIBLE);
        holder.setText(R.id.num_tv, albumInfo.getPhotos());
    }

    public void refresh(List list) {
        addList(true, list);
    }

    public void loadMore(List list) {

        addList(list);
    }

}
