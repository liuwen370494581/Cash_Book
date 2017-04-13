package star.liuwen.com.cash_books.Dao;

import java.util.List;

import star.liuwen.com.cash_books.Base.App;
import star.liuwen.com.cash_books.bean.UserInfoModel;
import star.liuwen.com.cash_books.bean.ZhiChuModel;

/**
 * Created by liuwen on 2017/4/13.
 */
public class DaoUserInfo {


    /**
     * 插入对象数据
     * 插入对象为UserInfoModel
     *
     * @param model
     */
    public static void insert(UserInfoModel model) {
        App.getDaoInstant().getUserInfoModelDao().insert(model);
    }

    /**
     * 删除指定的id的数据
     *
     * @param id
     */
    public static void deleteById(long id) {
        App.getDaoInstant().getUserInfoModelDao().deleteByKey(id);

    }

    /**
     * 删除指定的model的数据
     *
     * @param model
     */
    public static void deleteByModel(UserInfoModel model) {
        App.getDaoInstant().getUserInfoModelDao().delete(model);
    }


    /**
     * 删除所有
     */
    public static void deleteAllData() {
        App.getDaoInstant().getUserInfoModelDao().deleteAll();
    }


    /**
     * 更新数据
     *
     * @param model
     */
    public static void update(ZhiChuModel model) {
        App.getDaoInstant().getZhiChuModelDao().update(model);
    }

    /**
     * 查询 UserInfoModel的集合对象
     *
     * @return
     */
    public static List<UserInfoModel> query() {
        return App.getDaoInstant().getUserInfoModelDao().queryBuilder().list();
    }


    /**
     * 获取总数
     * UserInfoModel的总数
     *
     * @return
     */
    public static long getCount() {
        return App.getDaoInstant().getUserInfoModelDao().count();
    }

}
