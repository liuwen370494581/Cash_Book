package star.liuwen.com.cash_books.bean;

/**
 * Created by liuwen on 2017/1/5.
 */
public class ZhiChuModel {

    private long id;
    private int url;
    private String name;


    public ZhiChuModel(long id, int url, String name) {
        this.id = id;
        this.url = url;
        this.name = name;
    }

    public ZhiChuModel(int url, String name) {
        this.url = url;
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
