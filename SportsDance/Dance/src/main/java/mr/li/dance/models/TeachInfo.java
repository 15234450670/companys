package mr.li.dance.models;

import java.io.Serializable;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/20 0020
 * 描述:
 * 修订历史:
 */
public class TeachInfo extends BaseHomeItem implements Serializable {
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
    private String picture;//筛选  图片
    public  String name;
    private String img_fm;


    @Override
    public String getImg_fm() {
        return img_fm;
    }

    @Override
    public void setImg_fm(String img_fm) {
        this.img_fm = img_fm;
    }

    @Override
    public String getPicture() {
        return picture;
    }

    @Override
    public void setPicture(String picture) {
        this.picture = picture;
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
