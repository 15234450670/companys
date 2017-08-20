package mr.li.dance.ui.activitys.music;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.models.GeDanInfo;

/**
 * 类的用途：
 * Created by ：杨珺达
 * date：2017/8/20
 */

public class SingListPop extends BasePopwindow{

    public List<GeDanInfo.DataBean.ListBean> list;

    public SingListPop(Activity context) {
        super(context);
    }

    @Override
    protected PopupWindow createPop(View view) {
        int k = context.getWindowManager().getDefaultDisplay().getHeight();
        return new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, k/2);
    }

    @Override
    protected int getPopwindowViewId() {
        return R.layout.pop_list;
    }

    @Override
    protected void initPopwindowView() {
        final ListView lv = (ListView) findPopViewById(R.id.lv);
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list == null ? 0 : list.size();
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
                View inflate = View.inflate(context, R.layout.lv_item, null);
                TextView lv_title = (TextView) inflate.findViewById(R.id.lv_title);
                lv_title.setText(list.get(i).getTitle());
                return inflate;
            }
        });

        //条目点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                transportAction(i, null);
            }
        });
        ppw.setAnimationStyle(R.style.anim_menu_bottombar);
    }

    @Override
    protected void initPopwindowData() {

    }

    @Override
    protected void click(int viewId) {

    }

    @Override
    protected void initDataBeforeShow() {

    }
}
