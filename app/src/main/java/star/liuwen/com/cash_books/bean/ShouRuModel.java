package star.liuwen.com.cash_books.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by liuwen on 2017/3/8.
 */
@Entity
public class ShouRuModel {
    @Id(autoincrement = true)
    private long id;
    private int url;
    private String name;
    private String color;
    public String getColor() {
        return this.color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getUrl() {
        return this.url;
    }
    public void setUrl(int url) {
        this.url = url;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Generated(hash = 748550041)
    public ShouRuModel(long id, int url, String name, String color) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.color = color;
    }
    @Generated(hash = 835496406)
    public ShouRuModel() {
    }


    public ShouRuModel(int url, String name, String color) {
        this.url = url;
        this.name = name;
        this.color = color;
    }
}
