package star.liuwen.com.cash_books.Activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.bean.BaseModel;

/**
 * Created by liuwen on 2017/4/10.
 */
public class PayShowDetailActivity extends BaseActivity {
    private TextView tvName, tvNameValue, tvTime, tvAccount;
    private ImageView imageName;
    private BaseModel model;
    private int position;
    private HashMap<Integer, BaseModel> mHashMap = new HashMap<>();

    @Override
    public int activityLayoutRes() {
        return R.layout.pay_show_detail_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setLeftImage(R.mipmap.fanhui_lan);
        setTitle(getString(R.string.detail));
        setLeftText(getString(R.string.back));
        setRightImage(R.mipmap.account_add_shanchu, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHashMap.put(position, model);
                RxBus.getInstance().post(Config.RxPayShowDetailToPayShowActivity, mHashMap);
                finish();
            }

        });

        tvName = (TextView) findViewById(R.id.txt_name);
        tvNameValue = (TextView) findViewById(R.id.tv_name_value);
        tvTime = (TextView) findViewById(R.id.txt_time);
        tvAccount = (TextView) findViewById(R.id.txt_account);
        imageName = (ImageView) findViewById(R.id.image_url);
        position = getIntent().getIntExtra(Config.Position, 0);
        model = (BaseModel) getIntent().getExtras().getSerializable(Config.PayShowDetailModel);
        if (null != model) {
            tvName.setText(model.getName());
            tvNameValue.setText(String.format("%.2f", model.getMoney()));
            String[] timeMinsecs = model.getTimeMinSec().split("-");
            String timeMinSec = timeMinsecs[0] + "-" + timeMinsecs[1] + "-" + timeMinsecs[2];
            tvTime.setText(timeMinSec);
            tvAccount.setText(model.getAccountType());
            imageName.setImageResource(model.getUrl());
        }
    }


    private void deleteBaseModel() {

    }
}
