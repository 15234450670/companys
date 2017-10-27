package mr.li.dance.https.response;

import mr.li.dance.models.DetailsInfo;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/27 0027
 * 描述:
 * 修订历史:
 */
public class SheQuDetailsResponse extends BaseResponse{

    /**
     * data : {"id":"78","uid":"29883","title":"GG","content":"喇叭","dynamic_time":"2017-10-23 10:05:25","type":"1","upvote":"-4","view_count":"14","comment_count":"3","is_upvote":2,"user":[{"username":"1","picture_src":"http://"}],"address":["http://store.cdsf.org.cn/picture//558/1/0/dde0a7101b933cb89840f62dbaaa0642.png"],"comm":[{"id":"3","content":"Yyyy","comment_time":"2017-10-24 21:22:47","userid":"29812","address":[],"user":[{"username":"空空小僧","picture_src":"https://q.qlogo.cn/qqapp/1105479969/E053CB491F719084E02954277BD2F246/100"}],"comd":[]},{"id":"2","content":"嗯呢","comment_time":"2017-10-16 10:28:29","userid":"29883","address":[],"user":[{"username":"1","picture_src":null}],"comd":[]},{"id":"1","content":"评论","comment_time":"2017-10-16 10:05:59","userid":"29883","address":[],"user":[{"username":"1","picture_src":null}],"comd":[]}]}
     */

    private DetailsInfo data;

    public DetailsInfo getData() {
        return data;
    }

    public void setData(DetailsInfo data) {
        this.data = data;
    }


}
