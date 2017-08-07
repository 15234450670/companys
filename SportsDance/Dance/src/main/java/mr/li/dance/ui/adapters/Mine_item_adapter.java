package mr.li.dance.ui.adapters;


import android.content.Context;
import android.util.Log;

import mr.li.dance.R;
import mr.li.dance.models.Mine_itemInfo;

/**
 * 提现界面列表数据
 */
public class Mine_item_adapter extends BaseRecyclerAdapter<Mine_itemInfo.DataBean.DetailBean> {


    public Mine_item_adapter(Context ctx) {

        super(ctx);
    }

    @Override
    public int getItemLayoutId(int viewTyp) {
        return R.layout.item_mine_rv;
    }

    @Override
    public void bindData(RecyclerViewHolder holder, int position, Mine_itemInfo.DataBean.DetailBean item) {

        if(item.getIs_draw().equals("1")){
            String mMoney = item.getMoney();
            double money = Double.parseDouble(mMoney);
            if(money > 0){
                holder.setText(R.id.item_title, "红包");
                holder.setText(R.id.item_date,item.getTime());
                holder.setTextAndColor(R.id.item_money, "+ " + money + " 元", mContext.getResources().getColor(R.color.mine_item_add));
                Log.e("tx_money",money+"");
            }
        }else if(item.getIs_draw().equals("2")){
            holder.setText(R.id.item_title, "提现");
            holder.setText(R.id.item_date, item.getTime());
            holder.setTextAndColor(R.id.item_money, "- " + item.getGet_money() + " 元", mContext.getResources().getColor(R.color.mine_item_fu));
            Log.e("getmoney", item.getGet_money() + "");
        }else{

        }
    }
}



