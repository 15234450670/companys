package mr.li.dance.models;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/12/12 0012
 * 描述:
 * 修订历史:
 */
public class CheckLogin {

    /**
     * errorCode : 103
     * data : 登录信息无效
     */

    private int errorCode;
    private String data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
