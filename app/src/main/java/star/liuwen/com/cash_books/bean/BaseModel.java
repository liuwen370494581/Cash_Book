package star.liuwen.com.cash_books.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by liuwen on 2017/4/6.
 * 用于payShow的页面数据展示
 */
@Entity
public class BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;//为了避免反序列化失败 加的一个uid
    @Id(autoincrement = true)
    private Long id; //ID
    private int url; //图标
    private String name; //余额变更
    private String BalanceChange;//平账支出  这个是余额变更需要用的 字段
    private double money;//金额
    private String type;//是否是choiceAccount的数据 还是AccountModel的数据
    private String zhiChuShouRuType; //是否是支出或者是存款
    private String timeMinSec;//时间 格式 2017-4-16-13-15-15;
    private String AccountType;//用来区别是哪个账户 比如是信用卡账户或者是现金账户 这个是消费或者支出需要用的字段
    private long choiceAccountId;//用来标识账户的唯一性 因为之前都是以现金账户或者支付宝账户来查询 这就会形成了一个bug 就是在添加的一个相同的账户 同样的数据会显示在同一个账户中 不严谨
    private String date;//用来标识查询整月的消费和支出记录

    public BaseModel() {
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getChoiceAccountId() {
        return this.choiceAccountId;
    }

    public void setChoiceAccountId(long choiceAccountId) {
        this.choiceAccountId = choiceAccountId;
    }

    public String getAccountType() {
        return this.AccountType;
    }

    public void setAccountType(String AccountType) {
        this.AccountType = AccountType;
    }

    public String getTimeMinSec() {
        return this.timeMinSec;
    }

    public void setTimeMinSec(String timeMinSec) {
        this.timeMinSec = timeMinSec;
    }

    public String getZhiChuShouRuType() {
        return this.zhiChuShouRuType;
    }

    public void setZhiChuShouRuType(String zhiChuShouRuType) {
        this.zhiChuShouRuType = zhiChuShouRuType;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMoney() {
        return this.money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getBalanceChange() {
        return this.BalanceChange;
    }

    public void setBalanceChange(String BalanceChange) {
        this.BalanceChange = BalanceChange;
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1780390684)
    public BaseModel(Long id, int url, String name, String BalanceChange, double money, String type,
                     String zhiChuShouRuType, String timeMinSec, String AccountType, long choiceAccountId, String date) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.BalanceChange = BalanceChange;
        this.money = money;
        this.type = type;
        this.zhiChuShouRuType = zhiChuShouRuType;
        this.timeMinSec = timeMinSec;
        this.AccountType = AccountType;
        this.choiceAccountId = choiceAccountId;
        this.date = date;
    }


}
