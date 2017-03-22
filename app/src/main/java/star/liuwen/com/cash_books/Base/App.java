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
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.bean.AccountModel;
import star.liuwen.com.cash_books.bean.ChoiceAccount;
import star.liuwen.com.cash_books.bean.CreditCardModel;
import star.liuwen.com.cash_books.bean.DaoMaster;
import star.liuwen.com.cash_books.bean.DaoSession;
import star.liuwen.com.cash_books.bean.SaveMoneyPlanModel;
import star.liuwen.com.cash_books.netState.NetChangeObserver;
import star.liuwen.com.cash_books.netState.NetStateReceiver;
import star.liuwen.com.cash_books.netState.NetworkUtils;

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

    public NetworkUtils.NetworkType mNetType;
    private NetStateReceiver netStateReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化内存检测工具
        mRefWatcher = LeakCanary.install(this);
        //插入greenDao数据
        setUpDataBase();
        //初始化网络监察工具
        initNetChangeReceiver();
    }

    // 检测内存工具
    public static RefWatcher getRefWatcher(Context context) {
        App application = (App) context
                .getApplicationContext();
        return application.mRefWatcher;
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


    private void initNetChangeReceiver() {
        //获取当前网络类型
        mNetType = NetworkUtils.getNetworkType(this);

        //定义网络状态的广播接受者
        netStateReceiver = NetStateReceiver.getReceiver();

        //给广播接受者注册一个观察者
        netStateReceiver.registerObserver(netChangeObserver);

        //注册网络变化广播
        NetworkUtils.registerNetStateReceiver(this, netStateReceiver);
    }

    private NetChangeObserver netChangeObserver = new NetChangeObserver() {

        @Override
        public void onConnect(NetworkUtils.NetworkType type) {
            App.this.onNetConnect(type);
        }

        @Override
        public void onDisConnect() {
            App.this.onNetDisConnect();
        }
    };

    protected void onNetDisConnect() {
        ToastUtils.showToast(this, "网络已断开,请检查网络设置");
        mNetType = NetworkUtils.NetworkType.NETWORK_NONE;
    }

    protected void onNetConnect(NetworkUtils.NetworkType type) {
        if (type == mNetType) return; //net not change
        switch (type) {
            case NETWORK_WIFI:
                ToastUtils.showToast(this, "已切换到 WIFI 网络");
                break;
            case NETWORK_MOBILE:
                ToastUtils.showToast(this, "已切换到 2G/3G/4G 网络");
                break;
        }
        mNetType = type;
    }

    //释放广播接受者(建议在 最后一个 Activity 退出前调用)
    public void destroyReceiver() {
        //移除里面的观察者
        netStateReceiver.removeObserver(netChangeObserver);
        //解注册广播接受者,
        NetworkUtils.unRegisterNetStateReceiver(this, netStateReceiver);
    }


}
