package mr.li.dance.ui.adapters.new_adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.GridView;
import android.widget.TextView;

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
public class ExPandableAdapter implements ExpandableListAdapter {
    Context                    mContext;
    List<LabelSelect.DataBean> mData;


    public ExPandableAdapter(Context context, List<LabelSelect.DataBean> data) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    //  获得父项的数量
    @Override
    public int getGroupCount() {
        return mData.size();
    }

    //  获得某个父项的子项数目
    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    //  获得某个父项
    @Override
    public Object getGroup(int i) {
        return mData.get(i);
    }

    //  获得某个父项的某个子项
    @Override
    public Object getChild(int i, int i1) {
        return mData.get(i).getList().get(i1);
    }

    //  获得某个父项的id
    @Override
    public long getGroupId(int i) {
        return i;
    }

    //  获得某个父项的某个子项的id
    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //父项数据
    @Override
    public View getGroupView(final int i, boolean b, View view, ViewGroup viewGroup) {
        view = View.inflate(mContext, R.layout.expand_one, null);
        TextView name = (TextView) view.findViewById(R.id.name);
       final CheckBox check_box = (CheckBox) view.findViewById(R.id.check_box);
        name.setText(mData.get(i).getClass_name());
        if (b) {
            check_box.setChecked(false);
        } else {
            check_box.setChecked(true);
        }

        return view;
    }

    //子项数据
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        view = View.inflate(mContext, R.layout.expand_two, null);
        GridView gv = (GridView) view.findViewById(R.id.gv);
        gv.setAdapter(new PopGridViewAdapter(mContext, mData.get(i).getList()));
        return view;
    }
    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int i) {

    }

    @Override
    public void onGroupCollapsed(int i) {

    }

    @Override
    public long getCombinedChildId(long l, long l1) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long l) {
        return 0;
    }
}
