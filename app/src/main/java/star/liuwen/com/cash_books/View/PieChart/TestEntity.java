package star.liuwen.com.cash_books.View.PieChart;

/**
 * Created by liuwen on 2017/3/29.
 */

public class TestEntity implements IPieElement{

    private float mValue;
    private String mColor;

    public TestEntity(float value,String color){
        mValue=value;
        mColor=color;
    }
    @Override
    public float getValue() {
        return mValue;
    }

    @Override
    public String getColor() {
        return mColor;
    }
}
