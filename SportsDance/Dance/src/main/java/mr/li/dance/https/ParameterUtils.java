package mr.li.dance.https;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.CacheMode;
import com.yolanda.nohttp.rest.Request;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

import mr.li.dance.utils.AppConfigs;
import mr.li.dance.utils.NLog;
import mr.li.dance.utils.glide.ImageLoaderManager;

/**
 * 作者: Lixuewei
 * 版本: 1.0
 * 创建日期: 2017/5/22
 * 描述: 请求合成类,负责组成所有接口的请求参数
 * 修订历史:
 */
public class ParameterUtils {
    private volatile static ParameterUtils singleton;

    private ParameterUtils() {
    }

    public static ParameterUtils getSingleton() {
        if (singleton == null) {
            synchronized (ImageLoaderManager.class) {
                if (singleton == null) {
                    singleton = new ParameterUtils();
                }
            }
        }
        return singleton;
    }

    private Request<String> getBaseRequestForPost(String childUrl) {
        String url = new StringBuilder(AppConfigs.getDomainUrl()).append(childUrl).toString();
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        request.setCacheMode(CacheMode.ONLY_REQUEST_NETWORK);
        NLog.i("ParameterUtils", "url == " + url);
        return request;
    }

    private Request<String> getBaseRequestCacheForPost(String childUrl) {
        Request<String> request = getBaseRequestForPost(childUrl);
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        return request;
    }

    /**
     * 获取验证码
     * @param mobile
     * @return
     */
    public Request<String> getCodeMap(String mobile) {
        Request<String> request = getBaseRequestForPost("/passport.sendIdentifyCode");
        request.add("mobile", mobile);
        return request;
    }

    /**
     * 找回密码发送验证码
     * @param mobile
     * @return
     */
    public Request<String> getFindPwdCodeMap(String mobile) {
        Request<String> request = getBaseRequestForPost("/login.sendIdentifyCode");
        request.add("mobile", mobile);
        return request;
    }

    /**
     * 第三方登录获取验证码
     * @param mobile
     * @return
     */
    public Request<String> getSendIdentifyCode_thirdMap(String mobile) {
        Request<String> request = getBaseRequestForPost("/passport.sendIdentifyCode_third");
        request.add("mobile", mobile);
        return request;
    }

    /**
     * 修改密码
     * @param mobile
     * @param old_password
     * @param password
     * @param password_y
     * @return
     */
    public Request<String> getUpdatePwdMap(String mobile, String old_password, String password, String password_y) {
        Request<String> request = getBaseRequestForPost("/myInfo.retrieve");
        request.add("mobile", mobile);
        request.add("old_password", old_password);
        request.add("password", password);
        request.add("password_y", password_y);
        return request;
    }

    /**
     * 验证验证码
     * @param mobile
     * @param identifyingCode
     * @return
     */
    public Request<String> getCheckCodeMap(int type, String mobile, String identifyingCode) {
        Request<String> request = getBaseRequestForPost("/passport.register");
        request.add("type", type);
        request.add("mobile", mobile);
        request.add("identifyingCode", identifyingCode);
        return request;
    }

    public Request<String> getUserInfo() {

        Request<String> request = getBaseRequestForPost("/passport.register");

        return request;
    }

    /**
     * 补充资料
     * @param userid
     * @param real_name
     * @param id_card
     * @return
     */
    public Request<String> getReplenishInfoMap(String userid, String real_name, String id_card) {
        Request<String> request = getBaseRequestForPost("/passport.replenish");
        request.add("userid", userid);
        request.add("real_name", real_name);
        request.add("id_card", id_card);
        return request;
    }

    /**
     * 活动
     * @param appid
     * @param appsecret
     * @param url
     * @param userid
     * @return
     */
    public Request<String> getHuoDongInfoMap(String appid, String appsecret, String url, String userid) {
        Request<String> request = getBaseRequestForPost("/activity.vote");
        request.add("userid", userid);
        request.add("url", url);
        request.add("appsecret", appsecret);
        request.add("appid", appid);
        return request;
    }

    /**
     * 音乐界面
     */
    public Request<String> getMusicInfoMap() {
        Request<String> request = getBaseRequestForPost("/home.music");
        return request;
    }

    /**
     * 新音乐界面
     */
    public Request<String> getMusicInfo2Map(String page) {
        Request<String> request = getBaseRequestForPost("/home.music2");
        request.add("page", page);
        return request;
    }

    /**
     * 音乐分页
     */
    public Request<String> getMusicInfoMapIndex(String page) {
        Request<String> request = getBaseRequestForPost("/home.musicPage");
        request.add("page", page);
        return request;
    }

    /**
     * 歌单界面
     */
    public Request<String> getMusicInfoGeDanMap(String userid, String id, String page) {
        Request<String> request = getBaseRequestForPost("/music.musicList");
        request.add("userid", userid);
        request.add("page", page);

        request.add("id", id);
        return request;
    }

