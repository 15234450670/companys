package mr.li.dance.models;

import java.util.List;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2018/3/22 0022
 * 描述:
 * 修订历史:
 */
public class MoreNewResponse extends BaseResponse {

    private List<TeachInfo> data;

    public List<TeachInfo> getData() {
        return data;
    }

    public void setData(List<TeachInfo> data) {
        this.data = data;
    }


}
