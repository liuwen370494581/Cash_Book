package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.EventBus.C;
import star.liuwen.com.cash_books.EventBus.Event;
import star.liuwen.com.cash_books.R;

/**
 * Created by liuwen on 2017/1/13.
 */
public class SaveMoneyActivity extends BaseActivity {
    private LinearLayout lyCjShow;
    private ViewStub mViewStub;
    private TextView tvSaveMoney;
    private RelativeLayout rePlan;

    @Override
    public int activityLayoutRes() {
        return R.layout.save_money_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setLeftText(getString(R.string.back));
        setLeftImage(R.mipmap.fanhui_lan);
        lyCjShow = (LinearLayout) findViewById(R.id.re_save_money);
        mViewStub = (ViewStub) findViewById(R.id.view_stub);
        tvSaveMoney = (TextView) findViewById(R.id.txt_save_money);
        rePlan = (RelativeLayout) findViewById(R.id.re_save_money_plan);

        final String values = getIntent().getStringExtra("plan");
        if (values.equals("cunqian")) {
            setTitle(getString(R.string.save_money_plan));

        } else if (values.equals("zhangdan")) {
            setTitle(getString(R.string.zhangdan_zongjie));
            lyCjShow.setVisibility(View.GONE);
            mViewStub.inflate();
            tvSaveMoney.setText(getString(R.string.zhangdan_xiaofei));
        }

        rePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (values.equals("cunqian")) {
                    startActivity(new Intent(SaveMoneyActivity.this, PlanSaveMoneyActivity.class));
                } else if (values.equals("zhangdan")) {
                    startActivity(new Intent(SaveMoneyActivity.this, FixXiaofeiCheckActivity.class));
                }
            }
        });
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        switch (event.getCode()) {
            case C.EventCode.Close:
                //接收计划页面传递过来 当确定建立一个计划 需要关闭页面
                SaveMoneyActivity.this.finish();
                break;
        }
    }

}
