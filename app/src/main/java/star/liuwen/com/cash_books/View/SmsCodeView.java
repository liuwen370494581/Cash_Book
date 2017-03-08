package star.liuwen.com.cash_books.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.CheckGetUtil;

/**
 * Created by liuwen on 2017/2/13.
 * 图形验证码
 */
public class SmsCodeView extends View {
    private int[] mCheckNum = {0, 0, 0, 0};
    // 点数设置
    private final static int POINT_NUM = 100;
    // 线段数设置
    private final static int LINE_NUM = 2;

    private Paint mTempPaint = new Paint();

    public SmsCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTempPaint.setAntiAlias(true);
        mTempPaint.setTextSize(30);
        mTempPaint.setColor(context.getResources().getColor(R.color.text_color_33));
        mTempPaint.setStrokeWidth(3);
    }

    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#FFFFE7"));
        // 绘制验证码
        final int height = getHeight();
        final int width = getWidth();
        int dx = width / 4 - 10;
        for (int i = 0; i < 4; i++) {
            canvas.drawText("" + mCheckNum[i], dx, (height / 2) + 10, mTempPaint);
            dx += width / 5;
        }
//		int[] line;
//		for (int i = 0; i < LINE_NUM; i++) {
//			line = CheckGetUtil.getLine(height, width);
//			canvas.drawLine(line[0], line[1], line[2], line[3], mTempPaint);
//		}
//		// 绘制小圆点
//		int[] point;
//		for (int i = 0; i < POINT_NUM; i++) {
//			point = CheckGetUtil.getPoint(height, width);
//			canvas.drawCircle(point[0], point[1], 1, mTempPaint);
//		}
    }

    public int[] getCode() {
        return mCheckNum;
    }

    public void createNewCode() {
        mCheckNum = CheckGetUtil.getCheckNum();
        invalidate();
    }

}
