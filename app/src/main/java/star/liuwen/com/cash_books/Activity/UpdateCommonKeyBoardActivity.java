package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.GraphicLock.AppUtil;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.ApkInfoUtils;
import star.liuwen.com.cash_books.Utils.KeyboardUtil;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.Utils.SnackBarUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;

/**
 * Created by liuwen on 2017/2/16.
 */
public class UpdateCommonKeyBoardActivity extends BaseActivity {
    private EditText edMoney;
    private KeyboardUtil mKeyboardUtil;
    private int position;
    private boolean isShowInput;
    private KeyboardView mKeyBoardView;


    @Override
    public int activityLayoutRes() {
        return R.layout.update_common_keyboard_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setLeftImage(R.mipmap.fanhui_lan);
        setLeftText(getString(R.string.back));
        edMoney = (EditText) findViewById(R.id.update_common_money);
        mKeyBoardView = (KeyboardView) findViewById(R.id.keyboard_view);
        mKeyboardUtil = new KeyboardUtil(UpdateCommonKeyBoardActivity.this, UpdateCommonKeyBoardActivity.this, edMoney);

        String values = getIntent().getStringExtra(Config.SaveAPenPlatform);
        if (values.equals("reMoney")) {
            setTitle(getString(R.string.edit_money));
            edMoney.setHint(SharedPreferencesUtil.getStringPreferences(this, Config.TxtMoney, "").isEmpty() ? getString(R.string.ling) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtMoney, ""));
            position = 1;
            isShowInput = true;
        } else if (values.equals("reYield")) {
            setTitle(getString(R.string.edit_percent));
            edMoney.setHint(SharedPreferencesUtil.getStringPreferences(this, Config.TxtPercent, "").isEmpty() ? getString(R.string.ling) + "%" : SharedPreferencesUtil.getStringPreferences(this, Config.TxtPercent, ""));
            position = 2;
            isShowInput = true;
        } else if (values.equals("reRemark")) {
            setTitle(getString(R.string.edit_remark));
            edMoney.setHint(SharedPreferencesUtil.getStringPreferences(this, Config.TxtRemark, "").isEmpty() ? getString(R.string.edit_remark) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtRemark, ""));
            position = 3;
            isShowInput = false;
        } else if (values.equals("YuER")) {
            setTitle(getString(R.string.edit_input_yuer));
            edMoney.setHint(SharedPreferencesUtil.getStringPreferences(this, Config.TxtYuEr, "").isEmpty() ? getString(R.string.edit_input_yuer) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtYuEr, ""));
            position = 4;
            isShowInput = true;
        } else if (values.equals("AccountName")) {
            setTitle(getString(R.string.edit_account_name));
            edMoney.setHint(SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountName, "").isEmpty() ? getString(R.string.edit_account_name) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtAccountName, ""));
            position = 5;
            isShowInput = false;
        } else if (values.equals("AccountMoney")) {
            setTitle(getString(R.string.edit_Money));
            edMoney.setHint(SharedPreferencesUtil.getStringPreferences(this, Config.TxtMoney, "").isEmpty() ? getString(R.string.edit_money) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtMoney, ""));
            position = 6;
            isShowInput = true;
        } else if (values.equals("CreditLimit")) {
            setTitle(getString(R.string.edit_credit_limit));
            edMoney.setHint(SharedPreferencesUtil.getStringPreferences(this, Config.TxtCreditLimit, "").isEmpty() ? getString(R.string.edit_credit_limit) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtCreditLimit, ""));
            position = 7;
            isShowInput = true;
        } else if (values.equals("Debt")) {
            setTitle(getString(R.string.edit_debt));
            edMoney.setHint(SharedPreferencesUtil.getStringPreferences(this, Config.TxtDebt, "").isEmpty() ? getString(R.string.edit_debt) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtDebt, ""));
            position = 8;
            isShowInput = true;
        } else if (values.equals("PlanMoney")) {
            setTitle(getString(R.string.edit_Money));
            edMoney.setHint(SharedPreferencesUtil.getStringPreferences(this, Config.TxtPlanMoney, "").isEmpty() ? getString(R.string.edit_money) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtPlanMoney, ""));
            position = 9;
            isShowInput = true;
        } else if (values.equals("PlanReMark")) {
            setTitle(getString(R.string.edit_remark));
            edMoney.setHint(SharedPreferencesUtil.getStringPreferences(this, Config.TxtPlanRemark, "").isEmpty() ? getString(R.string.edit_remark) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtPlanRemark, ""));
            position = 10;
            isShowInput = false;
        } else if (values.equals("reNickName")) {
            setTitle(getString(R.string.edit_renickName));
            edMoney.setHint(SharedPreferencesUtil.getStringPreferences(this, Config.TxtNickName, "").isEmpty() ? getString(R.string.edit_renickName) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtNickName, ""));
            position = 11;
            isShowInput = false;
        } else if (values.equals("reSignature")) {
            setTitle(getString(R.string.edit_reSignature));
            edMoney.setHint(SharedPreferencesUtil.getStringPreferences(this, Config.TxtSigNature, "").isEmpty() ? getString(R.string.edit_reSignature) : SharedPreferencesUtil.getStringPreferences(this, Config.TxtSigNature, ""));
            position = 12;
            isShowInput = false;
        }

        setListener(isShowInput);

    }

    private void setListener(boolean isShowInput) {
        if (isShowInput) {
            edMoney.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    edMoney.setInputType(InputType.TYPE_NULL);
                    mKeyboardUtil.showKeyboard();
                    edMoney.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
                    return true;
                }
            });

            edMoney.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                    if (s.toString().contains(".")) {
                        if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                            s = s.toString().subSequence(0,
                                    s.toString().indexOf(".") + 3);
                            edMoney.setText(s);
                            edMoney.setSelection(s.length());
                        }
                    }
                    if (s.toString().trim().substring(0).equals(".")) {
                        s = "0" + s;
                        edMoney.setText(s);
                        edMoney.setSelection(2);
                    }

                    if (s.toString().startsWith("0")
                            && s.toString().trim().length() > 1) {
                        if (!s.toString().substring(1, 2).equals(".")) {
                            edMoney.setText(s.subSequence(0, 1));
                            edMoney.setSelection(1);
                            return;
                        }
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    // TODO Auto-generated method stub

                }

            });
            mKeyboardUtil.setOnEnterListener(new KeyboardUtil.EnterListener() {
                @Override
                public void enter() {
                    onSure(position);
                }
            });
        } else {
            mKeyBoardView.setVisibility(View.GONE);
            edMoney.setInputType(InputType.TYPE_CLASS_TEXT);
            ApkInfoUtils.showSoftInput(this);

            setRightText(getString(R.string.finish), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSure(position);
                }
            });
        }

    }

    private void onSure(int position) {
        String textInput = edMoney.getText().toString();
        if (TextUtils.isEmpty(textInput)) {
            ToastUtils.showToast(this, "亲，输入的不能为空哟");
            return;
        }
        if (textInput.equals("0") || textInput.equals("0.00")) {
            ToastUtils.showToast(this, "亲，请输入大于0的数字");
            return;
        }


        Intent intent = new Intent();
        switch (position) {
            case 1:
                intent.putExtra(Config.TextInPut, textInput);
                setResult(0, intent);
                finish();
                break;
            case 2:
                intent.putExtra(Config.TextInPut, textInput);
                setResult(0, intent);
                finish();
                break;
            case 3:
                intent.putExtra(Config.TextInPut, textInput);
                setResult(0, intent);

                finish();
                break;
            case 4:
                intent.putExtra(Config.TextInPut, textInput);
                setResult(0, intent);
                SharedPreferencesUtil.setStringPreferences(this, Config.TxtYuEr, textInput);
                finish();
            case 5:
                intent.putExtra(Config.TextInPut, textInput);
                setResult(0, intent);
                SharedPreferencesUtil.setStringPreferences(this, Config.TxtAccountName, textInput);
                finish();
                break;
            case 6:
                intent.putExtra(Config.TextInPut, textInput);
                setResult(0, intent);
                SharedPreferencesUtil.setStringPreferences(this, Config.TxtMoney, textInput);
                finish();
                break;
            case 7:
                intent.putExtra(Config.TextInPut, textInput);
                setResult(0, intent);
                SharedPreferencesUtil.setStringPreferences(this, Config.TxtCreditLimit, textInput);
                finish();
                break;
            case 8:
                intent.putExtra(Config.TextInPut, textInput);
                setResult(0, intent);
                SharedPreferencesUtil.setStringPreferences(this, Config.TxtDebt, textInput);
                finish();
                break;
            case 9:
                intent.putExtra(Config.TextInPut, textInput);
                setResult(0, intent);
                SharedPreferencesUtil.setStringPreferences(this, Config.TxtPlanMoney, textInput);
                finish();
                break;
            case 10:
                intent.putExtra(Config.TextInPut, textInput);
                setResult(0, intent);
                SharedPreferencesUtil.setStringPreferences(this, Config.TxtPlanRemark, textInput);
                finish();
                break;
            case 11:
                intent.putExtra(Config.TextInPut, textInput);
                setResult(0, intent);
                SharedPreferencesUtil.setStringPreferences(this, Config.TxtNickName, textInput);
                finish();
                break;
            case 12:
                intent.putExtra(Config.TextInPut, textInput);
                setResult(0, intent);
                SharedPreferencesUtil.setStringPreferences(this, Config.TxtSigNature, textInput);
                finish();
                break;

        }

    }


}
