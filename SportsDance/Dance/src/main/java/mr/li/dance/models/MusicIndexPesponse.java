package mr.li.dance.models;

import java.util.ArrayList;

import mr.li.dance.https.response.BaseResponse;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/8/16 0016
 * 描述:
 * 修订历史:
 */
public class MusicIndexPesponse extends BaseResponse {
    private ArrayList<MusicRecAppBean> data;

    public ArrayList<MusicRecAppBean> getData() {
        return data;
    }

    public void setData(ArrayList<MusicRecAppBean> data) {
        this.data = data;
    }
}