    /**
     * 舞蹈界面
     */
    public Request<String> getMusicInfoMapWuDao(String id) {
        Request<String> request = getBaseRequestForPost("/music.musicClass");
        request.add("id", id);
        return request;
    }

    /**
     * 提现Item
     */
    public Request<String> getTiXianInfoMap(String userid, String page) {
        Request<String> request = getBaseRequestForPost("/redpacket.detail");
        request.add("userid", userid);
        request.add("page", page);
        return request;
    }

    /**
     * 快速注册
     */
    public Request<String> getRegisterMap(String version, String mobile, String password, String device_token,
                                          String is_equipment, String phone_xh) {
        Request<String> request = getBaseRequestForPost("/passport.nicknames");
        request.setCacheMode(CacheMode.ONLY_REQUEST_NETWORK);
        request.add("version", version);
        request.add("is_equipment", is_equipment);
        request.add("mobile", mobile);
        request.add("password", password);
        request.add("phone_xh", phone_xh);
        request.add("device_token", device_token);
        return request;
    }

    /**
     * 绑定状态判断
     */
    public Request<String> getBound_state(String userid) {
        Request<String> request = getBaseRequestForPost("/redpacket.isAlipay");
        request.add("userid", userid);
        return request;
    }

    /**
     * 绑定状态判断
     */
    public Request<String> getTiXian_state(String userid) {
        Request<String> request = getBaseRequestForPost("/redpacket.isGetMoney");
        request.add("userid", userid);
        return request;
    }

    /**
     * 提现操作
     */
    public Request<String> getTiXian(String userid, String money, int times, int result, String sign) {
        Request<String> request = getBaseRequestForPost("/redpacket.getMoney");
        request.add("userid", userid);
        request.add("money", money);
        request.add("times", times);
        request.add("result", result);
        request.add("sign", sign);
        return request;
    }

    /**
     * 绑定支付宝
     */
    public Request<String> getBoundZFB(String userid, String alipay) {
        Request<String> request = getBaseRequestForPost("/redpacket.bind");
        request.add("userid", userid);
        request.add("alipay", alipay);
        return request;
    }

    /**
     * 获取服务器时间
     */
    public Request<String> getTime(String userid) {
        Request<String> request = getBaseRequestForPost("/redpacket.getTime");
        request.add("userid", userid);
        return request;
    }

    /**
     * 忘记密码
     * @param mobile
     * @param password
     * @return
     */
    public Request<String> getFindBackPwdMap(String mobile, String password) {
        Request<String> request = getBaseRequestForPost("/login.retrieve");
        request.add("mobile", mobile);
        request.add("password", password);
        request.add("password_y", password);
        return request;
    }

    /**
     * 设置头像和昵称和密码
     * @param mobile
     * @param password
     * @param picture
     * @param username
     * @return
     */
    public Request<String> getSetNickNameInfoMap(String device_token, String mobile, String password, String picture, String username, String version, String phone_xh) {
        Request<String> request = getBaseRequestForPost("/passport.nickname");
        request.add("mobile", mobile);
        request.add("password", password);
        request.add("picture", picture);
        request.add("username", username);
        request.add("device_token", device_token);
        request.add("version", version);
        request.add("phone_xh", phone_xh);
        return request;
    }

    /**
     * 登录
     * @param version
     * @param mobile
     * @param password
     * @return
     */
    public Request<String> getLoginMap(String version, String mobile, String password, String phone_xh) {
        Request<String> request = getBaseRequestForPost("/login.loginMob");
        request.setCacheMode(CacheMode.ONLY_REQUEST_NETWORK);
        request.add("version", version);
        request.add("mobile_type", "1");
        request.add("mobile", mobile);
        request.add("password", password);
        request.add("phone_xh", phone_xh);
        return request;
    }

    /**
     * 检查登陆
     */
    public Request<String> checkLogin(String userid, String version, String mobile_type, String mobile_xh, String device_token, String time) {
        Request<String> request = getBaseRequestForPost("/startApp.record");
        request.add("userid", userid);
        request.add("version", version);
        request.add("mobile_type", mobile_type);
        request.add("mobile_xh", mobile_xh);
        request.add("device_token", device_token);
        request.add("time", time);
        return request;
    }

    /**
     * 推送活动/外链
     */

    public Request<String> PushLove(String type, String id) {
        Request<String> request = getBaseRequestForPost("/startApp.open");
        request.add("type", type);
        request.add("id", id);
        return request;
    }


    /**
     * 推荐页面获取
     * @return
     */
    public Request<String> getHomeIndexMap() {
        Request<String> request = getBaseRequestCacheForPost("/home.index");
        return request;
    }


