package star.liuwen.com.cash_books.Activity;

import android.widget.ImageView;
import android.widget.TextView;

import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.bean.BaseModel;

/**
 * Created by liuwen on 2017/4/10.
 */
public class PayShowDetailActivity extends BaseActivity {
    private TextView tvName, tvNameValue, tvTime, tvAccount, tvType;
    private ImageView imageName;
    private BaseModel model;

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

        tvName = (TextView) findViewById(R.id.txt_name);
        tvNameValue = (TextView) findViewById(R.id.tv_name_value);
        tvTime = (TextView) findViewById(R.id.txt_time);
        tvType = (TextView) findViewById(R.id.txt_zhichuorshouru);
        tvAccount = (TextView) findViewById(R.id.txt_account);
        imageName = (ImageView) findViewById(R.id.image_url);
        model = (BaseModel) getIntent().getExtras().getSerializable(Config.PayShowDetailModel);
        if (null != model) {
            tvName.setText(model.getName());
            tvNameValue.setText(String.format("%.2f", model.getMoney()));
            String[] timeMinsecs = model.getTimeMinSec().split("-");
            String timeMinSec = timeMinsecs[0] + "-" + timeMinsecs[1] + "-" + timeMinsecs[2];
            tvTime.setText(timeMinSec);
            tvAccount.setText(model.getAccountType());
            imageName.setImageResource(model.getUrl());
            if (model.getZhiChuShouRuType().equals(Config.ZHI_CHU)) {
                tvType.setText("支出(记账宝提醒您合理消费)");
            } else {
                tvType.setText("收入(您马上就要走上人生巅峰了)");
            }
        }
    }


    private void deleteBaseModel() {

    }
}
