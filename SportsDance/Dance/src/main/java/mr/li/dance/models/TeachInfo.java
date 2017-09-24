package mr.li.dance.models;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/20 0020
 * 描述:
 * 修订历史:
 */
public class TeachInfo extends BaseHomeItem {
    /**
     * id : 1
     * title : 标题一
     * img : http://store.cdsf.org.cn/picture//212/0/0/2b04df3ecc1d94afddff082d139c6f15.jpg
     * described :
     */

    private String id;
    private String title;
    private String img;
    private String described;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescribed() {
        return described;
    }

    public void setDescribed(String described) {
        this.described = described;
    }
}