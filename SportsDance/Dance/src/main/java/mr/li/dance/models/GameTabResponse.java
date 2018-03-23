package mr.li.dance.models;

import java.util.List;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/23 0023
 * 描述:
 * 修订历史:
 */
public class GameTabResponse extends BaseResponse {

    /**
     * data : {"title":"镇江体育舞蹈全国公开赛","start_time":"2018-06-19","end_time":"2018-06-22","start_sign_up":"2018-05-01","end_sign_up":"2018-05-02","type":"10003","live":0,"address":"镇江市大港体育馆","zhuban":["江苏省镇江市体育总会","镇江市体育局"],"chengban":["镇江新区体育舞蹈协会","上海闵行区体育舞蹈协会"],"xieban":[],"longitude":"0.0000000","latitudes":"0.0000000"}
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
         * title : 镇江体育舞蹈全国公开赛
         * start_time : 2018-06-19
         * end_time : 2018-06-22
         * start_sign_up : 2018-05-01
         * end_sign_up : 2018-05-02
         * type : 10003
         * live : 0
         * address : 镇江市大港体育馆
         * zhuban : ["江苏省镇江市体育总会","镇江市体育局"]
         * chengban : ["镇江新区体育舞蹈协会","上海闵行区体育舞蹈协会"]
         * xieban : []
         * longitude : 0.0000000
         * latitudes : 0.0000000
         */

        private String title;
        private String       start_time;
        private String       end_time;
        private String       start_sign_up;
        private String       end_sign_up;
        private String       type;
        private int          live;
        private String       address;
        private String       longitude;
        private String       latitudes;
        private List<String> zhuban;
        private List<String> chengban;
        private List<String>      xieban;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getStart_sign_up() {
            return start_sign_up;
        }

        public void setStart_sign_up(String start_sign_up) {
            this.start_sign_up = start_sign_up;
        }

        public String getEnd_sign_up() {
            return end_sign_up;
        }

        public void setEnd_sign_up(String end_sign_up) {
            this.end_sign_up = end_sign_up;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getLive() {
            return live;
        }

        public void setLive(int live) {
            this.live = live;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitudes() {
            return latitudes;
        }

        public void setLatitudes(String latitudes) {
            this.latitudes = latitudes;
        }

        public List<String> getZhuban() {
            return zhuban;
        }

        public void setZhuban(List<String> zhuban) {
            this.zhuban = zhuban;
        }

        public List<String> getChengban() {
            return chengban;
        }

        public void setChengban(List<String> chengban) {
            this.chengban = chengban;
        }

        public List<String> getXieban() {
            return xieban;
        }

        public void setXieban(List<String> xieban) {
            this.xieban = xieban;
        }
    }
}
