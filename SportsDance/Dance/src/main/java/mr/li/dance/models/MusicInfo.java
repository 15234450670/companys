package mr.li.dance.models;

import java.util.List;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/15 0015
 * 描述:
 * 修订历史:
 */
public class MusicInfo extends BaseResponse {

    /**
     * data : {"banner":[{"id":"53","img":"http://store.cdsf.org.cn/picture//131/0/0/c898faebd80236fb4d891fd55f257238.jpg","number":"1","type":"10108","title":"第一","url":"http://work.cdsf.org.cn/h5/share.choujiang","appid":"JK48ada5a480e37d411","appsecret":"32dae2ac34079322325d28cfa0825w3aa1"},{"id":"52","img":"http://store.cdsf.org.cn/picture//635/1/0/8456a6b37fef1b9b773e83e45d1c56d5.jpg","number":"5","type":"10107","title":"红包抽奖","url":"http://work.cdsf.org.cn/h5/share.choujiang","appid":"JK48ada5a480e37d411","appsecret":"32dae2ac34079322325d28cfa0825w3aa1"}],"MusicRecApp":[{"name":"歌单1","inserttime":"2017-08-15 11:40:10","img":"/131/0/0/c898faebd80236fb4d891fd55f257238.jpg"},{"name":"歌单1","inserttime":"2017-08-15 16:06:15","img":"/131/0/0/c898faebd80236fb4d891fd55f257238.jpg"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<BannerBean>      banner;
        private List<MusicRecAppBean> MusicRecApp;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<MusicRecAppBean> getMusicRecApp() {
            return MusicRecApp;
        }

        public void setMusicRecApp(List<MusicRecAppBean> MusicRecApp) {
            this.MusicRecApp = MusicRecApp;
        }

        public static class BannerBean {
            /**
             * id : 53
             * img : http://store.cdsf.org.cn/picture//131/0/0/c898faebd80236fb4d891fd55f257238.jpg
             * number : 1
             * type : 10108
             * title : 第一
             * url : http://work.cdsf.org.cn/h5/share.choujiang
             * appid : JK48ada5a480e37d411
             * appsecret : 32dae2ac34079322325d28cfa0825w3aa1
             */

            private String id;
            private String img;
            private String number;
            private String type;
            private String title;
            private String url;
            private String appid;
            private String appsecret;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getAppsecret() {
                return appsecret;
            }

            public void setAppsecret(String appsecret) {
                this.appsecret = appsecret;
            }
        }

        public static class MusicRecAppBean {
            /**
             * name : 歌单1
             * inserttime : 2017-08-15 11:40:10
             * img : /131/0/0/c898faebd80236fb4d891fd55f257238.jpg
             */

            private String name;
            private String inserttime;
            private String img;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getInserttime() {
                return inserttime;
            }

            public void setInserttime(String inserttime) {
                this.inserttime = inserttime;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
