package mr.li.dance.models;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/24 0024
 * 描述:
 * 修订历史:
 */
public class PersonInfo  {

        /**
         * user_num : 0
         * attention_num : 0
         * photoClass_num : 0
         * dynamic_num : 0
         */

        private int user_num;
        private int attention_num;
        private int photoClass_num;
        private int dynamic_num;

        public int getUser_num() {
            return user_num;
        }

        public void setUser_num(int user_num) {
            this.user_num = user_num;
        }

        public int getAttention_num() {
            return attention_num;
        }

        public void setAttention_num(int attention_num) {
            this.attention_num = attention_num;
        }

        public int getPhotoClass_num() {
            return photoClass_num;
        }

        public void setPhotoClass_num(int photoClass_num) {
            this.photoClass_num = photoClass_num;
        }

        public int getDynamic_num() {
            return dynamic_num;
        }

        public void setDynamic_num(int dynamic_num) {
            this.dynamic_num = dynamic_num;
        }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "user_num=" + user_num +
                ", attention_num=" + attention_num +
                ", photoClass_num=" + photoClass_num +
                ", dynamic_num=" + dynamic_num +
                '}';
    }
}
