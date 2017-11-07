package mr.li.dance.ui.TXT;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yolanda.nohttp.error.ServerError;
import com.yolanda.nohttp.rest.Request;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import mabeijianxi.camera.MediaRecorderActivity;
import mr.li.dance.R;
import mr.li.dance.https.CallServer;
import mr.li.dance.https.HttpListener;
import mr.li.dance.https.ParameterUtils;
import mr.li.dance.https.response.GetDynamicIdResponse;
import mr.li.dance.models.TokenInfo;
import mr.li.dance.models.TokenResponse;
import mr.li.dance.models.VideoUpSuccess;
import mr.li.dance.ui.activitys.base.BaseActivity;
import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.JsonMananger;
import mr.li.dance.utils.LookFirstPic;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.OOSUtils;
import mr.li.dance.utils.UserInfoManager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/14 0014
 * 描述:       视频发布
 * 修订历史:
 */
public class PostVideoActivity extends BaseActivity implements View.OnClickListener, OOSUtils.VideoUP {
    private String TAG = getClass().getSimpleName();
    private TextView mzs;
    private int num = 25;
    //播放路径
    private String                mPath;
    private EditText              fb_title;
    private EditText              fb_content;
    private JCVideoPlayerStandard player;
    private ImageView             fistImage;
    private int                   dynamic_id;
    private String                video_name;
    private AlertDialog.Builder   myDialog;
    ProgressBar pb;
    private AlertDialog alertDialog;

    @Override
    public int getContentViewId() {
        return R.layout.fabu_sp_activity;
    }

    @Override
    public void initViews() {
        // 防止锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setHeadRightButtonVisibility(View.VISIBLE);  //显示右标题
        setTitleAndRightBtn1("视频", "发布", R.color.black);//设置头部 内容
        mPath = getIntent().getStringExtra(MediaRecorderActivity.VIDEO_URI);
        if (TextUtils.isEmpty(mPath)) {
            finish();
            return;
        }
        Bitmap bitmap = LookFirstPic.LookFirstPic(mPath);
        //设置视频第一帧图为封面
        fistImage.setImageBitmap(bitmap);
    }

