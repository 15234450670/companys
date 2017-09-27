package mr.li.dance.models;

import java.util.ArrayList;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/26 0026
 * 描述:
 * 修订历史:
 */
public class LabelSelectMusicInfo {

    /**
     * errorCode : 200
     * data : {"arr":[{"title":"郭宇琪4","img_fm":"http://store.cdsf.org.cn/picture//644/1/0/9f7b937d8fe46e8364d3f586fd3de109.jpg","id":"4"}]}
     */

    private int errorCode;
    private DataBean data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private ArrayList<MusicInfo> arr;

        public ArrayList<MusicInfo> getArr() {
            return arr;
        }

        public void setArr(ArrayList<MusicInfo> arr) {
            this.arr = arr;
        }


    }
}
