package star.liuwen.com.cash_books.Activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bigkoo.pickerview.TimePickerView;

import java.util.Date;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import star.liuwen.com.cash_books.Base.App;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Enage.DataEnige;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.View.SelectRemindCyclePopup;
import star.liuwen.com.cash_books.bean.ZhiChuModel;

import static com.bigkoo.pickerview.TimePickerView.Type.HOURS_MINS;

/**
 * Created by liuwen on 2017/1/17.
 */
public class RemindActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImageView;
    private RelativeLayout rlRemindTime, rlRemindCycle;
    private TextView txtRemindTime, txtRemindCycle;
    private TimePickerView pvTime;
    private int cycle;
    private String cycleDay;


    @Override
    public int activityLayoutRes() {
        return R.layout.remind_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setLeftImage(R.mipmap.fanhui_lan);
        setTitle(getString(R.string.remind));
        setLeftText(getString(R.string.back));
        setRightText(getString(R.string.finish), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSure();
            }
        });

        mImageView = (ImageView) findViewById(R.id.remind_push);
        rlRemindTime = (RelativeLayout) findViewById(R.id.remind_time);
        rlRemindCycle = (RelativeLayout) findViewById(R.id.remind_cycle);
        txtRemindCycle = (TextView) findViewById(R.id.remind_cycle_show);
        txtRemindTime = (TextView) findViewById(R.id.remind_time_show);

        txtRemindTime.setText(TextUtils.isEmpty(App.cycleTime) ? getString(R.string.no_setting) : App.cycleTime);
        txtRemindCycle.setText(TextUtils.isEmpty(App.cycleData) ? getString(R.string.no_setting) : App.cycleData);

        boolean isRemind = SharedPreferencesUtil.getBooleanPreferences(this, Config.isRemindPush, false);
        mImageView.setImageResource(isRemind ? R.mipmap.more_push_on : R.mipmap.more_push_off);
        rlRemindCycle.setVisibility(isRemind ? View.VISIBLE : View.GONE);
        rlRemindTime.setVisibility(isRemind ? View.VISIBLE : View.GONE);

        rlRemindCycle.setOnClickListener(this);
        rlRemindTime.setOnClickListener(this);

        pvTime = new TimePickerView(this, TimePickerView.Type.HOURS_MINS);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                txtRemindTime.setText(DateTimeUtil.getCurrentTimeHourMIn(date));
                App.cycleTime = DateTimeUtil.getCurrentTimeHourMIn(date);
            }
        });
    }

    private void onSure() {
        App.cycleData = cycleDay;
        finish();
    }


    public void toSwitchPush(View view) {
        mImageView.setImageResource(!App.isRemindPush ? R.mipmap.more_push_on : R.mipmap.more_push_off);
        rlRemindCycle.setVisibility(!App.isRemindPush ? View.VISIBLE : View.GONE);
        rlRemindTime.setVisibility(!App.isRemindPush ? View.VISIBLE : View.GONE);
        App.isRemindPush = !App.isRemindPush;
        SharedPreferencesUtil.setBooleanPreferences(this, Config.isRemindPush, App.isRemindPush);
    }

    @Override
    public void onClick(View v) {
        if (v == rlRemindCycle) {
            showPopWindowCycle();
        } else if (v == rlRemindTime) {
            pvTime.show();
        }
    }


    private void showPopWindowCycle() {
        final SelectRemindCyclePopup fp = new SelectRemindCyclePopup(this);
        fp.showPopup(rlRemindCycle);
        fp.setOnSelectRemindCyclePopupListener(new SelectRemindCyclePopup
                .SelectRemindCyclePopupOnClickListener() {

            @Override
            public void obtainMessage(int flag, String ret) {
                switch (flag) {
                    // 星期一
                    case 0:

                        break;
                    // 星期二
                    case 1:

                        break;
                    // 星期三
                    case 2:

                        break;
                    // 星期四
                    case 3:

                        break;
                    // 星期五
                    case 4:

                        break;
                    // 星期六
                    case 5:

                        break;
                    // 星期日
                    case 6:

                        break;
                    // 确定
                    case 7:
                        int repeat = Integer.valueOf(ret);
                        cycleDay = parseRepeat(repeat, 0);
                        txtRemindCycle.setText(cycleDay);
                        cycle = repeat;
                        fp.dismiss();
                        break;
                    case 8:
                        cycleDay = getString(R.string.everyday);
                        txtRemindCycle.setText(cycleDay);
                        cycle = 0;
                        fp.dismiss();
                        break;
                    case 9:
                        cycleDay = getString(R.string.ring);
                        txtRemindCycle.setText(cycleDay);
                        cycle = -1;
                        fp.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * @param repeat 解析二进制闹钟周期
     * @param flag   flag=0返回带有汉字的周一，周二cycle等，flag=1,返回weeks(1,2,3)
     * @return
     */
    public static String parseRepeat(int repeat, int flag) {
        String cycle = "";
        String weeks = "";
        if (repeat == 0) {
            repeat = 127;
        }
        if (repeat % 2 == 1) {
            cycle = "周一";
            weeks = "1";
        }
        if (repeat % 4 >= 2) {
            if ("".equals(cycle)) {
                cycle = "周二";
                weeks = "2";
            } else {
                cycle = cycle + "," + "周二";
                weeks = weeks + "," + "2";
            }
        }
        if (repeat % 8 >= 4) {
            if ("".equals(cycle)) {
                cycle = "周三";
                weeks = "3";
            } else {
                cycle = cycle + "," + "周三";
                weeks = weeks + "," + "3";
            }
        }
        if (repeat % 16 >= 8) {
            if ("".equals(cycle)) {
                cycle = "周四";
                weeks = "4";
            } else {
                cycle = cycle + "," + "周四";
                weeks = weeks + "," + "4";
            }
        }
        if (repeat % 32 >= 16) {
            if ("".equals(cycle)) {
                cycle = "周五";
                weeks = "5";
            } else {
                cycle = cycle + "," + "周五";
                weeks = weeks + "," + "5";
            }
        }
        if (repeat % 64 >= 32) {
            if ("".equals(cycle)) {
                cycle = "周六";
                weeks = "6";
            } else {
                cycle = cycle + "," + "周六";
                weeks = weeks + "," + "6";
            }
        }
        if (repeat / 64 == 1) {
            if ("".equals(cycle)) {
                cycle = "周日";
                weeks = "7";
            } else {
                cycle = cycle + "," + "周日";
                weeks = weeks + "," + "7";
            }
        }

        return flag == 0 ? cycle : weeks;
    }

}
