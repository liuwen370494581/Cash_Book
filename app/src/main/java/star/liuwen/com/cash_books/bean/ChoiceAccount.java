package star.liuwen.com.cash_books.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by liuwen on 2017/1/18.
 */
@Entity
public class ChoiceAccount implements Serializable {
    private static final long serialVersionUID = 1L;//为了避免反序列化失败 加的一个uid
    @Id(autoincrement = true)
    private Long id;
    private int url;//选择账户的url
    private String accountName;//账户名
    private double money;//金额(其他账户的余额)(信用卡的额度)
    private double Debt;//欠款（信用卡）
    private String DebtDate;//欠款日
    private String IssuingBank;//发卡行

    private int color;//颜色的字段
    public String mAccountType; //账户类型
    private double liuChu; //账户流出
    private double liuRu; //账户流入
    private String timeMinSec;// 消费和支出日期  2017-2-28-12-30-31
    private String date;

    public ChoiceAccount(int url, String accountName, double money, double debt, String accountType, int color) {
        this.url = url;
        this.accountName = accountName;
        this.money = money;
        Debt = debt;
        mAccountType = accountType;
        this.color = color;
    }

    public ChoiceAccount(int url, String accountName, double money, double debt, String debtDate, String issuingBank, int color, String accountType) {
        this.url = url;
        this.accountName = accountName;
        this.money = money;
        Debt = debt;
        DebtDate = debtDate;
        IssuingBank = issuingBank;
        this.color = color;
        mAccountType = accountType;
    }

    @Generated(hash = 562195584)
    public ChoiceAccount(Long id, int url, String accountName, double money, double Debt, String DebtDate, String IssuingBank, int color,
            String mAccountType, double liuChu, double liuRu, String timeMinSec, String date) {
        this.id = id;
        this.url = url;
        this.accountName = accountName;
        this.money = money;
        this.Debt = Debt;
        this.DebtDate = DebtDate;
        this.IssuingBank = IssuingBank;
        this.color = color;
        this.mAccountType = mAccountType;
        this.liuChu = liuChu;
        this.liuRu = liuRu;
        this.timeMinSec = timeMinSec;
        this.date = date;
    }

    @Generated(hash = 1743186768)
    public ChoiceAccount() {
    }


    public double getLiuRu() {
        return this.liuRu;
    }


    public void setLiuRu(double liuRu) {
        this.liuRu = liuRu;
    }


    public double getLiuChu() {
        return this.liuChu;
    }


    public void setLiuChu(double liuChu) {
        this.liuChu = liuChu;
    }


    public String getMAccountType() {
        return this.mAccountType;
    }


    public void setMAccountType(String mAccountType) {
        this.mAccountType = mAccountType;
    }


    public int getColor() {
        return this.color;
    }


    public void setColor(int color) {
        this.color = color;
    }


    public String getIssuingBank() {
        return this.IssuingBank;
    }


    public void setIssuingBank(String IssuingBank) {
        this.IssuingBank = IssuingBank;
    }


    public String getDebtDate() {
        return this.DebtDate;
    }


    public void setDebtDate(String DebtDate) {
        this.DebtDate = DebtDate;
    }


    public double getDebt() {
        return this.Debt;
    }


    public void setDebt(double Debt) {
        this.Debt = Debt;
    }


    public double getMoney() {
        return this.money;
    }


    public void setMoney(double money) {
        this.money = money;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    public int getUrl() {
        return this.url;
    }


    public void setUrl(int url) {
        this.url = url;
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getTimeMinSec() {
        return this.timeMinSec;
    }


    public void setTimeMinSec(String timeMinSec) {
        this.timeMinSec = timeMinSec;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
