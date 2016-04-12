package cn.efunding.fund.entity;

/**
 * Created by qiejinkai on 16/4/7.
 */
public class YSubject {

    private String title ;

    private int percent;

    public YSubject(String title, int percent) {
        this.title = title;
        this.percent = percent;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public YSubject(String title) {
        this.title = title;
    }

    public YSubject() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
