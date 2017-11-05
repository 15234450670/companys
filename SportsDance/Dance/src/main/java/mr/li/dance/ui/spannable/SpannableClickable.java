package mr.li.dance.ui.spannable;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.base.DanceApplication;


/**
 * @author miaoshuai
 * @Description:
 * @date
 */
public abstract class SpannableClickable extends ClickableSpan implements View.OnClickListener {

    private int DEFAULT_COLOR_ID = R.color.black;
    /**
     * text颜色
     */
    private int textColor ;

    public SpannableClickable() {
        this.textColor = DanceApplication.instances.getResources().getColor(DEFAULT_COLOR_ID);
    }

    public SpannableClickable(int textColor){
        this.textColor = textColor;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);

        ds.setColor(textColor);
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }
}
