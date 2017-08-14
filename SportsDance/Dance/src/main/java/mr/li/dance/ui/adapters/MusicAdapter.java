package mr.li.dance.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import mr.li.dance.R;
import mr.li.dance.ui.widget.SlideShowView;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/14 0014
 * 描述:
 * 修订历史:
 */
public class MusicAdapter extends DanceBaseAdapter {
    private final int TYPE_1              = 1;//轮播页面
    private final int TYPE_2              = 2;//列表
     List<Integer> lists;
    Context mContext;
    public MusicAdapter(Context context) {
        super();
        mContext = context;
        lists = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view = null;
        switch (viewType){
            case TYPE_1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_type1, null);
                viewHolder= new BannderViewholder(view);
                break;
            case TYPE_2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_rec_type_2, null);
                viewHolder = new ItemViewholder(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BannderViewholder) {
            bindType1((BannderViewholder) holder, position);
        } else if (holder instanceof ItemViewholder) {
            bindType2((ItemViewholder) holder, position);
        }


    }

    private void bindType1(BannderViewholder holder, int position) {

    }

    private void bindType2(ItemViewholder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return lists.size();
    }

    /**
     * 刷新
     * @param list
     */
    public void refresh(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
                  lists.add(list.get(i));
        }
    }

    /**
     * 加载
     */
    public void loadMore() {

    }

    class BannderViewholder extends RecyclerView.ViewHolder{
        SlideShowView slideShowView;
        public BannderViewholder(View itemView) {
            super(itemView);
            slideShowView = (SlideShowView) itemView.findViewById(R.id.slideShowView);
        }
    }
    class ItemViewholder extends RecyclerView.ViewHolder{
        ImageView item_pic;
        public ItemViewholder(View itemView) {
            super(itemView);
            item_pic = (ImageView) itemView.findViewById(R.id.item_pic);
        }
    }
}
