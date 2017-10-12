package mr.li.dance.ui.fragments.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;

import mr.li.dance.R;
import mr.li.dance.models.AlbumInfo;
import mr.li.dance.models.BaseHomeItem;
import mr.li.dance.ui.adapters.ListViewItemClickListener;
import mr.li.dance.ui.adapters.RecyclerViewHolder;
import mr.li.dance.utils.MyStrUtil;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/6 0006
 * 描述:     收藏相册适配器
 * 修订历史:
 */
public class NewCollectXC extends SwipeMenuAdapter<RecyclerViewHolder> {
    Context mContext;
    private ArrayList<BaseHomeItem> mDatas;
    ListViewItemClickListener<BaseHomeItem> mItemClickListener;
              private see s;
    public NewCollectXC(Context context, ListViewItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
        mDatas = new ArrayList<>();

    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {

            return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_albumcollect, parent, false);

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
            bindAlbum(holder, position);

    }

    private void bindAlbum(RecyclerViewHolder holder, int position) {
        AlbumInfo albumInfo = (AlbumInfo) mDatas.get(position);
        holder.setImageByUrlOrFilePath(R.id.imageView, albumInfo.getImg_fm(), R.drawable.default_video);
        holder.setText(R.id.name, albumInfo.getTitle());
        holder.setVisibility(R.id.typeicon_tv, View.INVISIBLE);
        holder.setImageResDrawable(R.id.typeicon_tv, R.drawable.home_icon_005);
        holder.setVisibility(R.id.picnum_tv, View.VISIBLE);
        holder.setText(R.id.time_tv, albumInfo.getInserttime());
     //   ImageLoaderManager.getSingleton().LoadCircle(mContext, albumInfo.getPicture_src(), (ImageView) holder.getView(R.id.headicon), R.drawable.icon_mydefault);

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

    public void addList(boolean isRefresh,   ArrayList<AlbumInfo> list ) {
        if (isRefresh) {
            mDatas.clear();
            currentPage = 1;
        }
        if (!MyStrUtil.isEmpty(list)) {
            mDatas.addAll(list);
            currentPage++;
            notifyDataSetChanged();
        }
    }

    public BaseHomeItem getItem(int position) {
        return mDatas.get(position);
    }

    public void removePosition(BaseHomeItem homeItem) {
       Dialogs(homeItem);
    }
    private void Dialogs(final BaseHomeItem homeItem) {
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle("提示")
                .setMessage("是否取消收藏?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(mContext, "已取消收藏", Toast.LENGTH_SHORT).show();
                        mDatas.remove(homeItem);
                        if (MyStrUtil.isEmpty(mDatas)) {
                            if (s != null) {
                                s.NoSee();
                            } else {
                                s.Look();
                            }
                        }
                        notifyDataSetChanged();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.setCanceledOnTouchOutside(false);
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
    public interface see {
        void NoSee();

        void Look();
    }

    public void Nosee(see s) {
        this.s = s;
    }
}
