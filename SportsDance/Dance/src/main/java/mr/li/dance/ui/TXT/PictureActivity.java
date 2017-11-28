package mr.li.dance.ui.TXT;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yolanda.nohttp.rest.Request;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import mr.li.dance.R;
import mr.li.dance.https.CallServer;
import mr.li.dance.https.HttpListener;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.models.PersonTuWen;
import mr.li.dance.models.PersonUpPicSucceed;
import mr.li.dance.ui.activitys.base.BaseActivity;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.NToast;
import mr.li.dance.utils.UserInfoManager;

import static mr.li.dance.R.id.input_zw;

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
    private int num = 25;  //限制标题数量
    private EditText input_content;
    private GridView gv_tjtp;
    int z = 0;//记录发布的数量
    private static final int               REQUEST_PICK       = 0;
    //存放所有选择的照片
    private              ArrayList<String> allSelectedPicture = new ArrayList<String>();
    //存放从选择界面选择的照片
    private              ArrayList<String> selectedPicture    = new ArrayList<String>();
    private GridAdapter  gridAdapter;
    private int          dynamic_id;
    private List<String> mData;

    @Override
    public int getContentViewId() {
        return R.layout.tuwen_activity;
    }

    @Override
    public void initViews() {
        setHeadRightButtonVisibility(View.VISIBLE);
        setTitleAndRightBtn1("图文", "发布", R.color.black);
        //显示图片
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)//设置当前线程的优先级
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//使用MD5对UIL进行加密命名
                .diskCacheSize(100 * 1024 * 1024)//50 Mb sd卡(本地)缓存的最大值
                .diskCacheFileCount(300)// 可以缓存的文件数量
                .tasksProcessingOrder(QueueProcessingType.LIFO)//后进先出
                .build();

        //初始化操作
        ImageLoader.getInstance().init(config);
        gridAdapter = new GridAdapter();
        gv_tjtp.setAdapter(gridAdapter);
    }

    @Override
    public void onHeadRightButtonClick(View v) {
        super.onHeadRightButtonClick(v);
        String title = input_tw.getText().toString().trim();
        String content = input_content.getText().toString().trim();
        String userId = UserInfoManager.getSingleton().getUserId(this);
        Request<String> request = ParameterUtils.getSingleton().getPersonAddDongTai(userId, 1, title, content);
        request(AppConfigs.FA_DONGTAI, request, false);
    }

    @Override
    public void initDatas() {
        super.initDatas();
        input_tw = (EditText) mDanceViewHolder.getView(R.id.input_tw);
        tw_zs = mDanceViewHolder.getTextView(R.id.tw_zs);
        input_content = (EditText) mDanceViewHolder.getView(input_zw);
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

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);
        if (what == AppConfigs.FA_DONGTAI) {
            PersonTuWen reponseResult = JsonMananger.getReponseResult(response, PersonTuWen.class);
            dynamic_id = reponseResult.getDynamic_id();
            if (allSelectedPicture.size() != 0) {
                for (int i = 0; i < allSelectedPicture.size(); i++) {
                    Log.e("allSelectedPicture",allSelectedPicture.get(i)) ;
                    z++;
                    File file = new File(allSelectedPicture.get(i));
                    File file1 = picCompressor(file);
                    Request<String> getfabutupian = ParameterUtils.getSingleton().getfabutupian(1, dynamic_id, file1);
                    CallServer.getRequestInstance().add(PictureActivity.this, 0, getfabutupian, new HttpListener() {
                        @Override
                        public void onSucceed(int what, String response) {
                            PersonUpPicSucceed reponseResult1 = JsonMananger.getReponseResult(response, PersonUpPicSucceed.class);
                            mData = reponseResult1.getData();
                            Log.e("data-->", mData.toString() + z);

                        }

                        @Override
                        public void onFailed(int what, int responseCode, String response) {
                            Log.e("response:", response + "-------" + responseCode);
                        }
                    }, false, false);
                }
                if (z == allSelectedPicture.size()) {
                  /*  input_tw.setText("");
                    input_content.setText("");
                    allSelectedPicture.clear();
                    gridAdapter.notifyDataSetChanged();*/
                    Toast.makeText(mContext, "图文发布完成", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                input_tw.setText("");
                input_content.setText("");
                NToast.shortToast(this, reponseResult.getData());
            }

        }
    }


    /**
     * 展示图片的GridView的适配器
     */
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
        if (allSelectedPicture.size() < 9) {
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

    /**
     * 压缩图片
     * @param file
     * @return
     */
    private File picCompressor(File file) {
        File compressedImage = new Compressor.Builder(this)
                .setMaxWidth(640)
                .setMaxHeight(480)
                .setQuality(75)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                .build()
                .compressToFile(file);
        return compressedImage;
    }
}
