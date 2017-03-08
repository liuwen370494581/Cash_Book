package star.liuwen.com.cash_books.PieChart;

import java.io.Serializable;

public class PieChartDetail implements Serializable {

    private String perAmount;
    private String perBase;

    public String getPerAmount() {
        return perAmount;
    }

    public void setPerAmount(String perAmount) {
        this.perAmount = perAmount;
    }

    public String getPerBase() {
        return perBase;
    }

    public void setPerBase(String perBase) {
        this.perBase = perBase;
    }


}
