package mr.li.dance.models;

import java.util.ArrayList;
import java.util.List;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/5/18 0018
 * 描述:
 * 修订历史:
 */
public class LiveCard extends BaseResponse {

    /**
     * data : {"menu":[{"start_time":"16:26:33","title":"11"},{"start_time":"16:26:40","title":"12"}],"menu_sum":"2"}
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
         * menu : [{"start_time":"16:26:33","title":"11"},{"start_time":"16:26:40","title":"12"}]
         * menu_sum : 2
         */

        private String         menu_sum;
        private List<MenuBean> menu;

        public String getMenu_sum() {
            return menu_sum;
        }

        public void setMenu_sum(String menu_sum) {
            this.menu_sum = menu_sum;
        }

        public List<MenuBean> getMenu() {
            return menu;
        }

        public void setMenu(ArrayList<MenuBean> menu) {
            this.menu = menu;
        }

        public static class MenuBean {
            /**
             * start_time : 16:26:33
             * title : 11
             */

            private String start_time;
            private String title;

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
