package star.liuwen.com.cash_books.bean;

/**
 * Description: EventBus传递消息总体类
 */

public class EventCenter<T> {

    private int eventCode = -1;

    private T data;

    public EventCenter(int eventCode) {
        this.eventCode = eventCode;
    }

    public EventCenter(int eventCode, T data) {
        this.eventCode = eventCode;
        this.data = data;
    }

    public int getEventCode() {
        return eventCode;
    }

    public T getData() {
        return data;
    }
}
