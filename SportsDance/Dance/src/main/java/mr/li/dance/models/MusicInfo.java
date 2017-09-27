package mr.li.dance.models;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/22 0022
 * 描述:
 * 修订历史:
 */
public class MusicInfo extends BaseHomeItem{

    /**
     * id : 1
     * title : 郭宇琪1
     * create_time : 2017-08-28 14:57:50
     * create_man : heaodong
     * click_sum : 127
     * img_fm : /361/0/0/993483a831c9b960dc1f00ab75f8eca1.jpg
     * sort : 1
     */

    private String id;
    private String title;
    private String create_time;
    private String create_man;
    private String click_sum;
    private String img_fm;
    private String sort;
    private String inserttime;

    public String getInserttime() {
        return inserttime;
    }

    public void setInserttime(String inserttime) {
        this.inserttime = inserttime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCreate_man() {
        return create_man;
    }

    public void setCreate_man(String create_man) {
        this.create_man = create_man;
    }

    public String getClick_sum() {
        return click_sum;
    }

    public void setClick_sum(String click_sum) {
        this.click_sum = click_sum;
    }

    public String getImg_fm() {
        return img_fm;
    }

    public void setImg_fm(String img_fm) {
        this.img_fm = img_fm;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
