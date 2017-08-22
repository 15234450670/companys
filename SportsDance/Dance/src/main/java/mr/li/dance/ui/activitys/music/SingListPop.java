package mr.li.dance.ui.activitys.music;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.models.GeDanInfo;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/18 0018
 * 描述:    PoPUpWindows
 * 修订历史:
 */

public class SingListPop extends BasePopwindow{

    public List<GeDanInfo.DataBean.ListBean> list;
    private ListView lv;
    public PopAdapter popAdapter;


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

        lv = (ListView) findPopViewById(R.id.lv);

        ppw.setAnimationStyle(R.style.anim_menu_bottombar);
        popAdapter = new PopAdapter();
        lv.setAdapter(popAdapter);

    }

    @Override
    protected void click(int viewId) {

    }

    @Override
    protected void initDataBeforeShow() {

    }

    @Override
    protected void initPopwindowData() {

    }

    public void setChange(int position){
        for(int i = 0 ; i < list.size() ; i++){
            GeDanInfo.DataBean.ListBean item = list.get(i);
            item.isFalse = (position == i ? true : false);
        }
        popAdapter.notifyDataSetChanged();
    }

   class PopAdapter extends BaseAdapter{

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
       public View getView(final int i, View view, ViewGroup viewGroup) {
           View inflate = View.inflate(context, R.layout.lv_item, null);
           ImageView item_gd_laba  = (ImageView) inflate.findViewById(R.id.item_gd_laba);
           TextView lv_title = (TextView) inflate.findViewById(R.id.gd_music);
           lv_title.setText(list.get(i).getTitle());
           if (list.get(i).isFalse) {
               item_gd_laba.setVisibility(View.VISIBLE);
               lv_title.setTextColor(Color.RED);
           } else {
               item_gd_laba.setVisibility(View.GONE);
               lv_title.setTextColor(Color.BLACK);
           }
           inflate.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   list.get(i).isFalse = true;
                   transportAction(i, list.get(i).getTitle());
                   setChange(i);
                   notifyDataSetChanged();
               }
           });

           return inflate;
       }
   }



}
