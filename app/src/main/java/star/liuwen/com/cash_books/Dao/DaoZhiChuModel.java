package star.liuwen.com.cash_books.Dao;

import java.util.List;

import star.liuwen.com.cash_books.Base.App;
import star.liuwen.com.cash_books.bean.ZhiChuModel;

/**
 * Created by liuwen on 2017/3/8.
 */
public class DaoZhiChuModel {

    /**
     * 插入对象数据
     * 插入对象为ZhiChuModel
     *
     * @param model
     */
    public static void insertZhiChu(ZhiChuModel model) {
        App.getDaoInstant().getZhiChuModelDao().insert(model);
    }

    /**
     * 删除指定的id的数据
     *
     * @param id
     */
    public static void deleteZhiChuById(long id) {
        App.getDaoInstant().getZhiChuModelDao().deleteByKey(id);

    }


    public static void deleteZhiChuByModel(ZhiChuModel model) {
        App.getDaoInstant().getZhiChuModelDao().delete(model);
    }


    /**
     * 删除所有
     */
    public static void deleteAllData() {
        App.getDaoInstant().getZhiChuModelDao().deleteAll();
    }


    /**
     * 更新数据
     *
     * @param model
     */
    public static void updateSaveMoney(ZhiChuModel model) {
        App.getDaoInstant().getZhiChuModelDao().update(model);
    }

    /**
     * 查询 SaveMoneyPlanModel的集合对象
     *
     * @return
     */
    public static List<ZhiChuModel> query() {
        return App.getDaoInstant().getZhiChuModelDao().queryBuilder().list();
    }



    /**
     * 获取总数
     * SaveMoneyPlanModel的总数
     *
     * @return
     */
    public static long getCount() {
        return App.getDaoInstant().getZhiChuModelDao().count();
    }

}
