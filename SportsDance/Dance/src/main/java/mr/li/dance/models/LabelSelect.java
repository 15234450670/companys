package mr.li.dance.models;

import com.google.gson.annotations.SerializedName;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/18 0018
 * 描述:
 * 修订历史:
 */
public class LabelSelect extends BaseResponse{



    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        @SerializedName("7")
        private _$7Bean _$7;
        @SerializedName("9")
        private _$9Bean _$9;
        @SerializedName("8")
        private _$8Bean _$8;

        public _$7Bean get_$7() {
            return _$7;
        }

        public void set_$7(_$7Bean _$7) {
            this._$7 = _$7;
        }

        public _$9Bean get_$9() {
            return _$9;
        }

        public void set_$9(_$9Bean _$9) {
            this._$9 = _$9;
        }

        public _$8Bean get_$8() {
            return _$8;
        }

        public void set_$8(_$8Bean _$8) {
            this._$8 = _$8;
        }

        public static class _$7Bean {
            /**
             * id : 7
             * name : 资讯管理栏目1
             * 12 : {"id":"12","name":"资讯管理栏目1标签3"}
             * 11 : {"id":"11","name":"资讯管理栏目1标签2"}
             * 10 : {"id":"10","name":"资讯管理栏目1标签1"}
             */

            private String id;
            private String   name;
            @SerializedName("12")
            private _$12Bean _$12;
            @SerializedName("11")
            private _$11Bean _$11;
            @SerializedName("10")
            private _$10Bean _$10;

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

            public _$12Bean get_$12() {
                return _$12;
            }

            public void set_$12(_$12Bean _$12) {
                this._$12 = _$12;
            }

            public _$11Bean get_$11() {
                return _$11;
            }

            public void set_$11(_$11Bean _$11) {
                this._$11 = _$11;
            }

            public _$10Bean get_$10() {
                return _$10;
            }

            public void set_$10(_$10Bean _$10) {
                this._$10 = _$10;
            }

            public static class _$12Bean {
                /**
                 * id : 12
                 * name : 资讯管理栏目1标签3
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

            public static class _$11Bean {
                /**
                 * id : 11
                 * name : 资讯管理栏目1标签2
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

            public static class _$10Bean {
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

        public static class _$9Bean {
            /**
             * id : 9
             * name : 资讯管理栏目3
             * 55 : {"id":"55","name":"资讯管理栏目3标签1"}
             * 56 : {"id":"56","name":"资讯管理栏目3标签3"}
             */

            private String id;
            private String   name;
            @SerializedName("55")
            private _$55Bean _$55;
            @SerializedName("56")
            private _$56Bean _$56;

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

            public _$55Bean get_$55() {
                return _$55;
            }

            public void set_$55(_$55Bean _$55) {
                this._$55 = _$55;
            }

            public _$56Bean get_$56() {
                return _$56;
            }

            public void set_$56(_$56Bean _$56) {
                this._$56 = _$56;
            }

            public static class _$55Bean {
                /**
                 * id : 55
                 * name : 资讯管理栏目3标签1
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

            public static class _$56Bean {
                /**
                 * id : 56
                 * name : 资讯管理栏目3标签3
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

        public static class _$8Bean {
            /**
             * id : 8
             * name : 资讯管理栏目2
             * 54 : {"id":"54","name":"资讯管理栏目2标签2"}
             * 53 : {"id":"53","name":"资讯管理栏目2标签1"}
             */

            private String id;
            private String   name;
            @SerializedName("54")
            private _$54Bean _$54;
            @SerializedName("53")
            private _$53Bean _$53;

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

            public _$54Bean get_$54() {
                return _$54;
            }

            public void set_$54(_$54Bean _$54) {
                this._$54 = _$54;
            }

            public _$53Bean get_$53() {
                return _$53;
            }

            public void set_$53(_$53Bean _$53) {
                this._$53 = _$53;
            }

            public static class _$54Bean {
                /**
                 * id : 54
                 * name : 资讯管理栏目2标签2
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

            public static class _$53Bean {
                /**
                 * id : 53
                 * name : 资讯管理栏目2标签1
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
}
