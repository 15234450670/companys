package mr.li.dance.https.response;

import java.util.ArrayList;

import mr.li.dance.models.SpecialInfo;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/28 0028
 * 描述:
 * 修订历史:
 */
public class SpecialResponse extends BaseResponse {

    private ArrayList<SpecialInfo> data;

    public ArrayList<SpecialInfo> getData() {
        return data;
    }

    public void setData(ArrayList<SpecialInfo> data) {
        this.data = data;
    }



}
