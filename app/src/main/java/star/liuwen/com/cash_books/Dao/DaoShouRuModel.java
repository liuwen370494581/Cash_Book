package star.liuwen.com.cash_books.Dao;

import java.util.List;

import star.liuwen.com.cash_books.Base.App;
import star.liuwen.com.cash_books.bean.ShouRuModel;
import star.liuwen.com.cash_books.bean.ZhiChuModel;

/**
 * Created by liuwen on 2017/3/8.
 */
public class DaoShouRuModel {


    public static void insertShouRu(ShouRuModel model) {
        App.getDaoInstant().getShouRuModelDao().insert(model);
    }


    public static void deleteShouRuById(long id) {
        App.getDaoInstant().getShouRuModelDao().deleteByKey(id);
    }


    public static void deleteShouRuByModel(ShouRuModel model) {
        App.getDaoInstant().getShouRuModelDao().delete(model);
    }

    /**
     * 删除所有
     */
    public static void deleteAllData() {
        App.getDaoInstant().getShouRuModelDao().deleteAll();
    }


    /**
     * 更新数据
     *
     * @param model
     */
    public static void updateSaveMoney(ShouRuModel model) {
        App.getDaoInstant().getShouRuModelDao().update(model);
    }

    /**
     * 查询 SaveMoneyPlanModel的集合对象
     *
     * @return
     */
    public static List<ShouRuModel> query() {
        return App.getDaoInstant().getShouRuModelDao().queryBuilder().list();
    }


    /**
     * 获取总数
     * SaveMoneyPlanModel的总数
     *
     * @return
     */
    public static long getCount() {
        return App.getDaoInstant().getShouRuModelDao().count();
    }

}
