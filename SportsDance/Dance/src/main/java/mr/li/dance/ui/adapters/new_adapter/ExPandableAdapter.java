package mr.li.dance.ui.adapters.new_adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mr.li.dance.R;
import mr.li.dance.models.LabelSelect;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/19
 * 描述:      二级列表
 * 修订历史:
 */
public class ExPandableAdapter extends BaseExpandableListAdapter {
    public static boolean isChildCanSelect;

    Context mContext;
    List<LabelSelect.DataBean> mData;
    int tab = -2;
    private final String tag = getClass().getSimpleName();
    private HashMap<Integer, PopGridViewAdapter> adapterMap;
    List<String> is_host = new ArrayList<>();

    public ExPandableAdapter(Context context, List<LabelSelect.DataBean> data) {
        isChildCanSelect = true;
        this.mData = data;
        this.mContext = context;
        adapterMap = new HashMap<>();
    }

    /**
     * 那个tab被选中了
     * @return
     */
    public int getTabPosition(){
        PopGridViewAdapter popGridViewAdapter = adapterMap.get(0);
        return popGridViewAdapter.getSelect();
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.expand_one, null);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView check_box = (TextView) convertView.findViewById(R.id.check_box);
        check_box.setSelected(isExpanded);
        name.setText(mData.get(groupPosition).getClass_name());
        String is_hot = mData.get(groupPosition).is_hot;
        is_host.add(is_hot);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.expand_two, null);
        GridView gv = (GridView) convertView.findViewById(R.id.gv);
        PopGridViewAdapter popGridViewAdapter = new PopGridViewAdapter(mContext, mData.get(groupPosition).getList());
        for (int i = 0; i < is_host.size(); i++) {
            popGridViewAdapter.isTab = Integer.valueOf(is_host.get(i))==1? true : false;
        }


        final PopGridViewAdapter adapter = adapterMap.get(groupPosition);
        if (adapter == null) {
            adapterMap.put(groupPosition, popGridViewAdapter);
        }

        if (groupPosition == 0) {
            popGridViewAdapter.setSelcetTab(new PopGridViewAdapter.SelectTab() {
                @Override
                public void isTabSelect(boolean flag) {
                    Log.d(tag, "flag = " + flag);
                    if (flag) {
                        for (int i = 1 ; i<mData.size() ; i++ ) {
                            adapterMap.get(i).selectNone();
                        }
                    }
                    notifyDataSetChanged();
                }
            });
        }

        gv.setAdapter(popGridViewAdapter);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 重置
     */
    public void itemReset(){
        for (int i = 0 ; i<mData.size() ; i++ ) {
            adapterMap.get(i).selectNone();
        }
        notifyDataSetChanged();
    }

}
