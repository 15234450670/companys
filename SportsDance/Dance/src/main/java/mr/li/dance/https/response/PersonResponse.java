package mr.li.dance.https.response;

import java.util.List;

import mr.li.dance.models.PersonItemInfo;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/24 0024
 * 描述:
 * 修订历史:
 */
public class PersonResponse extends BaseResponse {


    private List<PersonItemInfo> data;
    public List<PersonItemInfo> getData() {
        return data;
    }

    public void setData(List<PersonItemInfo> data) {
        this.data = data;
    }

}