    /**
     * 推荐页面分页获取
     * @param indexPage
     * @return
     */
    public Request<String> getHomeIndexPageMap(int indexPage) {
        Request<String> request = getBaseRequestForPost("/home.indexPage");
        request.add("page", indexPage);
        LogPage(indexPage);
        return request;
    }

    /**
     * 获取首页直播
     * @return
     */
    public Request<String> getHomeZhiboMap() {
        Request<String> request = getBaseRequestCacheForPost("/home.zhibo");

        return request;
    }

    public Request<String> getHomeZhiboMapFromServer() {
        Request<String> request = getBaseRequestForPost("/home.zhibo");
        return request;
    }

    /**
     * 首页分页获取直播
     * @param indexPage
     * @return
     */
    public Request<String> getHomeZhiboPageMap(int indexPage) {
        Request<String> request = getBaseRequestForPost("/home.zhiboPage");
        request.add("page", indexPage);
        LogPage(indexPage);
        return request;
    }

    /**
     * 直播详情
     * @param id
     * @return
     */
    public Request<String> getHZhiboDetailMap(String id, String page) {
        Request<String> request = getBaseRequestForPost("/revisionHome.zhiboDetail");
        request.add("id", id);
        request.add("page", page);
        return request;
    }

    /**
     * 视频详情
     * @param id
     * @return
     */
    public Request<String> getVideoDetailMap(String id, String userid) {
        Request<String> request = getBaseRequestForPost("/revisionHome.dianboDetail");
        request.add("id", id);
        request.add("userid", userid);
        return request;
    }

    /**
     * 首页获取点播（视频）
     * @return
     */
    public Request<String> getHomeDianboMap() {
        Request<String> request = getBaseRequestCacheForPost("/home.dianbo");
        return request;
    }

    /**
     * 新首页获取点播（视频）
     * @return
     */
    public Request<String> getHomeDianbo2Map(String page) {
        Request<String> request = getBaseRequestCacheForPost("/revisionHome.dianbo");
        request.add("page", page);
        return request;
    }

    /**
     * 相关专辑
     * @param page
     * @return
     */
    public Request<String> getVideoSpeial(String id, String page) {
        Request<String> request = getBaseRequestCacheForPost("/revisionHome.albumList");
        request.add("id", id);
        request.add("page", page);
        return request;
    }

    public Request<String> getHomeDianboMapFromServer() {
        Request<String> request = getBaseRequestForPost("/home.dianbo");
        return request;
    }

    /**
     * 首页分页获取点播（视频）
     * @return
     */
    public Request<String> getHomeDianboPageMap(int indexPage) {
        Request<String> request = getBaseRequestForPost("/home.dianboPage");
        request.add("page", indexPage);
        LogPage(indexPage);
        return request;
    }

    /**
     * 获取视频分类列表
     * @param indexPage
     * @param app_type_id
     * @return
     */
    public Request<String> getVideoListMap(int indexPage, String app_type_id) {
        Request<String> request = getBaseRequestForPost("/home.dianboList");
        request.add("app_type_id", app_type_id);
        request.add("page", indexPage);
        LogPage(indexPage);
        return request;
    }

    /**
     * 首页获取资讯
     * @return
     */
    public Request<String> getHomeZxMap(String page) {
        Request<String> request = getBaseRequestCacheForPost("/home.zx2");
        request.add("page", page);
        return request;
    }

    public Request<String> getLabelSelect(String type) {
        Request<String> request = getBaseRequestCacheForPost("/home.labelClass");
        request.add("type", type);
        return request;
    }


    public Request<String> getHomeZxMapTab(String page, String class_id) {
        Request<String> request = getBaseRequestCacheForPost("/home.zxTabel");
        request.add("page", page);
        request.add("class_id", class_id);
        return request;
    }

    public Request<String> getHomeZxMapFromServer() {
        Request<String> request = getBaseRequestForPost("/home.zx");
        return request;
    }

    /**
     * 首页分页获取资讯
     * @param indexPage
     * @return
     */
    public Request<String> getHomeZxPageMap(int indexPage) {
        Request<String> request = getBaseRequestForPost("/home.zxPage");
        request.add("page", indexPage);
        LogPage(indexPage);
        return request;
    }

    private void LogPage(int indexPage) {
        NLog.i("ParameterUtils", "page == " + indexPage);
    }

    /**
     * 获取分类资讯列表
     * @param indexPage
     * @param
     * @return
     */
    public Request<String> getZixunListMap(int indexPage, String app_type_id) {
        Request<String> request = getBaseRequestForPost("/home.zxList");
        request.add("app_type_id", app_type_id);
        request.add("page", indexPage);
        LogPage(indexPage);
        return request;
    }

    /**
     * 教学数据
     */
    public Request<String> getHomeTeachMap(String page) {
        Request<String> request = getBaseRequestCacheForPost("/home.teachList");
        request.add("page", page);
        return request;
    }

