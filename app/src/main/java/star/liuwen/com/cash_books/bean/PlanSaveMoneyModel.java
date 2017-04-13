package star.liuwen.com.cash_books.bean;

import java.io.Serializable;

/**
 * Created by liuwen on 2017/1/13.
 */
public class PlanSaveMoneyModel implements Serializable {
    private static final long serialVersionUID = 1L;//为了避免反序列化失败 加的一个uid
    private long id;
    private String planName;
    private String planPeopleNumber;
    private int url;
    private String add;
    private String message;
    private int color;//为了重用model 在这里加了一个和本身无关的字段 颜色字段
    private int walletUrl;//增加的这个字段为了显示在钱包页面的图片不一致


    public PlanSaveMoneyModel() {
    }

    public PlanSaveMoneyModel(String planName, String planPeopleNumber, int url, String add, String message) {
        this.planName = planName;
        this.planPeopleNumber = planPeopleNumber;
        this.url = url;
        this.add = add;
        this.message = message;
    }

    public PlanSaveMoneyModel(int url, String add, String message, String planName, int color, int walletUrl) {
        this.url = url;
        this.add = add;
        this.message = message;
        this.planName = planName;
        this.color = color;
        this.walletUrl = walletUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanPeopleNumber() {
        return planPeopleNumber;
    }

    public void setPlanPeopleNumber(String planPeopleNumber) {
        this.planPeopleNumber = planPeopleNumber;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getWalletUrl() {
        return walletUrl;
    }

    public void setWalletUrl(int walletUrl) {
        this.walletUrl = walletUrl;
    }
}
