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
import mr.li.dance.models.MusicIndexPesponse;
import mr.li.dance.models.MusicRecAppBean;
import mr.li.dance.ui.activitys.music.DanceMusicActivity;
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
    private List<MusicRecAppBean> mDatas;
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
        final MusicRecAppBean musicRecAppBean = mDatas.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DanceMusicActivity.lunch(mContext,musicRecAppBean.getId(),musicRecAppBean.getName());

            }
        });

        if (!MyStrUtil.isEmpty(musicRecAppBean.getImg())) {
            holder.danceViewHolder.setRoundImageByUrlOrFilePath(R.id.item_pic, musicRecAppBean.getImg(), R.drawable.default_video);
            holder.danceViewHolder.setText(R.id.title,musicRecAppBean.getName());
        }

    }
    @Override
    public int getItemCount() {
        return mDatas ==null?0:mDatas.size();
    }

    public void refresh(MusicResponse homeResponse) {
        super.refresh();
        mDatas.clear();
        ArrayList<MusicRecAppBean> music_item = homeResponse.getData().getMusicRecApp();
        if (!MyStrUtil.isEmpty(music_item)) {
            mDatas.addAll(music_item);
        }

        notifyDataSetChanged();
    }

    public void loadMore(MusicIndexPesponse indexResponse) {
        ArrayList<MusicRecAppBean> music_item = indexResponse.getData();
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