    @Override
    public void initDatas() {
        super.initDatas();
        ViewStub stub = (ViewStub) mDanceViewHolder.getView(R.id.stub);
        stub.inflate();
        mzs = mDanceViewHolder.getTextView(R.id.mzs);
        fb_title = mDanceViewHolder.getEditText(R.id.fb_title);
        fb_content = mDanceViewHolder.getEditText(R.id.fb_content);
        //节操播放器
        player = (JCVideoPlayerStandard) mDanceViewHolder.getView(R.id.player_list_video);

        mDanceViewHolder.getImageView(R.id.fullscreen).setVisibility(View.GONE);
        fistImage = mDanceViewHolder.getImageView(R.id.play_first);
        //封面的点击播放录制视频
        findViewById(R.id.root).setOnClickListener(this);
        LimitTitle();
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.mydialog, null);
        pb = (ProgressBar) view.findViewById(R.id.pb);
        myDialog = new AlertDialog.Builder(this);
        myDialog.setView(view);
        alertDialog = myDialog.create();
    }

    //点击右按钮
    @Override
    public void onHeadRightButtonClick(View v) {
        super.onHeadRightButtonClick(v);
        if (TextUtils.isEmpty(fb_title.getText().toString()) && TextUtils.isEmpty(fb_content.getText().toString())) {
            Toast.makeText(mContext, "标题与内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            alertDialog.show();
            String userId = UserInfoManager.getSingleton().getUserId(this);
            Request<String> takeDynamicId = ParameterUtils.getSingleton().getPersonAddDongTai(userId, 2, fb_title.getText().toString(), fb_content.getText().toString());
            request(AppConfigs.FA_DONGTAI, takeDynamicId, false);
        }
    }

    @Override
    public void onSucceed(int what, String response) {
        super.onSucceed(what, response);

        if (what == AppConfigs.FA_DONGTAI) {
            GetDynamicIdResponse reponseResult = JsonMananger.getReponseResult(response, GetDynamicIdResponse.class);
            String data = reponseResult.getData();
            dynamic_id = reponseResult.getDynamic_id();
            if (!TextUtils.isEmpty(data) && !MyStrUtil.isEmpty(dynamic_id)) {
                Log.e(TAG, data + "-->" + dynamic_id);
                String userId = UserInfoManager.getSingleton().getUserId(this);
                Request<String> videoToken = ParameterUtils.getSingleton().getTakeVideoToken(userId, fb_title.getText().toString());
                CallServer.getRequestInstance().add(this, 0, videoToken, new HttpListener() {
                    @Override
                    public void onSucceed(int what, String response) {
                        TokenResponse reponseResult = JsonMananger.getReponseResult(response, TokenResponse.class);
                        String token = reponseResult.getData().getToken();
                        video_name = reponseResult.getData().getVideo_name();
                        Log.e(TAG, "video_name==>" + video_name);
                        token.replace("\\", "");
                        try {
                            TokenInfo tokenInfo = JsonMananger.jsonToBean(token, TokenInfo.class);
                            String securityToken = tokenInfo.getCredentials().getSecurityToken();
                            String accessKeyId = tokenInfo.getCredentials().getAccessKeyId();
                            String accessKeySecret = tokenInfo.getCredentials().getAccessKeySecret();

                            //上传视频
                            OOSUtils oosUtils = new OOSUtils();
                            OOSUtils credentialProvider = oosUtils.getCredentialProvider(accessKeyId, accessKeySecret, securityToken, PostVideoActivity.this);
                            credentialProvider.getUploadRequest("jkvedio", video_name, mPath);

                        } catch (ServerError serverError) {
                            serverError.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailed(int what, int responseCode, String response) {

                    }
                }, false, false);
            } else {
                Toast.makeText(mContext, "增加视频动态ID失败", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(mContext, "增加视频动态失败", Toast.LENGTH_SHORT).show();
        }
    }


    //限制标题数的方法
    public void LimitTitle() {
        fb_title.addTextChangedListener(new TextWatcher() {
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
                selectionStart = fb_title.getSelectionStart();
                selectionEnd = fb_title.getSelectionEnd();
                if (wordNum.length() > num) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    fb_title.setText(s);
                    fb_title.setSelection(tempSelection);//设置光标在最后
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //隐藏图片
            case R.id.play_first:
                fistImage.setVisibility(View.GONE);
                player.changeUiToPlayingShow();
                break;
        }
    }

    public static void lunch(Context context) {
        Intent intent = new Intent(context, PostVideoActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void takeVideoUp() {
        alertDialog.dismiss();
        Request<String> video = ParameterUtils.getSingleton().getVideo(String.valueOf(dynamic_id), video_name);
        CallServer.getRequestInstance().add(this, 0, video, new HttpListener() {
            @Override
            public void onSucceed(int what, String response) {
                Toast.makeText(mContext, "dasdsadasdas", Toast.LENGTH_SHORT).show();
                VideoUpSuccess reponseResult = JsonMananger.getReponseResult(response, VideoUpSuccess.class);
                String address = reponseResult.getData().getAddress();
                String dynamic_id = reponseResult.getData().getDynamic_id();
                if (MyStrUtil.isEmpty(address)) {
                    Toast.makeText(mContext, "上传视频失败", Toast.LENGTH_SHORT).show();
                } else {
                    fb_title.setText("");
                    fb_content.setText("");
                    Toast.makeText(mContext, "视频上传成功", Toast.LENGTH_SHORT).show();

                }
                Log.e(TAG, "address--->" + address + "---id" + dynamic_id);
            }

            @Override
            public void onFailed(int what, int responseCode, String response) {
                Log.e(TAG, "responseCode--->" + responseCode + "---response" + response);
            }
        }, false, false);

    }

    private int size;

    @Override
    public void progress(long currentSize, long totalSize) {
        // TODO: 2017/11/5
        int temp = (int) totalSize;
        if (size != temp) {
            size = temp;
            pb.setMax(size);
        }
        pb.setProgress((int) currentSize);
    }
}
