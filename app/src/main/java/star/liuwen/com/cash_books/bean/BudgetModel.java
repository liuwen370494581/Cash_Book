package star.liuwen.com.cash_books.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by liuwen on 2017/5/12.
 * 预算Model
 */
@Entity
public class BudgetModel implements Serializable {
    @Id(autoincrement = true)
    private static final long serialVersionUID = 1L;//为了避免反序列化失败 加的一个uid
    private Long id;//id
    private boolean openBudget;//是否开启了预算
    private String budgetMoney;//月预算金额
    private int budgetRemainMoney;// 剩下的金额


    @Generated(hash = 359100778)
    public BudgetModel(Long id, boolean openBudget, String budgetMoney,
            int budgetRemainMoney) {
        this.id = id;
        this.openBudget = openBudget;
        this.budgetMoney = budgetMoney;
        this.budgetRemainMoney = budgetRemainMoney;
    }

    @Generated(hash = 986427255)
    public BudgetModel() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isOpenBudget() {
        return openBudget;
    }

    public void setOpenBudget(boolean openBudget) {
        this.openBudget = openBudget;
    }

    public String getBudgetMoney() {
        return budgetMoney;
    }

    public void setBudgetMoney(String budgetMoney) {
        this.budgetMoney = budgetMoney;
    }

    public int getBudgetRemainMoney() {
        return budgetRemainMoney;
    }

    public void setBudgetRemainMoney(int budgetRemainMoney) {
        this.budgetRemainMoney = budgetRemainMoney;
    }

    public boolean getOpenBudget() {
        return this.openBudget;
    }

   
}
