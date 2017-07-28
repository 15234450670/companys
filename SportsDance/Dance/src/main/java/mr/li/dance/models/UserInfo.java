package mr.li.dance.models;

/**
 * 作者: Administrator
 * 时间: 2017/5/27.
 * 功能:
 */

public class UserInfo {
    private String userid;
    private String username;
    private String mobile;
    private String picture;
    private String real_name;
    private String sex;
    private String id_card;
    private String picture_src;
    private String is_login;

    private String password;
    private String device_token;
    private String version;
    private String phone_xh;
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPhone_xh() {
        return phone_xh;
    }

    public void setPhone_xh(String phone_xh) {
        this.phone_xh = phone_xh;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPicture() {
        return picture;
    }

    public String getReal_name() {
        return real_name;
    }

    public String getSex() {
        return sex;
    }

    public String getId_card() {
        return id_card;
    }

    public String getPicture_src() {
        return picture_src;
    }

    public void setPicture_src(String picture_src) {
        this.picture_src = picture_src;
    }

    public String getIs_login() {
        return is_login;
    }

    public void setIs_login(String is_login) {
        this.is_login = is_login;
    }
}
