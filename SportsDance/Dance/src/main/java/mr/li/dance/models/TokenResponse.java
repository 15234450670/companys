package mr.li.dance.models;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/11/1 0001
 * 描述:
 * 修订历史:
 */
public class TokenResponse extends BaseResponse {

    /**
     * data : {"video_name":"f67313034a18258dd51d8eb8561f85ad","token":"{\"RequestId\":\"61317A51-8661-4017-8B67-FAA41538C2A8\",\"AssumedRoleUser\":{\"AssumedRoleId\":\"338940481798569492:29864\",\"Arn\":\"acs:ram::1636677380178176:role/appwrite/29864\"},\"Credentials\":{\"AccessKeySecret\":\"CbJTJLQDqBp8UZ9xKMTEpbdnC63TZqFgiFjDHEqKSwnd\",\"AccessKeyId\":\"STS.FyUTGVWq1v42mcJr2QcTmwiLc\",\"Expiration\":\"2017-11-01T02:10:45Z\",\"SecurityToken\":\"CAIS7AF1q6Ft5B2yfSjIp7rgH/3iuq4QwfbZb0X7ljIEb9tBmKzngTz2IHtEdXVoBeAfsfU/mW5Z6vcYlqwpGs8bGRQBsEKycdcFnzm6aq/t5uaXj9Vd+rDHdEGXDxnkprywB8zyUNLafNq0dlnAjVUd6LDmdDKkLTfHWN/z/vwBVNkMWRSiZjdrHcpfIhAYyPUXLnzML/2gQHWI6yjydBM24VQi1jwkufzmnZ3FtUTk4QekmrNPlePYOYO5asRgBpB7Xuqu0fZ+Hqi7i3UAukQSqPcv0/0UpGmW4oDHGTlY+w2ZKevI/82Hxom/xFGmcRqAAQDijPTjfF0Ti8Tau4gcZrFAL4v4kN67TSNZEO+grHI8rix/EDjpTldqnMjwT7juhzr//ZGVz9UyZ0LN7M3PdDytMY1p6VN03/2PW8vdKx89KWDijS6oQTKBYv6nUszFkl4teyDXvtvupY4YM0qiwSPhC+5vsa6f3/tIB+aDENRs\"}}"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * video_name : f67313034a18258dd51d8eb8561f85ad
         * token : {"RequestId":"61317A51-8661-4017-8B67-FAA41538C2A8","AssumedRoleUser":{"AssumedRoleId":"338940481798569492:29864","Arn":"acs:ram::1636677380178176:role/appwrite/29864"},"Credentials":{"AccessKeySecret":"CbJTJLQDqBp8UZ9xKMTEpbdnC63TZqFgiFjDHEqKSwnd","AccessKeyId":"STS.FyUTGVWq1v42mcJr2QcTmwiLc","Expiration":"2017-11-01T02:10:45Z","SecurityToken":"CAIS7AF1q6Ft5B2yfSjIp7rgH/3iuq4QwfbZb0X7ljIEb9tBmKzngTz2IHtEdXVoBeAfsfU/mW5Z6vcYlqwpGs8bGRQBsEKycdcFnzm6aq/t5uaXj9Vd+rDHdEGXDxnkprywB8zyUNLafNq0dlnAjVUd6LDmdDKkLTfHWN/z/vwBVNkMWRSiZjdrHcpfIhAYyPUXLnzML/2gQHWI6yjydBM24VQi1jwkufzmnZ3FtUTk4QekmrNPlePYOYO5asRgBpB7Xuqu0fZ+Hqi7i3UAukQSqPcv0/0UpGmW4oDHGTlY+w2ZKevI/82Hxom/xFGmcRqAAQDijPTjfF0Ti8Tau4gcZrFAL4v4kN67TSNZEO+grHI8rix/EDjpTldqnMjwT7juhzr//ZGVz9UyZ0LN7M3PdDytMY1p6VN03/2PW8vdKx89KWDijS6oQTKBYv6nUszFkl4teyDXvtvupY4YM0qiwSPhC+5vsa6f3/tIB+aDENRs"}}
         */

        private String video_name;
        private String token;

        public String getVideo_name() {
            return video_name;
        }

        public void setVideo_name(String video_name) {
            this.video_name = video_name;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
