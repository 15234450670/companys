package mr.li.dance.ui.TXT;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import mr.li.dance.R;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/11 0011
 * 描述:
 * 修订历史:
 */
public class FabuDialog extends Dialog implements View.OnClickListener {
    Context             mcontext;
    DialogClickListener dialogClickListener;

    public FabuDialog(@NonNull Context context) {
        super(context, R.style.fabuDialog);
        mcontext = context;
    }

    public FabuDialog(Context context, DialogClickListener clickListener) {
        super(context, R.style.fabuDialog);
        mcontext = context;
        dialogClickListener = clickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.fabu_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void dispaly() {
        this.show();
        ImageView mfbgb = (ImageView) findViewById(R.id.fabu_guanbi);
        LinearLayout mfbsp = (LinearLayout) findViewById(R.id.fabu_sp);
        LinearLayout mfbtw = (LinearLayout) findViewById(R.id.fabu_tw);
        mfbgb.setOnClickListener(this);
        mfbsp.setOnClickListener(this);
        mfbtw.setOnClickListener(this);
    }
    public void dismin(){
        this.dismiss();
    }
    @Override
    public void onClick(View view) {
        this.dismiss();
        switch (view.getId()) {
            case R.id.fabu_guanbi:
                this.dismin();
                break;
            case R.id.fabu_sp:
                System.out.println("点击2");
                mcontext.startActivity(new Intent(mcontext, PostVideoActivity.class));
                break;
            case R.id.fabu_tw:
                System.out.println("点击1");
               mcontext.startActivity(new Intent(mcontext, PictureActivity.class));
                break;

            default:
        break;
    }
    }

    public interface DialogClickListener {
        public void selectItem(View view,String value);

    }
}
