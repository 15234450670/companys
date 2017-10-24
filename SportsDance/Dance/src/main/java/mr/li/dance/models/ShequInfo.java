package mr.li.dance.models;

import java.util.List;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/23 0023
 * 描述:
 * 修订历史:
 */
public class ShequInfo  {

        /**
         * id : 79
         * uid : 29883
         * title : GG
         * content : 123456
         * dynamic_time : 2017-10-23 10:50:12
         * upvote : 86
         * view_count : 8
         * comment_count : 0
         * type : 1
         * is_upvote : 2
         * picture_arr : ["http://store.cdsf.org.cn/picture//317/0/0/587aba0d4c3aeedde60a601db3651975.png","http://store.cdsf.org.cn/picture//415/0/0/1b48716c49f76d8411d4e63a4db0a9bb.png","http://store.cdsf.org.cn/picture//49/0/0/a3fac6e339f79121f75540ee85a908c9.png"]
         * user : {"picture_src":"http://","username":"1"}
         * video : [{"address":"http://jkvedioout.oss-cn-beijing.aliyuncs.com/99c464d876988db48830e871ea450f82.mp4"}]
         */

        private String id;
        private String          uid;
        private String          title;
        private String          content;
        private String          dynamic_time;
        private String          upvote;
        private String          view_count;
        private String          comment_count;
        private String          type;
        private int             is_upvote;
        private UserBean        user;
        private List<String>    picture_arr;
        private List<VideoBean> video;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDynamic_time() {
            return dynamic_time;
        }

        public void setDynamic_time(String dynamic_time) {
            this.dynamic_time = dynamic_time;
        }

        public String getUpvote() {
            return upvote;
        }

        public void setUpvote(String upvote) {
            this.upvote = upvote;
        }

        public String getView_count() {
            return view_count;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
        }

        public String getComment_count() {
            return comment_count;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getIs_upvote() {
            return is_upvote;
        }

        public void setIs_upvote(int is_upvote) {
            this.is_upvote = is_upvote;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<String> getPicture_arr() {
            return picture_arr;
        }

        public void setPicture_arr(List<String> picture_arr) {
            this.picture_arr = picture_arr;
        }

        public List<VideoBean> getVideo() {
            return video;
        }

        public void setVideo(List<VideoBean> video) {
            this.video = video;
        }

        public static class UserBean {
            /**
             * picture_src : http://
             * username : 1
             */

            private String picture_src;
            private String username;

            public String getPicture_src() {
                return picture_src;
            }

            public void setPicture_src(String picture_src) {
                this.picture_src = picture_src;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }

        public static class VideoBean {
            /**
             * address : http://jkvedioout.oss-cn-beijing.aliyuncs.com/99c464d876988db48830e871ea450f82.mp4
             */

            private String address;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }

}
