package mr.li.dance.ui.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import mr.li.dance.R;
import mr.li.dance.utils.glide.ImageLoaderManager;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/2/28 0028
 * 描述:
 * 修订历史:
 */
public class AdvertisingActivity extends Activity implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    private ImageView iv_welcome_img;
    private TextView  tv_time;
    private Button    ll_ad_skip_btn;
    private int time = 4;
    private String title;
    Timer     timer = new Timer();
    TimerTask task  = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    time--;
                    tv_time.setText(time + " S");
                    if (time < 0) {
                        timer.cancel();
                        tv_time.setVisibility(View.GONE);
                        startActivity(new Intent(AdvertisingActivity.this, MainActivity.class));
                        finish();
                    }
                }
            });
        }
    };
    private String type;
    private String value;
    private String appid;
    private String url;
    private String appsecret;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_advertising);
        initViews();
        initData();

    }

    private void initViews() {
        iv_welcome_img = (ImageView) findViewById(R.id.iv_welcome_img);
        tv_time = (TextView) findViewById(R.id.tv_time);
        ll_ad_skip_btn = (Button) findViewById(R.id.ll_ad_skip_btn);
        ll_ad_skip_btn.setOnClickListener(this);
        iv_welcome_img.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        value = intent.getStringExtra("number");// id
        title = intent.getStringExtra("title");
        type = intent.getStringExtra("type");
        String img = intent.getStringExtra("img");
        appid = intent.getStringExtra("appid");
        url = intent.getStringExtra("url");
        appsecret = intent.getStringExtra("appsecret");

        ImageLoaderManager.getSingleton().Load1(this, img, iv_welcome_img, R.drawable.splash_icon);
        Log.e(TAG, "id: " + value + " title: " + title + " type: " + type + " img: " + img);
        timer.schedule(task, 0, 1000);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //跳过
            case R.id.ll_ad_skip_btn:
                timer.cancel();
                startActivity(new Intent(AdvertisingActivity.this, MainActivity.class));
                finish();
                break;
            //点击广告
            case R.id.iv_welcome_img:
                if (!TextUtils.isEmpty(type)) {
                    timer.cancel();
                    Intent intent = new Intent(AdvertisingActivity.this, MainActivity.class);
                    intent.putExtra("adv", "adv");
                    intent.putExtra("type", type);
                    intent.putExtra("number", value);
                    intent.putExtra("title", title);
                    intent.putExtra("appid", appid);
                    intent.putExtra("appsecret", appsecret);
                    intent.putExtra("url", url);
                    startActivity(intent);
                    finish();
                }
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
    }
}
