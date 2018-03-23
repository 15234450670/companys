package mr.li.dance.models;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/12/12 0012
 * 描述:
 * 修订历史:
 */
public class CheckLogin {
    /**
     * errorCode : 102
     * data : 1520216229
     * startPage : {"id":"1","title":"对的1","type":"0","img":"","is_publish":"2","create_time":"2018-02-27 14:29:35","number":"0","describe":""}
     */

    private int errorCode;
    private String        data;
    private StartPageBean startPage;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public StartPageBean getStartPage() {
        return startPage;
    }

    public void setStartPage(StartPageBean startPage) {
        this.startPage = startPage;
    }

    public static class StartPageBean {
        /**
         * id : 1
         * title : 对的1
         * type : 0
         * img :
         * is_publish : 2
         * create_time : 2018-02-27 14:29:35
         * number : 0
         * describe :
         */

        private String id;
        private String title;
        private String type;
        private String img;
        private String is_publish;
        private String create_time;
        private String number;
        private String describe;
        private String appid;
        private String appsecret;
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getIs_publish() {
            return is_publish;
        }

        public void setIs_publish(String is_publish) {
            this.is_publish = is_publish;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }
    }

    /**
     * errorCode : 103
     * data : 登录信息无效
     */


}
