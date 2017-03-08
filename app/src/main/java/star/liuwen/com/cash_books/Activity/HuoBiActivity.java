package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;

/**
 * Created by liuwen on 2017/2/7.
 */
public class HuoBiActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout reHuoBi;
    private TextView txtHuoBiCh, txtHuoBiEn;


    @Override
    public int activityLayoutRes() {
        return R.layout.huo_bi_activity;
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.huobi_setting));
        setLeftImage(R.mipmap.fanhui_lan);
        setLeftText(getString(R.string.back));
        setBackView();

        reHuoBi = (RelativeLayout) findViewById(R.id.re_huobi);
        txtHuoBiCh = (TextView) findViewById(R.id.txt_huobi_ch);
        txtHuoBiEn = (TextView) findViewById(R.id.txt_huobi_en);

        reHuoBi.setOnClickListener(this);

        txtHuoBiCh.setText(SharedPreferencesUtil.getStringPreferences(this, Config.HuoBICh, "").isEmpty() ? getString(R.string.no_setting) : SharedPreferencesUtil.getStringPreferences(this, Config.HuoBICh, ""));
        txtHuoBiEn.setText(SharedPreferencesUtil.getStringPreferences(this, Config.HuoBIEn, "").isEmpty() ? getString(R.string.no_setting) : SharedPreferencesUtil.getStringPreferences(this, Config.HuoBIEn, ""));
    }

    private static final int HuoBICode = 101;

    @Override
    public void onClick(View v) {
        if (v == reHuoBi) {
            startActivityForResult(new Intent(this, HuoBiSettingActivity.class), HuoBICode);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case HuoBICode:
                txtHuoBiCh.setText(data.getExtras().getString(Config.HuoBICh));
                txtHuoBiEn.setText(data.getExtras().getString(Config.HuoBIEn));
                SharedPreferencesUtil.setStringPreferences(this, Config.HuoBICh, data.getExtras().getString(Config.HuoBICh));
                SharedPreferencesUtil.setStringPreferences(this, Config.HuoBIEn, data.getExtras().getString(Config.HuoBIEn));
                break;
        }
    }
}
