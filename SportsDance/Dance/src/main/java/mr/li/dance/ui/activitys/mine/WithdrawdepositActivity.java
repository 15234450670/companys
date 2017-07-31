package mr.li.dance.ui.activitys.mine;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;

import java.util.Map;

import mr.li.dance.R;
import mr.li.dance.ui.activitys.base.BaseActivity;
import mr.li.dance.utils.AuthResult;
import mr.li.dance.utils.MyStrUtil;
import mr.li.dance.utils.util.OrderInfoUtil2_0;

/**
 * 描述: 提现页面
 * Author     : yjd
 * Date       : 2017/7/24
 */

public class WithdrawdepositActivity extends BaseActivity {
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2017071707789285";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID       = "2088721447470156";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = System.currentTimeMillis() / 1000 + "";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE      = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCrBUG2bls333HdsroDZ" +
            "UXrqzONoNJVZrFSzxNKCx0ILtg02LbxYouggEA99jF0Y1PmZ0EBZ4KoI7+kMAJHq5T/wjT3XHjOH0iB6RWKqAy7w/zCFDN+iBB+Xzuq" +
            "/Khv9NwsGSx6bFyRbykjKHZ2lirBSc1ntV1vEsFcga6uPgwVD/YLe9GNCORenHTq2Dp6njqylOiDQH4KZckAlc7GVjw4IChOuC1i/S" +
            "FQWBc7MLedGJw/4NTPNg3y0h6e0azK1HTBqgjcFhMe6xXk9PJl5QOIVFbh7H0E2EXEz0z/5m3Jtd0aAP7V5xLRIwCgZHfUubbIeZLw" +
            "iXSHa+Fo1FIgPjhvAgMBAAECggEARpvK/7kcbDsS1geeiRxc++oZRNYrQRs/BwN74UwpVmGQ3nDDR3H6V+/bljl5PaZs02m6Cwxjg2" +
            "wPCYjQyCptQBM8rww8zE1aMFi9jhvH9hX6215fzFP9C0/iYLwkBuU/qe1S0dC/f0Q35c4k6t0hFeCUr8bPprZFox3fswznDCJ3xN6T" +
            "ZImSYWDuzGkLv3YvonYoyHJDbfvNLBqrCvJ4xAv78dnrWxKbvlGAEuzjxT3QYkPTxqQL8uHCYlLVth6OAox8OUsWNg3wCAIF758YVwj" +
            "bmnF0rbDyeLhApQ7ueHb+pjhgIoEYZEtX5TiFNb+3Y3qy57nyqPSYH+UKZrFDMQKBgQDeoY/vloO+5/lWCJxMZ2kwGPG68wZGdIhYK0" +
            "AvbUFPftVYVxj6zfsuXfs4mae56OOlxuDrXECeLUOqtXN2sdfbyNXcOUBFKcNi1cwYPrU+6GYM37FrPP0rDmNXEyhQi01QYotXv4pD0" +
            "2HRreMRuJNMuaTRtj6VrwyxH/awQVg2HQKBgQDEp2C5uT2SznZPmJItKD/axv8D3y4qL6KP/9EKt4I5A3+bD+Gt3tfDcWU6x1Uxrqq" +
            "k6q7UU8uc2tPxRWnd/Hy4JUDzkYccfk13s09lvxD2gLKaIeWMhNYqAUWEOiOPrDg6/6N10wGh4t5XQ/Fz5tsmRFLZdpIlroqpcIjqD" +
            "9Wy+wKBgGhEtsr5bX6t2qMmqT1HeYZjSGPMinXZAjp5FzifLOV0e6tCL5rXefq/XXemz11M68GqoZDZxhHPFVAQZ6GBrZ1sNG1OCwH" +
            "Zi37wrwPz6qMp6RHCeq/FArV1qaUQfeMrOuyZGrXgKXuWtsujayPTlHlswPrPJgSo1YLne3wVPqEpAoGAPkXGcml/Xwq0GlZ1C4mwV" +
            "sYnIQG+a6tz7eeTObKMsSMzeFXaICXt6zzwUmGmz7nA3cJXJHN4ia4d6UwSzzHSCLn7TGXsBBDW1S3Z2Z1ccMKmS/qp5wp1iw2mBs4" +
            "mTqu5jq0/BOORo8Lkp3ujbRFQmliOZ8CNXRNdY3R4Vq42/JsCgYEAjTBPCiycYWsPEUdzczhm1Nuf5U7DY6yfn+2LgmefJsVdCXcLOo" +
            "S0DV3NnBeo8L2eFBgUnUb1pjVks1KMwn8KUcM/nP+rsc9t/loGKLrHe02wUNZw3t3zO5u7U8U2XYdqm7oY/ANtYbbQS+Z8P7TZbYz5" +
            "cAldhd8x9MC2WEY/+Uk=";
    //支付宝公 钥
    public static final String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqwVBtm5bN99x3bK6A2VF66szjaDSVWaxUs8TSgsdCC7YNNi28WKLoIBAPfYxdGNT5mdBAWeCqCO/pDACR6uU/8I091x4zh9IgekViqgMu8P8whQzfogQfl87qvyob/TcLBksemxckW8pIyh2dpYqwUnNZ7VdbxLBXIGurj4MFQ/2C3vRjQjkXpx06tg6ep46spTog0B+CmXJAJXOxlY8OCAoTrgtYv0hUFgXOzC3nRicP+DUzzYN8tIentGsytR0waoI3BYTHusV5PTyZeUDiFRW4ex9BNhFxM9M/+ZtybXdGgD+1ecS0SMAoGR31Lm2yHmS8Il0h2vhaNRSID44bwIDAQAB";
    public static final String RSA_PRIVATE       = "";

    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);

                    String resultStatus = authResult.getResultStatus();
                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        String resultCode = authResult.getResultCode();
                        String authCode = authResult.getAuthCode();
                        if (resultCode.equals("200")) {
                            mDanceViewHolder.setText(R.id.mine_zfb_btn, "重新绑定支付宝");

                        }

                        Toast.makeText(WithdrawdepositActivity.this, "授权成功", Toast.LENGTH_SHORT).show();

                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(WithdrawdepositActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }


    };

    @Override
    public int getContentViewId() {
        return R.layout.tixian_activity;
    }

    @Override
    public void initViews() {
        setTitle("提现");
    }

    @Override
    public void getIntentData() {
        super.getIntentData();
        String back_money = mIntentExtras.getString("back_money");
        if (!MyStrUtil.isEmpty(back_money)) {
            mDanceViewHolder.setText(R.id.tixian_money, back_money);
        }


    }

    public void btn1(View v) {
        authV2(v);
    }

    public void btn2(View v) {
        Toast.makeText(mContext, "提现", Toast.LENGTH_SHORT).show();
    }

    /**
     * 支付宝账户授权业务
     * @param v
     */
    public void authV2(View v) {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Log.e("rsa2", rsa2 + "");
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
        Log.e("info", info);
        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        Log.e("privateKey", privateKey);
        final String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        Log.e("sign", sign);
        final String authInfo = info + "&" + sign;
        Log.e("authInfo", authInfo);
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(WithdrawdepositActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

}
