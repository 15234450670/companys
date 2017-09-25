package mr.li.dance.models;

import java.util.List;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/25 0025
 * 描述:
 * 修订历史:
 */
public class AlbumDetailInfo extends BaseResponse {


    /**
     * data : {"classInfo":{"id":"3","title":"相册二","img_fm":"http://store.cdsf.org.cn/picture//278/0/0/ba45c8f60456a672e003a875e469d0eb.jpg","compete_id":"1","compete_name":"2003年第三届全国城市体育舞蹈锦标赛","photos":4,"collection_id":0},"photoInfo":[{"id":"6","name":"Tulips","size":"79","width":"1000","height":"750","img":"http://store.cdsf.org.cn/picture//300/0/0/fafa5efeaf3cbe3b23b2748d13e629a1.jpg","updatetime":"2017-09-19 14:24:32"},{"id":"4","name":"Penguins","size":"101","width":"1000","height":"750","img":"http://store.cdsf.org.cn/picture//215/0/0/9d377b10ce778c4938b3c7e2c63a229a.jpg","updatetime":"2017-09-19 09:25:30"},{"id":"7","name":"Koala","size":"125","width":"0","height":"0","img":"http://store.cdsf.org.cn/picture//212/0/0/2b04df3ecc1d94afddff082d139c6f15.jpg","updatetime":"2017-09-19 14:01:17"},{"id":"8","name":"水母","size":"54","width":"0","height":"0","img":"http://store.cdsf.org.cn/picture//235/0/0/5a44c7ba5bbe4ec867233d67e4806848.jpg","updatetime":"2017-09-19 16:26:57"}]}
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
         * classInfo : {"id":"3","title":"相册二","img_fm":"http://store.cdsf.org.cn/picture//278/0/0/ba45c8f60456a672e003a875e469d0eb.jpg","compete_id":"1","compete_name":"2003年第三届全国城市体育舞蹈锦标赛","photos":4,"collection_id":0}
         * photoInfo : [{"id":"6","name":"Tulips","size":"79","width":"1000","height":"750","img":"http://store.cdsf.org.cn/picture//300/0/0/fafa5efeaf3cbe3b23b2748d13e629a1.jpg","updatetime":"2017-09-19 14:24:32"},{"id":"4","name":"Penguins","size":"101","width":"1000","height":"750","img":"http://store.cdsf.org.cn/picture//215/0/0/9d377b10ce778c4938b3c7e2c63a229a.jpg","updatetime":"2017-09-19 09:25:30"},{"id":"7","name":"Koala","size":"125","width":"0","height":"0","img":"http://store.cdsf.org.cn/picture//212/0/0/2b04df3ecc1d94afddff082d139c6f15.jpg","updatetime":"2017-09-19 14:01:17"},{"id":"8","name":"水母","size":"54","width":"0","height":"0","img":"http://store.cdsf.org.cn/picture//235/0/0/5a44c7ba5bbe4ec867233d67e4806848.jpg","updatetime":"2017-09-19 16:26:57"}]
         */

        private ClassInfoBean classInfo;
        private List<AlbumInfo> photoInfo;

        public ClassInfoBean getClassInfo() {
            return classInfo;
        }

        public void setClassInfo(ClassInfoBean classInfo) {
            this.classInfo = classInfo;
        }

        public List<AlbumInfo> getPhotoInfo() {
            return photoInfo;
        }

        public void setPhotoInfo(List<AlbumInfo> photoInfo) {
            this.photoInfo = photoInfo;
        }

        public static class ClassInfoBean extends BaseHomeItem{
            /**
             * id : 3
             * title : 相册二
             * img_fm : http://store.cdsf.org.cn/picture//278/0/0/ba45c8f60456a672e003a875e469d0eb.jpg
             * compete_id : 1
             * compete_name : 2003年第三届全国城市体育舞蹈锦标赛
             * photos : 4
             * collection_id : 0
             */

            private String id;
            private String title;
            private String img_fm;
            private String compete_id;
            private String compete_name;
            private String    photos;
            private int    collection_id;

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

            public String getImg_fm() {
                return img_fm;
            }

            public void setImg_fm(String img_fm) {
                this.img_fm = img_fm;
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

            public String getPhotos() {
                return photos;
            }

            public void setPhotos(String photos) {
                this.photos = photos;
            }

            public int getCollection_id() {
                return collection_id;
            }

            public void setCollection_id(int collection_id) {
                this.collection_id = collection_id;
            }
        }


    }
}
