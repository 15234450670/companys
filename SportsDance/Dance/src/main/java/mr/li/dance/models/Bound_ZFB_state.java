package mr.li.dance.models;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/2 0002
 * 描述:
 * 修订历史:
 */
public class Bound_ZFB_state extends BaseResponse {

    /**
     * data : {"start":2}
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
         * start : 2
         */

        private int start;

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }
    }
}
