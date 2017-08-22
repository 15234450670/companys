package mr.li.dance.models;

import java.util.List;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/17 0017
 * 描述:
 * 修订历史:
 */
public class GeDanInfo extends BaseResponse {

    /**
     * data : {"click_sum":"0","title":"歌单1","img_fm":"/131/0/0/c898faebd80236fb4d891fd55f257238.jpg","list":[{"id":"1","music_address":"","title":"歌曲1"},{"id":"2","music_address":"","title":"歌曲2"},{"id":"3","music_address":"","title":"歌曲3"},{"id":"4","music_address":"","title":"歌曲4"},{"id":"5","music_address":"","title":"歌曲5"},{"id":"6","music_address":"","title":"歌曲6"},{"id":"7","music_address":"","title":"歌曲7"},{"id":"8","music_address":"","title":"歌曲8"},{"id":"9","music_address":"","title":"歌曲9"},{"id":"10","music_address":"","title":"歌曲10"},{"id":"11","music_address":"","title":"歌曲11"},{"id":"12","music_address":"","title":"歌曲12"},{"id":"13","music_address":"","title":"歌曲13"},{"id":"14","music_address":"","title":"歌曲14"},{"id":"15","music_address":"","title":"歌曲15"},{"id":"16","music_address":"","title":"歌曲16"},{"id":"17","music_address":"","title":"歌曲17"},{"id":"18","music_address":"","title":"歌曲18"},{"id":"19","music_address":"","title":"歌曲19"},{"id":"20","music_address":"","title":"歌曲20"}]}
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
         * click_sum : 0
         * title : 歌单1
         * img_fm : /131/0/0/c898faebd80236fb4d891fd55f257238.jpg
         * list : [{"id":"1","music_address":"","title":"歌曲1"},{"id":"2","music_address":"","title":"歌曲2"},{"id":"3","music_address":"","title":"歌曲3"},{"id":"4","music_address":"","title":"歌曲4"},{"id":"5","music_address":"","title":"歌曲5"},{"id":"6","music_address":"","title":"歌曲6"},{"id":"7","music_address":"","title":"歌曲7"},{"id":"8","music_address":"","title":"歌曲8"},{"id":"9","music_address":"","title":"歌曲9"},{"id":"10","music_address":"","title":"歌曲10"},{"id":"11","music_address":"","title":"歌曲11"},{"id":"12","music_address":"","title":"歌曲12"},{"id":"13","music_address":"","title":"歌曲13"},{"id":"14","music_address":"","title":"歌曲14"},{"id":"15","music_address":"","title":"歌曲15"},{"id":"16","music_address":"","title":"歌曲16"},{"id":"17","music_address":"","title":"歌曲17"},{"id":"18","music_address":"","title":"歌曲18"},{"id":"19","music_address":"","title":"歌曲19"},{"id":"20","music_address":"","title":"歌曲20"}]
         */

        private String click_sum;
        private String         title;
        private String         img_fm;
        private List<ListBean> list;

        public String getClick_sum() {
            return click_sum;
        }

        public void setClick_sum(String click_sum) {
            this.click_sum = click_sum;
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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 1
             * music_address :
             * title : 歌曲1
             */

            private String id;
            private String music_address;
            private String title;
            public boolean isFalse;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMusic_address() {
                return music_address;
            }

            public void setMusic_address(String music_address) {
                this.music_address = music_address;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "id='" + id + '\'' +
                        ", music_address='" + music_address + '\'' +
                        ", title='" + title + '\'' +
                        ", isFalse=" + isFalse +
                        '}';
            }
        }
    }
}
