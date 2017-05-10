package star.liuwen.com.cash_books.bean;

import java.io.Serializable;

/**
 * Created by liuwen on 2017/5/9.
 */
public class AccountWealthModel implements Serializable {

    private static final long serialVersionUID = 1L;//为了避免反序列化失败 加的一个uid

    private Long id;//id;
    private String wealthTitle;//财富标题：eg 新手专享 优选活期
    private String wealthName;//财富名称
    private double wealthPercent;//财富收益率
    private String wealthDay;//理财天数 eg：30天
    private String wealthBaoOrNewOrHot;//是否是爆款 或者是新品或者是热品
    private String wealthYearYield;//年化收益率
    private String wealthIsHuoqiOrDingQi;//是否是定期或者是活期
    private double wealthPlan;//完成进度


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWealthName() {
        return wealthName;
    }

    public void setWealthName(String wealthName) {
        this.wealthName = wealthName;
    }

    public double getWealthPercent() {
        return wealthPercent;
    }

    public void setWealthPercent(double wealthPercent) {
        this.wealthPercent = wealthPercent;
    }

    public String getWealthDay() {
        return wealthDay;
    }

    public void setWealthDay(String wealthDay) {
        this.wealthDay = wealthDay;
    }

    public String getWealthIsHuoqiOrDingQi() {
        return wealthIsHuoqiOrDingQi;
    }

    public void setWealthIsHuoqiOrDingQi(String wealthIsHuoqiOrDingQi) {
        this.wealthIsHuoqiOrDingQi = wealthIsHuoqiOrDingQi;
    }

    public String getWealthTitle() {
        return wealthTitle;
    }

    public void setWealthTitle(String wealthTitle) {
        this.wealthTitle = wealthTitle;
    }

    public String getWealthBaoOrNewOrHot() {
        return wealthBaoOrNewOrHot;
    }

    public void setWealthBaoOrNewOrHot(String wealthBaoOrNewOrHot) {
        this.wealthBaoOrNewOrHot = wealthBaoOrNewOrHot;
    }

    public String getWealthYearYield() {
        return wealthYearYield;
    }

    public void setWealthYearYield(String wealthYearYield) {
        this.wealthYearYield = wealthYearYield;
    }

    public double getWealthPlan() {
        return wealthPlan;
    }

    public void setWealthPlan(double wealthPlan) {
        this.wealthPlan = wealthPlan;
    }

    public AccountWealthModel() {
    }


    public AccountWealthModel(String wealthTitle, String wealthName, double wealthPercent, String wealthDay, String wealthBaoOrNewOrHot, String wealthYearYield, String wealthIsHuoqiOrDingQi, double wealthPlan) {
        this.wealthTitle = wealthTitle;
        this.wealthName = wealthName;
        this.wealthPercent = wealthPercent;
        this.wealthDay = wealthDay;
        this.wealthBaoOrNewOrHot = wealthBaoOrNewOrHot;
        this.wealthYearYield = wealthYearYield;
        this.wealthIsHuoqiOrDingQi = wealthIsHuoqiOrDingQi;
        this.wealthPlan = wealthPlan;
    }
}
