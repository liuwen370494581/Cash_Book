package star.liuwen.com.cash_books.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by liuwen on 2017/3/8.
 */
@Entity
public class ShouRuModel {
    private static final long serialVersionUID = 1L;//为了避免反序列化失败 加的一个uid
    @Id(autoincrement = true)
    private long id;
    private int url;
    private String name;

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

    @Generated(hash = 1005587625)
    public ShouRuModel(long id, int url, String name) {
        this.id = id;
        this.url = url;
        this.name = name;
    }

    @Generated(hash = 835496406)
    public ShouRuModel() {
    }

}
