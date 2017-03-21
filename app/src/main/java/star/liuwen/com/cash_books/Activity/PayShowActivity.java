package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGADivider;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoAccount;
import star.liuwen.com.cash_books.Dao.DaoChoiceAccount;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.Utils.SnackBarUtil;
import star.liuwen.com.cash_books.Utils.StatusBarUtils;
import star.liuwen.com.cash_books.View.NumberAnimTextView;
import star.liuwen.com.cash_books.bean.AccountModel;
import star.liuwen.com.cash_books.bean.ChoiceAccount;

/**
 * Created by liuwen on 2017/2/17.
 */
public class PayShowActivity extends BaseActivity {
    private NumberAnimTextView tvAccount;
    private TextView txtMonth, txtLiuChu, txtLiuRu;
    private RelativeLayout ryBg;
    private TimePickerView pvTime;
    private ChoiceAccount model;
    private RecyclerView mRecyclerView;
    private List<AccountModel> mList = new ArrayList<>();
    private PaySHowAdapter mAdapter;
    private View headView;
    private double zhiChu, liuRu;
    private ViewStub mViewStub;


    @Override
    public int activityLayoutRes() {
        return R.layout.pay_show_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setLeftText(getString(R.string.back));
        setLeftTextColor(this.getResources().getColor(R.color.white));
        setRightTxtColor(this.getResources().getColor(R.color.white));
        setTitlesColor(this.getResources().getColor(R.color.white));
        setRightTxtColor(this.getResources().getColor(R.color.white));
        setRightText(getString(R.string.setting), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayShowActivity.this, PaySettingsActivity.class);
                intent.putExtra(Config.ModelWallet, model);
                startActivity(intent);
            }
        });

        headView = View.inflate(this, R.layout.head_pay_show, null);
        tvAccount = (NumberAnimTextView) headView.findViewById(R.id.pay_show_txt_account);
        txtMonth = (TextView) headView.findViewById(R.id.pay_show_txt_month);
        txtLiuChu = (TextView) headView.findViewById(R.id.pay_show_txt_liuchu);
        txtLiuRu = (TextView) headView.findViewById(R.id.pay_show_txt_liuru);
        ryBg = (RelativeLayout) headView.findViewById(R.id.pay_ry);

        txtMonth.setText(DateTimeUtil.getCurrentYearMonth());

        mRecyclerView = (RecyclerView) findViewById(R.id.pay_show_recycler_view);
        mViewStub = (ViewStub) findViewById(R.id.view_stub);
        mViewStub.inflate();
        mViewStub.setVisibility(View.GONE);
        model = (ChoiceAccount) getIntent().getExtras().getSerializable(Config.ModelWallet);
        mAdapter = new PaySHowAdapter(mRecyclerView);
        mAdapter.addHeaderView(headView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (model != null) {
            if (DaoAccount.queryByAccountType(model.getMAccountType()) != null || DaoAccount.queryByAccountType(model.getMAccountType()).size() != 0) {
                setAdapter(DateTimeUtil.getCurrentYearMonth());
            } else {
                mAdapter.setData(mList);
                mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
            }
            mRecyclerView.addItemDecoration(BGADivider.newShapeDivider());
            tvAccount.setText(String.format("%.2f", model.getMoney()));
            ryBg.setBackgroundResource(model.getColor());
            StatusBarUtils.setWindowStatusBarColor(this, model.getColor());
            setTitleBg(model.getColor());
            setTitle(model.getAccountName());
        }
    }

    private void setAdapter(String data) {
        if (model.getMAccountType().equals(Config.CASH)) {
            mList = DaoAccount.queryByAccountType("现金");
            if (mList.size() == 0 || null == mList) {
                mViewStub.setVisibility(View.VISIBLE);
            } else {
                mAdapter.setData(mList);
            }
            for (AccountModel model : mList) {
                if (model.getZhiChuShouRuType().equals(Config.ZHI_CHU)) {
                    zhiChu = zhiChu + model.getMoney();
                    txtLiuChu.setText(String.format("%.2f", zhiChu));
                } else {
                    liuRu = liuRu + model.getMoney();
                    txtLiuRu.setText(String.format("%.2f", liuRu));
                }
            }
            mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
        } else if (model.getMAccountType().equals(Config.CXK)) {
            mList = DaoAccount.queryByAccountType("储蓄卡");
            if(mList.size()==0||null==mList){
                mViewStub.setVisibility(View.VISIBLE);
            }else {
                mAdapter.setData(mList);
            }
            for (AccountModel model : mList) {
                if (model.getZhiChuShouRuType().equals(Config.ZHI_CHU)) {
                    zhiChu = zhiChu + model.getMoney();
                    txtLiuChu.setText(String.format("%.2f", zhiChu));
                } else {
                    liuRu = liuRu + model.getMoney();
                    txtLiuRu.setText(String.format("%.2f", liuRu));
                }
            }
            mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
        } else if (model.getMAccountType().equals(Config.XYK)) {
            mList = DaoAccount.queryByAccountType("信用卡");
            if(mList.size()==0||null==mList){
                mViewStub.setVisibility(View.VISIBLE);
            }else {
                mAdapter.setData(mList);
            }
            for (AccountModel model : mList) {
                if (model.getZhiChuShouRuType().equals(Config.ZHI_CHU)) {
                    zhiChu = zhiChu + model.getMoney();
                    txtLiuChu.setText(String.format("%.2f", zhiChu));
                } else {
                    liuRu = liuRu + model.getMoney();
                    txtLiuRu.setText(String.format("%.2f", liuRu));
                }
            }
            mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
        } else if (model.getMAccountType().equals(Config.ZFB)) {
            mList = DaoAccount.queryByAccountType("支付宝");
            if(mList.size()==0||null==mList){
                mViewStub.setVisibility(View.VISIBLE);
            }else {
                mAdapter.setData(mList);
            }
            for (AccountModel model : mList) {
                if (model.getZhiChuShouRuType().equals(Config.ZHI_CHU)) {
                    zhiChu = zhiChu + model.getMoney();
                    txtLiuChu.setText(String.format("%.2f", zhiChu));
                } else {
                    liuRu = liuRu + model.getMoney();
                    txtLiuRu.setText(String.format("%.2f", liuRu));
                }
            }
            mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
        }
    }


    public void toData(View view) {
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
                txtMonth.setText(DateTimeUtil.getTime(date));
                setAdapter(DateTimeUtil.getTime(date));
            }
        });
        //显示
        pvTime.show();
    }


    public void toYuer(View view) {
        Intent intent = new Intent(PayShowActivity.this, UpdateCommonKeyBoardActivity.class);
        intent.putExtra(Config.SaveAPenPlatform, "YuER");
        startActivityForResult(intent, YuER);
    }

    private static final int YuER = 101;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case YuER:
                    tvAccount.setNumberString(String.format("%.2f", Double.parseDouble(data.getExtras().getString(Config.TextInPut))));
                    if (model.getMAccountType().equals(Config.CASH)) {
                        ChoiceAccount model = DaoChoiceAccount.query().get(0);
                        model.setMoney(Double.parseDouble(data.getExtras().getString(Config.TextInPut)));
                        DaoChoiceAccount.updateAccount(model);
                        RxBus.getInstance().post(Config.CASH, true);
                    } else if (model.getMAccountType().equals(Config.CXK)) {
                        ChoiceAccount model = DaoChoiceAccount.query().get(1);
                        model.setMoney(Double.parseDouble(data.getExtras().getString(Config.TextInPut)));
                        DaoChoiceAccount.updateAccount(model);
                        RxBus.getInstance().post(Config.CASH, true);
                    } else if (model.getMAccountType().equals(Config.XYK)) {
                        ChoiceAccount model = DaoChoiceAccount.query().get(2);
                        model.setMoney(Double.parseDouble(data.getExtras().getString(Config.TextInPut)));
                        DaoChoiceAccount.updateAccount(model);
                        RxBus.getInstance().post(Config.CASH, true);
                    } else if (model.getMAccountType().equals(Config.ZFB)) {
                        ChoiceAccount model = DaoChoiceAccount.query().get(3);
                        model.setMoney(Double.parseDouble(data.getExtras().getString(Config.TextInPut)));
                        DaoChoiceAccount.updateAccount(model);
                        RxBus.getInstance().post(Config.CASH, true);
                    } else if (model.getMAccountType().equals(Config.JC)) {
                        ChoiceAccount model = DaoChoiceAccount.query().get(4);
                        model.setMoney(Double.parseDouble(data.getExtras().getString(Config.TextInPut)));
                        DaoChoiceAccount.updateAccount(model);
                        RxBus.getInstance().post(Config.CASH, true);
                    } else if (model.getMAccountType().equals(Config.JR)) {
                        ChoiceAccount model = DaoChoiceAccount.query().get(5);
                        model.setMoney(Double.parseDouble(data.getExtras().getString(Config.TextInPut)));
                        DaoChoiceAccount.updateAccount(model);
                        RxBus.getInstance().post(Config.CASH, true);
                    }
                    break;
            }
        }
    }
    private class PaySHowAdapter extends BGARecyclerViewAdapter<AccountModel> {

        public PaySHowAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_pay_show);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, AccountModel model) {
            if (needTitle(position)) {
                helper.setVisibility(R.id.item_data, View.VISIBLE);
                String currentData = DateTimeUtil.getCurrentYear();
                if (currentData.equals(model.getData())) {
                    helper.setText(R.id.item_data, "今天");
                } else {
                    helper.setText(R.id.item_data, model.getData());
                }

            } else {
                helper.setVisibility(R.id.item_data, View.GONE);
            }
            if (model.getZhiChuShouRuType().equals(Config.SHOU_RU)) {
                helper.setText(R.id.item_rd_money, String.format("+%.2f", model.getMoney()));
                helper.setTextColor(R.id.item_rd_money, getResources().getColor(R.color.blue));

            } else {
                helper.setText(R.id.item_rd_money, String.format("-%.2f", model.getMoney()));
            }
            helper.setImageResource(R.id.item_rd_image, model.getUrl());
            helper.setText(R.id.item_rd_txtName, model.getConsumeType());
        }

        private boolean needTitle(int position) {
            if (position == 0) {
                return true;
            }
            if (position < 0) {
                return false;

            }
            AccountModel currentModel = getItem(position);
            AccountModel previousModel = getItem(position - 1);
            if (currentModel == null || previousModel == null) {
                return false;
            }
            String currentData = currentModel.getData();
            String previousData = previousModel.getData();

            if (currentData.equals(previousData)) {
                return false;
            }
            return true;
        }
    }

}
