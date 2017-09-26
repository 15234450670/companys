package mr.li.dance.ui.adapters.new_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.response.MusicResponse;
import mr.li.dance.models.LabelSelectMusicInfo;
import mr.li.dance.models.MusicInfo;
import mr.li.dance.ui.activitys.music.SongActivity;
import mr.li.dance.ui.adapters.DanceBaseAdapter;
import mr.li.dance.ui.adapters.viewholder.BaseViewHolder;
import mr.li.dance.utils.MyStrUtil;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/14 0014
 * 描述:
 * 修订历史:
 */
public class NewMusicAdapter extends DanceBaseAdapter {
    private List<MusicInfo> mDatas;
    Context mContext;
    private final int TYPE_2 = 2;//列表
    public NewMusicAdapter(Context context) {
        this.mContext = context;
        mDatas = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = null;
        switch (viewType) {
            case TYPE_2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_rec_type_2, null);
                viewHolder = new MyViewHolder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindType2((MyViewHolder) holder, position);
    }
    private void bindType2(final MyViewHolder holder, final int position) {
        final MusicInfo musicRecAppBean = mDatas.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongActivity.lunch(mContext,musicRecAppBean.getId(),musicRecAppBean.getTitle());

            }
        });

        if (!MyStrUtil.isEmpty(musicRecAppBean.getImg_fm())) {
            holder.danceViewHolder.setRoundImageByUrlOrFilePath(R.id.item_pic, musicRecAppBean.getImg_fm(), R.drawable.default_video);
            holder.danceViewHolder.setText(R.id.title,musicRecAppBean.getTitle());
        }

    }
    @Override
    public int getItemCount() {
        return mDatas ==null?0:mDatas.size();
    }

    public void refresh(MusicResponse homeResponse) {
        super.refresh();
        mDatas.clear();
        ArrayList<MusicInfo> music_class = homeResponse.getData().getMusic_class();
        if (!MyStrUtil.isEmpty(music_class)) {
            mDatas.addAll(music_class);
        }

        notifyDataSetChanged();
    }
    public void refresh1(LabelSelectMusicInfo homeResponse) {
        super.refresh();
        mDatas.clear();
        ArrayList<MusicInfo> music_class = homeResponse.getData().getArr();
        if (!MyStrUtil.isEmpty(music_class)) {
            mDatas.addAll(music_class);
        }

        notifyDataSetChanged();
    }

    public void loadMore(MusicResponse indexResponse) {
        ArrayList<MusicInfo> music_item = indexResponse.getData().getMusic_class();
        if (!MyStrUtil.isEmpty(music_item)) {
            mDatas.addAll(music_item);
            super.loadMore();
        }
        notifyDataSetChanged();
    }
    public void loadMore1(LabelSelectMusicInfo indexResponse) {
        ArrayList<MusicInfo> music_item = indexResponse.getData().getArr();
        if (!MyStrUtil.isEmpty(music_item)) {
            mDatas.addAll(music_item);
            super.loadMore();
        }
        notifyDataSetChanged();
    }

    class MyViewHolder extends BaseViewHolder {

        public MyViewHolder(View itemView) {
            super(mContext, itemView);

        }
    }
    @Override
    public int getItemViewType(int position) {

        return TYPE_2;


    }
}