    /**
     * 所有的Tab
     */
    public Request<String> getHomeTabhMap(String id, String type, String page) {
        Request<String> request = getBaseRequestCacheForPost("/revisionHome.labelSearch");
        request.add("id", id);
        request.add("type", type);
        request.add("page", page);
        return request;
    }

    /**
     * 教学详情
     */
    public Request<String> getHomeTeachDetailsMap(String id, String page) {
        Request<String> request = getBaseRequestCacheForPost("/revisionHome.teachDetail");
        request.add("id", id);
        request.add("page", page);
        return request;
    }

    /**
     * 首页获取图片
     * @return
     */
    public Request<String> getHomeAlbumMap(int indexPage) {
        Request<String> request = getBaseRequestCacheForPost("/home.album");
        request.add("page", indexPage);
        LogPage(indexPage);
        return request;
    }

    /**
     * 新首页获取图片
     * @param indexPage
     * @return
     */
    public Request<String> getHomeAlbum2Map(int indexPage) {
        Request<String> request = getBaseRequestCacheForPost("/home.photoClass");
        request.add("page", indexPage);
        LogPage(indexPage);
        return request;
    }


    public Request<String> getHomeAlbumMapFromServer(int indexPage) {
        Request<String> request = getBaseRequestForPost("/home.album");

        request.add("page", indexPage);
        LogPage(indexPage);
        return request;
    }

    /**
     * 搜索
     */
    public Request<String> getHomeSearchMap(String type, String content, int page) {
        Request<String> request = getBaseRequestForPost("/revisionHome.homeSearch");
        request.add("type", type);
        request.add("content", content);
        request.add("page", page);
        LogPage(page);
        return request;
    }

    /**
     * 获取相册详情
     * @param id
     * @return
     */
    public Request<String> getAlbumDetailMap(String userid, String id) {
        Request<String> request = getBaseRequestForPost("/home.albumDetail");
        request.add("userid", userid);
        request.add("id", id);
        return request;
    }

    public Request<String> getAlbumDetail2Map(String userid, String id, int page) {
        Request<String> request = getBaseRequestForPost("/home.photoList");
        request.add("userid", userid);
        request.add("id", id);
        request.add("page", page);
        return request;
    }

    /**
     * 分页获取相册内图片
     * @param id
     * @param indexPage
     * @return
     */
    public Request<String> getPhotoDetailMap(String id, int indexPage) {
        Request<String> request = getBaseRequestForPost("/home.photoDetail");
        request.add("id", id);
        request.add("page", indexPage);

        LogPage(indexPage);
        return request;
    }

    public Request<String> getCollectionMap(String userid, String thisid, int xc_video, int operation) {
        Request<String> request = getBaseRequestForPost("/home.collection");
        request.setCacheMode(CacheMode.ONLY_REQUEST_NETWORK);
        request.add("userid", userid);
        request.add("thisid", thisid);
        request.add("xc_video", xc_video);
        request.add("operation", operation);

        return request;
    }

    /**
     * 个人相册页面
     * @param userid
     * @param attention_userid
     * @return
     */
    public Request<String> getPersonalListMap(String userid, String attention_userid) {
        Request<String> request = getBaseRequestCacheForPost("/home.personalList");
        request.add("userid", userid);
        request.add("attention_userid", attention_userid);
        return request;
    }

    /**
     * 个人相册页面分页获取相册
     * @param attention_userid
     * @param indexPage
     * @return
     */
    public Request<String> getPersonalListPageMap(String attention_userid, int indexPage) {
        Request<String> request = getBaseRequestForPost("/home.personalListPage");
        request.add("attention_userid", attention_userid);
        request.add("page", indexPage);

        LogPage(indexPage);
        return request;
    }

    /**
     * 关注操作
     * @param userid
     * @param attention_userid
     * @param attention
     * @return
     */
    public Request<String> getAttentionOperationMap(String userid, String attention_userid, int attention) {
        Request<String> request = getBaseRequestForPost("/user.attentionOperation");
        request.add("userid", userid);
        request.add("attention_userid", attention_userid);
        request.add("attention", attention);
        return request;
    }

    /**
     * 获取赛事首页
     * @return
     */
    public Request<String> getMatchMap() {
        Request<String> request = getBaseRequestCacheForPost("/match.index");

        return request;
    }

    public Request<String> getMatchMapFromServer() {
        Request<String> request = getBaseRequestForPost("/match.index");

        return request;
    }

    /**
     * 赛事首页分页获取
     * @param indexPage
     * @return
     */
    public Request<String> getMatchIndexPageMap(int indexPage) {
        Request<String> request = getBaseRequestForPost("/match.indexList");
        request.add("page", indexPage);
        LogPage(indexPage);
        return request;
    }

