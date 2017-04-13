package star.liuwen.com.cash_books.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by liuwen on 2017/2/21.
 */
@Entity
public class SaveMoneyPlanModel implements Serializable {
    private static final long serialVersionUID = 1L;//为了避免反序列化失败 加的一个uid
    @Id(autoincrement = true)
    private long id;
    private String platForm;//存款平台
    private double saveMoney;//计划存款多少
    private double yield;//收益率
    private String startTime;//起息时间
    private String endTime;//结束时间
    private String remark;//备注
    private String accountType;//账户类型
    private String AddSaveMoney; //完成的金额
    private int AddPercent; //进度
    private String finishPercent; //完成的进度
    private double add;//增加的金额



    public double getAdd() {
        return this.add;
    }
    public void setAdd(double add) {
        this.add = add;
    }
    public String getFinishPercent() {
        return this.finishPercent;
    }
    public void setFinishPercent(String finishPercent) {
        this.finishPercent = finishPercent;
    }
    public int getAddPercent() {
        return this.AddPercent;
    }
    public void setAddPercent(int AddPercent) {
        this.AddPercent = AddPercent;
    }
    public String getAddSaveMoney() {
        return this.AddSaveMoney;
    }
    public void setAddSaveMoney(String AddSaveMoney) {
        this.AddSaveMoney = AddSaveMoney;
    }
    public String getAccountType() {
        return this.accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getEndTime() {
        return this.endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getStartTime() {
        return this.startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public double getYield() {
        return this.yield;
    }
    public void setYield(double yield) {
        this.yield = yield;
    }
    public double getSaveMoney() {
        return this.saveMoney;
    }
    public void setSaveMoney(double saveMoney) {
        this.saveMoney = saveMoney;
    }
    public String getPlatForm() {
        return this.platForm;
    }
    public void setPlatForm(String platForm) {
        this.platForm = platForm;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Generated(hash = 2134359123)
    public SaveMoneyPlanModel(long id, String platForm, double saveMoney,
            double yield, String startTime, String endTime, String remark,
            String accountType, String AddSaveMoney, int AddPercent,
            String finishPercent, double add) {
        this.id = id;
        this.platForm = platForm;
        this.saveMoney = saveMoney;
        this.yield = yield;
        this.startTime = startTime;
        this.endTime = endTime;
        this.remark = remark;
        this.accountType = accountType;
        this.AddSaveMoney = AddSaveMoney;
        this.AddPercent = AddPercent;
        this.finishPercent = finishPercent;
        this.add = add;
    }
    @Generated(hash = 534086048)
    public SaveMoneyPlanModel() {
    }
  
}
