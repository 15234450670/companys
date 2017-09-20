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
public class HomeTeachResponse extends BaseResponse {

    /**
     * data : {"label":[{"class_id":0,"class_name":"全部"},{"class_id":"16","class_name":"教学管理栏目1标签1"},{"class_id":"17","class_name":"教学管理栏目1标签2"},{"class_id":"18","class_name":"教学管理栏目1标签3"}],"teach":[{"id":"1","title":"标题一","img":"http://store.cdsf.org.cn/picture//212/0/0/2b04df3ecc1d94afddff082d139c6f15.jpg","described":""},{"id":"2","title":"标题二","img":"","described":""},{"id":"4","title":"0","img":"","described":""},{"id":"6","title":"教学管理标题一","img":"http://store.cdsf.org.cn/picture//212/0/0/2b04df3ecc1d94afddff082d139c6f15.jpg","described":"教学管理标题一描述"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private ArrayList<LabelInfo> label;
        private ArrayList<TeachInfo>      teach;

        public ArrayList<LabelInfo> getLabel() {
            return label;
        }

        public void setLabel(ArrayList<LabelInfo> label) {
            this.label = label;
        }

        public ArrayList<TeachInfo> getTeach() {
            return teach;
        }

        public void setTeach(ArrayList<TeachInfo> teach) {
            this.teach = teach;
        }


    }
}
