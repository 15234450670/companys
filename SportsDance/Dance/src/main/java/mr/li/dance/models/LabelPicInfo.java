package mr.li.dance.models;

import java.util.ArrayList;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/26 0026
 * 描述:
 * 修订历史:
 */
public class LabelPicInfo extends BaseResponse {


    /**
     * data : {"arr":[{"title":"相册三","img_fm":"http://store.cdsf.org.cn/picture//278/0/0/ba45c8f60456a672e003a875e469d0eb.jpg","id":"3"},{"title":"相册四","img_fm":"http://store.cdsf.org.cn/picture//278/0/0/ba45c8f60456a672e003a875e469d0eb.jpg","id":"4"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private ArrayList<PhotoClassBean> arr;

        public ArrayList<PhotoClassBean> getArr() {
            return arr;
        }

        public void setArr(ArrayList<PhotoClassBean> arr) {
            this.arr = arr;
        }


    }
}
