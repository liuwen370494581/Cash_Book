package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.MainActivity;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.bean.PlanSaveMoneyModel;

/**
 * Created by liuwen on 2017/3/22.
 */
public class newAddAccountActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout reBank, reAccount, reType, reMoney, reCreditLimit, reDebt, reDebtData;
    private TextView txtBank, txtAccount, txtType, txtMoney, txtCreditLimit, txtDebt, txtDebtData;
    private PlanSaveMoneyModel model;

    @Override
    public int activityLayoutRes() {
        return R.layout.new_add_account_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setLeftText(getString(R.string.back));
        setLeftImage(R.mipmap.fanhui_lan);
        setRightText(getString(R.string.sure), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSure();
            }
        });

        reBank = (RelativeLayout) findViewById(R.id.re_setting_bank);
        reAccount = (RelativeLayout) findViewById(R.id.re_setting_account);
        reType = (RelativeLayout) findViewById(R.id.re_setting_tyoe);
        reMoney = (RelativeLayout) findViewById(R.id.re_setting_money);
        reCreditLimit = (RelativeLayout) findViewById(R.id.re_setting_Credit_limit);
        reDebt = (RelativeLayout) findViewById(R.id.re_setting_debt);
        reDebtData = (RelativeLayout) findViewById(R.id.re_setting_debt_date);

        txtBank = (TextView) findViewById(R.id.setting_txt_bank);
        txtAccount = (TextView) findViewById(R.id.setting_txt_account);
        txtType = (TextView) findViewById(R.id.setting_txt_type);
        txtMoney = (TextView) findViewById(R.id.setting_txt_money);
        txtCreditLimit = (TextView) findViewById(R.id.setting_txt_Credit_limit);
        txtDebt = (TextView) findViewById(R.id.setting_txt_debt);
        txtDebtData = (TextView) findViewById(R.id.setting_txt_debt_date);

        reBank.setOnClickListener(this);
        reAccount.setOnClickListener(this);
        reType.setOnClickListener(this);
        reMoney.setOnClickListener(this);
        reCreditLimit.setOnClickListener(this);
        reDebt.setOnClickListener(this);
        reDebtData.setOnClickListener(this);

        model = (PlanSaveMoneyModel) getIntent().getExtras().getSerializable(Config.ModelChoiceAccount);
        if (model != null) {
            txtType.setText(model.getPlanName());
            if (model.getPlanName().equals(Config.XYK)) {
                setTitle(String.format(getString(R.string.new_built), model.getAdd()));
                reMoney.setVisibility(View.GONE);
                txtAccount.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountMoney, "").isEmpty() ? model.getPlanName() : SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountName, ""));
                txtBank.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountMoney, "").isEmpty() ? getString(R.string.empty_string) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountName, ""));
                txtCreditLimit.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtCreditLimit, "").isEmpty() ? getString(R.string.ling) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtCreditLimit, ""));
                txtDebt.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtDebt, "").isEmpty() ? getString(R.string.ling) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtDebt, ""));
            } else if (model.getPlanName().equals(Config.CXK)) {
                setTitle(String.format(getString(R.string.new_built), model.getAdd()));
                txtAccount.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountMoney, "").isEmpty() ? model.getPlanName() : SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountName, ""));
                txtBank.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountMoney, "").isEmpty() ? getString(R.string.empty_string) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountName, ""));
                setAccountVisible();
            } else if (model.getPlanName().equals(Config.ZFB)) {
                setTitle(String.format(getString(R.string.new_built), model.getAdd()));
                reBank.setVisibility(View.GONE);
                txtAccount.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountMoney, "").isEmpty() ? model.getPlanName() : SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountName, ""));
                setAccountVisible();
            } else if (model.getPlanName().equals(Config.CASH)) {
                setTitle(String.format(getString(R.string.new_built), model.getAdd()));
                txtAccount.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountMoney, "").isEmpty() ? model.getPlanName() : SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountName, ""));
                reBank.setVisibility(View.GONE);
                setAccountVisible();
            } else if (model.getPlanName().equals(Config.JC)) {
                setTitle(String.format(getString(R.string.new_built), model.getAdd()));
                reBank.setVisibility(View.GONE);
                setAccountVisible();
            } else if (model.getPlanName().equals(Config.JR)) {
                setTitle(String.format(getString(R.string.new_built), model.getAdd()));
                reBank.setVisibility(View.GONE);
                setAccountVisible();
            } else if (model.getPlanName().equals(Config.WEIXIN)) {
                setTitle(String.format(getString(R.string.new_built), model.getAdd()));
                txtAccount.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountMoney, "").isEmpty() ? model.getPlanName() : SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountName, ""));
                reBank.setVisibility(View.GONE);
                setAccountVisible();
            } else if (model.getPlanName().equals(Config.CZK)) {
                setTitle(String.format(getString(R.string.new_built), model.getAdd()));
                txtAccount.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountMoney, "").isEmpty() ? model.getPlanName() : SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountName, ""));
                reBank.setVisibility(View.GONE);
                setAccountVisible();
            } else if (model.getPlanName().equals(Config.TOUZI)) {
                setTitle(String.format(getString(R.string.new_built), model.getAdd()));
                txtAccount.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountMoney, "").isEmpty() ? model.getPlanName() : SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountName, ""));
                reBank.setVisibility(View.GONE);
                setAccountVisible();
            } else if (model.getPlanName().equals(Config.INTENTACCOUNT)) {
                setTitle(String.format(getString(R.string.new_built), model.getAdd()));
                txtAccount.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountMoney, "").isEmpty() ? model.getPlanName() : SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountName, ""));
                reBank.setVisibility(View.GONE);
                setAccountVisible();
            }
        }
    }

    private void doSure() {
        String tvMoney = txtMoney.getText().toString();
        String tvBank = txtBank.getText().toString();
        String tvCreditLimit = txtCreditLimit.getText().toString();
        String tvDebt=txtDebt.getText().toString();

        Intent intent = new Intent(newAddAccountActivity.this, MainActivity.class);
        intent.putExtra("id", 2);
        startActivity(intent);
    }

    /**
     * 通用的隐藏
     */
    public void setAccountVisible() {
        reCreditLimit.setVisibility(View.GONE);
        reDebt.setVisibility(View.GONE);
        reDebtData.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(newAddAccountActivity.this, UpdateCommonKeyBoardActivity.class);
        if (v == reBank) {
            startActivityForResult(new Intent(newAddAccountActivity.this, ChoiceIssuingBankActivity.class), ChoiceIssuingBank);
        } else if (v == reAccount) {
            intent.putExtra(Config.SaveAPenPlatform, "AccountName");
            startActivityForResult(intent, ACCOUNT);
        } else if (v == reMoney) {
            intent.putExtra(Config.SaveAPenPlatform, "AccountMoney");
            startActivityForResult(intent, MONEY);
        } else if (v == reCreditLimit) {
            intent.putExtra(Config.SaveAPenPlatform, "CreditLimit");
            startActivityForResult(intent, CreditLimit);
        } else if (v == reDebt) {
            intent.putExtra(Config.SaveAPenPlatform, "Debt");
            startActivityForResult(intent, Debt);
        } else if (v == reDebtData) {

        }
    }


    private static final int ACCOUNT = 101;
    private static final int MONEY = 102;
    private static final int CreditLimit = 103;
    private static final int Debt = 104;
    private static final int ChoiceIssuingBank = 105;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case ACCOUNT:
                txtAccount.setText(data.getExtras().getString(Config.TextInPut));
                break;
            case MONEY:
                txtMoney.setText(data.getExtras().getString(Config.TextInPut));
                break;
            case CreditLimit:
                txtCreditLimit.setText(data.getExtras().getString(Config.TextInPut));
                break;
            case Debt:
                txtDebt.setText(data.getExtras().getString(Config.TextInPut));
                break;
            case ChoiceIssuingBank:
                txtBank.setText(data.getExtras().getString("bank"));
                break;
        }
    }
}
