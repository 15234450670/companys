package mr.li.dance.ui.fragments.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;

import mr.li.dance.R;
import mr.li.dance.models.BaseHomeItem;
import mr.li.dance.models.Video;
import mr.li.dance.ui.adapters.ListViewItemClickListener;
import mr.li.dance.ui.adapters.RecyclerViewHolder;
import mr.li.dance.utils.MyStrUtil;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/6 0006
 * 描述:
 * 修订历史:
 */
public class NewCollectSP extends SwipeMenuAdapter<RecyclerViewHolder> {
    Context mContext;
    private ArrayList<BaseHomeItem> mDatas;
    ListViewItemClickListener<BaseHomeItem> mItemClickListener;

    public NewCollectSP(Context context, ListViewItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
        mDatas = new ArrayList<>();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {

        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect, parent, false);

    }

    @Override
    public RecyclerViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(mContext, realContentView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.itemClick(position, mDatas.get(position));
            }
        });

            bindVideo(holder, position);

    }

    private void bindVideo(RecyclerViewHolder holder, int position) {
        Video video = (Video) mDatas.get(position);
        holder.setImageByUrlOrFilePath(R.id.imageView, video.getImg_fm(), R.drawable.default_video);
        holder.setText(R.id.name, video.getName());
        holder.setText(R.id.time_tv, video.getInserttime());
        holder.setVisibility(R.id.typeicon_tv, View.VISIBLE);
        holder.setImageResDrawable(R.id.typeicon_tv, R.drawable.home_icon_005);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class DefaultViewHolder extends RecyclerViewHolder {
        public DefaultViewHolder(Context context, View itemView) {
            super(context, itemView);
        }
    }

    public void addList(boolean isRefresh,  ArrayList<Video> data) {
        if (isRefresh) {
            mDatas.clear();
            currentPage = 1;
        }
        if (!MyStrUtil.isEmpty(data)) {
            mDatas.addAll(data);
            currentPage++;
            notifyDataSetChanged();
        }
    }

    public BaseHomeItem getItem(int position) {
        return mDatas.get(position);
    }

    public void removePosition(BaseHomeItem homeItem) {
        Dialogs( homeItem);
    }

    private void Dialogs(final BaseHomeItem homeItem) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
      builder.setTitle("提示");
        builder.setMessage("是否取消收藏?");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDatas.remove(homeItem);
                Toast.makeText(mContext, "已取消收藏", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
                dialogInterface.dismiss();
            }
        }) ;
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        })   ;
        builder.create().show();
    }


    public void removeByID(String deleteId) {
        BaseHomeItem deleteItem = null;
        for (BaseHomeItem homeItem : mDatas) {
            if (TextUtils.equals(homeItem.getId(), deleteId)) {
                deleteItem = homeItem;
                break;
            }
        }
        if (deleteItem != null) {
            removePosition(deleteItem);
        }
    }

    public int currentPage = 1;

    public int getNextPage() {
        return currentPage + 1;
    }
}
