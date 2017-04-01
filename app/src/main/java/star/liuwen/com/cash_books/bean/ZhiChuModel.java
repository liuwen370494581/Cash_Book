package star.liuwen.com.cash_books.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by liuwen on 2017/3/8.
 */
@Entity
public class ZhiChuModel {
    @Id(autoincrement = true)
    private long id;
    private int url;
    private String names;
    public String getNames() {
        return this.names;
    }
    public void setNames(String names) {
        this.names = names;
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
    @Generated(hash = 196369667)
    public ZhiChuModel(long id, int url, String names) {
        this.id = id;
        this.url = url;
        this.names = names;
    }
    @Generated(hash = 1865233734)
    public ZhiChuModel() {
    }


    public ZhiChuModel(int url, String names) {
        this.url = url;
        this.names = names;
    }
}
