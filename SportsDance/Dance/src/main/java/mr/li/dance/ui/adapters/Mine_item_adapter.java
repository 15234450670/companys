package mr.li.dance.ui.adapters;


import android.content.Context;
import android.util.Log;
import android.view.View;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.models.Mine_itemInfo;
import mr.li.dance.utils.MyStrUtil;

/**
 * 提现界面列表数据
 */
public class Mine_item_adapter extends BaseRecyclerAdapter<Mine_itemInfo> {

    public Mine_item_adapter(Context ctx) {

        super(ctx);
    }

    @Override
    public int getItemLayoutId(int viewTyp) {
        return R.layout.item_mine_rv;
    }


    @Override
    public void bindData(RecyclerViewHolder holder, int position, Mine_itemInfo item) {
        List<Mine_itemInfo.DataBean.DetailBean> detail = item.getData().getDetail();
        double v = Double.parseDouble(item.getData().getRemaining_sum());

        if (!MyStrUtil.isEmpty(detail) && v > 0) {
                Log.e("position",position+"");
                String money = detail.get(position).getMoney();
                double tx_money = Double.parseDouble(money);
                String getMoney = detail.get(position).getGet_money();
                double tx_getMoney = Double.parseDouble(getMoney);
                if (detail.get(position).getIs_draw().equals("1") && tx_money>0) {
                    holder.setText(R.id.item_title, "红包");
                    holder.setText(R.id.item_date, detail.get(position).getTime());
                    holder.setTextAndColor(R.id.item_money, "+ " + tx_money + " 元", R.color.mine_item_add);
                    Log.e("tx_money",tx_money+"");
                }
                if (detail.get(position).getIs_draw().equals("2") && tx_getMoney >= 10) {
                    holder.setText(R.id.item_title, "提现");
                    holder.setText(R.id.item_date, detail.get(position).getTime());
                    holder.setTextAndColor(R.id.item_money, "- " + getMoney + " 元", R.color.mine_item_fu);
                    Log.e("getmoney", getMoney + "");
                }


           /* for (int i = 0; i < detail.size(); i++) {
                String money = detail.get(i).getMoney();
               double tx_money = Double.parseDouble(money);
                String getMoney = detail.get(i).getGet_money();
                double tx_getMoney = Double.parseDouble(getMoney);
                if (detail.get(i).getIs_draw().equals("1") && tx_money>0) {
                    holder.setText(R.id.item_title, "红包");
                    holder.setText(R.id.item_date, detail.get(i).getTime());
                    holder.setTextAndColor(R.id.item_money, "+ " + tx_money + " 元", R.color.mine_item_add);
                    Log.e("tx_money",tx_money+"");
                }
                if (detail.get(i).getIs_draw().equals("2") && tx_getMoney >= 10) {
                    holder.setText(R.id.item_title, "提现");
                    holder.setText(R.id.item_date, detail.get(i).getTime());
                    holder.setTextAndColor(R.id.item_money, "- " + getMoney+ " 元", R.color.mine_item_fu);
                    Log.e("getmoney",getMoney+"");
                }
            }*/

        }else {
            holder.setVisibility(R.id.views, View.INVISIBLE);
        }
    }

}



