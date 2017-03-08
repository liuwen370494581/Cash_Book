package star.liuwen.com.cash_books.bean;

import java.io.Serializable;

/**
 * Created by liuwen on 2017/1/13.
 */
public class PlanSaveMoneyModel implements Serializable {

    private long id;
    private String planName;
    private String planPeopleNumber;
    private int url;
    private String add;
    private String message;


    public PlanSaveMoneyModel() {
    }

    public PlanSaveMoneyModel(String planName, String planPeopleNumber, int url, String add, String message) {
        this.planName = planName;
        this.planPeopleNumber = planPeopleNumber;
        this.url = url;
        this.add = add;
        this.message = message;
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
}
