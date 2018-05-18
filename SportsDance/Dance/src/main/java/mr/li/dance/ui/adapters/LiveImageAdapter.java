package mr.li.dance.ui.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import mr.li.dance.R;
import mr.li.dance.models.ZhiBoBean;
import mr.li.dance.ui.activitys.MyDanceWebActivity;
import mr.li.dance.utils.MyStrUtil;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/5/18 0018
 * 描述:
 * 修订历史:
 */
public class LiveImageAdapter extends BaseRecyclerAdapter<ZhiBoBean.DataBean.AdWlinkBean> {
    public LiveImageAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.live_pic;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, final ZhiBoBean.DataBean.AdWlinkBean item) {
        ImageView imageView = holder.getImageView(R.id.image);
        holder.setImageByUrlOrFilePath1(imageView, item.getImg_fm(), R.drawable.default_banner);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MyStrUtil.isEmpty(item.getUrl())) {
                    MyDanceWebActivity.lunch(mContext, MyDanceWebActivity.OTHERTYPE, item.getTitle(), item.getUrl(), item.getId());
                }
            }
        });
    }

}
