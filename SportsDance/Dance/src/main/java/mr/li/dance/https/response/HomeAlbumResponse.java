package mr.li.dance.https.response;

import java.util.ArrayList;

import mr.li.dance.models.AlbumInfo;
import mr.li.dance.models.LabelInfo;

/**
 * Created by Lixuewei on 2017/5/26.
 */

public class HomeAlbumResponse extends BaseResponse {
    private ArrayList<AlbumInfo> data;
    private ArrayList<LabelInfo> label;

    public ArrayList<LabelInfo> getLabel() {
        return label;
    }

    public void setLabel(ArrayList<LabelInfo> label) {
        this.label = label;
    }

    public ArrayList<AlbumInfo> getData() {
        return data;
    }

    public void setData(ArrayList<AlbumInfo> data) {
        this.data = data;
    }
}
