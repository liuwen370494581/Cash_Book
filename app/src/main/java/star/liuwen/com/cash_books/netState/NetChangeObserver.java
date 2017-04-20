package star.liuwen.com.cash_books.netState;


/**
 * 描述: 网络注册的观察者
 * Created by liuwen on 2017/4/17.
 */

public interface NetChangeObserver {

    void onConnect(NetworkUtils.NetworkType type);

    void onDisConnect();

}
