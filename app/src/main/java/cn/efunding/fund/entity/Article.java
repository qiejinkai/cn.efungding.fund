package cn.efunding.fund.entity;

/**
 * Created by qiejinkai on 16/4/12.
 */
public class Article {

    private String url;
    private String imageUrl;

    public Article(String url, String imageUrl) {
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public Article() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
