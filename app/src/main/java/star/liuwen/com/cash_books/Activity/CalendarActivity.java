package star.liuwen.com.cash_books.Activity;

import android.view.WindowManager;
import android.widget.EditText;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;

/**
 * Created by liuwen on 2016/12/28.
 */
public class CalendarActivity extends BaseActivity {

    private EditText mEditText;

    @Override
    public int activityLayoutRes() {
        return R.layout.calendar_activity;
    }

    @Override
    public void initView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DatePicker datePicker = (DatePicker) findViewById(R.id.main_dp);
        datePicker.setTodayDisplay(true);
        String currentDate = DateTimeUtil.getCurrentTime_Today();
        String[] sCurrentDate = currentDate.split("-");
        String year = sCurrentDate[0];
        String month = sCurrentDate[1];
        datePicker.setDate(Integer.parseInt(year), Integer.parseInt(month));
        datePicker.setMode(DPMode.SINGLE);
        //单选模式
        datePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                ToastUtils.showToast(CalendarActivity.this, date);
            }
        });


    }
}
