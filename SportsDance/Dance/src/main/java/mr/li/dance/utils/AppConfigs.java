package mr.li.dance.utils;

/**
 * 作者: Administrator
 * 时间: 2017/4/25.
 * 功能:
 */

public class AppConfigs {
    //  private final static String MAINurl = "http://work.cdsf.org.cn";//测试环境
    //  private final static String MAINurl = "http://kf.cdsf.org.cn";//测试环境
      private final static String MAINurl = "http://cdsf.org.cn";//正式环境

    private final static String DomainUrl = MAINurl + "/mobileClient";
    private final static String SHAREURL  = MAINurl + "/h5";

    public final static String LOGING_NAME     = "loging_phone";
    public final static String LOGING_TIME     = "loging_time";
    public final static String LOGING_PASSWORD = "loging_password";
    public final static String USERINFOJSON    = "USERINFOJSON";
    public final static String USERID          = "USERID";
    public final static String KAOJIEXPLAIN    = DomainUrl + "/kaoji.explain";
    //课程介绍
    public final static String TEACHERCLASS    = SHAREURL + "/share.Course?id=";
    //资讯分享
    public final static String ZixunShareUrl   = DomainUrl + "/home.zxdetailf?id=%s";
    public final static String ZixunShareUrl2  = SHAREURL + "/share.zxfx?id=";
    public final static String ZixunShareUrl3  = SHAREURL + "/share.zx?id=";

    public final static String USER         = SHAREURL + "/share.Ament";
    public final static String tach_detial  = SHAREURL + "/share.spjx?id=";
    //社区分享
    public final static String shequ_detial = SHAREURL + "/share.shequ?id=";

    //系统消息
    public final static String systemManage = SHAREURL + "/share.packet?id=";

    //赛事规则，赛事设项 赛程表 分享
    public final static String SAISHIShareUrl = SHAREURL + "/share.gcfx?id=%s&page=%s";
    //相册分享
    public final static String SHAREPHOTOURL  = SHAREURL + "/share.photo?id=%s";
    //直播分享
    public final static String SHARELIVE      = SHAREURL + "/share.live?id=%s";
    //点播分享
    public final static String SHAREMOV       = SHAREURL + "/share.mov?id=%s";
    //赛事分享
    public final static String SHAREGAME      = SHAREURL + "/share.game?id=%s";
    public final static String SHAREGAME1     = SHAREURL + "/share.spfx?id=";
    public final static String SHAREPIC       = SHAREURL + "/share.tpfx?id=";
    public final static String SHARESOURE     = SHAREURL + "/share.cjfx?id=";
    //音乐分享
    public final static String SHAREMUSIC     = SHAREURL + "/share.gdfx?id=";
    //播放界面分享
    public final static String SHAREMUSICS    = SHAREURL + "/share.gqfx?classid=";
    //赛事秩序册
    public final static String GAMEPROGRAM    = SHAREURL + "/share.zcx?competeid=%s&indexid=%s";

    public final static String KEY_PLAY_UUID = "7fjvplaumn";
    public final static String KEY_PLAY_PU   = "393628f9df";


    public final static String finishactivityAction = "finish.activity.action";
    public final static String updateinfoAction     = "update.userinfo.action";

    public final static String VIDEO_ZHIBO = "http://19627.liveplay.myqcloud.com/live/19627_";

    public final static int SENDMSG_REQUEST_CODE      = 0x001;
    public final static int home_index                = 0x0002;
    public final static int home_index_page           = 0x0003;
    public final static int home_zhibo                = 0x0004;
    public final static int home_zhiboPage            = 0x0005;
    public final static int home_zhiboDetailD         = 0x0006;//视频
    public final static int home_dianbo               = 0x0007;//快速点播
    public final static int home_dianboPage           = 0x0008;//快速点播
    public final static int home_zx                   = 0x0009;//快速点播
    public final static int home_zx_screen            = 0x00068;//快速点播
    public final static int home_zxPage               = 0x0010;//快速点播
    public final static int home_album                = 0x0011;//首页相册
    public final static int home_photoDetail          = 0x0012;//首页相册
    public final static int passport_sendIdentifyCode = 0x0013;//首页相册
    public final static int passport_register         = 0x0014;//首页相册
    public final static int passport_nickname         = 0x0015;//首页相册
    public final static int login_loginMob            = 0x0016;//首页相册
    public final static int myInfo_retrieve           = 0x0017;//首页相册
    public final static int home_zhiboDetailL         = 0x0018;//直播详情
    public final static int home_dianboList           = 0x0019;//视频分类列表
    public final static int home_dianboDetailL        = 0x0020;//视频详情
    public final static int user_attentionOperation   = 0x0021;//视频详情
    public final static int home_Search               = 0x0022;//视频详情
    public final static int user_myAlbun              = 0x0023;//
    public final static int user_attention            = 0x0024;//
    public final static int user_version              = 0x0025;//
    public final static int user_photo                = 0x0026;//
    public final static int myInfo_myList             = 0x0027;//
    public final static int myInfo_edSex              = 0x0028;//
    public final static int myInfo_edPicture          = 0x0029;//
    public final static int myInfo_edMobile           = 0x0030;//
    public final static int user_opinion              = 0x0031;//d
    public final static int user_sendOpinion          = 0x0032;//d
    public final static int user_collection           = 0x0033;//d
    public final static int home_collectionList       = 0x0034;//d
    public final static int user_collections          = 0x0035;//d


