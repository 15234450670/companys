package mr.li.dance.models;

import java.util.List;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/22 0022
 * 描述:
 * 修订历史:
 */
public class GameDetailResponse extends BaseResponse {

    /**
     * data : {"title":"镇江体育舞蹈全国公开赛","img":"https://kfstore.cdsf.org.cn/picture//260/0/0/6b5dd3d24d0744a40a71b7f015a9612a.jpg","competeResult":"","competeZxc":1,"article":[{"id":"48472","picture":"https://kfstore.cdsf.org.cn/picture//170/0/0/163ac2d638cb61f49a3112865d8b899b.png","title":"2黑池巨星现场授课 舞蹈联盟助力天津体育舞蹈","writer":"新浪天津"},{"id":"48480","picture":"https://kfstore.cdsf.org.cn/picture//372/0/0/f682a661b0c6b65b616825162f2871dc.jpg","title":"1212","writer":""}],"competeVideo":{"id":"10000","picture":"http://1255612701.vod2.myqcloud.com/80956e85vodtransgzp1255612701/1b562abb4564972819127982558/snapshot/1516699168_3935331135.100_250500.jpg","name":"欧莱美杯2017年中国义乌体育舞蹈公开赛暨第七届武林大会  开幕式及授牌"},"competePhotoClass":{"id":"23","title":"相册13","picture":"https://kfstore.cdsf.org.cn/picture//77/0/0/13572458b11c157de09bef330953e60a.jpg","sum":"20"}}
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
         * title : 镇江体育舞蹈全国公开赛
         * img : https://kfstore.cdsf.org.cn/picture//260/0/0/6b5dd3d24d0744a40a71b7f015a9612a.jpg
         * competeResult :
         * competeZxc : 1
         * article : [{"id":"48472","picture":"https://kfstore.cdsf.org.cn/picture//170/0/0/163ac2d638cb61f49a3112865d8b899b.png","title":"2黑池巨星现场授课 舞蹈联盟助力天津体育舞蹈","writer":"新浪天津"},{"id":"48480","picture":"https://kfstore.cdsf.org.cn/picture//372/0/0/f682a661b0c6b65b616825162f2871dc.jpg","title":"1212","writer":""}]
         * competeVideo : {"id":"10000","picture":"http://1255612701.vod2.myqcloud.com/80956e85vodtransgzp1255612701/1b562abb4564972819127982558/snapshot/1516699168_3935331135.100_250500.jpg","name":"欧莱美杯2017年中国义乌体育舞蹈公开赛暨第七届武林大会  开幕式及授牌"}
         * competePhotoClass : {"id":"23","title":"相册13","picture":"https://kfstore.cdsf.org.cn/picture//77/0/0/13572458b11c157de09bef330953e60a.jpg","sum":"20"}
         */

        private String title;
        private String                img;
        private String                competeResult;
        private int                   competeZxc;
        private CompeteVideoBean      competeVideo;
        private CompetePhotoClassBean competePhotoClass;
        private List<ArticleBean>     article;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getCompeteResult() {
            return competeResult;
        }

        public void setCompeteResult(String competeResult) {
            this.competeResult = competeResult;
        }

        public int getCompeteZxc() {
            return competeZxc;
        }

        public void setCompeteZxc(int competeZxc) {
            this.competeZxc = competeZxc;
        }

        public CompeteVideoBean getCompeteVideo() {
            return competeVideo;
        }

        public void setCompeteVideo(CompeteVideoBean competeVideo) {
            this.competeVideo = competeVideo;
        }

        public CompetePhotoClassBean getCompetePhotoClass() {
            return competePhotoClass;
        }

        public void setCompetePhotoClass(CompetePhotoClassBean competePhotoClass) {
            this.competePhotoClass = competePhotoClass;
        }

        public List<ArticleBean> getArticle() {
            return article;
        }

        public void setArticle(List<ArticleBean> article) {
            this.article = article;
        }

        public static class CompeteVideoBean {
            /**
             * id : 10000
             * picture : http://1255612701.vod2.myqcloud.com/80956e85vodtransgzp1255612701/1b562abb4564972819127982558/snapshot/1516699168_3935331135.100_250500.jpg
             * name : 欧莱美杯2017年中国义乌体育舞蹈公开赛暨第七届武林大会  开幕式及授牌
             */

            private String id;
            private String picture;
            private String name;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class CompetePhotoClassBean {
            /**
             * id : 23
             * title : 相册13
             * picture : https://kfstore.cdsf.org.cn/picture//77/0/0/13572458b11c157de09bef330953e60a.jpg
             * sum : 20
             */

            private String id;
            private String title;
            private String picture;
            private String sum;

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

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getSum() {
                return sum;
            }

            public void setSum(String sum) {
                this.sum = sum;
            }
        }

        public static class ArticleBean {
            /**
             * id : 48472
             * picture : https://kfstore.cdsf.org.cn/picture//170/0/0/163ac2d638cb61f49a3112865d8b899b.png
             * title : 2黑池巨星现场授课 舞蹈联盟助力天津体育舞蹈
             * writer : 新浪天津
             */

            private String id;
            private String picture;
            private String title;
            private String writer;

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getWriter() {
                return writer;
            }

            public void setWriter(String writer) {
                this.writer = writer;
            }
        }
    }
}
