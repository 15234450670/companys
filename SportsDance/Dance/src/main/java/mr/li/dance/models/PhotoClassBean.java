package mr.li.dance.models;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/25 0025
 * 描述:
 * 修订历史:
 */
public  class PhotoClassBean {
    /**
     * id : 4
     * title : 相册四
     * img_fm :
     * compete_id : 1
     * photos : 0
     */

    private String id;
    private String title;
    private String img_fm;
    private String compete_id;
    private int    photos;

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

    public String getImg_fm() {
        return img_fm;
    }

    public void setImg_fm(String img_fm) {
        this.img_fm = img_fm;
    }

    public String getCompete_id() {
        return compete_id;
    }

    public void setCompete_id(String compete_id) {
        this.compete_id = compete_id;
    }

    public int getPhotos() {
        return photos;
    }

    public void setPhotos(int photos) {
        this.photos = photos;
    }
}