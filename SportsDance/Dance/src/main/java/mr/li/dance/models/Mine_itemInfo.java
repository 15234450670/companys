package mr.li.dance.models;

import java.util.List;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/7/25
 * 描述:  我的账户-itemBean
 * 修订历史:
 */
public class Mine_itemInfo extends BaseResponse {


    /**
     * data : {"remaining_sum":"47.40","detail":[{"is_draw":"1","money":"0.00","get_money":"0.00","time":"2017-07-26 13:59:11"},{"is_draw":"1","money":"5.90","get_money":"0.00","time":"2017-07-26 13:51:08"},{"is_draw":"1","money":"1.30","get_money":"0.00","time":"2017-07-26 13:51:06"},{"is_draw":"1","money":"1.30","get_money":"0.00","time":"2017-07-26 13:51:04"},{"is_draw":"1","money":"8.60","get_money":"0.00","time":"2017-07-26 13:51:02"}]}
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
         * remaining_sum : 47.40
         * detail : [{"is_draw":"1","money":"0.00","get_money":"0.00","time":"2017-07-26 13:59:11"},{"is_draw":"1","money":"5.90","get_money":"0.00","time":"2017-07-26 13:51:08"},{"is_draw":"1","money":"1.30","get_money":"0.00","time":"2017-07-26 13:51:06"},{"is_draw":"1","money":"1.30","get_money":"0.00","time":"2017-07-26 13:51:04"},{"is_draw":"1","money":"8.60","get_money":"0.00","time":"2017-07-26 13:51:02"}]
         */

        private String remaining_sum;
        private List<DetailBean> detail;

        public String getRemaining_sum() {
            return remaining_sum;
        }

        public void setRemaining_sum(String remaining_sum) {
            this.remaining_sum = remaining_sum;
        }

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
        }

        public static class DetailBean {
            /**
             * is_draw : 1
             * money : 0.00
             * get_money : 0.00
             * time : 2017-07-26 13:59:11
             */

            private String is_draw;
            private String money;
            private String get_money;
            private String time;

            public String getIs_draw() {
                return is_draw;
            }

            public void setIs_draw(String is_draw) {
                this.is_draw = is_draw;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getGet_money() {
                return get_money;
            }

            public void setGet_money(String get_money) {
                this.get_money = get_money;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }

}
