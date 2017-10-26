package mr.li.dance.ui.adapters.new_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yolanda.nohttp.rest.Request;

import java.util.ArrayList;
import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.CallServer;
import mr.li.dance.https.HttpListener;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.PersonResponse;
import mr.li.dance.models.PersonItemInfo;
import mr.li.dance.models.ReportInfo;
import mr.li.dance.ui.adapters.DanceBaseAdapter;
import mr.li.dance.ui.adapters.viewholder.BaseViewHolder;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.UserInfoManager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/24 0024
 * 描述:
 * 修订历史:
 */
public class PersonItemAdapter extends DanceBaseAdapter {
    Context mContext;
    private List<PersonItemInfo> mDatas;

    public PersonItemAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shequ_one_pic, null);
        return new MyViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindType((MyViewHolder) holder, position);
    }

    private void bindType(MyViewHolder holder, int position) {
        holder.danceViewHolder.setViewVisibility(R.id.shequ_ll_min, View.GONE);
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
            Log.d("size", picture_arr.size() + "");
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
        ImageView imageView = holder.danceViewHolder.getImageView(R.id.shequ_dianz_iv);
        int is_upvote = mDatas.get(position).getIs_upvote();
        if (is_upvote == 1) {
            imageView.setImageResource(R.drawable.dianzan2);
        } else {
            imageView.setImageResource(R.drawable.dianzan1);
        }
        onClickListen(holder, position);
    }

    private void onClickListen(final MyViewHolder holder, final int position) {
        final String userId = UserInfoManager.getSingleton().getUserId(mContext);//自己的ID
        //点赞
        View view1 = holder.danceViewHolder.getView(R.id.shequ_dianz_rl);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int operation = mDatas.get(position).getIs_upvote() == 1 ? 2 : 1;
                Log.e("operation",operation+"");
                Request<String> personLike = ParameterUtils.getSingleton().getPersonLike(userId, operation, mDatas.get(position).getId());
                CallServer.getRequestInstance().add(mContext, 0, personLike, new HttpListener() {
                    @Override
                    public void onSucceed(int what, String response) {
                        ReportInfo like = JsonMananger.getReponseResult(response, ReportInfo.class);
                        String data = like.getData();
                        mDatas.get(position).setUpvote(data);
                        mDatas.get(position).setIs_upvote();
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailed(int what, int responseCode, String response) {

                    }
                }, false, false);
                ImageView imageView = holder.danceViewHolder.getImageView(R.id.shequ_dianz_iv);
                if (mDatas.get(position).isDianZan()) {
                    imageView.setImageResource(R.drawable.dianzan2);
                } else {
                    imageView.setImageResource(R.drawable.dianzan1);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        if (MyStrUtil.isEmpty(mDatas)) {
            return 0;
        } else {
            return mDatas.size();
        }
    }

    public void refresh(PersonResponse homeResponse) {
        mDatas.clear();
        List<PersonItemInfo> data = homeResponse.getData();
        if (!MyStrUtil.isEmpty(data)) {
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void loadMore(PersonResponse homeResponse) {
        List<PersonItemInfo> data = homeResponse.getData();
        if (!MyStrUtil.isEmpty(data)) {
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    public class MyViewHolder extends BaseViewHolder {

        public MyViewHolder(Context context, View itemView) {
            super(context, itemView);
        }
    }


    private void OnePic(MyViewHolder holder) {
        holder.danceViewHolder.setViewVisibility(R.id.iamge_layout, View.VISIBLE);
        holder.danceViewHolder.setViewVisibility(R.id.iamge_layout_three, View.GONE);
        holder.danceViewHolder.setViewVisibility(R.id.iamge_layout_two, View.GONE);
    }

    private void TwoPic(MyViewHolder holder) {
        holder.danceViewHolder.setViewVisibility(R.id.iamge_layout, View.GONE);
        holder.danceViewHolder.setViewVisibility(R.id.iamge_layout_three, View.GONE);
        holder.danceViewHolder.setViewVisibility(R.id.iamge_layout_two, View.VISIBLE);
    }

    private void ThreePic(MyViewHolder holder) {
        holder.danceViewHolder.setViewVisibility(R.id.iamge_layout, View.GONE);
        holder.danceViewHolder.setViewVisibility(R.id.iamge_layout_three, View.VISIBLE);
        holder.danceViewHolder.setViewVisibility(R.id.iamge_layout_two, View.GONE);
    }

}
