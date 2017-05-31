package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoChoiceAccount;
import star.liuwen.com.cash_books.Dialog.TipandEditDialog;
import star.liuwen.com.cash_books.EventBus.C;
import star.liuwen.com.cash_books.EventBus.Event;
import star.liuwen.com.cash_books.EventBus.EventBusUtil;
import star.liuwen.com.cash_books.MainActivity;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.View.DatePickerDialog;
import star.liuwen.com.cash_books.bean.ChoiceAccount;

/**
 * Created by liuwen on 2017/2/17.
 */
public class PaySettingsActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout reBank, reAccount, reType, reMoney, reCreditLimit, reDebt, reDebtData, reChoiceColor, reColorBg;
    private TextView txtBank, txtAccount, txtType, txtMoney, txtCreditLimit, txtDebt, txtDebtData;
    private ChoiceAccount model;
    private DatePickerDialog dialog;
    private String backData;

    @Override
    public int activityLayoutRes() {
        return R.layout.pay_settings_activity;
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.pay_setting));
        setBackView();
        setLeftText(getString(R.string.back));
        setLeftImage(R.mipmap.fanhui_lan);
        setRightImage(R.mipmap.account_add_shanchu, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteChoiceAccount();
            }
        });

        reBank = (RelativeLayout) findViewById(R.id.re_setting_bank);
        reAccount = (RelativeLayout) findViewById(R.id.re_setting_account);
        reType = (RelativeLayout) findViewById(R.id.re_setting_tyoe);
        reMoney = (RelativeLayout) findViewById(R.id.re_setting_money);
        reCreditLimit = (RelativeLayout) findViewById(R.id.re_setting_Credit_limit);
        reDebt = (RelativeLayout) findViewById(R.id.re_setting_debt);
        reDebtData = (RelativeLayout) findViewById(R.id.re_setting_debt_date);
        reChoiceColor = (RelativeLayout) findViewById(R.id.re_setting_choice_account_color);
        reColorBg = (RelativeLayout) findViewById(R.id.re_setting_color_bg);

        txtBank = (TextView) findViewById(R.id.setting_txt_bank);
        txtAccount = (TextView) findViewById(R.id.setting_txt_account);
        txtType = (TextView) findViewById(R.id.setting_txt_type);
        txtMoney = (TextView) findViewById(R.id.setting_txt_money);
        txtCreditLimit = (TextView) findViewById(R.id.setting_txt_Credit_limit);
        txtDebt = (TextView) findViewById(R.id.setting_txt_debt);
        txtDebtData = (TextView) findViewById(R.id.setting_txt_debt_date);

        dialog = new DatePickerDialog(this);
        dialog.setCallback(new DatePickerDialog.OnClickCallback() {
            @Override
            public void onCancel() {
                dialog.dismiss();
            }

            @Override
            public void onSure(String data) {
                txtDebtData.setText(data);
                dialog.dismiss();
            }
        });

        reBank.setOnClickListener(this);
        reAccount.setOnClickListener(this);
        reType.setOnClickListener(this);
        reMoney.setOnClickListener(this);
        reCreditLimit.setOnClickListener(this);
        reDebt.setOnClickListener(this);
        reDebtData.setOnClickListener(this);
        reChoiceColor.setOnClickListener(this);

        model = (ChoiceAccount) getIntent().getExtras().getSerializable(Config.ModelWallet);
        if (model != null) {
            txtType.setText(model.getMAccountType());
            txtMoney.setText(String.format("%.2f", model.getMoney()));
            reColorBg.setBackgroundResource(model.getColor());
            if (model.mAccountType.equals(Config.XYK)) {
                reMoney.setVisibility(View.GONE);
                txtAccount.setText(model.getAccountName());
                txtBank.setText(model.getIssuingBank());
                txtCreditLimit.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtCreditLimit, "").isEmpty() ? getString(R.string.ling) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtCreditLimit, ""));
                txtDebt.setText(SharedPreferencesUtil.getStringPreferences(this, Config.TxtDebt, "").isEmpty() ? getString(R.string.ling) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtDebt, ""));
            } else if (model.mAccountType.equals(Config.CXK)) {
                txtAccount.setText(model.getAccountName());
                txtBank.setText(model.getIssuingBank());
                setAccountVisible();
            } else if (model.mAccountType.equals(Config.ZFB)) {
                reBank.setVisibility(View.GONE);
                txtAccount.setText(model.getAccountName());
                setAccountVisible();
            } else if (model.mAccountType.equals(Config.CASH)) {
                txtAccount.setText(model.getAccountName());
                reBank.setVisibility(View.GONE);
                setAccountVisible();
            } else if (model.mAccountType.equals(Config.JC)) {
                reBank.setVisibility(View.GONE);
                setAccountVisible();
            } else if (model.mAccountType.equals(Config.JR)) {
                reBank.setVisibility(View.GONE);
                setAccountVisible();
            } else if (model.mAccountType.equals(Config.WEIXIN)) {
                reBank.setVisibility(View.GONE);
                txtAccount.setText(model.getAccountName());
                setAccountVisible();
            } else if (model.mAccountType.equals(Config.CZK)) {
                reBank.setVisibility(View.GONE);
                txtAccount.setText(model.getAccountName());
                setAccountVisible();
            } else if (model.mAccountType.equals(Config.INTENTACCOUNT)) {
                reBank.setVisibility(View.GONE);
                txtAccount.setText(model.getAccountName());
                setAccountVisible();
            } else if (model.mAccountType.equals(Config.TOUZI)) {
                reBank.setVisibility(View.GONE);
                txtAccount.setText(model.getAccountName());
                setAccountVisible();
            }
        }
    }


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        switch (event.getCode()) {
            case C.EventCode.ChoiceColorToPaySettingAndPayShowAndWalletFragment:
                reColorBg.setBackgroundResource((Integer) event.getData());
                break;
        }
    }


    private void deleteChoiceAccount() {
        final TipandEditDialog dialog = new TipandEditDialog(this, "确定要删除这个账户吗");
        dialog.show();
        dialog.setLeftText(getString(R.string.cancel));
        dialog.setLeftTextColor(getResources().getColor(R.color.jiechu));
        dialog.setRightText(getString(R.string.sure));
        dialog.setRightTextColor(getResources().getColor(R.color.blue));
        dialog.setListener(new TipandEditDialog.ITipEndEditDialogListener() {
            @Override
            public void ClickLeft() {
                dialog.dismiss();
            }

            @Override
            public void ClickRight() {
                EventBusUtil.sendEvent(new Event(C.EventCode.PaySettingToWalletFragment, true));
                DaoChoiceAccount.deleteChoiceAccountByModel(model);
                //删除账户的时候 清除选项存储中保存账户的值 避免出现支出或者收入没有及时更新到账户上的bug
                SharedPreferencesUtil.cleanSharePreferences(PaySettingsActivity.this, Config.TxtChoiceAccount);
                Intent intent = new Intent(PaySettingsActivity.this, MainActivity.class);
                intent.putExtra("id", 2);
                startActivity(intent);
                finish();
            }
        });
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
        Intent intent = new Intent(PaySettingsActivity.this, UpdateCommonKeyBoardActivity.class);
        if (v == reBank) {
            startActivityForResult(new Intent(PaySettingsActivity.this, ChoiceIssuingBankActivity.class), ChoiceIssuingBank);
        } else if (v == reAccount) {
            intent.putExtra(Config.SaveAPenPlatform, "AccountName");
            startActivityForResult(intent, ACCOUNT);
        } else if (v == reMoney) {
//            intent.putExtra(Config.SaveAPenPlatform, "AccountMoney");
//            startActivityForResult(intent, MONEY);
        } else if (v == reCreditLimit) {
            intent.putExtra(Config.SaveAPenPlatform, "CreditLimit");
            startActivityForResult(intent, CreditLimit);
        } else if (v == reDebt) {
            intent.putExtra(Config.SaveAPenPlatform, "Debt");
            startActivityForResult(intent, Debt);
        } else if (v == reDebtData) {
            dialog.show();
        } else if (v == reChoiceColor) {
            Intent colorIntent = new Intent(PaySettingsActivity.this, ChoiceAccountColorActivity.class);
            colorIntent.putExtra(Config.ModelWallet, model);
            startActivity(colorIntent);
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
                if (model.getMAccountType().equals(Config.CASH)) {
                    backData = data.getExtras().getString(Config.TextInPut);
                    updateChoiceAccountCommon(true);
                } else if (model.getMAccountType().equals(Config.CXK)) {
                    backData = data.getExtras().getString(Config.TextInPut);
                    updateChoiceAccountCommon(true);
                } else if (model.getMAccountType().equals(Config.XYK)) {
                    backData = data.getExtras().getString(Config.TextInPut);
                    updateChoiceAccountCommon(true);
                } else if (model.getMAccountType().equals(Config.ZFB)) {
                    backData = data.getExtras().getString(Config.TextInPut);
                    updateChoiceAccountCommon(true);
                } else if (model.getMAccountType().equals(Config.JC)) {
                    backData = data.getExtras().getString(Config.TextInPut);
                    updateChoiceAccountCommon(true);
                } else if (model.getMAccountType().equals(Config.JR)) {
                    backData = data.getExtras().getString(Config.TextInPut);
                    updateChoiceAccountCommon(true);
                } else if (model.getMAccountType().equals(Config.TOUZI)) {
                    backData = data.getExtras().getString(Config.TextInPut);
                    updateChoiceAccountCommon(true);
                } else if (model.getMAccountType().equals(Config.CZK)) {
                    backData = data.getExtras().getString(Config.TextInPut);
                    updateChoiceAccountCommon(true);
                } else if (model.getMAccountType().equals(Config.INTENTACCOUNT)) {
                    backData = data.getExtras().getString(Config.TextInPut);
                    updateChoiceAccountCommon(true);
                } else if (model.getMAccountType().equals(Config.WEIXIN)) {
                    backData = data.getExtras().getString(Config.TextInPut);
                    updateChoiceAccountCommon(true);
                }
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
                txtAccount.setText(data.getExtras().getString("bank"));
                SharedPreferencesUtil.setStringPreferences(this, Config.TxtIssuingBank, data.getExtras().getString("bank"));
                if (model.getMAccountType().equals(Config.CXK)) {
                    backData = data.getExtras().getString("bank");
                    updateChoiceAccountCommon(false);
                } else if (model.getMAccountType().equals(Config.XYK)) {
                    backData = data.getExtras().getString("bank");
                    updateChoiceAccountCommon(false);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  RxBus.getInstance().release();
    }

    private void updateChoiceAccountCommon(boolean isIssubank) {
        if (isIssubank) {
            ChoiceAccount choiceModel = DaoChoiceAccount.queryByAccountId(model.getId()).get(0);
            choiceModel.setAccountName(backData);
            DaoChoiceAccount.updateAccount(choiceModel);
            EventBusUtil.sendEvent(new Event(C.EventCode.PaySettingToPayShowActivityAndWalletFragment, model.getId()));
        } else {
            ChoiceAccount choiceModel = DaoChoiceAccount.queryByAccountId(model.getId()).get(0);
            choiceModel.setIssuingBank(backData);
            choiceModel.setAccountName(backData);
            DaoChoiceAccount.updateAccount(choiceModel);
            EventBusUtil.sendEvent(new Event(C.EventCode.PaySettingToPayShowActivityAndWalletFragment, model.getId()));
        }

    }
}
