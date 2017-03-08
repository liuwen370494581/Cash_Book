package star.liuwen.com.cash_books.Dao;

import java.util.List;

import star.liuwen.com.cash_books.Base.App;
import star.liuwen.com.cash_books.bean.AccountModel;
import star.liuwen.com.cash_books.bean.ChoiceAccount;

/**
 * Created by liuwen on 2017/2/27.
 */
public class DaoChoiceAccount {


    /**
     * 插入对象数据
     * 插入对象为ChoiceAccount
     *
     * @param model
     */
    public static void insertChoiceAccount(ChoiceAccount model) {
        App.getDaoInstant().getChoiceAccountDao().insert(model);
    }


    public static void deleteChoiceAccountByModel(ChoiceAccount model) {
        App.getDaoInstant().getChoiceAccountDao().delete(model);
    }

    /**
     * 删除所有
     */
    public static void deleteAllData() {
        App.getDaoInstant().getChoiceAccountDao().deleteAll();
    }


    /**
     * 更新数据
     *
     * @param model
     */
    public static void updateAccount(ChoiceAccount model) {
        App.getDaoInstant().getChoiceAccountDao().update(model);
    }

    /**
     * 查询 SaveMoneyPlanModel的集合对象
     *
     * @return
     */
    public static List<ChoiceAccount> query() {
        return App.getDaoInstant().getChoiceAccountDao().queryBuilder().list();
    }


    /**
     * 获取总数
     * SaveMoneyPlanModel的总数
     *
     * @return
     */
    public static long getCount() {
        return App.getDaoInstant().getChoiceAccountDao().count();
    }


}
