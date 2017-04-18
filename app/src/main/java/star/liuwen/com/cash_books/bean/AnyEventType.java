package star.liuwen.com.cash_books.bean;

/**
 * Created by liuwen on 2017/4/18.
 */
public class AnyEventType {

    public int mType;
    public Object mObj;

    public AnyEventType(int type,Object obj) {
        mType = type;
        mObj = obj;
    }
    public int getType(){
        return mType;
    }
    public Object getObj(){
        return mObj;
    }
}
