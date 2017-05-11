package star.liuwen.com.cash_books.Activity;

import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.R;

/**
 * Created by liuwen on 2017/5/11.
 * 预算页面
 */
public class BudgetActivity extends BaseActivity {


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
    }
}
