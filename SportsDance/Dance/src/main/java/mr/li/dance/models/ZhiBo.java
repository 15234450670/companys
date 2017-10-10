package mr.li.dance.models;

import java.util.ArrayList;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/10 0010
 * 描述:
 * 修订历史:
 */
public class ZhiBo extends BaseResponse {

    /**
     * data : {"compete":[{"match_id":"51","name":"2017年中国.镇江体育舞蹈全国公开赛\u200b10.3","activity_id":"A20170928000003p","begin_time":"2017-10-03 08:00:00","end_time":"2017-10-03 23:00:00","brief":"      2017年中国.镇江体育舞蹈全国公开赛将于2017年10月2日至3日在镇江市大港体育馆召开。作为中国体育舞蹈联合会三级赛事，参加相关规范组别选手将列入《中国体育舞蹈联合会竞赛积分系统》获得年度积分，规范组别：职业组、A组、青年组、壮年I组、少年II组、少年I组标准舞和拉丁舞，准入条件按照CDSF标准。","compete_name":"2017年中国-镇江体育舞蹈全国公开赛","compete_id":"239","picture_app":"/464/0/0/afb34445c2532db94521c1d414e27f84.png","compete_trailer":"","dot_num":"14","picture":"http://store.cdsf.org.cn/picture//464/0/0/afb34445c2532db94521c1d414e27f84.png"}],"menu":["开幕式","H组决赛","G组决赛","F组决赛","E组决赛","D组决赛","C组决赛","B组决赛","A组决赛","I组决赛"]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private ArrayList<CompeteBean> compete;
        private ArrayList<String>      menu;

        public ArrayList<CompeteBean> getCompete() {
            return compete;
        }

        public void setCompete(ArrayList<CompeteBean> compete) {
            this.compete = compete;
        }

        public ArrayList<String> getMenu() {
            return menu;
        }

        public void setMenu(ArrayList<String> menu) {
            this.menu = menu;
        }

        public static class CompeteBean {
            /**
             * match_id : 51
             * name : 2017年中国.镇江体育舞蹈全国公开赛​10.3
             * activity_id : A20170928000003p
             * begin_time : 2017-10-03 08:00:00
             * end_time : 2017-10-03 23:00:00
             * brief :       2017年中国.镇江体育舞蹈全国公开赛将于2017年10月2日至3日在镇江市大港体育馆召开。作为中国体育舞蹈联合会三级赛事，参加相关规范组别选手将列入《中国体育舞蹈联合会竞赛积分系统》获得年度积分，规范组别：职业组、A组、青年组、壮年I组、少年II组、少年I组标准舞和拉丁舞，准入条件按照CDSF标准。
             * compete_name : 2017年中国-镇江体育舞蹈全国公开赛
             * compete_id : 239
             * picture_app : /464/0/0/afb34445c2532db94521c1d414e27f84.png
             * compete_trailer :
             * dot_num : 14
             * picture : http://store.cdsf.org.cn/picture//464/0/0/afb34445c2532db94521c1d414e27f84.png
             */

            private String match_id;
            private String name;
            private String activity_id;
            private String begin_time;
            private String end_time;
            private String brief;
            private String compete_name;
            private String compete_id;
            private String picture_app;
            private String compete_trailer;
            private String dot_num;
            private String picture;

            public String getMatch_id() {
                return match_id;
            }

            public void setMatch_id(String match_id) {
                this.match_id = match_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getActivity_id() {
                return activity_id;
            }

            public void setActivity_id(String activity_id) {
                this.activity_id = activity_id;
            }

            public String getBegin_time() {
                return begin_time;
            }

            public void setBegin_time(String begin_time) {
                this.begin_time = begin_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getBrief() {
                return brief;
            }

            public void setBrief(String brief) {
                this.brief = brief;
            }

            public String getCompete_name() {
                return compete_name;
            }

            public void setCompete_name(String compete_name) {
                this.compete_name = compete_name;
            }

            public String getCompete_id() {
                return compete_id;
            }

            public void setCompete_id(String compete_id) {
                this.compete_id = compete_id;
            }

            public String getPicture_app() {
                return picture_app;
            }

            public void setPicture_app(String picture_app) {
                this.picture_app = picture_app;
            }

            public String getCompete_trailer() {
                return compete_trailer;
            }

            public void setCompete_trailer(String compete_trailer) {
                this.compete_trailer = compete_trailer;
            }

            public String getDot_num() {
                return dot_num;
            }

            public void setDot_num(String dot_num) {
                this.dot_num = dot_num;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }
        }
    }
}
