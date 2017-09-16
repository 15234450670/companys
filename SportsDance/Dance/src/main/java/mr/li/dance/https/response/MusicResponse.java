package mr.li.dance.https.response;

import java.util.ArrayList;

import mr.li.dance.models.BannerInfo;
import mr.li.dance.models.LabelInfo;
import mr.li.dance.models.MusicRecAppBean;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/16 0016
 * 描述:
 * 修订历史:
 */
public class MusicResponse extends BaseResponse {
    private MusicData data;

    public MusicData getData() {
        return data;
    }

    public void setData(MusicData data) {
        this.data = data;
    }

    public static class MusicData{
        private ArrayList<BannerInfo>      banner;
        private ArrayList<MusicRecAppBean> MusicRecApp;
        private ArrayList<LabelInfo>       label;

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

        public ArrayList<MusicRecAppBean> getMusicRecApp() {
            return MusicRecApp;
        }

        public void setMusicRecApp(ArrayList<MusicRecAppBean> musicRecApp) {
            MusicRecApp = musicRecApp;
        }
    }

}
