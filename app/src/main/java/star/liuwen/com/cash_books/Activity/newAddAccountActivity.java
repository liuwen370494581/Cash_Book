package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoChoiceAccount;
import star.liuwen.com.cash_books.MainActivity;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.View.DatePickerDialog;
import star.liuwen.com.cash_books.bean.ChoiceAccount;
import star.liuwen.com.cash_books.bean.PlanSaveMoneyModel;

/**
 * Created by liuwen on 2017/3/22.
 * 添加账户页面
 */
public class newAddAccountActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout reBank, reAccount, reType, reMoney, reCreditLimit, reDebt, reDebtData;
    private TextView txtBank, txtAccount, txtType, txtMoney, txtCreditLimit, txtDebt, txtDebtData;
    private PlanSaveMoneyModel model;
    private DatePickerDialog dialog;
    private List<ChoiceAccount> walletList = new ArrayList<>();
    private boolean isAccountName = false;
    private boolean isMoneyOrCredit = false;
    private String tvDebtDate;


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
        dialog = new DatePickerDialog(this);
        dialog.setCallback(new DatePickerDialog.OnClickCallback() {
            @Override
            public void onCancel() {
                dialog.dismiss();
            }

            @Override
            public void onSure(String data) {
                txtDebtData.setText(data);
                tvDebtDate = data;
                dialog.dismiss();
            }
        });


        model = (PlanSaveMoneyModel) getIntent().getExtras().getSerializable(Config.ModelChoiceAccount);
        if (model != null) {
            txtType.setText(model.getPlanName());
            if (model.getPlanName().equals(Config.XYK)) {
                setTitle(String.format(getString(R.string.new_built), model.getAdd()));
                reMoney.setVisibility(View.GONE);
                txtAccount.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountMoney, "").isEmpty() ? model.getPlanName() : SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountName, ""));
                txtBank.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountMoney, "").isEmpty() ? getString(R.string.empty_string) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountName, ""));
                txtCreditLimit.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtCreditLimit, "").isEmpty() ? getString(R.string.empty_string) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtCreditLimit, ""));
                txtDebt.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtDebt, "").isEmpty() ? getString(R.string.empty_string) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtDebt, ""));
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
        String tvAccount = txtAccount.getText().toString();
        String tvMoney = txtMoney.getText().toString();
        String tvBank = txtBank.getText().toString();
        String tvCreditLimit = txtCreditLimit.getText().toString();
        String tvDebt = txtDebt.getText().toString();
        String tvDebtDate = txtDebtData.getText().toString();

        if (TextUtils.isEmpty(tvAccount.trim())) {
            ToastUtils.showToast(this, "账户名不能为空");
            return;
        }

        if (model.getPlanName().equals(Config.XYK)) {
            if (TextUtils.isEmpty(tvBank.trim())) {
                ToastUtils.showToast(this, "发卡行不能为空");
                return;
            }
            if (TextUtils.isEmpty(tvCreditLimit.trim())) {
                ToastUtils.showToast(this, "信用卡额度不能为空");
                return;
            }
            if (TextUtils.isEmpty(tvDebt.trim())) {
                ToastUtils.showToast(this, "欠款不能为空");
                return;
            }
            if (TextUtils.isEmpty(tvDebtDate.trim())) {
                ToastUtils.showToast(this, "还款日不能为空");
                return;
            }
        } else if (model.getPlanName().equals(Config.CXK)) {
            if (TextUtils.isEmpty(tvMoney.trim())) {
                ToastUtils.showToast(this, "金额不能为空");
                return;
            }
            if (TextUtils.isEmpty(tvBank.trim())) {
                ToastUtils.showToast(this, "发卡行不能为空");
                return;
            }
        } else {
            if (TextUtils.isEmpty(tvMoney.trim())) {
                ToastUtils.showToast(this, "金额不能为空");
                return;
            }
        }
        int y = 1 + (int) (Math.random() * 10000000);
        ChoiceAccount account = new ChoiceAccount(DaoChoiceAccount.getCount() + y,
                model.getWalletUrl(),
                isAccountName ? TextUtils.isEmpty(tvAccount.trim()) ? "" : tvAccount : model.getPlanName(),
                isMoneyOrCredit ? TextUtils.isEmpty(tvMoney) ? 0.00 : Double.parseDouble(tvMoney) : Double.parseDouble(tvCreditLimit),
                TextUtils.isEmpty(tvDebt) ? 0.00 : Double.parseDouble(tvDebt),
                TextUtils.isEmpty(tvDebtDate) ? "" : tvDebtDate,
                TextUtils.isEmpty(tvBank.trim()) ? "" : tvBank,
                model.getColor(), model.getPlanName(), 0.00, 0.00, DateTimeUtil.getCurrentTime_Today());
        DaoChoiceAccount.insertChoiceAccount(account);
        RxBus.getInstance().post(Config.RxModelToWalletFragment, account);
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
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().removeObserverable(Config.RxModelToWalletFragment);
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
            dialog.show();
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
                isAccountName = true;
                break;
            case MONEY:
                txtMoney.setText(data.getExtras().getString(Config.TextInPut));
                isMoneyOrCredit = true;
                break;
            case CreditLimit:
                txtCreditLimit.setText(data.getExtras().getString(Config.TextInPut));
                isMoneyOrCredit = false;
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
