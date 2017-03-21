package star.liuwen.com.cash_books.PieChart;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Calendar;

import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.ToastUtils;


public class StatisticsView extends ViewGroup implements OnClickListener {

    private Context context;
    private View view;
    private TextView mLast, mCurrent, mNext, mFindDetail, mItemDetail;
    private CountView txtMonery;
    private int mLastDate, mCurrDate, mNextDate, mYear, mDay;
    private int mMaxMonth, mMaxYear, mMinMonth, mMinYear;
    private String startDate, endDate;
    private OnDateChangedLinstener mDateChangedListener;
    private OnClickDetailListener mListener;
    private PieChartView pieChart;
    private String[] colors = {"#bc3c46", "#fe992a", "#c03c9a", "#11b60d", "#fa376c"};
    private float[] items;
    private TextView txtView3;
    private float animSpeed = 7f;
    private int total;
    private String[] type;
    private ViewStub mViewStub;

    private RelativeLayout infoLayout, layoutBaseInfo, reShow;
    private LinearLayout layout_title;

    public StatisticsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StatisticsView(Context context, float[] items, int total,
                          String[] type) {
        super(context);
        this.context = context;
        this.items = items;
        this.total = total;
        this.type = type;
        initView();
    }

    private void initView() {
        view = LayoutInflater.from(context).inflate(
                R.layout.statistics_layout, null);

        txtMonery = (CountView) view.findViewById(R.id.txtMonery);
        infoLayout = (RelativeLayout) view.findViewById(R.id.infoLayout);
        layoutBaseInfo = (RelativeLayout) view.findViewById(R.id.layoutBaseInfo);
        txtView3 = (TextView) view.findViewById(R.id.txtView3);

        layout_title = (LinearLayout) view.findViewById(R.id.layout_title);

        mLast = (TextView) view.findViewById(R.id.last);
        mCurrent = (TextView) view.findViewById(R.id.curr);
        mNext = (TextView) view.findViewById(R.id.next);
        mFindDetail = (TextView) view.findViewById(R.id.find_detail);
        mItemDetail = (TextView) view.findViewById(R.id.item_xiaofei_detail);

        reShow = (RelativeLayout) view.findViewById(R.id.pieLayout);
        mViewStub = (ViewStub) view.findViewById(R.id.view_stub);
        mViewStub.inflate();

        mLast.setOnClickListener(this);
        mCurrent.setOnClickListener(this);
        mNext.setOnClickListener(this);
        mFindDetail.setOnClickListener(this);
        intitPieChart();
        this.addView(view);
        initDate();
    }

