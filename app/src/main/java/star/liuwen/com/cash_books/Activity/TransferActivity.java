package star.liuwen.com.cash_books.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import star.liuwen.com.cash_books.Adapter.PopWindowAdapter;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoChoiceAccount;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.KeyboardUtil;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.bean.ChoiceAccount;

/**
 * Created by liuwen on 2017/3/24.
 */
public class TransferActivity extends BaseActivity implements View.OnClickListener {

    private KeyboardView mKeyBoardView;
    private KeyboardUtil mKeyboardUtil;
    private EditText edMoneyjian, edMoneyJia;
    private TextView tvDate, tvZhuanChu, tvZhuanRu;
    private RelativeLayout reZhuanChu, reZhuanRu;
    private boolean isZhuanChuORZhuanRu = false;

    private ListView mListView;
    private PopWindowAdapter mPopWindowAdapter;
    private PopupWindow window;
    private ChoiceAccount model;


    @Override
    public int activityLayoutRes() {
        return R.layout.transfer_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setLeftImage(R.mipmap.fanhui_lan);
        setLeftText(getString(R.string.back));
        edMoneyJia = (EditText) findViewById(R.id.yuer_jia);
        edMoneyjian = (EditText) findViewById(R.id.yuer_jian);
        tvDate = (TextView) findViewById(R.id.transfer_data);
        tvZhuanChu = (TextView) findViewById(R.id.zhuanchu);
        tvZhuanRu = (TextView) findViewById(R.id.zhuanru);
        reZhuanChu = (RelativeLayout) findViewById(R.id.transfer_zhuanchu);
        reZhuanRu = (RelativeLayout) findViewById(R.id.transfer_zhuanru);

        mKeyBoardView = (KeyboardView) findViewById(R.id.keyboard_view);
        mKeyboardUtil = new KeyboardUtil(TransferActivity.this, TransferActivity.this, edMoneyJia);
        mKeyboardUtil = new KeyboardUtil(TransferActivity.this, TransferActivity.this, edMoneyjian);
        tvDate.setOnClickListener(this);
        reZhuanRu.setOnClickListener(this);
        reZhuanChu.setOnClickListener(this);
        setListener();
    }

    private void setListener() {
        edMoneyJia.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edMoneyJia.setInputType(InputType.TYPE_NULL);
                mKeyboardUtil.showKeyboard();
                edMoneyJia.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
                return true;
            }
        });

        edMoneyJia.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        edMoneyJia.setText(s);
                        edMoneyJia.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    edMoneyJia.setText(s);
                    edMoneyJia.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        edMoneyJia.setText(s.subSequence(0, 1));
                        edMoneyJia.setSelection(1);
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
                ToastUtils.showToast(TransferActivity.this, "点击了确定");
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == reZhuanChu) {
            isZhuanChuORZhuanRu = true;
            showAccount(isZhuanChuORZhuanRu);
            if (window.isShowing()) {
                window.dismiss();
            } else {
                window.showAtLocation(reZhuanChu, Gravity.BOTTOM, 0, 0);
                backgroundAlpha(0.5f);
            }
        } else if (v == tvDate) {
            ToastUtils.showToast(this, "点击了日期");
        } else if (v == reZhuanRu) {
            isZhuanChuORZhuanRu = false;
            showAccount(isZhuanChuORZhuanRu);
            if (window.isShowing()) {
                window.dismiss();
            } else {
                window.showAtLocation(reZhuanRu, Gravity.BOTTOM, 0, 0);
                backgroundAlpha(0.5f);
            }
        }
    }

    private void showAccount(final boolean iszhuanoRchu) {
        View popView = View.inflate(this, R.layout.pop_zhanghu_dialog, null);
        mListView = (ListView) popView.findViewById(R.id.lv_popup_list);
        window = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mPopWindowAdapter = new PopWindowAdapter(this, R.layout.item_pop_account);
        if (DaoChoiceAccount.query().size() != 0 || DaoChoiceAccount.query() != null) {
            mPopWindowAdapter.setData(DaoChoiceAccount.query());
        }
        mListView.setAdapter(mPopWindowAdapter);
        for (int i = 0; i < DaoChoiceAccount.query().size(); i++) {
            model = DaoChoiceAccount.query().get(i);
            if (model.getMAccountType().equals(Config.JC)) {
                mPopWindowAdapter.removeItem(model);
            } else if (model.getMAccountType().equals(Config.JR)) {
                mPopWindowAdapter.removeItem(model);
            }
        }
        window.setFocusable(true);
        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.setAnimationStyle(R.style.AnimBottom);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                window.dismiss();
                mPopWindowAdapter.getItem(position).getAccountName();
                if (iszhuanoRchu) {
                    tvZhuanChu.setText(mPopWindowAdapter.getItem(position).getAccountName());
                    reZhuanChu.setBackgroundResource(mPopWindowAdapter.getItem(position).getColor());
                } else {
                    tvZhuanRu.setText(mPopWindowAdapter.getItem(position).getAccountName());
                    reZhuanRu.setBackgroundResource(mPopWindowAdapter.getItem(position).getColor());
                }
                // SharedPreferencesUtil.setStringPreferences(getActivity(), Config.TxtChoiceAccount, AccountType);
            }
        });

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setOutsideTouchable(true);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
}
