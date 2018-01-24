package mr.li.dance.models;

import java.util.List;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/20 0020
 * 描述:
 * 修订历史:
 */
public class TeachDetailInfo extends BaseResponse {

    /**
     * data : {"detail":[{"id":"1","title":"标题一","img":"http://store.cdsf.org.cn/picture//212/0/0/2b04df3ecc1d94afddff082d139c6f15.jpg","described":""}],"otherList":[{"id":"7690","name":"【教学】教学测试","picture":"http://i3.letvimg.com/lc08_yunzhuanma/201709/13/14/41/f68d74839c4f3d8772fddd47b584e398_v2_NDg2ODE4OTg0/thumb/2.jpg","video_unique":"80109935ab"},{"id":"5094","name":"2013年全国体育舞蹈裁判及教师培训班-VICTOR NIKOVSKY牛仔-2 ","picture":"/520/1/0/8a7e37e5ca92cfad13e98257c08aeecc.jpg","video_unique":""},{"id":"5093","name":"2013年全国体育舞蹈裁判及教师培训班-VICTOR NIKOVSKY牛仔-1 ","picture":"/232/0/0/3f3963a67eb7ceae50f9b5cb4e4c9ec2.jpg","video_unique":""},{"id":"4967","name":"2011第二十一届全国体育舞蹈锦标赛讲习会--国际级教师裁判--沈毅讲习","picture":"/275/0/0/347a06671a979cabe7a7d1e96793a8d0.jpg","video_unique":""},{"id":"6598","name":"\u201c锦鲲杯\u201d2017中国体育舞蹈系列公开赛 温州站--宣传片","picture":"http://i1.letvimg.com/lc10_yunzhuanma/201705/25/10/28/31e1ef5dfde78076b3051924fb02e4d3_v2_NDcyMTIwOTE4/thumb/7_640_360.jpg","video_unique":"f155b9b17a"},{"id":"7690","name":"【教学】教学测试","picture":"http://i3.letvimg.com/lc08_yunzhuanma/201709/13/14/41/f68d74839c4f3d8772fddd47b584e398_v2_NDg2ODE4OTg0/thumb/2.jpg","video_unique":"80109935ab"},{"id":"5094","name":"2013年全国体育舞蹈裁判及教师培训班-VICTOR NIKOVSKY牛仔-2 ","picture":"/520/1/0/8a7e37e5ca92cfad13e98257c08aeecc.jpg","video_unique":""},{"id":"5093","name":"2013年全国体育舞蹈裁判及教师培训班-VICTOR NIKOVSKY牛仔-1 ","picture":"/232/0/0/3f3963a67eb7ceae50f9b5cb4e4c9ec2.jpg","video_unique":""},{"id":"4967","name":"2011第二十一届全国体育舞蹈锦标赛讲习会--国际级教师裁判--沈毅讲习","picture":"/275/0/0/347a06671a979cabe7a7d1e96793a8d0.jpg","video_unique":""},{"id":"6598","name":"\u201c锦鲲杯\u201d2017中国体育舞蹈系列公开赛 温州站--宣传片","picture":"http://i1.letvimg.com/lc10_yunzhuanma/201705/25/10/28/31e1ef5dfde78076b3051924fb02e4d3_v2_NDcyMTIwOTE4/thumb/7_640_360.jpg","video_unique":"f155b9b17a"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<DetailBean>    detail;
        private List<OtherListBean> otherList;

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
        }

        public List<OtherListBean> getOtherList() {
            return otherList;
        }

        public void setOtherList(List<OtherListBean> otherList) {
            this.otherList = otherList;
        }

        public static class DetailBean {
            /**
             * id : 1
             * title : 标题一
             * img : http://store.cdsf.org.cn/picture//212/0/0/2b04df3ecc1d94afddff082d139c6f15.jpg
             * described :
             */

            private String id;
            private String title;
            private String img;
            private String described;

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

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getDescribed() {
                return described;
            }

            public void setDescribed(String described) {
                this.described = described;
            }
        }

        public static class OtherListBean extends BaseHomeItem{
            /**
             * id : 7690
             * name : 【教学】教学测试
             * picture : http://i3.letvimg.com/lc08_yunzhuanma/201709/13/14/41/f68d74839c4f3d8772fddd47b584e398_v2_NDg2ODE4OTg0/thumb/2.jpg
             * video_unique : 80109935ab
             */

            private String id;
            private String name;
            private String picture;
            private String video_unique;
            private String video_duration;
            public String video;
            public boolean isClick =false;

            public String getVideo_duration() {
                return video_duration;
            }

            public void setVideo_duration(String video_duration) {
                this.video_duration = video_duration;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getVideo_unique() {
                return video_unique;
            }

            public void setVideo_unique(String video_unique) {
                this.video_unique = video_unique;
            }
        }
    }
}
