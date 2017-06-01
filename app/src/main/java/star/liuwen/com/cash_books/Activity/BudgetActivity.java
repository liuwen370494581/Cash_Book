package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoAccount;
import star.liuwen.com.cash_books.EventBus.C;
import star.liuwen.com.cash_books.EventBus.Event;
import star.liuwen.com.cash_books.EventBus.EventBusUtil;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.Utils.SnackBarUtil;
import star.liuwen.com.cash_books.View.WaveLoadingView;
import star.liuwen.com.cash_books.bean.AccountModel;

/**
 * Created by liuwen on 2017/5/11.
 * 预算页面
 */
public class BudgetActivity extends BaseActivity implements View.OnClickListener {
    private WaveLoadingView mCircleProgress;
    private RelativeLayout reBudgetMonth, reBudgetMonthShow;
    private TextView txtBudgetMonth;
    private ImageView imageBudget;
    private boolean isOpenBudget = false;
    private String budgetMoney;//预算金额

    @Override
    public int activityLayoutRes() {
        return R.layout.budget_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setTitle("预算");
        setLeftText(getString(R.string.back));
        setLeftImage(R.mipmap.fanhui_lan);
        setRightText(getString(R.string.sure), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSure();
            }
        });

        reBudgetMonthShow = (RelativeLayout) findViewById(R.id.re_budget_show);
        reBudgetMonth = (RelativeLayout) findViewById(R.id.re_budget_month);
        txtBudgetMonth = (TextView) findViewById(R.id.budget_txt_time);
        mCircleProgress = (WaveLoadingView) findViewById(R.id.circle_progress);
        mCircleProgress.setDescribe(getString(R.string.monthBudget));
        mCircleProgress.setMoneySize(50);
        mCircleProgress.setDescribeSize(25);
        imageBudget = (ImageView) findViewById(R.id.budget_push);

        isOpenBudget = SharedPreferencesUtil.getBooleanPreferences(this, Config.isBudgetPush, false);
        String tvYuSuan = SharedPreferencesUtil.getStringPreferences(this, Config.TxtBudgetYuSuan, "");
        txtBudgetMonth.setText(tvYuSuan);
        setRightTxtVisible(isOpenBudget);
        imageBudget.setImageResource(isOpenBudget ? R.mipmap.more_push_on : R.mipmap.more_push_off);
        reBudgetMonthShow.setVisibility(isOpenBudget ? View.VISIBLE : View.GONE);
        reBudgetMonth.setVisibility(isOpenBudget ? View.VISIBLE : View.GONE);
        reBudgetMonth.setOnClickListener(this);
    }

    private void onSure() {

        if (TextUtils.isEmpty(txtBudgetMonth.getText().toString().trim())) {
            SnackBarUtil.show(txtBudgetMonth, "您还没有编辑预算");
            return;
        }
        SharedPreferencesUtil.setBooleanPreferences(this, Config.isBudgetPush, isOpenBudget);
        UpdateAccountBudget();
        this.finish();
    }

    public void toSwitchPush(View view) {
        imageBudget.setImageResource(!isOpenBudget ? R.mipmap.more_push_on : R.mipmap.more_push_off);
        reBudgetMonth.setVisibility(!isOpenBudget ? View.VISIBLE : View.GONE);
        reBudgetMonthShow.setVisibility(!isOpenBudget ? View.VISIBLE : View.GONE);
        setRightTxtVisible(!isOpenBudget);
        isOpenBudget = !isOpenBudget;
    }

    private static final int Budget = 120;

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, UpdateCommonKeyBoardActivity.class);
        if (v == reBudgetMonth) {
            intent.putExtra(Config.SaveAPenPlatform, "Budget");
            startActivityForResult(intent, Budget);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        budgetMoney = data.getExtras().getString(Config.TextInPut);
        switch (requestCode) {
            case Budget:
                txtBudgetMonth.setText(budgetMoney);
                mCircleProgress.setPercent(100);
                mCircleProgress.setMoney(budgetMoney);
                mCircleProgress.setDescribe(getString(R.string.monthBudget));
                break;
        }
    }

    private void UpdateAccountBudget() {
        AccountModel model = DaoAccount.query().get(0);
        model.setBudget(budgetMoney);
        DaoAccount.updateAccount(model);
        EventBusUtil.sendEvent(new Event(C.EventCode.BudgetActivityToHomeFragment, budgetMoney));
    }
}
