package star.liuwen.com.cash_books.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuwen on 2017/6/1.
 * 预算自定义view
 */
public class WaveLoadingView extends View {
    private final Paint mSRCPaint;

    private Paint mPaint;
    private Paint mTextPaint;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private int y;
    private int x;

    private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private Bitmap bgBitmap;
    private Path mPath;
    private boolean isLeft;
    //    private int y;
    private int mWidth;
    private int mHeight;
    private int mPercent;
    private String mMoney = "0.00"; //设置默认值
    private String mDescribe = "月预算"; //设置默认值
    private int mMoneySize = 26;
    private int mDescribeSize = 18;
    private String unFinishPercentColor = "#5EBF93";
    private String finishPercentColor = "#076E4F";


    public WaveLoadingView(Context context) {
        this(context, null);
    }

    public WaveLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();

        mPaint.setStrokeWidth(10);


        //   bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wing);
        mPath = new Path();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor(finishPercentColor));//设置加载进度颜色

        mSRCPaint = new Paint();
        mSRCPaint.setAntiAlias(true);
        mBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mSRCPaint.setColor(Color.parseColor(unFinishPercentColor));//设置未加载进度颜色
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }

        y = mHeight;
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {


//        if (y > -50) {
//            y--;
//        }
        if (x > 50) {
            isLeft = true;
        } else if (x < 0) {
            isLeft = false;
        }

        if (isLeft) {
            x = x - 1;
        } else {
            x = x + 1;
        }
        mPath.reset();
        y = (int) ((1 - mPercent / 100f) * mHeight);
        mPath.moveTo(0, y);
        mPath.cubicTo(100 + x * 2, 50 + y, 100 + x * 2, y - 50, mWidth, y);
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();


        //清除掉图像 不然path会重叠
        mBitmap.eraseColor(Color.parseColor("#00000000"));
        //dst
//        mCanvas.drawBitmap(bgBitmap,0,0,null);

//        mSRCPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));

        mCanvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mSRCPaint);

        mPaint.setXfermode(mMode);
        //src
        mCanvas.drawPath(mPath, mPaint);
        mPaint.setXfermode(null);


        canvas.drawBitmap(mBitmap, 0, 0, null);

        //   String str = mPercent + "";
        String sts = mMoney;
        mTextPaint.setTextSize(mMoneySize);
        float txtLength = mTextPaint.measureText(sts);
        mTextPaint.setColor(Color.parseColor("#FFFFFF"));//设置字体为白色
        canvas.drawText(sts, mWidth / 2 - txtLength / 2, mHeight / 2 + 15, mTextPaint);

//        mTextPaint.setTextSize(40);
//        canvas.drawText("%", mWidth / 2 + 50, mHeight / 2 - 20, mTextPaint);

        mTextPaint.setTextSize(mDescribeSize);
        String text = mDescribe;
        float textLength = mTextPaint.measureText(text);
        canvas.drawText(text, mWidth / 2 - textLength / 2, mHeight / 2 + 40, mTextPaint);

        postInvalidateDelayed(10);

    }


    /**
     * 设置进度
     *
     * @param percent
     */
    public void setPercent(int percent) {
        mPercent = percent;
    }

    public void setMoney(String money) {
        this.mMoney = money;
    }

    public void setDescribe(String describe) {
        this.mDescribe = describe;
    }

    public void setMoneySize(int size) {
        this.mMoneySize = size;
    }

    public void setDescribeSize(int size) {
        this.mDescribeSize = size;
    }

    public void setUnFinishPercent(String color) {
        this.unFinishPercentColor = color;
    }

    public void setFinishPercent(String color) {
        this.finishPercentColor = color;
    }


}
