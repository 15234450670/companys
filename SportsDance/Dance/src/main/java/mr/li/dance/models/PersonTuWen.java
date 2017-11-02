package mr.li.dance.models;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/11/2 0002
 * 描述:
 * 修订历史:
 */
public class PersonTuWen extends BaseResponse {

    /**
     * data : 动态文字上传成功
     * dynamic_id : 191
     */

    private String data;
    private int dynamic_id;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getDynamic_id() {
        return dynamic_id;
    }

    public void setDynamic_id(int dynamic_id) {
        this.dynamic_id = dynamic_id;
    }
}
