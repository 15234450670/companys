package mr.li.dance.https.response;

import java.util.ArrayList;

import mr.li.dance.models.BannerInfo;
import mr.li.dance.models.HomeTypeBtn;
import mr.li.dance.models.LabelInfo;
import mr.li.dance.models.ZiXunInfo;

/**
 * 作者: Administrator
 * 时间: 2017/5/26.
 * 功能:
 */

public class HomeZxResponse extends BaseResponse {
    private Entity data;

    public Entity getData() {
        return data;
    }

    public void setData(Entity data) {
        this.data = data;
    }

    public static class Entity {
        private ArrayList<BannerInfo>  banner;
        private ArrayList<HomeTypeBtn> zx_type;
        private ArrayList<ZiXunInfo>   zxRec;
        private ArrayList<LabelInfo>   label;

        public ArrayList<LabelInfo> getLabel() {
            return label;
        }

        public void setLabel(ArrayList<LabelInfo> label) {
            this.label = label;
        }

        public ArrayList<BannerInfo> getBanner() {
            return banner;
        }

        public void setBanner(ArrayList<BannerInfo> banner) {
            this.banner = banner;
        }

        public ArrayList<HomeTypeBtn> getZx_type() {
            return zx_type;
        }

        public void setZx_type(ArrayList<HomeTypeBtn> zx_type) {
            this.zx_type = zx_type;
        }

        public ArrayList<ZiXunInfo> getZxRec() {
            return zxRec;
        }

        public void setZxRec(ArrayList<ZiXunInfo> zxRec) {
            this.zxRec = zxRec;
        }
    }
}
