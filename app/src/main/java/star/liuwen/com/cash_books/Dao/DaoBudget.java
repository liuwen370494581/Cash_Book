package star.liuwen.com.cash_books.Dao;

import java.util.List;

import star.liuwen.com.cash_books.Base.App;
import star.liuwen.com.cash_books.bean.BudgetModel;

/**
 * Created by liuwen on 2017/5/12.
 */
public class DaoBudget {


    /**
     * 插入对象数据
     * 插入对象为BudgetModel
     *
     * @param model
     */
    public static void insert(BudgetModel model) {
        App.getDaoInstant().getBudgetModelDao().insert(model);
    }

    /**
     * 删除指定的model的数据
     *
     * @param model
     */
    public static void deleteByModel(BudgetModel model) {
        App.getDaoInstant().getBudgetModelDao().delete(model);
    }


    /**
     * 删除所有
     */
    public static void deleteAllData() {
        App.getDaoInstant().getBudgetModelDao().deleteAll();
    }


    /**
     * 更新数据
     *
     * @param model
     */
    public static void update(BudgetModel model) {
        App.getDaoInstant().getBudgetModelDao().update(model);
    }

    /**
     * 查询 BudgetModel的集合对象
     *
     * @return
     */
    public static List<BudgetModel> query() {
        return App.getDaoInstant().getBudgetModelDao().queryBuilder().list();
    }


    /**
     * 获取总数
     * UserInfoModel的总数
     *
     * @return
     */
    public static long getCount() {
        return App.getDaoInstant().getBudgetModelDao().count();
    }
}
