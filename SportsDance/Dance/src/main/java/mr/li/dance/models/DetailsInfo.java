package mr.li.dance.models;

import java.util.List;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/27 0027
 * 描述:
 * 修订历史:
 */
public class DetailsInfo {
    /**
     * id : 78
     * uid : 29883
     * title : GG
     * content : 喇叭
     * dynamic_time : 2017-10-23 10:05:25
     * type : 1
     * upvote : -4
     * view_count : 14
     * comment_count : 3
     * is_upvote : 2
     * user : [{"username":"1","picture_src":"http://"}]
     * address : ["http://store.cdsf.org.cn/picture//558/1/0/dde0a7101b933cb89840f62dbaaa0642.png"]
     * comm : [{"id":"3","content":"Yyyy","comment_time":"2017-10-24 21:22:47","userid":"29812","address":[],"user":[{"username":"空空小僧","picture_src":"https://q.qlogo.cn/qqapp/1105479969/E053CB491F719084E02954277BD2F246/100"}],"comd":[]},{"id":"2","content":"嗯呢","comment_time":"2017-10-16 10:28:29","userid":"29883","address":[],"user":[{"username":"1","picture_src":null}],"comd":[]},{"id":"1","content":"评论","comment_time":"2017-10-16 10:05:59","userid":"29883","address":[],"user":[{"username":"1","picture_src":null}],"comd":[]}]
     */

    private String         id;
    private String         uid;
    private String         title;
    private String         content;
    private String         dynamic_time;
    private String         type;
    private String         upvote;
    private String         view_count;
    private String         comment_count;
    private int            is_upvote;
    private List<UserBean> user;
    private List<String>   address;
    private List<String> video;

    public List<String> getVideo() {
        return video;
    }

    public void setVideo(List<String> video) {
        this.video = video;
    }

    private List<CommBean> comm;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getIs_upvote() {
        return is_upvote;
    }

    public void setIs_upvote(int is_upvote) {
        this.is_upvote = is_upvote;
    }

    public boolean isDianZan() {
        return is_upvote == 1;
    }

    public void setIs_upvote() {
        is_upvote = is_upvote == 1 ? 2 : 1;
    }

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public List<CommBean> getComm() {
        return comm;
    }

    public void setComm(List<CommBean> comm) {
        this.comm = comm;
    }

    public static class UserBean {
        /**
         * username : 1
         * picture_src : http://
         */

        private String username;
        private String picture_src;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPicture_src() {
            return picture_src;
        }

        public void setPicture_src(String picture_src) {
            this.picture_src = picture_src;
        }
    }

    public static class CommBean {
        /**
         * id : 3
         * content : Yyyy
         * comment_time : 2017-10-24 21:22:47
         * userid : 29812
         * address : []
         * user : [{"username":"空空小僧","picture_src":"https://q.qlogo.cn/qqapp/1105479969/E053CB491F719084E02954277BD2F246/100"}]
         * comd : []
         */

        private String          id;
        private String          content;
        private String          comment_time;
        private String          userid;
        private List<?>         address;
        private List<UserBeanX> user;
        private List<Revert>    comd;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getComment_time() {
            return comment_time;
        }

        public void setComment_time(String comment_time) {
            this.comment_time = comment_time;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public List<?> getAddress() {
            return address;
        }

        public void setAddress(List<?> address) {
            this.address = address;
        }

        public List<UserBeanX> getUser() {
            return user;
        }

        public void setUser(List<UserBeanX> user) {
            this.user = user;
        }

        public List<Revert> getComd() {
            return comd;
        }

        public void setComd(List<Revert> comd) {
            this.comd = comd;
        }

        public static class UserBeanX {
            /**
             * username : 空空小僧
             * picture_src : https://q.qlogo.cn/qqapp/1105479969/E053CB491F719084E02954277BD2F246/100
             */

            private String username;
            private String picture_src;

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPicture_src() {
                return picture_src;
            }

            public void setPicture_src(String picture_src) {
                this.picture_src = picture_src;
            }
        }

        public static class Revert {
            private String          id;
            private String          content;
            private String          comment_time;
            private String          userid;
            private List<UserBeanX> user;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getComment_time() {
                return comment_time;
            }

            public void setComment_time(String comment_time) {
                this.comment_time = comment_time;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public List<UserBeanX> getUser() {
                return user;
            }

            public void setUser(List<UserBeanX> user) {
                this.user = user;
            }
        }
    }
}