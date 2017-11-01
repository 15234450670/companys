package mr.li.dance.models;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/11/1 0001
 * 描述:
 * 修订历史:
 */
public class TokenInfo  {


    /**
     * RequestId : 3091D05D-06AC-45E9-AA56-20CF3ED5EB3E
     * AssumedRoleUser : {"AssumedRoleId":"338940481798569492:29864","Arn":"acs:ram::1636677380178176:role/appwrite/29864"}
     * Credentials : {"AccessKeySecret":"hf4Z8ZJ3PrnTxMR1JKbxMitYgvWr81d3fhTeVuzM67W","AccessKeyId":"STS.JnSCrBU3ra8k2sRTZgJN7WXnc","Expiration":"2017-11-01T02:38:39Z","SecurityToken":"CAIS7AF1q6Ft5B2yfSjIq63mCMj2uOxT1vqAMFXjsFoyRsEbuJ3FgTz2IHtEdXVoBeAfsfU/mW5Z6vcYlqwpGs8bGRQVzGSzcdcFnzm6aq/t5uaXj9Vd+rDHdEGXDxnkprywB8zyUNLafNq0dlnAjVUd6LDmdDKkLTfHWN/z/vwBVNkMWRSiZjdrHcpfIhAYyPUXLnzML/2gQHWI6yjydBM24VQi1jwkufzmnZ3FtUTk4QekmrNPlePYOYO5asRgBpB7Xuqu0fZ+Hqi7i3UAukQSqPcv0/0UpGmW4oDHGTlY+w2ZKevI/82Hxom/xFGmcRqAASSQZqBm+04dou/sQZo9QB+XSVtvDKmeRlgV5Whr3xH1Dotty8XtOE+VNsIUNciHFcK5J2yli93r7bGzHjADbJ9mnDwHIozRXmvbyjFFa+Ar74+hufsCGZpAorf6QIIONdEbTq9MgRtgsCOAy0oL5pyvpcSNv94uZjiN84/VaO0H"}
     */

    private String RequestId;
    private AssumedRoleUserBean AssumedRoleUser;
    private CredentialsBean     Credentials;

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String RequestId) {
        this.RequestId = RequestId;
    }

    public AssumedRoleUserBean getAssumedRoleUser() {
        return AssumedRoleUser;
    }

    public void setAssumedRoleUser(AssumedRoleUserBean AssumedRoleUser) {
        this.AssumedRoleUser = AssumedRoleUser;
    }

    public CredentialsBean getCredentials() {
        return Credentials;
    }

    public void setCredentials(CredentialsBean Credentials) {
        this.Credentials = Credentials;
    }

    public static class AssumedRoleUserBean {
        /**
         * AssumedRoleId : 338940481798569492:29864
         * Arn : acs:ram::1636677380178176:role/appwrite/29864
         */

        private String AssumedRoleId;
        private String Arn;

        public String getAssumedRoleId() {
            return AssumedRoleId;
        }

        public void setAssumedRoleId(String AssumedRoleId) {
            this.AssumedRoleId = AssumedRoleId;
        }

        public String getArn() {
            return Arn;
        }

        public void setArn(String Arn) {
            this.Arn = Arn;
        }
    }

    public static class CredentialsBean {
        /**
         * AccessKeySecret : hf4Z8ZJ3PrnTxMR1JKbxMitYgvWr81d3fhTeVuzM67W
         * AccessKeyId : STS.JnSCrBU3ra8k2sRTZgJN7WXnc
         * Expiration : 2017-11-01T02:38:39Z
         * SecurityToken : CAIS7AF1q6Ft5B2yfSjIq63mCMj2uOxT1vqAMFXjsFoyRsEbuJ3FgTz2IHtEdXVoBeAfsfU/mW5Z6vcYlqwpGs8bGRQVzGSzcdcFnzm6aq/t5uaXj9Vd+rDHdEGXDxnkprywB8zyUNLafNq0dlnAjVUd6LDmdDKkLTfHWN/z/vwBVNkMWRSiZjdrHcpfIhAYyPUXLnzML/2gQHWI6yjydBM24VQi1jwkufzmnZ3FtUTk4QekmrNPlePYOYO5asRgBpB7Xuqu0fZ+Hqi7i3UAukQSqPcv0/0UpGmW4oDHGTlY+w2ZKevI/82Hxom/xFGmcRqAASSQZqBm+04dou/sQZo9QB+XSVtvDKmeRlgV5Whr3xH1Dotty8XtOE+VNsIUNciHFcK5J2yli93r7bGzHjADbJ9mnDwHIozRXmvbyjFFa+Ar74+hufsCGZpAorf6QIIONdEbTq9MgRtgsCOAy0oL5pyvpcSNv94uZjiN84/VaO0H
         */

        private String AccessKeySecret;
        private String AccessKeyId;
        private String Expiration;
        private String SecurityToken;

        public String getAccessKeySecret() {
            return AccessKeySecret;
        }

        public void setAccessKeySecret(String AccessKeySecret) {
            this.AccessKeySecret = AccessKeySecret;
        }

        public String getAccessKeyId() {
            return AccessKeyId;
        }

        public void setAccessKeyId(String AccessKeyId) {
            this.AccessKeyId = AccessKeyId;
        }

        public String getExpiration() {
            return Expiration;
        }

        public void setExpiration(String Expiration) {
            this.Expiration = Expiration;
        }

        public String getSecurityToken() {
            return SecurityToken;
        }

        public void setSecurityToken(String SecurityToken) {
            this.SecurityToken = SecurityToken;
        }
    }
}