    public int getBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 38;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {
            e1.printStackTrace();
            sbar = 50;
        }
        return sbar;
    }

    private int getExtraHeight() {
        layout_title.measure(0, 0);
        int titleHeight = layout_title.getMeasuredHeight();
        int barHeight = getBarHeight();
        return titleHeight + barHeight;
    }

    private void intitPieChart() {
        pieChart = (PieChartView) view.findViewById(R.id.parbar_view);
        pieChart.setAnimEnabled(true);// 是否开启动画
        pieChart.setItemsColors(colors);// 设置各个块的颜色
        pieChart.setItemsSizes(items);// 设置各个块的值
        pieChart.setRotateSpeed(animSpeed);// 设置旋转速度
        pieChart.setTotal(100);
        pieChart.setActualTotal(total);
        pieChart.setExtraHeight(getExtraHeight());
        DisplayMetrics dm = getResources().getDisplayMetrics();
        pieChart.setRaduis((int) (dm.widthPixels / 2.5));// 设置饼状图半径
        pieChart.setOnItemSelectedListener(new OnPieChartItemSelectedLinstener() {
            public void onPieChartItemSelected(PieChartView view, int position,
                                               String colorRgb, float size, float rate,
                                               boolean isFreePart, float rotateTime) {

                try {
                    txtView3.setTextColor(Color.parseColor(pieChart.getShowItemColor()));
                    if (isFreePart) {
                    } else {
                        float percent = (float) (Math.round(size * 100)) / 100;
                        txtView3.setText(type[position]);
                    }
                    if (total > 0)
                        infoLayout.setVisibility(View.VISIBLE);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f,
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                            ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation.setDuration(800);
                    scaleAnimation.setFillAfter(true);
                    scaleAnimation.setInterpolator(new BounceInterpolator());
                    layoutBaseInfo.startAnimation(scaleAnimation);
                    mItemDetail.setText(String.format(("%s元"), items[position]));
                    mItemDetail.setTextColor(Color.parseColor(pieChart.getShowItemColor()));
//					Animation myAnimation_Alpha = new AlphaAnimation(0.1f, 1.0f);
//					myAnimation_Alpha.setDuration((int) (3 * rotateTime));
//					textInfo.startAnimation(myAnimation_Alpha);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onTriggerClicked() {
                Toast.makeText(context, "点击了切换按钮!",
                        Toast.LENGTH_SHORT).show();
            }

        });
        pieChart.setPieChartSlideLinstener(new OnPieChartSlideLinstener() {
            @Override
            public void OnPieChartSlide() {
                infoLayout.setVisibility(View.GONE);
            }
        });
        pieChart.setShowItem(0, true, true);// 设置显示的块
    }

    private void initDate() {
        Calendar c = Calendar.getInstance();
        mMaxYear = mYear = c.get(Calendar.YEAR);
        mMinMonth = mMaxMonth = mCurrDate = c.get(Calendar.MONTH) + 1;
        mLastDate = mCurrDate - 1;
        mNextDate = mCurrDate + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mMinYear = mMaxYear - 1;
        freshDate();

        txtMonery.setNumber(3870.30f);
        txtMonery.showNumberWithAnimation(3870.30f);
    }

    /**
     * 设置消费明细
     *
     * @param sb
     */
    public void setConsumeDetail(float sb) {
        if (txtMonery != null) {
            txtMonery.setNumber(sb);
            txtMonery.showNumberWithAnimation(sb);
        }
    }

    public void setViewStubVisible(boolean isShow) {
        if (mViewStub != null) {
            if (isShow) {
                mViewStub.setVisibility(GONE);
            } else {
                mViewStub.setVisibility(VISIBLE);
            }
        }
    }

    public void setReVisible(boolean isShow) {
        if (reShow != null) {
            if (isShow) {
                infoLayout.setVisibility(VISIBLE);
                pieChart.setVisibility(VISIBLE);
                reShow.setVisibility(VISIBLE);
            } else {
                infoLayout.setVisibility(GONE);
                pieChart.setVisibility(GONE);
                reShow.setVisibility(GONE);
            }
        }
    }

    public void setCurrDate(int year, int month) {
        mMaxYear = mYear = year;
        mMinMonth = mMaxMonth = mCurrDate = month;
        mNextDate = mCurrDate + 1;
        mLastDate = mCurrDate - 1;
        mMinYear = mMaxYear - 1;
        freshDate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
        child.layout(l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeigth = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeigth);
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            int widthSpec = 0;
            int heightSpec = 0;
            LayoutParams params = v.getLayoutParams();
            if (params.width > 0) {
                widthSpec = MeasureSpec.makeMeasureSpec(params.width,
                        MeasureSpec.EXACTLY);
            } else if (params.width == -1) {
                widthSpec = MeasureSpec.makeMeasureSpec(measureWidth,
                        MeasureSpec.EXACTLY);
            } else if (params.width == -2) {
                widthSpec = MeasureSpec.makeMeasureSpec(measureWidth,
                        MeasureSpec.AT_MOST);
            }

            if (params.height > 0) {
                heightSpec = MeasureSpec.makeMeasureSpec(params.height,
                        MeasureSpec.EXACTLY);
            } else if (params.height == -1) {
                heightSpec = MeasureSpec.makeMeasureSpec(measureHeigth,
                        MeasureSpec.EXACTLY);
            } else if (params.height == -2) {
                heightSpec = MeasureSpec.makeMeasureSpec(measureHeigth,
                        MeasureSpec.AT_MOST);
            }
            v.measure(widthSpec, heightSpec);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.last:
                if (mDateChangedListener != null) {
                    if (mMinYear >= mYear && mLastDate < mMinMonth) {
                        Toast.makeText(context, "只能查询一年内的数据哦!",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mLastDate == 1) {
                        mLastDate = 12;
                        mCurrDate--;
                        mNextDate--;
                    } else if (mLastDate == 12) {
                        mLastDate--;
                        mCurrDate = 12;
                        mNextDate--;
                        mYear--;
                    } else if (mLastDate == 11) {
                        mLastDate--;
                        mCurrDate--;
                        mNextDate = 12;
                    } else {
                        mLastDate--;
                        mCurrDate--;
                        mNextDate--;
                    }
                    freshDate();
                    String startDate = mYear + "-" + mCurrDate + "-" + "1 00:00:00";
                    String endDate = mYear + "-" + (mCurrDate + 1) + "-"
                            + "1 00:00:00";
                    mDateChangedListener.onDateChanged(startDate, endDate);

                }
                break;

            case R.id.next:
                if (mDateChangedListener != null) {

                    if (mMaxYear == mYear && mNextDate > mMaxMonth) {
                        Toast.makeText(context, "还没有这个月的数据哦!",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (mNextDate == 12) {
                        mLastDate++;
                        mCurrDate++;
                        mNextDate = 1;
                    } else if (mNextDate == 1) {
                        mLastDate++;
                        mCurrDate = 1;
                        mNextDate++;
                        mYear++;
                    } else if (mNextDate == 2) {
                        mLastDate = 1;
                        mCurrDate++;
                        mNextDate++;
                    } else {
                        mLastDate++;
                        mCurrDate++;
                        mNextDate++;
                    }
                    freshDate();

                    String startDate = mYear + "-" + mCurrDate + "-1 00:00:00";
                    String endDate = mYear + "-" + (mCurrDate + 1) + "-1 00:00:00";
                    mDateChangedListener.onDateChanged(startDate, endDate);
                }
                break;

            case R.id.find_detail:
                mListener.showDetail();
                break;
            default:
                break;
        }
    }

    public void freshDate() {
        mLast.setText(mLastDate + "月");
        mCurrent.setText(mYear + "年" + mCurrDate + "月");
        mNext.setText(mNextDate + "月");
    }


    public void upDateView() {
        view.invalidate();
    }

    public void setDateChangedListener(
            OnDateChangedLinstener mDateChangedListener) {
        this.mDateChangedListener = mDateChangedListener;
    }

    public void setDetailListener(OnClickDetailListener mClickDetailListener) {
        this.mListener = mClickDetailListener;

    }

    public interface OnClickDetailListener {
        void showDetail();
    }
}