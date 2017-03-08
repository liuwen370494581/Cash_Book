package star.liuwen.com.cash_books.Base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

import star.liuwen.com.cash_books.Dao.DaoChoiceAccount;
import star.liuwen.com.cash_books.Enage.DataEnige;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.bean.AccountModel;
import star.liuwen.com.cash_books.bean.ChoiceAccount;
import star.liuwen.com.cash_books.bean.CreditCardModel;
import star.liuwen.com.cash_books.bean.DaoMaster;
import star.liuwen.com.cash_books.bean.DaoSession;
import star.liuwen.com.cash_books.bean.SaveMoneyPlanModel;

/**
 * Created by liuwen on 2017/1/6.
 */
public class App extends Application {

    public static int position;//账户position;
    public static boolean isBgCash = true;
    public static boolean isBgCXK = true;
    public static boolean isBgXYK = true;
    public static boolean isBgZFB = true;
    public static boolean isBgJC = true;
    public static boolean isBgJR = true;
    public static boolean isRemindPush = false;
    public static boolean isOpenCodedLock = false;
    public static String cycleData = "";
    public static String cycleTime = "";

    private RefWatcher mRefWatcher;
    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        RxBus.getInstance();
        //初始化内存检测工具
        mRefWatcher = LeakCanary.install(this);
        setUpDataBase();


    }

    private void setUpDataBase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "Cash_book", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
        if (SharedPreferencesUtil.getBooleanPreferences(this, Config.FistStar, true)) {
            DataEnige.InsertAccountData();
            DataEnige.InsertShouRuData();
            DataEnige.InsertZHiCHuData();
        }
    }


    public static DaoSession getDaoInstant() {
        return mDaoSession;
    }

    // 在自己的Application中添加如下代码
    public static RefWatcher getRefWatcher(Context context) {

        App application = (App) context
                .getApplicationContext();
        return application.mRefWatcher;
    }
}
