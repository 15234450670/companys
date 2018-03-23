package mr.li.dance.https.response;

import java.util.List;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/21 0021
 * 描述:
 * 修订历史:
 */
public class GameHomeResponse extends BaseResponse {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 239
         * title : 镇江体育舞蹈全国公开赛
         * img : https://kfstore.cdsf.org.cn/picture//260/0/0/6b5dd3d24d0744a40a71b7f015a9612a.jpg
         * address : 镇江市大港体育馆
         * start_date : 2018-06-19
         * end_date : 2018-06-22
         * start_sign_up : 2018-05-01
         * end_sign_up : 2018-05-02
         * state : 5
         */

        private String id;
        private String title;
        private String img;
        private String address;
        private String start_date;
        private String end_date;
        private String start_sign_up;
        private String end_sign_up;
        private int    state;
        private String province;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
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

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
