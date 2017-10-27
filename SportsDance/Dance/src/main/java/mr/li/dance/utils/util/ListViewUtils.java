package mr.li.dance.utils.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/27 0027
 * 描述:
 * 修订历史:
 */
public class ListViewUtils extends ListView {

    public ListViewUtils(Context context) {
        super(context);
    }

    public ListViewUtils(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewUtils(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
