package mr.li.dance.models;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/12/20 0020
 * 描述:
 * 修订历史:
 */
public class PushLove extends BaseResponse {

    /**
     * data : {"url":"http://kf.cdsf.org.cn/h5/share.freeticket","title":"精彩有你！ 2017WDSF比赛门票大放送","appid":"1840000005","appsecret":"1840000005xxxvvvbbbnnn","number":"43"}
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
         * url : http://kf.cdsf.org.cn/h5/share.freeticket
         * title : 精彩有你！ 2017WDSF比赛门票大放送
         * appid : 1840000005
         * appsecret : 1840000005xxxvvvbbbnnn
         * number : 43
         */

        private String url;
        private String title;
        private String appid;
        private String appsecret;
        private String number;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getAppsecret() {
            return appsecret;
        }

        public void setAppsecret(String appsecret) {
            this.appsecret = appsecret;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
