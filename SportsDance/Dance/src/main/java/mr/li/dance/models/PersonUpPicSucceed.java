package mr.li.dance.models;

import java.util.List;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/11/2 0002
 * 描述:
 * 修订历史:
 */
public class PersonUpPicSucceed extends BaseResponse {

    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
