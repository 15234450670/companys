package mr.li.dance.ui.TXT;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.base.BaseActivity;
import mr.li.dance.ui.activitys.shequ.SelectPictureActivity;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/11 0011
 * 描述:
 * 修订历史:
 */
public class PictureActivity extends BaseActivity {
    //存放所有选择的照片
    private              ArrayList<String> allSelectedPicture = new ArrayList<String>();
    //存放从选择界面选择的照片
    private              ArrayList<String> selectedPicture    = new ArrayList<String>();
    private static final int               REQUEST_PICK       = 0;
    private EditText input_tw;
    private TextView tw_zs;
    private int num = 25;
    private EditText    input_content;
    private GridView    gv_tjtp;
    private GridAdapter gridAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.tuwen_activity;
    }

    @Override
    public void initViews() {
        setHeadRightButtonVisibility(View.VISIBLE);
        setTitleAndRightBtn1("图文", "发布", R.color.black);
        gridAdapter = new GridAdapter();
        gv_tjtp.setAdapter(gridAdapter);
    }

    @Override
    public void onHeadRightButtonClick(View v) {
        super.onHeadRightButtonClick(v);
        Toast.makeText(mContext, "发布了图文", Toast.LENGTH_SHORT).show();
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

    class GridAdapter extends BaseAdapter {

        public LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());

        @Override
        public int getCount() {
            return allSelectedPicture.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.list_item_image, null);

                holder.image = (ImageView) convertView.findViewById(R.id.iv_img);
                holder.btn = (ImageView) convertView.findViewById(R.id.iv_quxiao);

                holder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == allSelectedPicture.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getApplicationContext().getResources(), R.drawable.tianjiatp));
                holder.btn.setVisibility(View.GONE);

                holder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("dianjia");
                        selectClick();
                    }
                });
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                ImageLoader.getInstance().displayImage("file://" + allSelectedPicture.get(position),
                        holder.image);
                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击后移除图片
                        allSelectedPicture.remove(position);

                        //更新UI
                        gv_tjtp.setAdapter(gridAdapter);
                    }
                });

            }
            return convertView;
        }
    }

    public class ViewHolder {
        public ImageView image;
        public ImageView btn;
    }

    private void selectClick() {
        Intent intent = new Intent();
        intent.setClass(this, SelectPictureActivity.class);

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("allSelectedPicture", allSelectedPicture);
        intent.putExtras(bundle);
        System.out.println("dianjia1");
        if (allSelectedPicture.size() < 9) {
            System.out.println("dianjia2");
            startActivityForResult(intent, REQUEST_PICK);
        }
    }
    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            selectedPicture = (ArrayList) data.getSerializableExtra(SelectPictureActivity.INTENT_SELECTED_PICTURE);
            for (String str : selectedPicture) {
                if (!allSelectedPicture.contains(str)) {
                    allSelectedPicture.add(str);
                    gv_tjtp.setAdapter(gridAdapter);
                }
            }
        }
    }
}
