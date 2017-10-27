package mr.li.dance.ui.adapters.new_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import mr.li.dance.R;
import mr.li.dance.utils.glide.ImageLoaderManager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/27 0027
 * 描述:    社区详情图文适配器
 * 修订历史:
 */
public class DetailsListAdapter extends BaseAdapter {

    Context context;
    List<String> address;
    public DetailsListAdapter(Context context, List<String> address) {
                this.context = context;
        this.address = address;
    }

    @Override
    public int getCount() {
        return address.size();
    }

    @Override
    public Object getItem(int i) {
        return address.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.shequ_details_pic_item, null);
        ImageView pic = (ImageView) inflate.findViewById(R.id.pic_item);
        ImageLoaderManager.getSingleton().Load(context,address.get(i),pic,R.drawable.default_banner);
        return inflate;
    }
}
