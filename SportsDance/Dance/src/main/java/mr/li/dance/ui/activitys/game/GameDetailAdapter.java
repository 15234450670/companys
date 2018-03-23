package mr.li.dance.ui.activitys.game;

import android.content.Context;
import android.view.View;

import mr.li.dance.R;
import mr.li.dance.models.GameDetailResponse;
import mr.li.dance.ui.activitys.MyDanceWebActivity;
import mr.li.dance.ui.adapters.BaseRecyclerAdapter;
import mr.li.dance.ui.adapters.RecyclerViewHolder;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.MyStrUtil;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/22 0022
 * 描述:
 * 修订历史:
 */
public class GameDetailAdapter extends BaseRecyclerAdapter<GameDetailResponse.DataBean.ArticleBean> {
    Context c;
    public GameDetailAdapter(Context ctx) {
        super(ctx);
        c = ctx;
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_consultation_type1;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, final int position, final GameDetailResponse.DataBean.ArticleBean item) {
        holder.setText(R.id.name, item.getTitle());
        holder.setImageByUrlOrFilePath(R.id.imageView, item.getPicture(), R.drawable.default_banner);
        if (MyStrUtil.isEmpty(item.getWriter())) {
            holder.getView(R.id.laiyuan).setVisibility(View.GONE);
        } else {
            holder.setText(R.id.from_tv, "来源 ：" + item.getWriter());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDanceWebActivity.lunch(c, MyDanceWebActivity.ZIXUNTYPE, item.getTitle(), AppConfigs.ZixunShareUrl2 + item.getId(), true);
            }
        });
    }


}
