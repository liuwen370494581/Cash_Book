package star.liuwen.com.cash_books.Dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import star.liuwen.com.cash_books.Base.App;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.bean.AccountModel;
import star.liuwen.com.cash_books.bean.AccountModelDao;

/**
 * Created by liuwen on 2017/2/27.
 */
public class DaoAccount {


    /**
     * 插入对象数据
     * 插入对象为AccountModel
     *
     * @param model
     */
    public static void insertAccount(AccountModel model) {
        App.getDaoInstant().getAccountModelDao().insert(model);
    }


    public static void deleteAccountById(long id) {
        App.getDaoInstant().getAccountModelDao().deleteByKey(id);
    }


    /**
     * 删除所有
     */
    public static void deleteAllData() {
        App.getDaoInstant().getAccountModelDao().deleteAll();
    }


    /**
     * 更新数据
     *
     * @param model
     */
    public static void updateAccount(AccountModel model) {
        App.getDaoInstant().getAccountModelDao().update(model);
    }

    /**
     * 查询 AccountModel的集合对象
     * 都可以按timeMinSecond来排序
     *
     * @return
     */
    public static List<AccountModel> query() {
        List<AccountModel> list = new ArrayList<>();
        list = App.getDaoInstant().getAccountModelDao().queryBuilder().list();
        Collections.sort(list, new Comparator<AccountModel>() {
            @Override
            public int compare(AccountModel model1, AccountModel model2) {
                return model2.getTimeMinSec().compareTo(model1.getTimeMinSec());
            }
        });
        return list;
    }


    /**
     * 根据关键字(消费金额 是否支出或者是消费，账户类型,消费种类)查询
     *
     * @param keyWord
     * @return
     */
    public static List<AccountModel> queryByKeyWord(String keyWord) {
        List<AccountModel> list = new ArrayList<>();
        list = App.getDaoInstant().getAccountModelDao().queryBuilder().whereOr(AccountModelDao.Properties.AccountType.eq(keyWord), AccountModelDao.Properties.ConsumeType.eq(keyWord),
                AccountModelDao.Properties.Money.eq(keyWord), AccountModelDao.Properties.ZhiChuShouRuType.eq(keyWord))
                .build().list();
        Collections.sort(list, new Comparator<AccountModel>() {
            @Override
            public int compare(AccountModel model1, AccountModel model2) {
                return model2.getTimeMinSec().compareTo(model1.getTimeMinSec());
            }
        });
        return list;
    }

    /**
     * 根据具体的某一天日期来查询 eg:2017-4-25
     *
     * @param date
     * @return
     */
    public static List<AccountModel> queryByDate(String date) {
        List<AccountModel> list = new ArrayList<>();
        list = App.getDaoInstant().getAccountModelDao().queryBuilder().where(AccountModelDao.Properties.Data.eq(date)).build().list();
        Collections.sort(list, new Comparator<AccountModel>() {
            @Override
            public int compare(AccountModel model1, AccountModel model2) {
                return model2.getTimeMinSec().compareTo(model1.getTimeMinSec());
            }
        });
        return list;
    }

