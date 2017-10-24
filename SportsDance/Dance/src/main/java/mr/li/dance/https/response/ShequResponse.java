package mr.li.dance.https.response;

import java.util.ArrayList;

import mr.li.dance.models.ShequInfo;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/23 0023
 * 描述:
 * 修订历史:
 */
public class ShequResponse extends BaseResponse {
    private ArrayList<ShequInfo> data;

    public ArrayList<ShequInfo> getData() {
        return data;
    }

    public void setData(ArrayList<ShequInfo> data) {
        this.data = data;
    }
}