    /**
     * 赛事搜索的接口
     * @param date
     * @param content
     * @param indexPage
     * @return
     */
    public Request<String> getSearchMatchMap(String date, String content, int indexPage) {
        Request<String> request = getBaseRequestForPost("/match.matchSearch");
        request.add("date", date);
        request.add("content", content);
        request.add("page", indexPage);
        LogPage(indexPage);
        return request;
    }

    public Request<String> getPhotoSearchMap(String beihao, String compete_id) {
        Request<String> request = getBaseRequestForPost("/match.photoSearch");
        request.add("beihao", beihao);
        request.add("compete_id", compete_id);
        return request;
    }

    /**
     * 赛事分类接口
     * @param date
     * @param indexPage
     * @return
     */
    public Request<String> getmMatchFenleiMap(String date, int type, int indexPage) {
        Request<String> request = getBaseRequestForPost("/match.matchFenlei");
        request.add("date", date);
        request.add("type", type);
        request.add("page", indexPage);

        LogPage(indexPage);
        return request;
    }

    /**
     * 赛事详情
     * @param id
     * @return
     */
    public Request<String> getmMatchDetailMap(String id) {
        Request<String> request = getBaseRequestForPost("/match.matchDetail");
        request.add("id", id);
        return request;
    }

    /**
     * 成绩查询
     * @param id
     * @return
     */
    public Request<String> getmScoreQueryMap(String id, int indexPage) {
        Request<String> request = getBaseRequestForPost("/match.scoreQuery");
        request.add("id", id);
        request.add("page", indexPage);
        LogPage(indexPage);
        return request;
    }

    /**
     * 成绩分享
     */
    public Request<String> getmScoreShareMap(String id) {
        Request<String> request = getBaseRequestForPost("/match.shareGrade");
        request.add("id", id);
        return request;
    }


    /**
     * 成绩查询名次的接口
     * @param group_name
     * @param page
     * @return
     */
    public Request<String> getmscoreQueryMMap(String matchId, String group_name, int page) {
        Request<String> request = getBaseRequestForPost("/match.scoreQueryM?page=" + page);
        request.add("group_name", group_name);
        request.add("page", page);
        request.add("id", matchId);
        LogPage(page);
        return request;
    }

    /**
     * 赛事视频的接口
     * @return
     */
    public Request<String> getMatchVedioMap(String id) {
        Request<String> request = getBaseRequestCacheForPost("/revisionMatch.matchVedio");
        request.add("id", id);
        return request;
    }

    /*
    分享视频
     */
    public Request<String> getMatchShareVedioMap(String id) {
        Request<String> request = getBaseRequestCacheForPost("/match.shareVedio");
        request.add("id", id);
        return request;
    }

    /**
     * 赛事视频列表的接口
     * @param id
     * @param page
     * @return
     */
    public Request<String> getMatchVedioListMap(String id, int page) {
        Request<String> request = getBaseRequestForPost("/match.matchVedioList");
        request.add("id", id);
        request.add("page", page);
        LogPage(page);
        return request;
    }

    /**
     * 精彩图片
     * @param title
     * @return
     */
    public Request<String> getWonderfulPicListMap(String id, String title) {
        Request<String> request = getBaseRequestForPost("/match.jingcaiPhoto");
        request.add("id", id);
        request.add("title", title);
        return request;
    }

    /**
     * 图片分享
     */
    public Request<String> getWonderfulPicShareMap(String id) {
        Request<String> request = getBaseRequestForPost("/match.sharePicture");
        request.add("id", id);
        return request;
    }

    /**
     * 直播视频详情的接口
     * @param title
     * @param page
     * @return
     */
    public Request<String> getMatchPhotoListMap(String title, int page) {
        Request<String> request = getBaseRequestForPost("/match.matchPhotoList");
        request.add("title", title);
        request.add("page", page);
        LogPage(page);
        return request;
    }

    private void log(HashMap<String, Object> requestMap) {
        for (Entry<String, Object> entry : requestMap.entrySet()) {
            NLog.i("ParameterUtils", entry.getKey() + "--->" + entry.getValue());
        }
    }

    public Request<String> getWebMap(String id) {
        Request<String> request = getBaseRequestForPost("/home.zxDetail");
        request.add("id", id);
        return request;
    }

    /**
     * 关于我们
     * @return
     */
    public Request<String> getAboutUsMap() {
        Request<String> request = getBaseRequestForPost("/user.aboutUs");
        return request;
    }

    public Request<String> getxcUploadDetailMap() {
        Request<String> request = getBaseRequestForPost("/user.xcUploadDetail");
        return request;
    }

    public Request<String> getMatch_Jsgz_Sx_SCB_Map(String compete_id, String w_page) {
        Request<String> request = getBaseRequestForPost("/match.graphicDetails");
        request.add("compete_id", compete_id);
        request.add("w_page", w_page);
        return request;
    }

