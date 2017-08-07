package mr.li.dance.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.models.Mine_itemInfo;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/4 0004
 * 描述:暂时没什么用
 * 修订历史:
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context                                 context;
    List<Mine_itemInfo.DataBean.DetailBean> detail;

    public MyAdapter(Context context, List<Mine_itemInfo.DataBean.DetailBean> detail) {
        this.context = context;
        this.detail = detail;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_mine_rv, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        String is_draw = detail.get(position).getIs_draw();
        if (is_draw != null && is_draw.equals("1")) {
            holder.item_title.setText("抽奖");
            holder.item_date.setText(detail.get(position).getTime());
            holder.item_money.setTextColor(Color.RED);
            holder.item_money.setText("+ " + detail.get(position).getMoney());
            return;
        } else {
            holder.item_title.setText("提现");
            holder.item_date.setText(detail.get(position).getTime());
            holder.item_money.setTextColor(Color.GREEN);
            holder.item_money.setText("- " + detail.get(position).getGet_money());
        }
    }

    @Override
    public int getItemCount() {
        return detail == null ? 0 : detail.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView item_title;
        private final TextView item_date;
        private final TextView item_money;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_title = (TextView) itemView.findViewById(R.id.item_title);
            item_date = (TextView) itemView.findViewById(R.id.item_date);
            item_money = (TextView) itemView.findViewById(R.id.item_money);

        }
    }
}
