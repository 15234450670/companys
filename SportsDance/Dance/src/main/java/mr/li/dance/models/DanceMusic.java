package mr.li.dance.models;

import java.util.List;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/17 0017
 * 描述:
 * 修订历史:
 */
public class DanceMusic extends BaseResponse {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 6
         * title : 歌单6
         * img_fm : http://store.cdsf.org.cn/picture//131/0/0/c898faebd80236fb4d891fd55f257238.jpg
         * click_sum : 0
         */

        private String id;
        private String title;
        private String img_fm;
        private String click_sum;

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

        public String getImg_fm() {
            return img_fm;
        }

        public void setImg_fm(String img_fm) {
            this.img_fm = img_fm;
        }

        public String getClick_sum() {
            return click_sum;
        }

        public void setClick_sum(String click_sum) {
            this.click_sum = click_sum;
        }
    }
}