    /**
     * 意见反馈
     * @param userid
     * @return
     */
    public Request<String> getUserOpinionMap(String userid) {
        Request<String> request = getBaseRequestForPost("/user.opinion");
        request.add("userid", userid);
        return request;
    }

    /**
     * 发送问题反馈
     * @param username
     * @return
     */
    public Request<String> getSendOpinionMap(String username, String userid, String content) {
        Request<String> request = getBaseRequestForPost("/user.sendOpinion");
        request.add("username", username);
        request.add("userid", userid);
        request.add("content", content);
        return request;
    }

    /**
     * 我的相册
     * @param userid
     * @return
     */
    public Request<String> getMyAlbumMap(String userid) {
        Request<String> request = getBaseRequestForPost("/user.myAlbun");
        request.add("userid", userid);
        return request;
    }

    /**
     * 我的关注
     * @param userid
     * @return
     */
    public Request<String> getMyAttentionMap(String userid) {
        Request<String> request = getBaseRequestForPost("/user.attention");
        request.add("userid", userid);
        return request;
    }

    /**
     * 我的收藏
     * @param userid
     * @param xc_video
     *         // 相册的收藏：   10601 // 视频的收藏：   10602    //音乐10603
     * @param page
     * @return
     */
    public Request<String> getCollectionListMap(String userid, int xc_video, int page) {
        Request<String> request = getBaseRequestForPost("/user.collectionList");
        request.add("userid", userid);
        request.add("xc_video", xc_video);
        request.add("page", page);
        LogPage(page);
        return request;
    }

    public Request<String> getCollectionListMap2(String userid, int xc_video, int page) {
        Request<String> request = getBaseRequestForPost("/revisionUser.collectionList");
        request.add("userid", userid);
        request.add("xc_video", xc_video);
        request.add("page", page);
        LogPage(page);
        return request;
    }

    /**
     * 版本控制
     * @param version
     * @return
     */
    public Request<String> getVersionMap(String version) {
        Request<String> request = getBaseRequestForPost("/user.version");
        request.add("type", 1);
        request.add("version", version);
        return request;
    }

    /**
     * 上传头像
     * @param filepath
     * @return
     */
    public Request<String> getUpdateFileMap(String filepath) {
        Request<String> request = getBaseRequestForPost("/user.photo");
        request.add("picture", new File(filepath));
        return request;
    }

    public Request<String> getUpdateHeadIconMap(String userid, String new_picture) {
        Request<String> request = getBaseRequestForPost("/myInfo.edPicture");
        request.add("new_picture", new_picture);
        request.add("userid", userid);
        return request;
    }

    public Request<String> getUserInfoMap(String userId) {
        Request<String> request = getBaseRequestForPost("/myInfo.myList");
        request.add("userid", userId);
        return request;
    }

    public Request<String> getUpdateRealNameMap(String userId, String realName) {
        Request<String> request = getBaseRequestForPost("/myInfo.edName");
        request.add("userid", userId);
        request.add("new_real_name", realName);
        return request;
    }

    public Request<String> getUpdateCardMap(String userId, String new_id_card) {
        Request<String> request = getBaseRequestForPost("/myInfo.edIdCard");
        request.add("userid", userId);
        request.add("new_id_card", new_id_card);
        return request;
    }

    public Request<String> getUpdateSexMap(String userId, String new_sex) {
        Request<String> request = getBaseRequestForPost("/myInfo.edSex");
        request.add("userid", userId);
        request.add("new_sex", new_sex);
        return request;
    }

    public Request<String> getUpdateNickMap(String userId, String new_sex) {
        Request<String> request = getBaseRequestForPost("/myInfo.edUname");
        request.add("userid", userId);
        request.add("new_username", new_sex);
        return request;
    }

    public Request<String> getUpdateMobileMap(String userId, String mobile, String identifyingCode) {
        Request<String> request = getBaseRequestForPost("/myInfo.edMobile");
        request.add("userid", userId);
        request.add("mobile", mobile);
        request.add("identifyingCode", identifyingCode);
        return request;
    }

    public Request<String> getPassportRegister_thirdMap(String mobile) {
        Request<String> request = getBaseRequestForPost("/passport.register_third");
        request.add("mobile", mobile);
        return request;
    }

    /**
     * 考级证书查询
     * @param idn
     *         身份证号
     * @param name
     *         姓名
     * @param type
     *         类型 1：已考 2：全部  3：未考
     * @return
     */
    public Request<String> getKaojiCertificateMap(String idn, String name, int type) {
        Request<String> request = getBaseRequestForPost("/kaoji.certificate");
        request.add("idn", idn);
        request.add("name", name);
        request.add("type", type);
        return request;
    }

    public Request<String> getMyMessage(String userid, int page) {
        Request<String> request = getBaseRequestForPost("/user.myInfo");
        request.add("userid", userid);
        request.add("page", page);
        LogPage(page);
        return request;
    }


