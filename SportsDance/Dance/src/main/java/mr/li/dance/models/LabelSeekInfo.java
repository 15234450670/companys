package mr.li.dance.models;

import java.util.ArrayList;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/20 0020
 * 描述:
 * 修订历史:
 */
public class LabelSeekInfo extends BaseResponse {

    /**
     * data : {"arr":[{"title":"标题一","img":"http://store.cdsf.org.cn/picture//212/0/0/2b04df3ecc1d94afddff082d139c6f15.jpg","described":""},{"title":"教学管理标题一","img":"http://store.cdsf.org.cn/picture//212/0/0/2b04df3ecc1d94afddff082d139c6f15.jpg","described":"教学管理标题一描述"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private ArrayList<TeachInfo> arr;

        public ArrayList<TeachInfo> getArr() {
            return arr;
        }

        public void setArr(ArrayList<TeachInfo> arr) {
            this.arr = arr;
        }


    }
}