    /**
     * 查询条件
     * AccountModel
     *
     * @param accountType 账户名称
     * @return
     */
    public static List<AccountModel> queryByAccountType(String accountType) {
        List<AccountModel> list = new ArrayList<>();
        if (accountType.equals(Config.XYK)) {
            // list=App.getDaoInstant().getAccountModelDao().queryBuilder().whereOr(AccountModelDao.Properties.AccountType.eq(accountType),AccountModelDao.Properties.Data.eq())
            list = App.getDaoInstant().getAccountModelDao().queryBuilder().where(AccountModelDao.Properties.AccountType.eq(accountType)).build().list();
        } else if (accountType.equals(Config.CXK)) {
            list = App.getDaoInstant().getAccountModelDao().queryBuilder().where(AccountModelDao.Properties.AccountType.eq(accountType)).build().list();
        } else if (accountType.equals(Config.CASH)) {
            list = App.getDaoInstant().getAccountModelDao().queryBuilder().where(AccountModelDao.Properties.AccountType.eq(accountType)).build().list();
        } else if (accountType.equals(Config.ZFB)) {
            list = App.getDaoInstant().getAccountModelDao().queryBuilder().where(AccountModelDao.Properties.AccountType.eq(accountType)).build().list();
        } else if (accountType.equals(Config.WEIXIN)) {
            list = App.getDaoInstant().getAccountModelDao().queryBuilder().where(AccountModelDao.Properties.AccountType.eq(accountType)).build().list();
        } else if (accountType.equals(Config.TOUZI)) {
            list = App.getDaoInstant().getAccountModelDao().queryBuilder().where(AccountModelDao.Properties.AccountType.eq(accountType)).build().list();
        } else if (accountType.equals(Config.CZK)) {
            list = App.getDaoInstant().getAccountModelDao().queryBuilder().where(AccountModelDao.Properties.AccountType.eq(accountType)).build().list();
        } else if (accountType.equals(Config.INTENTACCOUNT)) {
            list = App.getDaoInstant().getAccountModelDao().queryBuilder().where(AccountModelDao.Properties.AccountType.eq(accountType)).build().list();
        }
        Collections.sort(list, new Comparator<AccountModel>() {
            @Override
            public int compare(AccountModel model1, AccountModel model2) {
                return model2.getTimeMinSec().compareTo(model1.getTimeMinSec());
            }
        });
        return list;
    }


    /**
     * 根据ID来查找数据 简单版Api
     *
     * @param id
     * @return
     */
    public static List<AccountModel> queryById(long id) {
        List<AccountModel> list = new ArrayList<>();
        list = App.getDaoInstant().getAccountModelDao().queryBuilder().where(AccountModelDao.Properties.ChoiceAccountId.eq(id)).build().list();
        Collections.sort(list, new Comparator<AccountModel>() {
            @Override
            public int compare(AccountModel model1, AccountModel model2) {
                return model2.getTimeMinSec().compareTo(model1.getTimeMinSec());
            }
        });
        return list;
    }


    /**
     * 根据ID和两个日期之间来查询数据(查询一个月消费了多少)
     *
     * @param startData
     * @param endData
     * @return
     */
    public static List<AccountModel> queryByIdAndDate(long id, StringBuilder startData, StringBuilder endData) {
        List<AccountModel> list = new ArrayList<>();
        list = App.getDaoInstant().getAccountModelDao().queryBuilder().where(AccountModelDao.Properties.ChoiceAccountId.eq(id), AccountModelDao.Properties.Data.between(startData, endData)).build().list();
        Collections.sort(list, new Comparator<AccountModel>() {
            @Override
            public int compare(AccountModel model1, AccountModel model2) {
                return model2.getTimeMinSec().compareTo(model1.getTimeMinSec());
            }
        });
        return list;
    }


    /**
     * 支出或者是收入类型
     *
     * @param zhichushouruType
     * @return
     */
    public static List<AccountModel> queryByZhiChuSHouRuType(String zhichushouruType) {
        List<AccountModel> list = new ArrayList<>();
        if (zhichushouruType.equals(Config.ZHI_CHU)) {
            list = App.getDaoInstant().getAccountModelDao().queryBuilder().where(AccountModelDao.Properties.ZhiChuShouRuType.eq(zhichushouruType)).build().list();
        } else if (zhichushouruType.equals(Config.SHOU_RU)) {
            list = App.getDaoInstant().getAccountModelDao().queryBuilder().where(AccountModelDao.Properties.ZhiChuShouRuType.eq(zhichushouruType)).build().list();
        }
        Collections.sort(list, new Comparator<AccountModel>() {
            @Override
            public int compare(AccountModel model1, AccountModel model2) {
                return model2.getTimeMinSec().compareTo(model1.getTimeMinSec());
            }
        });
        return list;
    }


    /**
     * 获取总数
     * SaveMoneyPlanModel的总数
     *
     * @return
     */
    public static long getCount() {
        return App.getDaoInstant().getAccountModelDao().count();
    }


}