    public Request<String> getMyMessageDetail(int mes_id) {
        Request<String> request = getBaseRequestForPost("/user.myInfoDetail");
        request.add("mes_id", mes_id);
        return request;
    }

    //    openid ：uid (新浪的uid ，微信的openid ，qq的openid)
    //    source  登录方式 10402.qq   10403.微博  10404.微信
    //    mobile_type  手机类型  1.Android  2.ios
    //    varsion  App版本号
    //    phone_xh  手机型号
    public Request<String> getPassportIsOpenId(String openid, String source, String version, String phone_xh) {
        Request<String> request = getBaseRequestForPost("/passport.isOpenid");
        request.add("openid", openid);
        request.add("source", source);
        request.add("mobile_type", 1);
        request.add("version", version);
        request.add("phone_xh", phone_xh);
        String parmars = "openid = " + openid + " source=" + source + " mobile_type=" + 1 + " varsion=" + version + " phone_xh=" + phone_xh;
        NLog.i("ParameterUtils", "parmars == " + parmars);
        return request;
    }

    public Request<String> getPassportEdMobile(String device_token, String openid, String source, String mobile, String picture, String version, String phone_xh) {
        Request<String> request = getBaseRequestForPost("/passport.edMobile");
        request.add("openid", openid);
        request.add("source", source);
        request.add("mobile", mobile);
        request.add("picture", picture);
        request.add("device_token", device_token);
        request.add("version", version);
        request.add("phone_xh", phone_xh);
        request.add("mobile_type", 1);
        String parmars = "openid = " + openid + " source=" + source + " mobile=" + mobile;
        NLog.i("ParameterUtils", "parmars == " + parmars);
        return request;
    }

    public Request<String> getPassportPassword(String device_token, String openid, String mobile, String source, String username,
                                               String picture, String password, String password_y) {
        Request<String> request = getBaseRequestForPost("/passport.password");
        request.add("device_token", device_token);
        request.add("openid", openid);
        request.add("mobile", mobile);
        request.add("source", source);
        request.add("username", username);
        request.add("picture", picture);
        request.add("password", password);
        request.add("password_y", password_y);
        request.add("is_equipment", 1);

        String parmars = "openid = " + openid + " source=" + source + " mobile=" + mobile;
        NLog.i("ParameterUtils", "parmars == " + parmars);
        return request;
    }

    public Request<String> getHomeWlinkClickMap(int id) {
        Request<String> request = getBaseRequestForPost("/home.WlinkClick");
        request.add("id", id);
        return request;
    }

    //社区——最新最热
    public Request<String> getNewsFragment(String is_type, String page, String userid) {
        Request<String> request = getBaseRequestForPost("/revisionCommunity.index");
        request.add("is_type", is_type);
        request.add("page", page);
        request.add("userid", userid);
        return request;
    }

    //个人中心头部
    public Request<String> getPerson(String attention_userid, String userid) {
        Request<String> request = getBaseRequestForPost("/revisionCommunity.heads");
        request.add("attention_userid", attention_userid);
        request.add("userid", userid);
        return request;
    }

    //个人中心Item
    public Request<String> getPersonItem(String is_type, String page, String userid, String self_id) {
        Request<String> request = getBaseRequestForPost("/revisionCommunity.personage");
        request.add("is_type", is_type);
        request.add("page", page);
        request.add("userid", userid);
        request.add("self_id", self_id);
        return request;
    }

    //我的粉丝
    public Request<String> getPersonFans(String userid) {
        Request<String> request = getBaseRequestForPost("/community.fans");
        request.add("userid", userid);
        return request;
    }

    //我的关注
    public Request<String> getPersonLook(String userid) {
        Request<String> request = getBaseRequestForPost("/user.attention");
        request.add("userid", userid);
        return request;
    }

    //举报
    public Request<String> getPersonReport(String userid, String type, String id) {
        Request<String> request = getBaseRequestForPost("/community.report");
        request.add("userid", userid);
        request.add("type", type);
        request.add("id", id);
        return request;
    }

    //删除动态 评论
    public Request<String> getPersonDelete(String is_type, String id) {
        Request<String> request = getBaseRequestForPost("/community.dynamicDel");
        request.add("is_type", is_type);
        request.add("id", id);
        return request;
    }

    //点赞
    public Request<String> getPersonLike(String userid, int is_type, String id) {
        Request<String> request = getBaseRequestForPost("/community.upvote");
        request.add("userid", userid);
        request.add("is_type", is_type);
        request.add("id", id);
        return request;
    }

    //关注操作
    public Request<String> getPersonCancelLook(String userid, String attention_userid, int attention) {
        Request<String> request = getBaseRequestForPost("/user.attentionOperation");
        request.add("userid", userid);
        request.add("attention_userid", attention_userid);
        request.add("attention", attention);
        return request;
    }

