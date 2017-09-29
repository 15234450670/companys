package mr.li.dance.ui.adapters.new_adapter;

import android.content.Context;
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
    public boolean isTab;

    public PopGridViewAdapter(Context context, List<LabelSelect.DataBean.ListBean> list) {
        this.list = list;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = View.inflate(mContext, R.layout.gv_text, null);
        TextView name = (TextView) view.findViewById(R.id.name);
        final LabelSelect.DataBean.ListBean bean = list.get(i);
        name.setText(bean.getName());
        name.setSelected(bean.isSelect);

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTab){
                    selectOne(i);
                } else {
                    bean.isSelect =!bean.isSelect;
                    notifyDataSetChanged();
                }
            }
        });

        return view;
    }

    /**
     * 单选
     * @param position
     */
    public void selectOne(int position){

        for (int i = 0 ; i<list.size() ; i++) {
            if(i==position){
                list.get(i).isSelect = ! list.get(i).isSelect;
            } else {
                list.get(i).isSelect = false;
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 全不选
     */
    public void selectNone(){
        for (int i = 0 ; i<list.size() ; i++) {
            list.get(i).isSelect = false;
        }
        notifyDataSetChanged();
    }

    /**
     * 那个tab被选中了
     * @return
     */
    public int getSelect(){
        for (int i = 0 ; i < list.size() ; i++) {
            if(list.get(i).isSelect){
                return i;
            }
        }
        return -1;
    }

    /**
     * 选中一个标签
     * @param position
     */
    public void setSelect (int position) {
        for (int i = 0 ; i<list.size() ; i++) {
            list.get(i).isSelect = i==position? true : false;
        }
    }
}
