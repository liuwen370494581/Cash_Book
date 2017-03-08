package star.liuwen.com.cash_books.PieChart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

@SuppressLint("DrawAllocation")
public class PieChartView extends View implements Runnable, OnGestureListener {

    public static final int TO_RIGHT = 0;
    public static final int TO_BOTTOM = 1;
    public static final int TO_LEFT = 2;
    public static final int TO_TOP = 3;
    public static final int NO_ROTATE = -1;
    private static final String[] DEFAULT_ITEMS_COLORS = {"#8B99DF",
            "#20B2AA", "#00BFFF", "#CD5C5C", "#8B658B", "#CD853F", "#006400",
            "#FF4500", "#D8BFD8", "#4876FF", "#FF00FF", "#FF83FA", "#0000FF",
            "#363636", "#FFDAB9", "#90EE90", "#8B008B", "#00BFFF", "#00FF00",
            "#006400", "#00FFFF", "#00FFFF", "#668B8B", "#000080", "#008B8B"};
    private static final String DEAFULT_BORDER_COLOR = "#000000";// 饼状图边缘圆环默认颜色
    private static final int DEFAULT_STROLE_WIDTH = 2;// 默认圆边的宽度
    private static final int DEFAULT_RADIUS = 100;// 默认饼状图半径，不包括边缘
    private static final int DEFAULT_SEPARATE_DISTENCE = 10;// 被选中的item分离的距离
    private static final int TIME_HANDLER_DELY = 10;// 旋转间隔10毫秒
    private static final float MIN_ANIMSPEED = (float) 0.5;// 旋转间隔最小度数
    private static final float MAX_ANIMSPEED = (float) 10.0;// 旋转间隔最大度数
    private float rotateSpeed = (float) 1; // 旋转间隔度数，用来表示速度
    private float total;// 总大小
    private float[] itemSizesTemp;// 传递过来的items
    private float[] itemsSizes;// 最终的items
    private String[] itemsColors;// 每一项的颜色
    private float[] itemsAngle;// 每一项所占的角度
    private float[] itemsBeginAngle;// 每一项的起始角度
    private float[] itemsRate;// 每一块占的比例
    private float rotateAng = 0;// 用于旋转时的递减角度
    private float lastAng = 0;// 旋转至角度
    private boolean bClockWise; // 顺时针旋转
    private boolean isRotating;// 正在旋转，则点击无效
    private boolean isAnimEnabled = true;
    private String radiusBorderStrokeColor;// 边缘颜色
    private float strokeWidth = 0;// 边缘半径
    private float radius;// 内圆半径
    private int itemPostion = -1;// 被点击的块的id
    private int rotateWhere = 1;// 转到什么位置
    private float separateDistence = 10;// 被选中的块分离的距离
    private Handler rotateHandler = new Handler();
    private static final String TAG = "ParBarView";
    int position;
    private int totalNum;
    private RectF oval;
    private boolean isDraging = false;
    private Point center;
    private Point lastEventPoint;
    private GestureDetector mGDetector;
    private int extraHeight = 50;

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.radiusBorderStrokeColor = DEAFULT_BORDER_COLOR;// 外环颜色，默认黑色
        mGDetector = new GestureDetector(context, this);
        invalidate();
    }

    public void setRaduis(int radius) {
        if (radius < 0) {
            this.radius = DEFAULT_RADIUS;
        } else {
            this.radius = radius;
        }
        invalidate();
    }

    public void setItemsColors(String[] colors) {
        if (itemsSizes != null && itemsSizes.length > 0) {
            if (colors == null) {
                setDefaultColor();
            } else if (colors.length < itemsSizes.length) {
                this.itemsColors = colors;
                setLeftColor();
            } else {
                this.itemsColors = colors;
            }
        }
        invalidate();
    }

    public void setItemsSizes(float[] items) {
        if (items != null && items.length > 0) {
            this.itemSizesTemp = items;
            reSetTotal();
            refreshItemsAngs();
            setItemsColors(itemsColors);
        }
        invalidate();
    }

    public void setActualTotal(int total) {
        this.totalNum = total;
    }

    public void setTotal(int total) {
        this.total = total;

        reSetTotal();

        invalidate();
    }

    public void relaseTotal(int total) {
        this.total = total;
    }

    public float getTotal() {
        return this.total;
    }

    public void setAnimEnabled(boolean isAnimEnabled) {
        this.isAnimEnabled = isAnimEnabled;
        invalidate();
    }

    public boolean isAnimEnabled() {
        return isAnimEnabled;
    }

    public void setRotateSpeed(float rotateSpeed) {
        if (rotateSpeed < MIN_ANIMSPEED) {
            rotateSpeed = MIN_ANIMSPEED;
        }
        if (rotateSpeed > MAX_ANIMSPEED) {
            rotateSpeed = MAX_ANIMSPEED;
        }
        this.rotateSpeed = rotateSpeed;
    }

    public float getRotateSpeed() {
        if (isAnimEnabled()) {
            return rotateSpeed;
        } else {
            return 0;
        }
    }

    public void setExtraHeight(int extraHeight) {
        this.extraHeight = extraHeight;
    }

    public void setShowItem(int position, boolean anim, boolean listen) {
        if (itemsSizes != null && position < itemsSizes.length && position >= 0) {
            this.itemPostion = position;
            this.position = position;
            if (listen) {
                notifySelectedListeners(position, itemsColors[position], itemsSizes[position], itemsRate[position], isPositionFree(position), getAnimTime(Math.abs(lastAng - rotateAng)));// 发出选中条目的消息
            }

            if (this.rotateWhere == NO_ROTATE) {

            } else {
                lastAng = getLastRotateAngle(position);
                if (startDegree == 0)
                    startDegree = lastAng;

                if (anim) {
                    rotateAng = 0;
                    if (lastAng > 0) {
                        bClockWise = true;
                    } else {
                        bClockWise = false;
                    }
                    isRotating = true;
                } else {
                    rotateAng = lastAng;
                }
                rotateHandler.postDelayed(this, 1);
            }

        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float bigRadius = radius + strokeWidth;
        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        if (strokeWidth != 0) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.parseColor(radiusBorderStrokeColor));
            paint.setStrokeWidth(strokeWidth);
            canvas.drawCircle(centerX, centerY, bigRadius, paint);
        }

        canvas.save();
        if (itemsAngle != null && itemsBeginAngle != null) {
            float rigthBottom = 2 * (radius + strokeWidth) + separateDistence;
            float leftTop = separateDistence;
            if (!isDraging) {
                canvas.rotate(rotateAng, centerX, centerY);
                paint.setStrokeWidth(1);
                oval = new RectF(leftTop, leftTop, rigthBottom, rigthBottom);
                for (int i = 0; i < itemsAngle.length; i++) {
                    if (itemPostion == i && !isRotating) {
                        switch (rotateWhere) {
                            case TO_RIGHT:
                                oval = new RectF(leftTop, leftTop, rigthBottom + separateDistence, rigthBottom);
                                break;
                            case TO_TOP:
                                oval = new RectF(leftTop, leftTop - separateDistence, rigthBottom, rigthBottom);
                                break;
                            case TO_BOTTOM:
                                oval = new RectF(leftTop, leftTop, rigthBottom, rigthBottom + separateDistence);
                                break;
                            case TO_LEFT:
                                oval = new RectF(leftTop - separateDistence, leftTop, rigthBottom, rigthBottom);
                                break;
                            default:
                                break;
                        }
                    } else {
                        oval = new RectF(leftTop, leftTop, rigthBottom, rigthBottom);
                    }
                    paint.setColor(Color.parseColor(itemsColors[i]));
                    canvas.drawArc(oval, itemsBeginAngle[i], itemsAngle[i], true, paint);
                }
            } else {
                float startAngle = this.startDegree;
                oval = new RectF(leftTop, leftTop, rigthBottom, rigthBottom);
                for (int i = 0; i < itemsAngle.length; i++) {
                    if (position == i) {
                        switch (rotateWhere) {
                            case TO_RIGHT:
                                oval = new RectF(leftTop, leftTop, rigthBottom + separateDistence, rigthBottom);
                                break;
                            case TO_TOP:
                                oval = new RectF(leftTop, leftTop - separateDistence, rigthBottom, rigthBottom);
                                break;
                            case TO_BOTTOM:
                                oval = new RectF(leftTop, leftTop, rigthBottom, rigthBottom + separateDistence);
                                break;
                            case TO_LEFT:
                                oval = new RectF(leftTop - separateDistence, leftTop, rigthBottom, rigthBottom);
                                break;
                            default:
                                break;
                        }
                    } else {
                        oval = new RectF(leftTop, leftTop, rigthBottom, rigthBottom);
                    }
                    if (i > 0) {
                        startAngle += itemsAngle[i - 1];
                    }
                    paint.setColor(Color.parseColor(itemsColors[i]));
                    canvas.drawArc(oval, startAngle, itemsAngle[i], true, paint);
                }
            }

            canvas.restore();
            Paint p1 = new Paint();
            p1.setAntiAlias(true);
            p1.setColor(Color.parseColor("#F0EFF3"));
            canvas.drawCircle(centerX, centerY, radius / 1.6f, p1);
        }
    }

    public String getShowItemColor() {
        return itemsColors[itemPostion];
    }

    private float startDegree = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!isRotating && itemsSizes != null && itemsSizes.length > 0) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    isRotating = false;
                    if (isDraging) {
                        onStop();
                    }
                    break;
                default:
                    break;
            }
            mGDetector.onTouchEvent(event);
        }
        return true;
    }

    private void onStop() {
        float targetAngle = getTargetDegree();
        position = getEventPart(targetAngle);
        float offset = getOffsetOfPartCenter(targetAngle, position);
        startDegree += offset;
        lastAng = getLastRotateAngle(position);
        resetBeginAngle(lastAng);
        notifySelectedListeners(position, itemsColors[position], itemsSizes[position], itemsRate[position], isPositionFree(position), getAnimTime(Math.abs(lastAng - rotateAng)));// 发出选中条目的消息
        invalidate();
    }

    protected float getOffsetOfPartCenter(float degree, int targetIndex) {
        float currentSum = 0;
        for (int i = 0; i <= targetIndex; i++) {
            currentSum += itemsAngle[i];
        }
        float offset = degree - (currentSum - itemsAngle[targetIndex] / 2);
        return offset;
    }

    protected int getEventPart(float degree) {
        float currentSum = 0;

        for (int i = 0; i < itemsAngle.length; i++) {
            currentSum += itemsAngle[i];
            if (currentSum >= degree) {
                return i;
            }
        }
        return -1;
    }

    private void rotate(Point eventPoint, int newDegree) {
        int lastDegree = getEventAngle(lastEventPoint, center);
        startDegree += newDegree - lastDegree;
        if (startDegree >= 360) {
            startDegree -= 360;
        } else if (startDegree <= -360) {
            startDegree += 360;
        }
        if (null != pieChartSlideLinstener) {
            pieChartSlideLinstener.OnPieChartSlide();
        }

        postInvalidate();
    }

    protected float getTargetDegree() {
        float targetDegree = -1;
        float tmpStart = startDegree;
        if (tmpStart < 0) {
            tmpStart += 360;
        }
        if (tmpStart < 90) {
            targetDegree = 90 - tmpStart;
        } else {
            targetDegree = 360 + 90 - tmpStart;
        }
        return targetDegree;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        rotateHandler.removeCallbacks(this);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float widthHeight = (float) (2 * (radius + strokeWidth + separateDistence));
        setMeasuredDimension((int) widthHeight, (int) widthHeight);
    }

    public void run() {
        if (bClockWise) {
            rotateAng += rotateSpeed;//每次旋转的速度
            invalidate();
            rotateHandler.postDelayed(this, TIME_HANDLER_DELY);
            if (rotateAng - lastAng >= 0) {
                rotateAng = 0;
                rotateHandler.removeCallbacks(this);
                resetBeginAngle(lastAng);
                // invalidate();
                isRotating = false;
            }
        } else {
            rotateAng -= rotateSpeed;
            invalidate();
            rotateHandler.postDelayed(this, TIME_HANDLER_DELY);
            if (rotateAng - lastAng <= 0) {
                rotateAng = 0;
                rotateHandler.removeCallbacks(this);
                resetBeginAngle(lastAng);
                // invalidate();
                isRotating = false;
            }
        }
    }

    private void refreshItemsAngs() {
        if (itemSizesTemp != null && itemSizesTemp.length > 0) {
            if (getTotal() > getAllSizes()) {
                itemsSizes = new float[itemSizesTemp.length + 1];
                for (int m = 0; m < itemSizesTemp.length; m++) {
                    itemsSizes[m] = itemSizesTemp[m];
                }
                itemsSizes[itemsSizes.length - 1] = getTotal() - getAllSizes();
            } else {
                itemsSizes = new float[itemSizesTemp.length];
                itemsSizes = itemSizesTemp;
            }

            itemsRate = new float[itemsSizes.length];// 每一项所占的比例
            itemsBeginAngle = new float[itemsSizes.length];// 每一个角度临界点
            itemsAngle = new float[itemsSizes.length];// 每一个角度临界点
            float beginAngle = 0;

            for (int i = 0; i < itemsSizes.length; i++) {
                itemsRate[i] = (float) (itemsSizes[i] * 1.0 / getTotal() * 1.0);
            }

            for (int i = 0; i < itemsRate.length; i++) {
                if (i == 1) {
                    beginAngle = 360 * itemsRate[i - 1];
                } else if (i > 1) {
                    beginAngle = 360 * itemsRate[i - 1] + beginAngle;
                }
                itemsBeginAngle[i] = beginAngle;
                itemsAngle[i] = 360 * itemsRate[i];
            }
        }

    }

    private boolean isPositionFree(int position) {
        if (position == itemsSizes.length - 1 && getTotal() > getAllSizes()) {
            return true;
        } else {
            return false;
        }
    }

    private float getAnimTime(float ang) {
        return (int) Math.floor((ang / getRotateSpeed()) * TIME_HANDLER_DELY);
    }

    private float getTouchedPointAngle(float x, float y, float x1, float y1) {

        float ax = x1 - x;
        float ay = y1 - y;
        ay = -ay;
        double a = 0;
        double t = ay / Math.sqrt((double) (ax * ax + ay * ay));
        if (ax > 0) {
            if (ay > 0)
                a = Math.asin(t);
            else
                a = 2 * Math.PI + Math.asin(t);
        } else {
            if (ay > 0)
                a = Math.PI - Math.asin(t);
            else
                a = Math.PI - Math.asin(t);
        }
        return (float) (360 - (a * 180 / (Math.PI)) % (360));
    }
    private int getShowItem(float ang) {
        int position = 0;
        for (int i = 0; i < itemsBeginAngle.length; i++) {
            if (i != itemsBeginAngle.length - 1) {
                if (ang >= itemsBeginAngle[i] && ang < itemsBeginAngle[i + 1]) {
                    position = i;
                    break;
                }
            } else {
                if (ang > itemsBeginAngle[itemsBeginAngle.length - 1] && ang < itemsBeginAngle[0]) {
                    position = itemsSizes.length - 1;
                } else if (isUpperSort(itemsBeginAngle) || isLowerSort(itemsBeginAngle)) {
                    position = itemsSizes.length - 1;
                } else {
                    position = getPointItem(itemsBeginAngle);
                }
            }
        }
        return position;
    }

    private float getLastRotateAngle(int position) {
        float result = 0;
        result = itemsBeginAngle[position];
        result = itemsBeginAngle[position] + (itemsAngle[position]) / 2 + getRotateWhereAngle();
        if (result >= 360) {
            result -= 360;
        }
        if (result <= 180) {
            result = -result;
        } else {
            result = 360 - result;
        }
        return result;
    }

    private boolean isUpperSort(float[] all) {
        boolean result = true;
        float temp = all[0];
        for (int a = 0; a < all.length - 1; a++) {
            if ((all[a + 1] - temp) > 0) {
                temp = all[a + 1];
            } else {
                return false;
            }
        }
        return result;
    }

    private boolean isLowerSort(float[] all) {
        boolean result = true;
        float temp = all[0];
        for (int a = 0; a < all.length - 1; a++) {
            if ((all[a + 1] - temp) < 0) {
                temp = all[a + 1];
            } else {
                return false;
            }
        }
        return result;
    }

    private int getPointItem(float[] all) {
        int item = 0;

        float temp = all[0];
        for (int a = 0; a < all.length - 1; a++) {
            if ((all[a + 1] - temp) > 0) {
                temp = all[a];
            } else {
                return a;
            }
        }

        return item;
    }

    private void resetBeginAngle(float angle) {
        for (int i = 0; i < itemsBeginAngle.length; i++) {
            float newBeginAngle = itemsBeginAngle[i] + angle;

            if (newBeginAngle < 0) {
                itemsBeginAngle[i] = newBeginAngle + 360;
            } else if (newBeginAngle > 360) {
                itemsBeginAngle[i] = newBeginAngle - 360;
            } else {
                itemsBeginAngle[i] = newBeginAngle;
            }

        }
    }

    private void setDefaultColor() {
        if (itemsSizes != null && itemsSizes.length > 0 && itemsColors == null) {
            itemsColors = new String[itemsSizes.length];
            if (itemsColors.length <= DEFAULT_ITEMS_COLORS.length) {
                System.arraycopy(DEFAULT_ITEMS_COLORS, 0, itemsColors, 0, itemsColors.length);
            } else {
                int multiple = itemsColors.length / DEFAULT_ITEMS_COLORS.length;
                int left = itemsColors.length % DEFAULT_ITEMS_COLORS.length;

                for (int a = 0; a < multiple; a++) {
                    System.arraycopy(DEFAULT_ITEMS_COLORS, 0, itemsColors, a * DEFAULT_ITEMS_COLORS.length, DEFAULT_ITEMS_COLORS.length);
                }
                if (left > 0) {
                    System.arraycopy(DEFAULT_ITEMS_COLORS, 0, itemsColors, multiple * DEFAULT_ITEMS_COLORS.length, left);
                }
            }
        }

    }

    private void setLeftColor() {

        if (itemsSizes != null && itemsSizes.length > itemsColors.length) {
            String[] preItemsColors = new String[itemsColors.length];
            preItemsColors = itemsColors;
            int leftall = itemsSizes.length - itemsColors.length;
            itemsColors = new String[itemsSizes.length];
            System.arraycopy(preItemsColors, 0, itemsColors, 0, preItemsColors.length);// 先把设定的颜色加上
            if (leftall <= DEFAULT_ITEMS_COLORS.length) {
                System.arraycopy(DEFAULT_ITEMS_COLORS, 0, itemsColors, preItemsColors.length, leftall);
            } else {
                int multiple = leftall / DEFAULT_ITEMS_COLORS.length;
                int left = leftall % DEFAULT_ITEMS_COLORS.length;
                for (int a = 0; a < multiple; a++) {
                    System.arraycopy(DEFAULT_ITEMS_COLORS, 0, itemsColors, a * DEFAULT_ITEMS_COLORS.length, DEFAULT_ITEMS_COLORS.length);
                }
                if (left > 0) {
                    System.arraycopy(DEFAULT_ITEMS_COLORS, 0, itemsColors, multiple * DEFAULT_ITEMS_COLORS.length, left);
                }
            }
        }
    }

    private void reSetTotal() {
        // 不允许all小于总和
        float totalSizes = getAllSizes();
        if (getTotal() < totalSizes) {
            this.total = totalSizes;
        }
    }

    private float getAllSizes() {
        float tempAll = 0;
        if (itemSizesTemp != null && itemSizesTemp.length > 0) {
            for (float itemsize : itemSizesTemp) {
                tempAll += itemsize;
            }
        }

        return tempAll;
    }

    private float getRotateWhereAngle() {

        float result = 0;
        switch (rotateWhere) {
            case TO_RIGHT:
                result = 0;
                break;
            case TO_LEFT:
                result = 180;
                break;
            case TO_TOP:
                result = 90;
                break;
            case TO_BOTTOM:
                result = 270;
                break;

            default:
                break;
        }
        return result;
    }

    private List<OnPieChartItemSelectedLinstener> itemSelectedListeners = new LinkedList<OnPieChartItemSelectedLinstener>();
    private OnPieChartSlideLinstener pieChartSlideLinstener;


    public OnPieChartSlideLinstener getPieChartSlideLinstener() {
        return pieChartSlideLinstener;
    }

    public void setPieChartSlideLinstener(
            OnPieChartSlideLinstener pieChartSlideLinstener) {
        this.pieChartSlideLinstener = pieChartSlideLinstener;
    }

    public void setOnItemSelectedListener(OnPieChartItemSelectedLinstener listener) {
        itemSelectedListeners.add(listener);
    }

    public void removeItemSelectedListener(OnPieChartItemSelectedLinstener listener) {
        itemSelectedListeners.remove(listener);
    }

    protected void notifySelectedListeners(int position, String colorRgb, float size, float rate, boolean isFreePart, float animTime) {
        for (OnPieChartItemSelectedLinstener listener : itemSelectedListeners) {
            listener.onPieChartItemSelected(this, position, colorRgb, size, rate, isFreePart, animTime);
        }
    }

    protected void notifyTriggerClickListeners() {
        for (OnPieChartItemSelectedLinstener listener : itemSelectedListeners) {
            listener.onTriggerClicked();
        }
    }


    protected void computeCenter() {
        if (center == null) {
            int x = (int) oval.left + (int) ((oval.right - oval.left) / 2f);
            int y = (int) oval.top + (int) ((oval.bottom - oval.top) / 2f) + extraHeight; //状态栏的高度是50
            center = new Point(x, y);
        }
    }

    protected int getEventAngle(Point eventPoint, Point center) {
        int x = eventPoint.x - center.x;//x轴方向的偏移量  
        int y = eventPoint.y - center.y; //y轴方向的偏移量  
        double z = Math.hypot(Math.abs(x), Math.abs(y)); //求直角三角形斜边的长度
        double sinA = (double) Math.abs(y) / z;
        double asin = Math.asin(sinA); //求反正玄，得到当前点和x轴的角度,是最小的那个
        int degree = (int) (asin / 3.14f * 180f);
        int realDegree = 0;
        if (x <= 0 && y <= 0) {
            realDegree = 180 + degree;

        } else if (x >= 0 && y <= 0) {
            realDegree = 360 - degree;
        } else if (x <= 0 && y >= 0) {
            realDegree = 180 - degree;
        } else {
            realDegree = degree;

        }
        return realDegree;

    }

    protected Point getEventAbsoluteLocation(MotionEvent event) {
        int[] location = new int[2];
        this.getLocationOnScreen(location); //当前控件在屏幕上的位置      
        int x = (int) event.getX();
        int y = (int) event.getY();
        x += location[0];
        y += location[1]; //这样x,y就代表当前事件相对于整个屏幕的坐标  
        Point p = new Point(x, y);
        return p;
    }


    @Override
    public boolean onDown(MotionEvent e) {
        Point eventPoint = getEventAbsoluteLocation(e);
        computeCenter(); //计算中心坐标

        isDraging = false;
        lastEventPoint = eventPoint;

        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Point eventPoint = getEventAbsoluteLocation(e);
        lastEventPoint = eventPoint;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        float x1 = e.getX();
        float y1 = e.getY();
        float clickPoint = (getWidth() / 2 - x1) * (getWidth() / 2 - x1) + (getHeight() / 2 - y1) * (getHeight() / 2 - y1) - (radius / 1.6f) * (radius / 1.6f);
        float r = radius + strokeWidth;// 圆的半径
        if (clickPoint <= 0) {
        } else {
            x1 = e.getX();
            y1 = e.getY();
            position = getShowItem(getTouchedPointAngle(r, r, x1, y1));
            setShowItem(position, isAnimEnabled(), true);
            startDegree += lastAng;
        }

        isDraging = false;

        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        isDraging = true;
        Point eventPoint = getEventAbsoluteLocation(e2);
        computeCenter();
        int newAngle = getEventAngle(eventPoint, center);

        position = -1;
        rotate(eventPoint, newAngle);
        lastEventPoint = eventPoint;

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }


}
