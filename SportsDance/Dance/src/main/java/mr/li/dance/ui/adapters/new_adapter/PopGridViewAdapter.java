package mr.li.dance.ui.adapters.new_adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.models.LabelSelect;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/22 0022
 * 描述:
 * 修订历史:
 */
public class PopGridViewAdapter extends BaseAdapter {
    List<LabelSelect.DataBean.ListBean> list;
    Context mContext;
    public PopGridViewAdapter(Context context, List<LabelSelect.DataBean.ListBean> list) {
        this.list = list;
        Log.e("xxxxxx",list.size()+"");
        this.mContext = context;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = View.inflate(mContext, R.layout.gv_text,null);
        TextView name = (TextView) view.findViewById(R.id.name);
         name.setText(list.get(i).getName());
        Log.e("text",list.get(i).getName());
        return view;
    }
}
