package mr.li.dance.utils.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 类的用途：
 * Created by ：杨珺达
 * date：2017/9/29
 */

public class GridUtils extends GridView {
    public GridUtils(Context context) {
        super(context);
    }

    public GridUtils(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridUtils(Context context, AttributeSet attrs, int defStyleAttr) {
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