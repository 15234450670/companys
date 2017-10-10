package mr.li.dance.https.response;

import java.util.ArrayList;

import mr.li.dance.models.TeachInfo;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/10 0010
 * 描述:
 * 修订历史:
 */
public class TeacherRespone extends BaseResponse {
    private ArrayList<TeachInfo> data;

    public ArrayList<TeachInfo> getData() {
        return data;
    }

    public void setData(ArrayList<TeachInfo> data) {
        this.data = data;
    }
}
