package mr.li.dance.ui.activitys.game;

import android.content.Context;
import android.view.View;

import mr.li.dance.R;
import mr.li.dance.https.response.GameHomeResponse;
import mr.li.dance.ui.adapters.BaseRecyclerAdapter;
import mr.li.dance.ui.adapters.RecyclerViewHolder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/21 0021
 * 描述:
 * 修订历史:
 */
public class GameAdapter extends BaseRecyclerAdapter<GameHomeResponse.DataBean> {

      Context mContext;
    public GameAdapter(Context ctx) {
        super(ctx);
        mContext = ctx;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.game_home_item;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, final GameHomeResponse.DataBean item) {
        holder.setImageByUrlOrFilePath(R.id.game_pic, item.getImg(), R.drawable.default_video);
        String end_date = item.getEnd_date();
        String start_date = item.getStart_date();
        String replace = start_date.replace("-", ".");
        String substring = end_date.substring(5).replace("-",".");
        holder.setText(R.id.game_tv_time, replace+"-"+substring);
        holder.setText(R.id.game_tv_address, item.getProvince());
        holder.setText(R.id.game_tv, item.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               GameDetailActivity.lunch(mContext,item.getId());
            }
        });
    }


}
