package star.liuwen.com.cash_books.Dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import star.liuwen.com.cash_books.Base.App;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.bean.AccountModel;
import star.liuwen.com.cash_books.bean.AccountModelDao;
import star.liuwen.com.cash_books.bean.BaseModel;
import star.liuwen.com.cash_books.bean.BaseModelDao;

/**
 * Created by liuwen on 2017/4/7.
 */
public class DaoAccountBalance {

    /**
     * 插入对象数据
     * 插入对象为BaseModel
     *
     * @param model
     */
    public static void insert(BaseModel model) {
        App.getDaoInstant().getBaseModelDao().insert(model);
    }

    /**
     * 插入数据为List<BaseModel></>
     *
     * @param list
     */
    public static void insertList(List<BaseModel> list) {
        App.getDaoInstant().getBaseModelDao().insert((BaseModel) list);
    }


    /**
     * 查询 BaseModel集合对象
     * 都可以按timeMinSecond来排序
     *
     * @return
     */
    public static List<BaseModel> query() {
        List<BaseModel> list = new ArrayList<>();
        list = App.getDaoInstant().getBaseModelDao().queryBuilder().list();
        Collections.sort(list, new Comparator<BaseModel>() {
            @Override
            public int compare(BaseModel model1, BaseModel model2) {
                return model2.getTimeMinSec().compareTo(model1.getTimeMinSec());
            }
        });
        return list;
    }

    public static List<BaseModel> queryByType(String accountType) {
        List<BaseModel> list = new ArrayList<>();
        if (accountType.equals(Config.XYK)) {
            // list=App.getDaoInstant().getAccountModelDao().queryBuilder().whereOr(AccountModelDao.Properties.AccountType.eq(accountType),AccountModelDao.Properties.Data.eq())
            list = App.getDaoInstant().getBaseModelDao().queryBuilder().where(BaseModelDao.Properties.AccountType.eq(accountType)).build().list();
        } else if (accountType.equals(Config.CXK)) {
            list = App.getDaoInstant().getBaseModelDao().queryBuilder().where(BaseModelDao.Properties.AccountType.eq(accountType)).build().list();
        } else if (accountType.equals(Config.CASH)) {
            list = App.getDaoInstant().getBaseModelDao().queryBuilder().where(BaseModelDao.Properties.AccountType.eq(accountType)).build().list();
        } else if (accountType.equals(Config.ZFB)) {
            list = App.getDaoInstant().getBaseModelDao().queryBuilder().where(BaseModelDao.Properties.AccountType.eq(accountType)).build().list();
        } else if (accountType.equals(Config.WEIXIN)) {
            list = App.getDaoInstant().getBaseModelDao().queryBuilder().where(BaseModelDao.Properties.AccountType.eq(accountType)).build().list();
        } else if (accountType.equals(Config.TOUZI)) {
            list = App.getDaoInstant().getBaseModelDao().queryBuilder().where(BaseModelDao.Properties.AccountType.eq(accountType)).build().list();
        } else if (accountType.equals(Config.CZK)) {
            list = App.getDaoInstant().getBaseModelDao().queryBuilder().where(BaseModelDao.Properties.AccountType.eq(accountType)).build().list();
        } else if (accountType.equals(Config.INTENTACCOUNT)) {
            list = App.getDaoInstant().getBaseModelDao().queryBuilder().where(BaseModelDao.Properties.AccountType.eq(accountType)).build().list();
        }
        Collections.sort(list, new Comparator<BaseModel>() {
            @Override
            public int compare(BaseModel model1, BaseModel model2) {
                return model2.getTimeMinSec().compareTo(model1.getTimeMinSec());
            }
        });
        return list;
    }


    /**
     * 删除所有
     */
    public static void deleteAllData() {
        App.getDaoInstant().getBaseModelDao().deleteAll();
    }

    /**
     * 按照id来删除
     *
     * @param id
     */
    public static void deleteById(long id) {
        App.getDaoInstant().getBaseModelDao().deleteByKey(id);
    }


    public static void deleteByModel(BaseModel model) {
        App.getDaoInstant().getBaseModelDao().delete(model);
    }


    /**
     * 获取总数
     * SaveMoneyPlanModel的总数
     *
     * @return
     */
    public static long getCount() {
        return App.getDaoInstant().getBaseModelDao().count();
    }
}
