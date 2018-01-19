package mr.li.dance.models;

/**
 * 作者: SuiFeng
 * 版本:
 * 创建日期:2017/10/11 0011
 * 描述:
        * 修订历史:
        */
public  class MenuBean {
    /**
     * title : 赛事预告1
     */

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "MenuBean{" +
                "title='" + title + '\'' +
                '}';
    }
}