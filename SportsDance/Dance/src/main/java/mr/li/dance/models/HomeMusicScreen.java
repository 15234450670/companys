package mr.li.dance.models;

import java.util.ArrayList;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/27 0027
 * 描述:
 * 修订历史:
 */
public class HomeMusicScreen extends BaseResponse {

    private ArrayList<MusicInfo> data;

    public ArrayList<MusicInfo> getData() {
        return data;
    }

    public void setData(ArrayList<MusicInfo> data) {
        this.data = data;
    }



}
