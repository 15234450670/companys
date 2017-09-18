package mr.li.dance.ui.adapters.new_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.models.LabelSelect;
import mr.li.dance.ui.adapters.viewholder.BaseViewHolder;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/18 0018
 * 描述:
 * 修订历史:
 */
public class LabelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE1 = 0;
    private final int TYPE2 = 1;
    Context                    context;
    List<LabelSelect.DataBean> data;

    public LabelAdapter(Context context, List<LabelSelect.DataBean> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE1:
                view = View.inflate(context, R.layout.labelselect_type1, null);
                return new ViewHolder1(view);
           /* case TYPE2:
                view = View.inflate(context, R.layout.labelselect_type1, null);

                return new ViewHolder2(view);*/
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder1) {
            bind((ViewHolder1) holder, position);
        }
    }

    private void bind(ViewHolder1 holder, int position) {
        holder.name.setText(data.get(position).get_$7().getName());
        Log.e("xxx",data.get(position).get_$7().getName()) ;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public int getItemViewType(int position) {
        /*if (position == 0) {
            return TYPE1;
        } else {
            return TYPE2;
        }*/
        return TYPE1;
    }

    private class ViewHolder1 extends BaseViewHolder {

        private final TextView name;
        private final View     v;
        private final TextView label_item;

        public ViewHolder1(View view) {
            super(context, view);
            name = (TextView) view.findViewById(R.id.name);
            v = view.findViewById(R.id.all);
            label_item = (TextView) view.findViewById(R.id.label_item);
        }
    }

    private class ViewHolder2 extends BaseViewHolder {
        public ViewHolder2(View view) {
            super(context, view);
        }
    }
}
