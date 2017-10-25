package mr.li.dance.models;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/25 0025
 * 描述:
 * 修订历史:
 */
public class ReportInfo extends BaseResponse {

    /**
     * data : 举报成功
     */

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
