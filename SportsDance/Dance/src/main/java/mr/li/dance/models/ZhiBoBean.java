package mr.li.dance.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/5/16 0016
 * 描述:
 * 修订历史:
 */
public class ZhiBoBean extends BaseResponse {


    /**
     * data : {"liveInfo":{"live_id":"128","live_title":"直播推流测试","brief":"","picture_begin":"https://kfstore.cdsf.org.cn/picture//402/0/0/8eb8c61cfb09803efb6589181b64681c.jpg","picture_end":"","begin_time":"2018-05-03 15:24:01","end_time":"2018-05-31 15:24:08","time":"2018-05-18 09:58:19","compete_id":"0","compete_name":"","tencent_streamId":"b762e0dbe8"},"adVideo":{"id":"10063","picture":"http://1255612701.vod2.myqcloud.com/80956e85vodtransgzp1255612701/12f508ee4564972819022136875/snapshot/1516080273_2948459046.100_93600.jpg","url":"http://1255612701.vod2.myqcloud.com/80956e85vodtransgzp1255612701/12f508ee4564972819022136875/v.f20.mp4"},"adWlink":[{"id":"27","img_fm":"https://kfstore.cdsf.org.cn/picture//518/1/0/32934cf90620d93119234990a437065a.jpg","title":"2017国庆双节祝福图片","url":"https://www.baidu.com"},{"id":"26","img_fm":"https://kfstore.cdsf.org.cn/picture//10/0/0/b89143fce0a2ef69e95c74ba5204b03e.png","title":"中国体育舞蹈公开系列赛（北京站）","url":"https://www.baidu.com"}]}
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
         * liveInfo : {"live_id":"128","live_title":"直播推流测试","brief":"","picture_begin":"https://kfstore.cdsf.org.cn/picture//402/0/0/8eb8c61cfb09803efb6589181b64681c.jpg","picture_end":"","begin_time":"2018-05-03 15:24:01","end_time":"2018-05-31 15:24:08","time":"2018-05-18 09:58:19","compete_id":"0","compete_name":"","tencent_streamId":"b762e0dbe8"}
         * adVideo : {"id":"10063","picture":"http://1255612701.vod2.myqcloud.com/80956e85vodtransgzp1255612701/12f508ee4564972819022136875/snapshot/1516080273_2948459046.100_93600.jpg","url":"http://1255612701.vod2.myqcloud.com/80956e85vodtransgzp1255612701/12f508ee4564972819022136875/v.f20.mp4"}
         * adWlink : [{"id":"27","img_fm":"https://kfstore.cdsf.org.cn/picture//518/1/0/32934cf90620d93119234990a437065a.jpg","title":"2017国庆双节祝福图片","url":"https://www.baidu.com"},{"id":"26","img_fm":"https://kfstore.cdsf.org.cn/picture//10/0/0/b89143fce0a2ef69e95c74ba5204b03e.png","title":"中国体育舞蹈公开系列赛（北京站）","url":"https://www.baidu.com"}]
         */

        private LiveInfoBean           liveInfo;
        private AdVideoBean            adVideo;
        private ArrayList<AdWlinkBean> adWlink;

        public LiveInfoBean getLiveInfo() {
            return liveInfo;
        }

        public void setLiveInfo(LiveInfoBean liveInfo) {
            this.liveInfo = liveInfo;
        }

        public AdVideoBean getAdVideo() {
            return adVideo;
        }

        public void setAdVideo(AdVideoBean adVideo) {
            this.adVideo = adVideo;
        }

        public ArrayList<AdWlinkBean> getAdWlink() {
            return adWlink;
        }

        public void setAdWlink(ArrayList<AdWlinkBean> adWlink) {
            this.adWlink = adWlink;
        }

        public static class LiveInfoBean {
            /**
             * live_id : 128
             * live_title : 直播推流测试
             * brief :
             * picture_begin : https://kfstore.cdsf.org.cn/picture//402/0/0/8eb8c61cfb09803efb6589181b64681c.jpg
             * picture_end :
             * begin_time : 2018-05-03 15:24:01
             * end_time : 2018-05-31 15:24:08
             * time : 2018-05-18 09:58:19
             * compete_id : 0
             * compete_name :
             * tencent_streamId : b762e0dbe8
             */

            private String live_id;
            private String live_title;
            private String brief;
            private String picture_begin;
            private String picture_end;
            private String begin_time;
            private String end_time;
            private String time;
            private String compete_id;
            private String compete_name;
            private String tencent_streamId;

            public String getLive_id() {
                return live_id;
            }

            public void setLive_id(String live_id) {
                this.live_id = live_id;
            }

            public String getLive_title() {
                return live_title;
            }

            public void setLive_title(String live_title) {
                this.live_title = live_title;
            }

            public String getBrief() {
                return brief;
            }

            public void setBrief(String brief) {
                this.brief = brief;
            }

            public String getPicture_begin() {
                return picture_begin;
            }

            public void setPicture_begin(String picture_begin) {
                this.picture_begin = picture_begin;
            }

            public String getPicture_end() {
                return picture_end;
            }

            public void setPicture_end(String picture_end) {
                this.picture_end = picture_end;
            }

            public String getBegin_time() {
                return begin_time;
            }

            public void setBegin_time(String begin_time) {
                this.begin_time = begin_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getCompete_id() {
                return compete_id;
            }

            public void setCompete_id(String compete_id) {
                this.compete_id = compete_id;
            }

            public String getCompete_name() {
                return compete_name;
            }

            public void setCompete_name(String compete_name) {
                this.compete_name = compete_name;
            }

            public String getTencent_streamId() {
                return tencent_streamId;
            }

            public void setTencent_streamId(String tencent_streamId) {
                this.tencent_streamId = tencent_streamId;
            }
        }

        public static class AdVideoBean implements Serializable {
            /**
             * id : 10063
             * picture : http://1255612701.vod2.myqcloud.com/80956e85vodtransgzp1255612701/12f508ee4564972819022136875/snapshot/1516080273_2948459046.100_93600.jpg
             * url : http://1255612701.vod2.myqcloud.com/80956e85vodtransgzp1255612701/12f508ee4564972819022136875/v.f20.mp4
             */

            private String id;
            private String picture;
            private String url;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class AdWlinkBean implements Parcelable {
            /**
             * id : 27
             * img_fm : https://kfstore.cdsf.org.cn/picture//518/1/0/32934cf90620d93119234990a437065a.jpg
             * title : 2017国庆双节祝福图片
             * url : https://www.baidu.com
             */

            private String id;
            private String img_fm;
            private String title;
            private String url;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImg_fm() {
                return img_fm;
            }

            public void setImg_fm(String img_fm) {
                this.img_fm = img_fm;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(id);
                dest.writeString(img_fm);
                dest.writeString(title);
                dest.writeString(url);
            }
            public static final Parcelable.Creator<AdWlinkBean> CREATOR = new Creator<AdWlinkBean>() {

                @Override
                public AdWlinkBean createFromParcel(Parcel source) {
                    // TODO Auto-generated method stub

                    AdWlinkBean stu = new AdWlinkBean();
                    stu.id = source.readString();
                    stu.title = source.readString();
                    stu.img_fm = source.readString();
                    stu.url = source.readString();
                    return stu;

                }

                @Override
                public AdWlinkBean[] newArray(int size) {
                    // TODO Auto-generated method stub
                    return new AdWlinkBean[size];
                }
            };

        }
    }
}
