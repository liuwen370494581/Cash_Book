package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import star.liuwen.com.cash_books.Adapter.PopWindowAdapter;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoChoiceAccount;
import star.liuwen.com.cash_books.Dao.DaoSaveMoneyPlan;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.bean.SaveMoneyPlanModel;

/**
 * Created by liuwen on 2017/2/15.
 */
public class SaveAPenActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout reSavePlatform, reMoney, reYield, reAccount, reStartTime, reEndTime, reRemark;
    private TextView txtSavePlatform, txtMoney, txtYield, txtAccount, txtStartTime, txtEndTime, txtRemark;
    private ListView mListView;
    private PopupWindow window;
    private PopWindowAdapter mPopWindowAdapter;
    private String AccountType, savePlatForm, moneys, percent, remarks;
    private TimePickerView pvTime;
    private int position;
    private Date startDate, endDate;
    private List<SaveMoneyPlanModel> mList = new ArrayList<>();
    private static int i = 1;


    @Override

    public int activityLayoutRes() {
        return R.layout.save_a_pen_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setLeftText(getString(R.string.back));
        setTitle(getString(R.string.save_a_pen));
        setLeftImage(R.mipmap.fanhui_lan);
        setRightText(getString(R.string.sure), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSure();
            }
        });

        reSavePlatform = (RelativeLayout) findViewById(R.id.re_save_a_pen_platform);
        reMoney = (RelativeLayout) findViewById(R.id.re_save_a_pen_money);
        reYield = (RelativeLayout) findViewById(R.id.re_save_a_pen_yield);
        reAccount = (RelativeLayout) findViewById(R.id.re_save_a_pen_account);
        reStartTime = (RelativeLayout) findViewById(R.id.re_save_a_pen_start_time);
        reEndTime = (RelativeLayout) findViewById(R.id.re_save_a_pen_end_time);
        reRemark = (RelativeLayout) findViewById(R.id.re_save_a_pen_remark);

        txtSavePlatform = (TextView) findViewById(R.id.txt_save_a_pen_platform);
        txtMoney = (TextView) findViewById(R.id.txt_save_a_pen_money);
        txtYield = (TextView) findViewById(R.id.txt_save_a_pen_yield);
        txtAccount = (TextView) findViewById(R.id.txt_save_a_pen_account);
        txtStartTime = (TextView) findViewById(R.id.txt_save_a_pen_start_time);
        txtEndTime = (TextView) findViewById(R.id.txt_save_a_pen_end_time);
        txtRemark = (TextView) findViewById(R.id.txt_save_a_pen_remark);

        reSavePlatform.setOnClickListener(this);
        reMoney.setOnClickListener(this);
        reYield.setOnClickListener(this);
        reAccount.setOnClickListener(this);
        reStartTime.setOnClickListener(this);
        reEndTime.setOnClickListener(this);
        reRemark.setOnClickListener(this);

        txtSavePlatform.setText(SharedPreferencesUtil.getStringPreferences(this, Config.SaveAPenPlatform, "").isEmpty() ? "" : SharedPreferencesUtil.getStringPreferences(this, Config.SaveAPenPlatform, "'"));
        txtRemark.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtRemark, "").isEmpty() ? "" : SharedPreferencesUtil.getStringPreferences(this, Config.TxtRemark, ""));
        txtAccount.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccount, "").isEmpty() ? "" : SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccount, ""));
        initDate();
    }

    private void doSure() {
        String savePlatform = txtSavePlatform.getText().toString().trim();
        String money = txtMoney.getText().toString().trim();
        String yield = txtYield.getText().toString().trim();
        String account = txtAccount.getText().toString().trim();
        String startTime = txtStartTime.getText().toString().trim();
        String endTime = txtEndTime.getText().toString().trim();
        String remark = txtRemark.getText().toString().trim();
        if (savePlatform.isEmpty()) {
            ToastUtils.showToast(this, "存款平台不能为空");
            return;
        }
        if (money.isEmpty() || money.equals("0.00")) {
            ToastUtils.showToast(this, "金额不能为空");
            return;
        }
        if (yield.isEmpty() || yield.equals("0.00%")) {
            ToastUtils.showToast(this, "收益率不能为空");
            return;
        }
        if (account.isEmpty()) {
            ToastUtils.showToast(this, "账户不能为空");
            return;
        }
        if (startTime.isEmpty()) {
            ToastUtils.showToast(this, "起息时间不能为空");
            return;
        }
        if (endTime.isEmpty()) {
            ToastUtils.showToast(this, "结束时间不能为空");
            return;
        }
        String[] str = startTime.split("-");
        String startTimes = str[0] + str[1] + str[2];
        String[] strs = endTime.split("-");
        String endTimes = strs[0] + strs[1] + strs[2];
        if ((Integer.parseInt(startTimes) - Integer.parseInt(endTimes)) > 0) {
            ToastUtils.showToast(this, "结算时间不能小于起息时间哟");
            return;
        }

        SharedPreferencesUtil.setStringPreferences(SaveAPenActivity.this, Config.SaveAPenPlatform, savePlatForm != null ? savePlatForm : SharedPreferencesUtil.getStringPreferences(this, Config.SaveAPenPlatform, ""));
        SharedPreferencesUtil.setStringPreferences(SaveAPenActivity.this, Config.TxtAccount, AccountType != null ? AccountType : SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccount, ""));
        SharedPreferencesUtil.setStringPreferences(this, Config.TxtMoney, moneys != null ? moneys : SharedPreferencesUtil.getStringPreferences(this, Config.TxtMoney, ""));
        SharedPreferencesUtil.setStringPreferences(this, Config.TxtPercent, percent != null ? percent : SharedPreferencesUtil.getStringPreferences(this, Config.TxtPercent, ""));
        SharedPreferencesUtil.setStringPreferences(this, Config.TxtRemark, remarks != null ? remark : SharedPreferencesUtil.getStringPreferences(this, Config.TxtRemark, ""));

        SaveMoneyPlanModel model = new SaveMoneyPlanModel();
        model.setPlatForm(savePlatform);
        model.setSaveMoney(Double.parseDouble(moneys));
        model.setYield(Double.parseDouble(percent));
        model.setAccountType(AccountType);
        model.setStartTime(DateTimeUtil.getYearMonthDay_(startDate));
        model.setEndTime(DateTimeUtil.getYearMonthDay_(endDate));
        model.setRemark(remarks);
        model.setId(DaoSaveMoneyPlan.getCount());
        mList.add(model);
        RxBus.getInstance().post(Config.ModelSaveAPen, mList);
        this.finish();
    }

    private void initDate() {
        mList = new ArrayList<>();
    }


    private static final int ReSavePlatForm = 101;
    private static final int ReMoney = 102;
    private static final int ReYield = 103;
    private static final int ReRemark = 104;


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(SaveAPenActivity.this, UpdateCommonKeyBoardActivity.class);
        if (v == reSavePlatform) {
            startActivityForResult(new Intent(SaveAPenActivity.this, SavePlatformActivity.class), ReSavePlatForm);
        } else if (v == reMoney) {
            intent.putExtra(Config.SaveAPenPlatform, "reMoney");
            startActivityForResult(intent, ReMoney);
        } else if (v == reYield) {
            intent.putExtra(Config.SaveAPenPlatform, "reYield");
            startActivityForResult(intent, ReYield);
        } else if (v == reAccount) {
            showAccount();
            if (window.isShowing()) {
                window.dismiss();
            } else {
                window.showAtLocation(reAccount, Gravity.BOTTOM, 0, 0);
                backgroundAlpha(0.5f);
            }
        } else if (v == reStartTime) {
            position = 1;
            showDate(position);

        } else if (v == reEndTime) {
            position = 2;
            showDate(position);
        } else if (v == reRemark) {
            intent.putExtra(Config.SaveAPenPlatform, "reRemark");
            startActivityForResult(intent, ReRemark);
        }
    }

    private void showDate(int position) {
        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 30);
        pvTime.setTime(new Date());
        //设置是否循环
        pvTime.setCyclic(false);
        //设置是否可以关闭
        pvTime.setCancelable(true);
        //设置选择监听
        switch (position) {
            case 1:
                pvTime.setTitle("起息时间");

                pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        startDate = date;
                        txtStartTime.setText(DateTimeUtil.getYearMonthDay_(date));

                    }
                });
                break;
            case 2:
                pvTime.setTitle("结束时间");
                pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        endDate = date;
                        txtEndTime.setText(DateTimeUtil.getYearMonthDay_(date));

                    }
                });
                break;
        }
        //显示
        pvTime.show();
    }


    private void showAccount() {
        View popView = View.inflate(this, R.layout.pop_zhanghu_dialog, null);
        mListView = (ListView) popView.findViewById(R.id.lv_popup_list);
        window = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindowAdapter = new PopWindowAdapter(this, R.layout.item_pop_account);
        mPopWindowAdapter.setData(DaoChoiceAccount.query());
        mListView.setAdapter(mPopWindowAdapter);
        window.setFocusable(true);
        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.setAnimationStyle(R.style.AnimBottom);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                window.dismiss();
                AccountType = mPopWindowAdapter.getItem(position).getAccountName();
                mPopWindowAdapter.setShowGou(AccountType);
                txtAccount.setText(AccountType);
            }
        });
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setOutsideTouchable(true);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });


        mPopWindowAdapter.setShowGou(AccountType);

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case ReSavePlatForm:
                savePlatForm = data.getExtras().getString("bank");
                txtSavePlatform.setText(data.getExtras().getString("bank"));
                break;
            case ReMoney:
                moneys = data.getExtras().getString(Config.TextInPut);
                txtMoney.setText(data.getExtras().getString(Config.TextInPut));
                break;

            case ReYield:
                percent = data.getExtras().getString(Config.TextInPut);
                txtYield.setText(data.getExtras().getString(Config.TextInPut) + "%");
                break;
            case ReRemark:
                remarks = data.getExtras().getString(Config.TextInPut);
                txtRemark.setText(data.getExtras().getString(Config.TextInPut));
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().removeObserverable(Config.ModelSaveAPen);
    }
}