    //社区详情
    public Request<String> getPersonDetails(String dynamic_id, String userid, String attention_userid) {
        Request<String> request = getBaseRequestForPost("/revisionCommunity.details");
        request.add("dynamic_id", dynamic_id);
        request.add("userid", userid);
        request.add("attention_userid", attention_userid);
        return request;
    }

    //增加动态
    public Request<String> getPersonAddDongTai(String userid, int is_type, String title, String content) {
        Request<String> request = getBaseRequestForPost("/community.dynamicAdd");
        request.add("userid", userid);
        request.add("is_type", is_type);
        request.add("title", title);
        request.add("content", content);
        return request;
    }

    //发布图片
    public Request<String> getfabutupian(int is_type, int dynamic_id, File file) {
        Request<String> request = getBaseRequestForPost("/community.mUpload");
        request.add("dynamic_id", dynamic_id);
        request.add("is_type", is_type);
        request.add("file[]", file);
        return request;
    }

    //获取视频token
    public Request<String> getTakeVideoToken(String userid, String videoName) {
        Request<String> request = getBaseRequestForPost("/community.frontUpload");
        request.add("userid", userid);
        request.add("video_name", videoName);
        return request;
    }

    //上传视频
    public Request<String> getVideo(String dynamic_id, String video_name) {
        Request<String> request = getBaseRequestForPost("/community.vUpload");
        request.add("dynamic_id", dynamic_id);
        request.add("video_name", video_name);
        return request;
    }

    //发表评论
    public Request<String> publishingDynamics(String userid, int is_type, String content, String correlation_id) {
        Request<String> request = getBaseRequestForPost("/community.comment");
        request.add("userid", userid);
        request.add("is_type", is_type);
        request.add("content", content);
        request.add("correlation_id", correlation_id);
        request.add("picture", "");
        return request;
    }

    //红包活动
    public Request<String> getMoneyEvent(String userid, String sign, String activityid, String ymd) {
        Request<String> request = getBaseRequestForPost("/activity.addNum");
        request.add("userid", userid);
        request.add("sign", sign);
        request.add("activityid", activityid);
        request.add("ymd", ymd);
        return request;
    }

    /***
     *   改版腾讯云后的的首页
     *   /revisionHome.index 第二版
     */
    public Request<String> getHomeIndexMap1(String page) {
        Request<String> request = getBaseRequestCacheForPost("/revisionHome.index");
        request.add("page", page);
        return request;
    }

    public Request<String> getHomeIndexMap2(String page) {
        Request<String> request = getBaseRequestCacheForPost("/revisionHome.indexPage");
        request.add("page", page);
        return request;
    }

    /**
     * 2.0 赛事 接口
     */
    //首页
    public Request<String> getGameMap(String page) {
        Request<String> request = getBaseRequestCacheForPost("/revisionMatch.index");
        request.add("page", page);
        return request;
    }

    //搜索
    public Request<String> getGameMapSearch(String compete_year, String type, String state, String content, String page) {
        Request<String> request = getBaseRequestCacheForPost("/revisionMatch.matchSearch");
        request.add("compete_year", compete_year);
        request.add("type", type);
        request.add("state", state);
        request.add("content", content);
        request.add("page", page);
        return request;
    }

    //详情
    public Request<String> getGameMapDetail(String id) {
        Request<String> request = getBaseRequestCacheForPost("/revisionMatch.detail");
        request.add("id", id);
        return request;
    }

    //新闻更多
    public Request<String> getGameMapNew(String id, String page) {
        Request<String> request = getBaseRequestCacheForPost("/revisionMatch.matchArticle");
        request.add("id", id);
        request.add("page", page);
        return request;
    }

    //视频更多
    public Request<String> getGameMapVideo(String id, String page) {
        Request<String> request = getBaseRequestCacheForPost("/revisionMatch.matchVideo");
        request.add("id", id);
        request.add("page", page);
        return request;
    }

    //图片更多
    public Request<String> getGameMapPic(String id, String page) {
        Request<String> request = getBaseRequestCacheForPost("/revisionMatch.matchPhotoClass");
        request.add("id", id);
        request.add("page", page);
        return request;
    }

    //赛事介绍
    public Request<String> getGameMapIntroduce(String id) {
        Request<String> request = getBaseRequestCacheForPost("/revisionMatch.matchIntroduction");
        request.add("id", id);
        return request;
    }

    //组别查询
    //图片更多
    public Request<String> getGameMapGradeSearch(String id, String name, String page) {
        Request<String> request = getBaseRequestCacheForPost("/revisionMatch.searchGroup");
        request.add("id", id);
        request.add("name", name);
        request.add("page", page);
        return request;
    }
    /**
     *
     */
    public Request<String> getLiveCard(String id,String page) {
        Request<String> request = getBaseRequestCacheForPost("/revisionHome.menu");
        request.add("id", id);
        request.add("page", page);
        return request;
    }
}
