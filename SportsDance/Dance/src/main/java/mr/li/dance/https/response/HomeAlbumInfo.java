package mr.li.dance.https.response;

import java.util.List;

import mr.li.dance.models.LabelInfo;
import mr.li.dance.models.PhotoClassBean;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/25 0025
 * 描述:
 * 修订历史:
 */
public class HomeAlbumInfo extends BaseResponse {

    /**
     * data : {"label":[{"class_id":0,"class_name":"全部"},{"class_id":"28","class_name":"图片管理栏目1标签1"}],"photoClass":[{"id":"4","title":"相册四","img_fm":"","compete_id":"1","photos":0},{"id":"2","title":"相册","img_fm":"/51/0/0/076e3caed758a1c18c91a0e9cae3368f.jpg","compete_id":"0","photos":0},{"id":"3","title":"相册二","img_fm":"/278/0/0/ba45c8f60456a672e003a875e469d0eb.jpg","compete_id":"1","photos":4}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<LabelInfo>      label;
        private List<PhotoClassBean> photoClass;

        public List<LabelInfo> getLabel() {
            return label;
        }

        public void setLabel(List<LabelInfo> label) {
            this.label = label;
        }

        public List<PhotoClassBean> getPhotoClass() {
            return photoClass;
        }

        public void setPhotoClass(List<PhotoClassBean> photoClass) {
            this.photoClass = photoClass;
        }


    }
}
