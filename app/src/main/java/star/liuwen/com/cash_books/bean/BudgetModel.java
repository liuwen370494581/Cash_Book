package star.liuwen.com.cash_books.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by liuwen on 2017/5/12.
 * 预算Model
 */
@Entity
public class BudgetModel implements Serializable {

    private static final long serialVersionUID = 1L;//为了避免反序列化失败 加的一个uid

    @Id(autoincrement = true)
    private Long id;//id
    private boolean openBudget;//是否开启了预算
    private String budgetMoney;//月预算金额
    private int budgetRemainMoney;// 百分比
    private String budgetDescription;//预算描述
    public String getBudgetDescription() {
        return this.budgetDescription;
    }
    public void setBudgetDescription(String budgetDescription) {
        this.budgetDescription = budgetDescription;
    }
    public int getBudgetRemainMoney() {
        return this.budgetRemainMoney;
    }
    public void setBudgetRemainMoney(int budgetRemainMoney) {
        this.budgetRemainMoney = budgetRemainMoney;
    }
    public String getBudgetMoney() {
        return this.budgetMoney;
    }
    public void setBudgetMoney(String budgetMoney) {
        this.budgetMoney = budgetMoney;
    }
    public boolean getOpenBudget() {
        return this.openBudget;
    }
    public void setOpenBudget(boolean openBudget) {
        this.openBudget = openBudget;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1579486143)
    public BudgetModel(Long id, boolean openBudget, String budgetMoney,
            int budgetRemainMoney, String budgetDescription) {
        this.id = id;
        this.openBudget = openBudget;
        this.budgetMoney = budgetMoney;
        this.budgetRemainMoney = budgetRemainMoney;
        this.budgetDescription = budgetDescription;
    }
    @Generated(hash = 986427255)
    public BudgetModel() {
    }



}
