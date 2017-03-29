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

import com.bigkoo.pickerview.TimePickerView;

import java.util.Calendar;
import java.util.Date;

import star.liuwen.com.cash_books.Adapter.PopWindowAdapter;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoChoiceAccount;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
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
    private RelativeLayout reZhuanChu, reZhuanRu, reShowDate;
    private boolean isZhuanChuORZhuanRu = false;

    private ListView mListView;
    private PopWindowAdapter mPopWindowAdapter;
    private PopupWindow window;
    private ChoiceAccount model;
    private TimePickerView pvTime;


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
        reShowDate = (RelativeLayout) findViewById(R.id.choice_date);

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

        edMoneyjian.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edMoneyJia.setInputType(InputType.TYPE_NULL);
                mKeyboardUtil.showKeyboard();
                edMoneyjian.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
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
                TransferActivity.this.finish();
                reShowDate.setVisibility(View.GONE);
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
            showDate();
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


    private void showDate() {
        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH);
        //设置标题
        pvTime.setTitle("选择日期");
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 30);
        pvTime.setTime(new Date());
        //设置是否循环
        pvTime.setCyclic(false);
        //设置是否可以关闭
        pvTime.setCancelable(true);
        //设置选择监听
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                tvDate.setText(DateTimeUtil.getTime(date));
            }
        });
        //显示
        pvTime.show();
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
