package star.liuwen.com.cash_books.Dao;

import java.util.List;

import star.liuwen.com.cash_books.Base.App;
import star.liuwen.com.cash_books.bean.SaveMoneyPlanModel;

/**
 * Created by liuwen on 2017/2/23.
 */
public class DaoSaveMoneyPlan {


    /**
     * 插入对象数据
     * 插入对象为SaveMoneyPlanModel
     *
     * @param model
     */
    public static void insertSaveMoney(SaveMoneyPlanModel model) {
        App.getDaoInstant().getSaveMoneyPlanModelDao().insert(model);
    }

    /**
     * 删除指定的id的数据
     *
     * @param id
     */
    public static void deleteSaveMoneyById(long id) {
        App.getDaoInstant().getSaveMoneyPlanModelDao().deleteByKey(id);

    }


    public static void deleteSaveMoneyByModel(SaveMoneyPlanModel model) {
        App.getDaoInstant().getSaveMoneyPlanModelDao().delete(model);
    }

    /**
     * 删除所有
     */
    public static void deleteAllData() {
        App.getDaoInstant().getSaveMoneyPlanModelDao().deleteAll();
    }

    /**
     * 删除某个对象
     *
     * @param model
     */
    public static void deleteSaveMoney(SaveMoneyPlanModel model) {
        App.getDaoInstant().getSaveMoneyPlanModelDao().delete(model);
    }

    /**
     * 更新数据
     *
     * @param model
     */
    public static void updateSaveMoney(SaveMoneyPlanModel model) {
        App.getDaoInstant().getSaveMoneyPlanModelDao().update(model);
    }

    /**
     * 查询 SaveMoneyPlanModel的集合对象
     *
     * @return
     */
    public static List<SaveMoneyPlanModel> querySaveMoney() {
        return App.getDaoInstant().getSaveMoneyPlanModelDao().queryBuilder().list();
    }


    /**
     * 获取总数
     * SaveMoneyPlanModel的总数
     *
     * @return
     */
    public static long getCount() {
        return App.getDaoInstant().getSaveMoneyPlanModelDao().count();
    }


}
