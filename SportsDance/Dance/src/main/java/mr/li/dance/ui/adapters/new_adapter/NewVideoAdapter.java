package mr.li.dance.ui.adapters.new_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.response.HomeVideoResponse;
import mr.li.dance.models.LabelSeekInfoVideo;
import mr.li.dance.models.Video;
import mr.li.dance.ui.activitys.MainActivity;
import mr.li.dance.ui.activitys.video.VideoDetailActivity;
import mr.li.dance.ui.adapters.DanceBaseAdapter;
import mr.li.dance.ui.adapters.viewholder.BaseViewHolder;
import mr.li.dance.utils.MyStrUtil;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/12 0012
 * 描述:
 * 修订历史:
 */
public class NewVideoAdapter extends DanceBaseAdapter {
    Context mContext;
    private List<Video>          mDatas;

    private int EXARCOUNT = 0;
    private boolean hasTypeList;
    private boolean hasQuickList;

    /**
     * 构造器
     * @param
     */
    public NewVideoAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<>();

    }


    @Override
    public int getItemCount() {
        if (MyStrUtil.isEmpty(mDatas)) {
            return 0;
        }
        return mDatas.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_albumnotitle, null);
        return new ViewHolderMain(view);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindTypeMain((ViewHolderMain) holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return position;

    }



    class ViewHolderMain extends BaseViewHolder {
        public ViewHolderMain(View view) {
            super(mContext, view);
        }
    }

    private void bindTypeMain(ViewHolderMain holder, int position) {
        final Video video = mDatas.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.myBinder.binderIsPlaying()) {
                    MainActivity.myBinder.binderPause();
                }
                VideoDetailActivity.lunch(mContext, video.getId());

            }
        });
       /* holder.danceViewHolder.setViewVisibility(R.id.typeicon_tv, View.VISIBLE);

        holder.danceViewHolder.setRoundImageByUrlOrFilePath(R.id.imageView, video.getPicture(), R.drawable.default_video);
        holder.danceViewHolder.setText(R.id.name, video.getTitle());
        //   holder.danceViewHolder.setText(R.id.time_tv, video.getStart_time());*/
        if (!MyStrUtil.isEmpty(video)) {
            if (TextUtils.isEmpty( video.getTitle())) {
                holder.danceViewHolder.setText(R.id.name, video.getName());
            } else {
                holder.danceViewHolder.setText(R.id.name, video.getTitle());
            }

            holder.danceViewHolder.setImageByUrlOrFilePath(R.id.imageView, video.getPicture(), R.drawable.default_video);
        }
    }
    public void refresh(HomeVideoResponse response) {
        super.refresh();
        EXARCOUNT = 0;
        hasQuickList = false;
        hasTypeList = false;
        mDatas.clear();

        ArrayList<Video> videos = response.getData().getDb_rec();
        if (!MyStrUtil.isEmpty(videos)) {
            mDatas.addAll(videos);
        }
        notifyDataSetChanged();
    }
    public void refresh1(LabelSeekInfoVideo response) {
        super.refresh();
        EXARCOUNT = 0;
        hasQuickList = false;
        hasTypeList = false;
        mDatas.clear();
        ArrayList<Video> videos = response.getData().getArr();
        if (!MyStrUtil.isEmpty(videos)) {
            mDatas.addAll(videos);
        }
        notifyDataSetChanged();
    }

    public void loadMore(HomeVideoResponse response) {

        ArrayList<Video> videos = response.getData().getDb_rec();
        if (!MyStrUtil.isEmpty(videos)) {
            mDatas.addAll(videos);
            super.loadMore();
        }
        notifyDataSetChanged();
    }
    public void loadMore1(LabelSeekInfoVideo response) {
        ArrayList<Video> videos = response.getData().getArr();
        if (!MyStrUtil.isEmpty(videos)) {
            mDatas.addAll(videos);
            super.loadMore();
        }
        notifyDataSetChanged();
    }

}
