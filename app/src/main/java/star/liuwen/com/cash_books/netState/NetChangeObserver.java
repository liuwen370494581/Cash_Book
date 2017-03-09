package star.liuwen.com.cash_books.netState;


/**
 * 描述: 网络注册的观察者
 * Created by mjd on 2017/1/16.
 */

public interface NetChangeObserver {

    void onConnect(NetworkUtils.NetworkType type);

    void onDisConnect();

}
