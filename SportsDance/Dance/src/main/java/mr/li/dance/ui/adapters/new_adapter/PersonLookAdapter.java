package mr.li.dance.ui.adapters.new_adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;
import java.util.List;

import mr.li.dance.R;
import mr.li.dance.models.MyFansInfo;
import mr.li.dance.ui.adapters.RecyclerViewHolder;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.glide.ImageLoaderManager;


/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/25 0025
 * 描述:
 * 修订历史:
 */
public class PersonLookAdapter extends SwipeMenuAdapter<RecyclerViewHolder> {
    Context mContext;
    private List<MyFansInfo.DataBean> mDatas;
    String id;

    public PersonLookAdapter(Context context, String userId) {
        mContext = context;
        mDatas = new ArrayList<>();
        id = userId;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.myfans, parent, false);
    }

    @Override
    public RecyclerViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(mContext, realContentView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        bindLook(holder, position);
    }

    private void bindLook(RecyclerViewHolder holder, int position) {
        ImageLoaderManager.getSingleton().LoadCircle(mContext, mDatas.get(position).getPicture_src(), holder.getImageView(R.id.fans_head), R.drawable.default_icon);
        holder.setText(R.id.fans_name, mDatas.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public int currentPage = 1;

    public int getNextPage() {
        return currentPage + 1;
    }

    public void addList(boolean isRefresh, List<MyFansInfo.DataBean> data) {
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

    public MyFansInfo.DataBean getItem(int position) {
        return mDatas.get(position);
    }

    public void removePosition(MyFansInfo.DataBean homeItem) {
        Dialogs(homeItem);
    }

    private void Dialogs(final MyFansInfo.DataBean homeItem) {
            AlertDialog dialog = new AlertDialog.Builder(mContext)
                    .setTitle("提示")
                    .setMessage("是否取消关注?")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(mContext, "已取消关注", Toast.LENGTH_SHORT).show();
                            mDatas.remove(homeItem);
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

    class DefaultViewHolder extends RecyclerViewHolder {
        public DefaultViewHolder(Context context, View itemView) {
            super(context, itemView);
        }
    }
}
