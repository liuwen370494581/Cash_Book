package star.liuwen.com.cash_books.bean;

/**
 * Created by liuwen on 2017/1/16.
 */
public class IndexModel {

    public String topc;
    public String name;

    public IndexModel(String name) {
        this.name = name;
    }

    public IndexModel(String topc, String name) {
        this.name = name;
        this.topc = topc;
    }


}
