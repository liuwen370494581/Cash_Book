package star.liuwen.com.cash_books.bean;

import java.io.Serializable;

/**
 * Created by liuwen on 2017/2/7.
 */
public class ReportsDetailModel implements Serializable {
    private static final long serialVersionUID = 1L;//为了避免反序列化失败 加的一个uid
    private long id;
    private String consumeName;
    private int url;
    private String consumeMoney;
    private float consumePercent;

    public ReportsDetailModel() {
    }

    public ReportsDetailModel(String consumeName, int url, String consumeMoney, float consumePercent) {
        this.consumeName = consumeName;
        this.url = url;
        this.consumeMoney = consumeMoney;
        this.consumePercent = consumePercent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConsumeName() {
        return consumeName;
    }

    public void setConsumeName(String consumeName) {
        this.consumeName = consumeName;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public String getConsumeMoney() {
        return consumeMoney;
    }

    public void setConsumeMoney(String consumeMoney) {
        this.consumeMoney = consumeMoney;
    }

    public float getConsumePercent() {
        return consumePercent;
    }

    public void setConsumePercent(float consumePercent) {
        this.consumePercent = consumePercent;
    }
}
