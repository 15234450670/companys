package mr.li.dance.ui.TXT;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;

import mabeijianxi.camera.MediaRecorderActivity;
import mabeijianxi.camera.VCamera;
import mabeijianxi.camera.model.AutoVBRMode;
import mabeijianxi.camera.model.MediaRecorderConfig;
import mabeijianxi.camera.util.DeviceUtils;
import mr.li.dance.R;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/11 0011
 * 描述:
 * 修订历史:
 */
public class FabuDialog extends Dialog implements View.OnClickListener {
    Context mcontext;
    DialogClickListener dialogClickListener;
    private final int PERMISSION_REQUEST_CODE = 0x001;
    private static final String[] permissionManifest = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


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
        initSmallVideo();
        permissionCheck();

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

    public void dismin() {
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
                //视频
                playVideo();
                break;
            case R.id.fabu_tw:
                //图文
                mcontext.startActivity(new Intent(mcontext, PictureActivity.class));
                break;

            default:
                break;
        }
    }

    private void initSmallVideo() {
        // 设置拍摄视频缓存路径
        File dcim = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                VCamera.setVideoCachePath(dcim + "/mabeijianxi/");
            } else {
                VCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/",
                        "/sdcard-ext/")
                        + "/mabeijianxi/");
            }
        } else {
            VCamera.setVideoCachePath(dcim + "/mabeijianxi/");
        }
        // 开启log输出,ffmpeg输出到logcat
        VCamera.setDebugMode(true);
        // 初始化拍摄
        VCamera.initialize(getContext());
    }

    private void permissionCheck() {
        if (Build.VERSION.SDK_INT >= 23) {
            boolean permissionState = true;
            for (String permission : permissionManifest) {
                if (ContextCompat.checkSelfPermission(mcontext, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    permissionState = false;
                }
            }
            if (!permissionState) {
                ActivityCompat.requestPermissions((Activity) mcontext, permissionManifest, PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void playVideo() {
//        BaseMediaBitrateConfig recordMode;
//        BaseMediaBitrateConfig compressMode = null;
//
//        recordMode = new AutoVBRMode();
//        recordMode.setVelocity("medium");

//      FFMpegUtils.captureThumbnails("/storage/emulated/0/DCIM/mabeijianxi/1496455533800/1496455533800.mp4", "/storage/emulated/0/DCIM/mabeijianxi/1496455533800/1496455533800.jpg", "1");

//        MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
//                .fullScreen(false)
//                .recordTimeMax(Integer.valueOf(60000))
//                .recordTimeMin(Integer.valueOf(3000))
//                .maxFrameRate(Integer.valueOf(maxFramerate))
//                .videoBitrate(Integer.valueOf(bitrate))
//                .captureThumbnailsTime(1)
//                .build();
//        MediaRecorderActivity.goSmallVideoRecorder((Activity) mcontext, PlayerVideoActivity.class.getName(), config);


        MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                .doH264Compress(new AutoVBRMode()
//                        .setVelocity(BaseMediaBitrateConfig.Velocity.ULTRAFAST)
                )
                .setMediaBitrateConfig(new AutoVBRMode()
//                        .setVelocity(BaseMediaBitrateConfig.Velocity.ULTRAFAST)
                )
                .smallVideoWidth(480)
                .smallVideoHeight(360)
                .recordTimeMax(6 * 10000)
                .maxFrameRate(20)
                .captureThumbnailsTime(1)
                .recordTimeMin((int) (3 * 1000))
                .build();
        MediaRecorderActivity.goSmallVideoRecorder((Activity) mcontext, PostVideoActivity.class.getName(), config);

    }

    public interface DialogClickListener {
        public void selectItem(View view, String value);

    }
}
