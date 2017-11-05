package mr.li.dance.models;

import mr.li.dance.https.response.BaseResponse;

/**
 * 类的用途：
 * Created by ：杨珺达
 * date：2017/11/4
 */

public class VideoUpSuccess extends BaseResponse {

    /**
     * data : {"dynamic_id":"1","address":"http://jkvedioout.oss-cn-beijing.aliyuncs.com/1001.mp4"}
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
         * dynamic_id : 1
         * address : http://jkvedioout.oss-cn-beijing.aliyuncs.com/1001.mp4
         */

        private String dynamic_id;
        private String address;

        public String getDynamic_id() {
            return dynamic_id;
        }

        public void setDynamic_id(String dynamic_id) {
            this.dynamic_id = dynamic_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
