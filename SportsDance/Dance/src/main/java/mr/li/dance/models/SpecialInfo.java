package mr.li.dance.models;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/9/28 0028
 * 描述:
 * 修订历史:
 */
public class SpecialInfo extends BaseHomeItem {
    private String id;
    private String picture;
    private String name;
    private String compete_name;
    private String video_unique;
    private String start_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompete_name() {
        return compete_name;
    }

    public void setCompete_name(String compete_name) {
        this.compete_name = compete_name;
    }

    public String getVideo_unique() {
        return video_unique;
    }

    public void setVideo_unique(String video_unique) {
        this.video_unique = video_unique;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
}
