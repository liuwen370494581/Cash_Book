package star.liuwen.com.cash_books.EventBus;

/**
 * Created by liuwen on 2017/5/12.
 * 事件 EventBus订阅发布
 */
public class Event<T> {

    private int code;
    private T data;

    public Event(int code) {
        this.code = code;
    }

    public Event(T data) {
        this.data = data;
    }

    public Event(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
