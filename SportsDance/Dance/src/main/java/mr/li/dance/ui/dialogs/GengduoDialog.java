package mr.li.dance.ui.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mr.li.dance.R;

import static mr.li.dance.R.id.shanchu;


public class GengduoDialog extends Dialog implements View.OnClickListener {
    Context             context;
    DialogClickListener dialogClickListener;

    public GengduoDialog(@NonNull Context context) {
        super(context, R.style.FullScreenDialog);
    }

    public GengduoDialog(Context context, DialogClickListener clickListener) {
        super(context, R.style.BottomDialogStyleBottom);
        this.context = context;
        dialogClickListener = clickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.gengduo_bottom);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void dispalyMy() {
        this.show();
        TextView shangchu = (TextView) findViewById(R.id.shanchu);
        TextView fenxiang = (TextView) findViewById(R.id.fenxaing);
        TextView juban = (TextView) findViewById(R.id.juban);
        TextView cancelBtn = (TextView) findViewById(R.id.cancelbtn);
        shangchu.setOnClickListener(this);
        fenxiang.setOnClickListener(this);
        juban.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        juban.setVisibility(View.GONE);
    }
    public void dispalyOther() {
        this.show();
        TextView shangchu = (TextView) findViewById(R.id.shanchu);
        TextView fenxiang = (TextView) findViewById(R.id.fenxaing);
        TextView juban = (TextView) findViewById(R.id.juban);
        TextView cancelBtn = (TextView) findViewById(R.id.cancelbtn);
        shangchu.setOnClickListener(this);
        fenxiang.setOnClickListener(this);
        juban.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        shangchu.setVisibility(View.GONE);
    }
    public void dismin(){
        this.dismiss();
    }
    @Override
    public void onClick(View view) {
        this.dismiss();
        switch (view.getId()) {
            case shanchu:
                TextView shanchu = (TextView) findViewById(R.id.shanchu);
                dialogClickListener.selectItem(view, shanchu.getText().toString());
                break;
            case R.id.fenxaing:
                TextView fenxaing = (TextView) findViewById(R.id.fenxaing);
                dialogClickListener.selectItem(view, fenxaing.getText().toString());
                break;
            case R.id.juban:
                TextView juban = (TextView) findViewById(R.id.juban);
                dialogClickListener.selectItem(view, juban.getText().toString());
                break;
            case R.id.cancelbtn:
                this.dismiss();
                break;

            default:
                break;
        }
    }

    public interface DialogClickListener {
        public void selectItem(View view, String value);

    }
}
