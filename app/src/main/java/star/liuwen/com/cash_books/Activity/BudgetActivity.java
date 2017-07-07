package star.liuwen.com.cash_books.Activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoBudget;
import star.liuwen.com.cash_books.EventBus.C;
import star.liuwen.com.cash_books.EventBus.Event;
import star.liuwen.com.cash_books.EventBus.EventBusUtil;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.SnackBarUtil;
import star.liuwen.com.cash_books.View.WaveLoadingView;
import star.liuwen.com.cash_books.bean.BudgetModel;

/**
 * Created by liuwen on 2017/5/11.
 * 预算页面
 */
public class BudgetActivity extends BaseActivity implements View.OnClickListener {
    private WaveLoadingView mCircleProgress;
    private RelativeLayout reBudgetMonth, reBudgetMonthShow;
    private TextView txtBudgetMonth;
    private ImageView imageBudget;
    private boolean isOpenBudget;
    private String budgetMoney;//预算金额
    private BudgetModel model;

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
        //结果以MB为单位进行返回，我们开发时应用程序的内存不能超过这个限制，否则会出现OOM。
        ActivityManager manager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        int heapSize = manager.getMemoryClass();//256M  超出256M  软件才会oom

        reBudgetMonthShow = (RelativeLayout) findViewById(R.id.re_budget_show);
        reBudgetMonth = (RelativeLayout) findViewById(R.id.re_budget_month);
        txtBudgetMonth = (TextView) findViewById(R.id.budget_txt_time);
        mCircleProgress = (WaveLoadingView) findViewById(R.id.circle_progress);
        imageBudget = (ImageView) findViewById(R.id.budget_push);
        mCircleProgress.setMoneySize(55);
        mCircleProgress.setDescribeSize(25);

        model = (BudgetModel) getIntent().getExtras().getSerializable(Config.ModelBudget);
        if (model != null) {
            if (model.getOpenBudget()) {
                mCircleProgress.setMoney(model.getBudgetMoney());
                mCircleProgress.setPercent(model.getBudgetRemainMoney());
                mCircleProgress.setDescribe(model.getBudgetDescription());
                txtBudgetMonth.setText(model.getBudgetMoney());
            }
            isOpenBudget = model.getOpenBudget();
        }
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
        UpdateAccountBudget();
        this.finish();
    }

    public void toSwitchPush(View view) {
        imageBudget.setImageResource(!isOpenBudget ? R.mipmap.more_push_on : R.mipmap.more_push_off);
        reBudgetMonth.setVisibility(!isOpenBudget ? View.VISIBLE : View.GONE);
        reBudgetMonthShow.setVisibility(!isOpenBudget ? View.VISIBLE : View.GONE);
        setRightTxtVisible(!isOpenBudget);
        isOpenBudget = !isOpenBudget;
        if (!isOpenBudget) {
            //还原预算设置
            mCircleProgress.setMoney(getString(R.string.ling));
            mCircleProgress.setPercent(0);
            mCircleProgress.setDescribe(getString(R.string.no_setting_month_Budget));
            txtBudgetMonth.setText(getString(R.string.ling));
            UpdateAccountBudget();
        }
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
        model.setBudgetMoney(budgetMoney == null ? getString(R.string.calendar_jia) : budgetMoney);
        model.setOpenBudget(isOpenBudget);
        model.setBudgetDescription(isOpenBudget ? getString(R.string.monthBudget) : getString(R.string.no_setting_month_Budget));
        model.setBudgetRemainMoney(isOpenBudget ? 100 : 0);
        DaoBudget.update(model);
        EventBusUtil.sendEvent(new Event(C.EventCode.BudgetActivityToHomeFragment, model));
    }


    /**
     * TRIM_MEMORY_COMPLETE：内存不足，并且该进程在后台进程列表最后一个，马上就要被清理
     * TRIM_MEMORY_MODERATE：内存不足，并且该进程在后台进程列表的中部。
     * TRIM_MEMORY_BACKGROUND：内存不足，并且该进程是后台进程。
     * TRIM_MEMORY_UI_HIDDEN：内存不足，并且该进程的UI已经不可见了。
     * 以上4个是4.0增加
     * TRIM_MEMORY_RUNNING_CRITICAL：内存不足(后台进程不足3个)，并且该进程优先级比较高，需要清理内存
     * TRIM_MEMORY_RUNNING_LOW：内存不足(后台进程不足5个)，并且该进程优先级比较高，需要清理内存
     * TRIM_MEMORY_RUNNING_MODERATE：内存不足(后台进程超过5个)，并且该进程优先级比较高，需要清理内存
     * 以上3个是4.1增加
     * 系统也提供了一个ComponentCallbacks2，通过Context.registerComponentCallbacks()注册后，就会被系统回调到。
     *
     * @param level
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            case TRIM_MEMORY_BACKGROUND:

                break;

            case TRIM_MEMORY_UI_HIDDEN:
                //当UI隐藏掉的时候 释放资源  让内存释放
                break;
        }
    }
}
