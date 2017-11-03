package mr.li.dance.ui.TXT;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.base.BaseActivity;
import mr.li.dance.ui.activitys.shequ.TranscribeVideo;

import static mr.li.dance.R.id.input_bt;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/14 0014
 * 描述:       视频发布
 * 修订历史:
 */
public class PostVideoActivity extends BaseActivity implements View.OnClickListener {

    private EditText mBt;
    private TextView mzs;
    private int num = 25;

    @Override
    public int getContentViewId() {
        return R.layout.fabu_sp_activity;
    }

    @Override
    public void initViews() {
        setHeadRightButtonVisibility(View.VISIBLE);  //显示右标题
        setTitleAndRightBtn1("视频", "发布", R.color.black);//设置头部 内容

    }

    //点击右按钮
    @Override
    public void onHeadRightButtonClick(View v) {
        super.onHeadRightButtonClick(v);
        Toast.makeText(mContext, "发布了视频", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initDatas() {
        super.initDatas();
        mBt = (EditText) mDanceViewHolder.getView(input_bt);
        mzs = mDanceViewHolder.getTextView(R.id.sp_zs);
        mDanceViewHolder.getView(R.id.fabushiping_rl_sp).setOnClickListener(this);
        LimitTitle();
    }

    //限制标题数的方法
    public void LimitTitle() {
        mBt.addTextChangedListener(new TextWatcher() {
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
                mzs.setText(s.length() + "/" + num);
                selectionStart = mBt.getSelectionStart();
                selectionEnd = mBt.getSelectionEnd();
                if (wordNum.length() > num) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    mBt.setText(s);
                    mBt.setSelection(tempSelection);//设置光标在最后
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabushiping_rl_sp:
                TranscribeVideo.lunch(this);
                break;
        }
    }

    public static void lunch(Context context) {
        Intent intent = new Intent(context, PostVideoActivity.class);
        context.startActivity(intent);
    }
}
