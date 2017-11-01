package mr.li.dance.ui.TXT;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.base.BaseActivity;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/11 0011
 * 描述:
 * 修订历史:
 */
public class PictureActivity extends BaseActivity {
    private EditText input_tw;
    private TextView tw_zs;
    private int num = 25;
    private EditText input_content;
    private GridView gv_tjtp;

    @Override
    public int getContentViewId() {
        return R.layout.tuwen_activity;
    }

    @Override
    public void initViews() {
        setHeadRightButtonVisibility(View.VISIBLE);
        setTitleAndRightBtn1("图文", "发布", R.color.black);

    }

    @Override
    public void onHeadRightButtonClick(View v) {
        super.onHeadRightButtonClick(v);
        Toast.makeText(mContext, "fabu", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initDatas() {
        super.initDatas();
        input_tw = (EditText) mDanceViewHolder.getView(R.id.input_tw);
        tw_zs = mDanceViewHolder.getTextView(R.id.tw_zs);
        input_content = (EditText) mDanceViewHolder.getView(R.id.input_zw);
        gv_tjtp = (GridView) mDanceViewHolder.getView(R.id.gv_tjtp);
        LimitTitle();
    }


    //限制标题数量
    public void LimitTitle() {
        input_tw.addTextChangedListener(new TextWatcher() {
            private CharSequence wordNum;//记录输入的字数
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordNum = s;//实时记录输入的字数
            }

            @Override
            public void afterTextChanged(Editable s) {
                //TextView显示剩余字数
                tw_zs.setText(s.length() + "/" + num);
                selectionStart = input_tw.getSelectionStart();
                selectionEnd = input_tw.getSelectionEnd();
                if (wordNum.length() > num) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    input_tw.setText(s);
                    input_tw.setSelection(tempSelection);//设置光标在最后
                }
            }
        });
    }


}
