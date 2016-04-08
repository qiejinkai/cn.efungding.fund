package cn.efunding.fund.entity;

/**
 * Created by qiejinkai on 16/4/7.
 */
public class Banner {

    private String title;
    private String url;
    private int imageId;

    public Banner(String url, int imageId) {
        this.url = url;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
