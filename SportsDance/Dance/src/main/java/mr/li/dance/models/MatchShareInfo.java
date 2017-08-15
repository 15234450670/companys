package mr.li.dance.models;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/15 0015
 * 描述:
 * 修订历史:
 */
public class MatchShareInfo extends BaseResponse {

    /**
     * data : http://work.cdsf.org.cn/abc?id=234
     */

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