    public final static int getMatch_index_code = 0x0035;

    public final static int match_indexList         = 0x0036;
    public final static int match_matchSearch       = 0x0037;
    public final static int match_matchFenlei       = 0x0038;
    public final static int match_matchDetail       = 0x0039;
    public final static int match_scoreQuery        = 0x0040;
    public final static int match_scoreQueryM       = 0x0041;
    public final static int match_matchVedio        = 0x0042;
    public final static int match_matchVedioList    = 0x0043;
    public final static int match_jingcaiPhoto      = 0x0044;
    public final static int match_photoSearch       = 0x0045;
    public final static int webcode                 = 0x0047;
    public final static int kaoji_certificate       = 0x0048;
    public final static int kaoji_mycertificate     = 0x0049;
    public final static int user_myInfo             = 0x0050;
    public final static int passport_isOpenid       = 0x0051;
    public final static int passport_register_third = 0x0052;
    public final static int passport_edMobile       = 0x0053;
    public final static int passport_password       = 0x0054;
    public final static int login_retrieve          = 0x0055;
    public final static int home_WlinkClick         = 0x0056;
    public final static int item_tx                 = 0x0057;
    public final static int Bound_ZFB               = 0x0058;
    public final static int Bound_state             = 0x0059;
    public final static int match_share_cj          = 0x0060;
    public final static int home_music              = 0x0061;
    public final static int home_music_page         = 0x0062;
    public final static int home_music_gedan        = 0x0063;
    public final static int home_music_wudao        = 0x0064;
    public final static int home_tab_zx             = 0x0065;
    public final static int home_tab_teach          = 0x0066;
    public final static int home_tab_teachs         = 0x0066;
    public final static int home_tab_teach_details  = 0x0067;
    public final static int video_special           = 0x0068;
    public final static int shequ_news_fragment     = 0x0069;
    public final static int shequ_news_fragments    = 0x0070;
    public final static int person                  = 0x0071;//个人主页
    public final static int person_item             = 0x0071;//个人列表
    public final static int person_items            = 0x0072;//个人列表
    public final static int person_look             = 0x0073;//关注
    public final static int person_details          = 0x0074;//社区详情
    public final static int FA_DONGTAI              = 0x0075;//发布动态
    public final static int GET_TAKEVIDEO           = 0X0076; //视频上传
    public final static int GET_PUBLISH             = 0X0079; //发布评论
    public final static int GET_DYNSMICDEL          = 0X0080; //删除评论
    public final static int getMatch_search         = 0x0081; //赛事搜索
    public final static int getGame_detail          = 0x0082;  //赛事详情
    public final static int card_live          = 0x0083;  //节目单
    public static String getDomainUrl() {
        return DomainUrl;
    }

    public final static String CLICK_EVENT_1  = "1";//首页推荐
    public final static String CLICK_EVENT_2  = "2";//首页点播
    public final static String CLICK_EVENT_3  = "3";//首页直播
    public final static String CLICK_EVENT_4  = "4";//首页图片
    public final static String CLICK_EVENT_5  = "5";//首页资讯
    public final static String CLICK_EVENT_6  = "6";//首页搜索
    public final static String CLICK_EVENT_7  = "7";//赛事首页
    public final static String CLICK_EVENT_8  = "8";//考级模块
    public final static String CLICK_EVENT_33 = "33";//社区
    public final static String CLICK_EVENT_9  = "9";//我的
    public final static String CLICK_EVENT_10 = "10";//我的消息
    public final static String CLICK_EVENT_11 = "11";//我的关注
    public final static String CLICK_EVENT_12 = "12";//我的相册
    public final static String CLICK_EVENT_13 = "13";//收藏相册
    public final static String CLICK_EVENT_14 = "14";//收藏视频
    public final static String CLICK_EVENT_15 = "15";//个人信息
    public final static String CLICK_EVENT_16 = "16";//退出
    public final static String CLICK_EVENT_17 = "17";//首页模块
    public final static String CLICK_EVENT_18 = "18";//视频分享
    public final static String CLICK_EVENT_19 = "19";//直播分享
    public final static String CLICK_EVENT_20 = "20";//相册分享
    public final static String CLICK_EVENT_21 = "21";//资讯分享

    public final static String CLICK_EVENT_22 = "22";//赛事分享
    public final static String CLICK_EVENT_23 = "23";//外联分享
    public final static String CLICK_EVENT_24 = "24";//考级分享
    public final static String CLICK_EVENT_25 = "25";//赛事章程
    public final static String CLICK_EVENT_26 = "26";//赛事设项目
    public final static String CLICK_EVENT_27 = "27";//赛程表
    public final static String CLICK_EVENT_28 = "28";//加号
    public final static String CLICK_EVENT_29 = "29";//成绩查询
    public final static String CLICK_EVENT_30 = "30";//音乐
    public final static String CLICK_EVENT_31 = "31";//音乐
    public final static String CLICK_EVENT_32 = "32";//音乐


}
