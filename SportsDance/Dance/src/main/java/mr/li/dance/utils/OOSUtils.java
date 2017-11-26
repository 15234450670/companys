package mr.li.dance.utils;


import android.util.Log;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ResumableUploadRequest;
import com.alibaba.sdk.android.oss.model.ResumableUploadResult;

import mr.li.dance.ui.activitys.base.DanceApplication;

/**
 * Created by Miaoshuai on 10/31/17.
 */

public class OOSUtils {
    String endpoint = "http://oss-cn-beijing.aliyuncs.com";     //自己的域名
    // 在移动端建议使用STS方式初始化OSSClient。
    private OSSCredentialProvider mOSSCredentialProvider;
    private VideoUP               mVideoUp;


    // 更多信息可查看sample 中 sts 使用方式(https://github.com/aliyun/aliyun-oss-android-sdk/tree/master/app/src/main/java/com/alibaba/sdk/android/oss/app)
    public OOSUtils getCredentialProvider(String accessKeyId, String secretKeyId, String securityToken, VideoUP videoUP) {
        mOSSCredentialProvider = new OSSStsTokenCredentialProvider(accessKeyId, secretKeyId, securityToken);
        getOSS();
        this.mVideoUp = videoUP;
        return this;
    }
    //该配置类如果不设置，会有默认配置，具体可看该类
    //    ClientConfiguration conf = new ClientConfiguration();
    //    conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
    //    conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
    //    conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
    //    conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
    //开启可以在控制台看到日志，并且会支持写入手机sd卡中的一份日志文件位置在SDCard_path\OSSLog\logs.csv  默认不开启
    //日志会记录oss操作行为中的请求数据，返回数据，异常信息
    //例如requestId,response header等
    //android_version：5.1  android版本
    //mobile_model：XT1085  android手机型号
    //network_state：connected  网络状况
    //network_type：WIFI 网络连接类型
    //具体的操作行为信息:
    //[2017-09-05 16:54:52] - Encounter local execpiton: //java.lang.IllegalArgumentException: The bucket name is invalid.
    //A bucket name must:
    //1) be comprised of lower-case characters, numbers or dash(-);
    //2) start with lower case or numbers;
    //3) be between 3-63 characters long.
    //------>end of log

    private OSS getOSS() {
        return new OSSClient(DanceApplication.instances, endpoint, mOSSCredentialProvider);
    }

    /**
     * @param bucketName
     *         阿里云平台文件夹的名字
     * @param objectKey
     *         //上传文件的名字
     * @param uploadFilePath
     *         //上传文件的本地路径
     */
    public void getUploadRequest(String bucketName, String objectKey, String uploadFilePath) {

        // 创建断点上传请求
        ResumableUploadRequest request = new ResumableUploadRequest(bucketName, objectKey, uploadFilePath);
        // 设置上传过程回调
        request.setProgressCallback(new OSSProgressCallback<ResumableUploadRequest>() {
            @Override
            public void onProgress(ResumableUploadRequest request, long currentSize, long totalSize) {
                mVideoUp.progress(currentSize, totalSize);
            }

        });
        // 异步调用断点上传
        OSSAsyncTask resumableTask = getOSS().asyncResumableUpload(request, new OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult>() {
            @Override
            public void onSuccess(ResumableUploadRequest request, ResumableUploadResult result) {
                Log.d("shuaimiao", "onsuccess");
                mVideoUp.takeVideoUp();

            }

            @Override
            public void onFailure(ResumableUploadRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 异常处理
                Toast.makeText(DanceApplication.instances, "上传失败", Toast.LENGTH_SHORT).show();
                Log.d("shuaimiao", "onFailure: " + clientExcepion.getMessage() + "...." + serviceException.getMessage());
            }
        });
        //resumableTask.waitUntilFinished(); // 可以等待直到任务完成


    }

    public interface VideoUP {
        void takeVideoUp();

        void progress(long currentSize, long totalSize);
    }


}

