package mr.li.dance.https.response;

/**
 * Created by Miaoshuai on 10/30/17.
 */

public class GetDynamicIdResponse extends BaseResponse {
    private String data;
    private int dynamic_id;

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public int getDynamic_id() {
        return dynamic_id;
    }

    public void setDynamic_id(int dynamic_id) {
        this.dynamic_id = dynamic_id;
    }
}
