package mr.li.dance.models;

import java.util.ArrayList;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/26 0026
 * 描述:
 * 修订历史:
 */
public class LabelSeekInfoVideo extends BaseResponse{


    /**
     * data : {"arr":[{"name":"第13届全国青少年体育舞蹈锦标赛--青年组单项恰恰决赛","video":"http://player.youku.com/player.php/Type/Folder/Fid/25935700/Ob/1/sid/XMTI5MTg0NjU0OA==/v.swf","picture":"/683/1/0/ce62eb47b0fc4132365c2bb0f872fe66.jpg","video_unique":""}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private ArrayList<Video> arr;

        public ArrayList<Video> getArr() {
            return arr;
        }

        public void setArr(ArrayList<Video> arr) {
            this.arr = arr;
        }


    }
}
