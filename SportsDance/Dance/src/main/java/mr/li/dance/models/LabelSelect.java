package mr.li.dance.models;

import com.google.gson.annotations.SerializedName;

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


    /**
     * data : {"guanxi":{"7":[{"id":10,"name":"资讯管理栏目1标签1"},{"id":11,"name":"资讯管理栏目1标签2"},{"id":12,"name":"资讯管理栏目1标签3"}],"9":[{"id":55,"name":"资讯管理栏目3标签1"},{"id":56,"name":"资讯管理栏目3标签3"}],"8":[{"id":53,"name":"资讯管理栏目2标签1"},{"id":54,"name":"资讯管理栏目2标签2"}]},"label":{"7":"资讯管理栏目1","9":"资讯管理栏目3","8":"资讯管理栏目2"}}
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
         * guanxi : {"7":[{"id":10,"name":"资讯管理栏目1标签1"},{"id":11,"name":"资讯管理栏目1标签2"},{"id":12,"name":"资讯管理栏目1标签3"}],"9":[{"id":55,"name":"资讯管理栏目3标签1"},{"id":56,"name":"资讯管理栏目3标签3"}],"8":[{"id":53,"name":"资讯管理栏目2标签1"},{"id":54,"name":"资讯管理栏目2标签2"}]}
         * label : {"7":"资讯管理栏目1","9":"资讯管理栏目3","8":"资讯管理栏目2"}
         */

        private GuanxiBean guanxi;
        private LabelBean label;

        public GuanxiBean getGuanxi() {
            return guanxi;
        }

        public void setGuanxi(GuanxiBean guanxi) {
            this.guanxi = guanxi;
        }

        public LabelBean getLabel() {
            return label;
        }

        public void setLabel(LabelBean label) {
            this.label = label;
        }

        public static class GuanxiBean {
            @SerializedName("7")
            private List<_$7Bean> _$7;
            @SerializedName("9")
            private List<_$9Bean> _$9;
            @SerializedName("8")
            private List<_$8Bean> _$8;

            public List<_$7Bean> get_$7() {
                return _$7;
            }

            public void set_$7(List<_$7Bean> _$7) {
                this._$7 = _$7;
            }

            public List<_$9Bean> get_$9() {
                return _$9;
            }

            public void set_$9(List<_$9Bean> _$9) {
                this._$9 = _$9;
            }

            public List<_$8Bean> get_$8() {
                return _$8;
            }

            public void set_$8(List<_$8Bean> _$8) {
                this._$8 = _$8;
            }

            public static class _$7Bean {
                /**
                 * id : 10
                 * name : 资讯管理栏目1标签1
                 */

                private int id;
                private String name;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class _$9Bean {
                /**
                 * id : 55
                 * name : 资讯管理栏目3标签1
                 */

                private int id;
                private String name;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class _$8Bean {
                /**
                 * id : 53
                 * name : 资讯管理栏目2标签1
                 */

                private int id;
                private String name;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
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

        public static class LabelBean {
            /**
             * 7 : 资讯管理栏目1
             * 9 : 资讯管理栏目3
             * 8 : 资讯管理栏目2
             */

            @SerializedName("7")
            private String _$7;
            @SerializedName("9")
            private String _$9;
            @SerializedName("8")
            private String _$8;

            public String get_$7() {
                return _$7;
            }

            public void set_$7(String _$7) {
                this._$7 = _$7;
            }

            public String get_$9() {
                return _$9;
            }

            public void set_$9(String _$9) {
                this._$9 = _$9;
            }

            public String get_$8() {
                return _$8;
            }

            public void set_$8(String _$8) {
                this._$8 = _$8;
            }
        }
    }
}
