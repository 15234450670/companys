package mr.li.dance.models;

import java.util.List;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/24 0024
 * 描述:
 * 修订历史:
 */
public class MyFansInfo extends BaseResponse {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userid : 29816
         * username : 李季
         * picture_src : http://store.cdsf.org.cn/picture//82/0/0/5845e169f294422e33da486df35cbe99.jpg
         */

        private String userid;
        private String username;
        private String picture_src;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPicture_src() {
            return picture_src;
        }

        public void setPicture_src(String picture_src) {
            this.picture_src = picture_src;
        }
    }
}
