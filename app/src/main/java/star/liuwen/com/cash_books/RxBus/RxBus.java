package star.liuwen.com.cash_books.RxBus;


import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by liuwen on 2016/12/7.
 */
public class RxBus {

    private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());
    private final Map<String, Object> tags = new HashMap<>();//post队列
    private final Map<String, Object> sendtags = new HashMap<>();//结果发送队列

    private static RxBus rxBus;

    private RxBus() {

    }

    //单列模式(双重锁模式)
    public static RxBus getInstance() {
        if (rxBus == null) {
            synchronized (RxBus.class) {
                if (rxBus == null) {
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }

    /**
     * 发送事件
     *
     * @param tag    用户区分事件
     * @param object 事件的参数
     */
    public void post(String tag, Object object) {

        if (!tags.containsKey(tag)) {
            tags.put(tag, object);
            _bus.onNext(object);
            sendtags.put(tag, object);
        } else {
            tags.remove(tag);
            tags.put(tag, object);
            _bus.onNext(object);
            sendtags.put(tag, object);
        }

    }


    /**
     * 主线程中执行
     *
     * @param tag
     * @param rxBusResult
     */
    public void toObserverableOnMainThread(final String tag, final RxBusResult rxBusResult) {

        _bus.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (sendtags.containsKey(tag)) {
                    rxBusResult.onRxBusResult(sendtags.get(tag));
                    //sendtags.remove(tag);
                }
            }
        });
    }

    /**
     * 子线程中执行
     *
     * @param tag
     * @param rxBusResult
     */
    public void toObserverableChildThread(final String tag, final RxBusResult rxBusResult) {

        _bus.observeOn(Schedulers.io()).subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (sendtags.containsKey(tag)) {
                    rxBusResult.onRxBusResult(sendtags.get(tag));
                    //sendtags.remove(tag);
                }
            }
        });
    }


    /**
     * 移除标记
     *
     * @param tag
     */
    public void removeObserverable(String tag) {
        if (tags.containsKey(tag)) {
            tags.remove(tag);
        }
        if (sendtags.containsKey(tag)) {
            sendtags.remove(tag);
        }
    }

    /**
     * 退出应用时，清空资源
     */
    public void release() {
        tags.clear();
        sendtags.clear();
        rxBus = null;
    }

}
