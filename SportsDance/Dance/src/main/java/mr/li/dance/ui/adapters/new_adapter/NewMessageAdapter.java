package mr.li.dance.ui.adapters.new_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mr.li.dance.R;
import mr.li.dance.https.response.HomeZxResponse;
import mr.li.dance.https.response.ZiXunIndexResponse;
import mr.li.dance.models.ZiXunInfo;
import mr.li.dance.ui.adapters.DanceBaseAdapter;
import mr.li.dance.ui.adapters.ListViewItemClickListener;
import mr.li.dance.ui.adapters.viewholder.BaseViewHolder;
import mr.li.dance.utils.MyStrUtil;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/8 0008
 * 描述:  资讯FrameLayout适配器
 * 修订历史:
 */
public class NewMessageAdapter extends DanceBaseAdapter {

    Context mContext;
    public static final int TYPE_2 = 0xff02;
    private List<ZiXunInfo> mDatas;

    ListViewItemClickListener<ZiXunInfo> mItemClickListener;

    /**
     * 构造器
     * @param
     */
    public NewMessageAdapter(Context context, ListViewItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
        mDatas = new ArrayList<>();

    }


    @Override
    public int getItemCount() {
        if (MyStrUtil.isEmpty(mDatas)) {
            return 0;
        } else {
            return mDatas.size();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consultation_type1, null);
                return new ConsultationViewHolder3(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ConsultationViewHolder3) {
            bindType3((ConsultationViewHolder3) holder, position);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_2;
    }


    class ConsultationViewHolder3 extends BaseViewHolder {
        public TextView nameTv;

        public ConsultationViewHolder3(View view) {
            super(mContext, view);
            nameTv = (TextView) itemView.findViewById(R.id.name);
        }
    }

    private void bindType3(ConsultationViewHolder3 holder, final int position) {
        final ZiXunInfo ziXunInfo = mDatas.get(position);
        if (!MyStrUtil.isEmpty(ziXunInfo)) {
            holder.danceViewHolder.setText(R.id.name, ziXunInfo.getTitle());
            if (MyStrUtil.isEmpty(ziXunInfo.getWriter())) {
                holder.danceViewHolder.setViewVisibility(R.id.laiyuan, View.GONE);
            } else {
                holder.danceViewHolder.setText(R.id.from_tv, ziXunInfo.getWriter());
            }
                holder.danceViewHolder.setImageByUrlOrFilePath(R.id.imageView, ziXunInfo.getPicture(), R.drawable.default_video);



            /*if ("1".equals(ziXunInfo.getImg_num())) {
                holder.danceViewHolder.setRoundImageByUrlOrFilePath(R.id.imageView, ziXunInfo.getPicture(), R.drawable.default_video);
            } else {
                holder.danceViewHolder.setImageByUrlOrFilePath(R.id.pic1_iv, ziXunInfo.getPicture(), R.drawable.default_video);
                holder.danceViewHolder.setImageByUrlOrFilePath(R.id.pic2_iv, ziXunInfo.getPicture_2(), R.drawable.default_video);
                holder.danceViewHolder.setImageByUrlOrFilePath(R.id.pic3_iv, ziXunInfo.getPicture_3(), R.drawable.default_video);
            }*/
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.itemClick(position, mDatas.get(position));
                }
            });

        }


    }

    public void refresh(HomeZxResponse response) {
        super.refresh();
        mDatas.clear();
        ArrayList<ZiXunInfo> ziXunInfos = response.getData().getZxRec();

        if (!MyStrUtil.isEmpty(ziXunInfos)) {
            mDatas.addAll(ziXunInfos);
        }

        notifyDataSetChanged();
    }


    public void loadMore(ZiXunIndexResponse indexResponse) {
        ArrayList<ZiXunInfo> ziXunInfos = indexResponse.getData();
        if (!MyStrUtil.isEmpty(ziXunInfos)) {
            mDatas.addAll(ziXunInfos);
            Log.e("xxx", mDatas.toString());
            super.loadMore();
        }
        notifyDataSetChanged();
    }
}
