package mr.li.dance.ui.adapters;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.models.Mine_itemInfo;
import mr.li.dance.utils.MyStrUtil;

/**
 * 提现界面列表数据
 */
public class Mine_item_adapter extends BaseRecyclerAdapter<Mine_itemInfo>{

    public Mine_item_adapter(Context ctx) {
        super(ctx);
    }

    @Override
    public int getItemLayoutId(int viewTyp) {
        return R. layout.item_mine_rv;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Mine_itemInfo item) {
        List<Mine_itemInfo.DataBean.DetailBean> detail = item.getData().getDetail();

        if (MyStrUtil.isEmpty(detail)) {
            Toast.makeText(mContext, "没有参与活动", Toast.LENGTH_SHORT).show();
            holder.setVisibility(R.id.views, View.INVISIBLE);
        }else
        for (int i = 0; i < detail.size(); i++) {
            String is_draw = detail.get(i).getIs_draw();
            Log.e("is_draw:--",is_draw);
            if (!MyStrUtil.isEmpty(is_draw)) {
                if (is_draw.equals("1")) {
                    holder.setText(R.id.item_title,"抽奖");
                    holder.setText(R.id.item_date,detail.get(i).getTime());
                    holder.setBackground(R.id.item_money,R.color.mine_item_add);
                    holder.setTextAndColor(R.id.item_money,"+"+detail.get(i).getMoney()+"元", R.color.mine_item_add);

                }else{
                    holder.setText(R.id.item_title,"提现");
                    holder.setText(R.id.item_date,detail.get(i).getTime());
                    holder.setTextAndColor(R.id.item_money ,"-"+detail.get(i).getMoney()+"元" , R.color.mine_item_fu);
                }
            }
            }
    }


}



