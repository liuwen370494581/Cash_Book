package star.liuwen.com.cash_books.PieChart;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CountView extends TextView{

	private Context mContext=null;
   private int duration = 1200;
   private float number;

    public CountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
    }

    public void showNumberWithAnimation(float number) {
        ObjectAnimator objectAnimator= ObjectAnimator.ofFloat(mContext,"number",0,number);
        objectAnimator.setDuration(duration);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
        setText(String.format("%1$7.2f",number));
    }
}
