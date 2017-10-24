package mr.li.dance.https.response;

import mr.li.dance.models.PersonInfo;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/24 0024
 * 描述:
 * 修订历史:
 */
public class PersonLookAndFansResponse extends BaseResponse {

    /**
     * data : {"user_num":1,"attention_num":4,"photoClass_num":0,"dynamic_num":14}
     */

    private PersonInfo data;

    public PersonInfo getData() {
        return data;
    }

    public void setData(PersonInfo data) {
        this.data = data;
    }



}
