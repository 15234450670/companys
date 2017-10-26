package mr.li.dance.models;

import java.util.List;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/24 0024
 * 描述:
 * 修订历史:
 */
public class PersonItemInfo {
    /**
     * id : 26
     * uid : 29858
     * title : Sfsdafds
     * content : Fdssafdsa
     * dynamic_time : 2017-07-15 12:26:05
     * upvote : 41
     * view_count : 31
     * comment_count : 3
     * type : 1
     * is_upvote : 1
     * picture_arr : ["http://store.cdsf.org.cn/picture//130/0/0/f90bdbe424b0aaeb440d11f2d3174744.png","http://store.cdsf.org.cn/picture//49/0/0/a3fac6e339f79121f75540ee85a908c9.png","http://store.cdsf.org.cn/picture//558/1/0/dde0a7101b933cb89840f62dbaaa0642.png","http://store.cdsf.org.cn/picture//317/0/0/587aba0d4c3aeedde60a601db3651975.png","http://store.cdsf.org.cn/picture//58/0/0/d4d1baf22a8c36022da34c5f5186d1a0.png"]
     * user : {"picture_src":"http://store.cdsf.org.cn/picture/./touxiang11.","username":"9629"}
     */
    private String       id;
    private String       uid;
    private String       title;
    private String       content;
    private String       dynamic_time;
    private String       upvote;
    private String       view_count;
    private String       comment_count;
    private String       type;
    private int          is_upvote;
    private UserBean     user;
    private List<String> picture_arr;
    public boolean isDianZan() {
        return is_upvote == 1;
    }
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
    public void setIs_upvote() {
        is_upvote = is_upvote == 1 ? 2 : 1;
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

    public static class UserBean {
        /**
         * picture_src : http://store.cdsf.org.cn/picture/./touxiang11.
         * username : 9629
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
}
