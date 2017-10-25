package mr.li.dance.ui.adapters.new_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.response.ShequResponse;
import mr.li.dance.models.ShequInfo;
import mr.li.dance.ui.activitys.newActivitys.PersonageActivity;
import mr.li.dance.ui.adapters.DanceBaseAdapter;
import mr.li.dance.ui.adapters.viewholder.BaseViewHolder;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.glide.ImageLoaderManager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/23 0023
 * 描述:
 * 修订历史:
 */
public class SheQuAdapter extends DanceBaseAdapter {
    private List<ShequInfo> mDatas;

    Context mContext;

    public SheQuAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shequ_one_pic, null);
        return new MyViewHolder1(mContext, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindType((MyViewHolder1) holder, position);
    }

    private void bindType(MyViewHolder1 holder, final int position) {
        final ShequInfo.UserBean user = mDatas.get(position).getUser();
        holder.danceViewHolder.setText(R.id.shequ_name, user.getUsername());
        holder.danceViewHolder.setText(R.id.shequ_time, mDatas.get(position).getDynamic_time());
        ImageLoaderManager.getSingleton().LoadCircle(mContext, user.getPicture_src(), holder.danceViewHolder.getImageView(R.id.shequ_head), R.drawable.default_icon);
        holder.danceViewHolder.setText(R.id.title, mDatas.get(position).getTitle());
        if (MyStrUtil.isEmpty(mDatas.get(position).getContent())) {
            holder.danceViewHolder.setViewVisibility(R.id.content, View.GONE);
        } else {
            holder.danceViewHolder.setViewVisibility(R.id.content, View.VISIBLE);
            holder.danceViewHolder.setText(R.id.content, mDatas.get(position).getContent());
        }
        if (MyStrUtil.isEmpty(mDatas.get(position).getUpvote())) {
            holder.danceViewHolder.setText(R.id.shequ_dianz_tv, "0");
        } else {
            holder.danceViewHolder.setText(R.id.shequ_dianz_tv, mDatas.get(position).getUpvote());
        }
        if (MyStrUtil.isEmpty(mDatas.get(position).getComment_count())) {
            holder.danceViewHolder.setText(R.id.shequ_pinl_tv, "0");
        } else {
            holder.danceViewHolder.setText(R.id.shequ_pinl_tv, mDatas.get(position).getComment_count());
        }

        String type = mDatas.get(position).getType();
        if (type.equals("1")) {
            //图片
            List<String> picture_arr = mDatas.get(position).getPicture_arr();
            if (MyStrUtil.isEmpty(picture_arr)) {
                holder.danceViewHolder.setViewVisibility(R.id.iamge_layout, View.GONE);
                holder.danceViewHolder.setViewVisibility(R.id.iamge_layout_three, View.GONE);
                holder.danceViewHolder.setViewVisibility(R.id.iamge_layout_two, View.GONE);
            } else if (picture_arr.size() == 1) {
                OnePic(holder);
                holder.danceViewHolder.setImageByUrlOrFilePath(R.id.imageView, mDatas.get(position).getPicture_arr().get(0), R.drawable.default_banner);
            } else if (picture_arr.size() == 2) {
                TwoPic(holder);
                holder.danceViewHolder.setImageByUrlOrFilePath(R.id.imageView_two_1, mDatas.get(position).getPicture_arr().get(0), R.drawable.default_banner);
                holder.danceViewHolder.setImageByUrlOrFilePath(R.id.imageView_two_2, mDatas.get(position).getPicture_arr().get(1), R.drawable.default_banner);
            } else if (picture_arr.size() >= 3) {
                ThreePic(holder);
                holder.danceViewHolder.setImageByUrlOrFilePath(R.id.imageView_three_1, mDatas.get(position).getPicture_arr().get(0), R.drawable.default_banner);
                holder.danceViewHolder.setImageByUrlOrFilePath(R.id.imageView_three_2, mDatas.get(position).getPicture_arr().get(1), R.drawable.default_banner);
                holder.danceViewHolder.setImageByUrlOrFilePath(R.id.imageView_three_3, mDatas.get(position).getPicture_arr().get(2), R.drawable.default_banner);
            }

        } else {
            //视频
            OnePic(holder);
            ImageView imageView = holder.danceViewHolder.getImageView(R.id.sq_ship_iv);
            imageView.setVisibility(View.VISIBLE);
            holder.danceViewHolder.setImageResDrawable(R.id.imageView, R.drawable.default_banner, R.drawable.default_video);

        }
        onClickListen(holder, position, mDatas);
    }

    @Override
    public int getItemCount() {
        if (MyStrUtil.isEmpty(mDatas)) {
            return 0;
        } else {
            return mDatas.size();
        }
    }

    private void OnePic(MyViewHolder1 holder) {
        holder.danceViewHolder.setViewVisibility(R.id.iamge_layout, View.VISIBLE);
        holder.danceViewHolder.setViewVisibility(R.id.iamge_layout_three, View.GONE);
        holder.danceViewHolder.setViewVisibility(R.id.iamge_layout_two, View.GONE);
    }

    private void TwoPic(MyViewHolder1 holder) {
        holder.danceViewHolder.setViewVisibility(R.id.iamge_layout, View.GONE);
        holder.danceViewHolder.setViewVisibility(R.id.iamge_layout_three, View.GONE);
        holder.danceViewHolder.setViewVisibility(R.id.iamge_layout_two, View.VISIBLE);
    }

    private void ThreePic(MyViewHolder1 holder) {
        holder.danceViewHolder.setViewVisibility(R.id.iamge_layout, View.GONE);
        holder.danceViewHolder.setViewVisibility(R.id.iamge_layout_three, View.VISIBLE);
        holder.danceViewHolder.setViewVisibility(R.id.iamge_layout_two, View.GONE);
    }

    public void refresh(ShequResponse homeResponse) {
        mDatas.clear();
        ArrayList<ShequInfo> data = homeResponse.getData();
        if (!MyStrUtil.isEmpty(data)) {
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void loadMore(ShequResponse homeResponse) {
        ArrayList<ShequInfo> data = homeResponse.getData();
        if (!MyStrUtil.isEmpty(data)) {
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    public class MyViewHolder1 extends BaseViewHolder {

        public MyViewHolder1(Context context, View itemView) {
            super(context, itemView);
        }
    }

    public void onClickListen(MyViewHolder1 holder, final int position, final List<ShequInfo> mDatas) {
        //个人信息
        final ShequInfo.UserBean user = mDatas.get(position).getUser();
        //点击头像去个人主页
        View view = holder.danceViewHolder.getView(R.id.shequ_ll_min);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonageActivity.lunch(mContext, mDatas.get(position).getUid(), user.getUsername(), user.getPicture_src());
            }
        });
        //点赞


        //点击更多


        //点击标题正文图片视频


    }

}
