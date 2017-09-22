package mr.li.dance.models;

import java.util.List;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/18 0018
 * 描述:
 * 修订历史:
 */
public class LabelSelect extends BaseResponse{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * class_name : 资讯管理栏目1
         * list : [{"id":"10","name":"资讯管理栏目1标签1"},{"id":"11","name":"资讯管理栏目1标签2"},{"id":"12","name":"资讯管理栏目1标签3"}]
         */

        private String class_name;
        private List<ListBean> list;
        public boolean allCheck = false;

        public boolean isAllCheck() {
            return allCheck;
        }
        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 10
             * name : 资讯管理栏目1标签1
             */

            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
