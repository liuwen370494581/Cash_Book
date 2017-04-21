package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.bingoogolapple.androidcommon.adapter.BGADivider;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoAccount;
import star.liuwen.com.cash_books.Dao.DaoAccountBalance;
import star.liuwen.com.cash_books.Dao.DaoChoiceAccount;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.RxBus.RxBusResult;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.Utils.RxUtil;
import star.liuwen.com.cash_books.Utils.StatusBarUtils;
import star.liuwen.com.cash_books.View.NumberAnimTextView;
import star.liuwen.com.cash_books.bean.AccountModel;
import star.liuwen.com.cash_books.bean.BaseModel;
import star.liuwen.com.cash_books.bean.ChoiceAccount;

/**
 * Created by liuwen on 2017/2/17.
 */
public class PayShowActivity extends BaseActivity implements BGAOnRVItemClickListener {
    private NumberAnimTextView tvAccount;
    private TextView txtMonth, txtLiuChu, txtLiuRu;
    private RelativeLayout ryBg;
    private TimePickerView pvTime;
    private ChoiceAccount model;
    private RecyclerView mRecyclerView;
    private List<AccountModel> mList = new ArrayList<>();
    private List<BaseModel> choiceList = new ArrayList<>();
    private List<BaseModel> baseList = new ArrayList<>();
    private PaySHowAdapter mAdapter;
    private View headView;
    private double zhiChu, liuRu;
    private ViewStub mViewStub;
    private int position;
    private double tvAccountValue;
    private long payShowId;


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
                intent.putExtra(Config.Position, position);
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

        position = getIntent().getIntExtra(Config.Position, 0);
        mAdapter = new PaySHowAdapter(mRecyclerView);
        mAdapter.addHeaderView(headView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (model != null) {
            payShowId = model.getId();
            if (DaoAccount.queryByAccountType(model.getMAccountType()).size() != 0 || DaoChoiceAccount.query().size() != 0) {
                //setAdapter(DateTimeUtil.getCurrentYearMonth());
                PayShowList(payShowId);
            } else {
                mAdapter.setData(baseList);
                mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
            }
            mRecyclerView.addItemDecoration(BGADivider.newShapeDivider());
            tvAccount.setText(String.format("%.2f", model.getMoney()));
            ryBg.setBackgroundResource(model.getColor());
            StatusBarUtils.setWindowStatusBarColor(this, model.getColor());
            setTitleBg(model.getColor());
            setTitle(model.getAccountName());
        }
        mAdapter.setOnRVItemClickListener(this);
        initData();
    }


    private void initData() {
        RxBus.getInstance().toObserverableOnMainThread(Config.RxPayShowDetailToPayShowActivity, new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                HashMap<Integer, BaseModel> hashMap = (HashMap<Integer, BaseModel>) o;
                Set<Map.Entry<Integer, BaseModel>> maps = hashMap.entrySet();
                String accountType = "";
                for (Map.Entry<Integer, BaseModel> entry : maps) {
                    DaoAccountBalance.deleteByModel(entry.getValue());
                    Log.e("MainActivity", entry.getValue().getMoney() + "");
                    Log.e("MainActivity", entry.getKey() + "");
                    mAdapter.removeItem(entry.getKey());
                    accountType = entry.getValue().getAccountType();
                }
                baseList = DaoAccountBalance.queryByType(accountType);
                if (baseList.size() == 0) {
                    txtLiuChu.setText("0");
                    txtLiuRu.setText("0");
                    tvAccount.setText("0");
                    mViewStub.setVisibility(View.VISIBLE);
                } else {
                    for (BaseModel model : baseList) {
                        if (model.getZhiChuShouRuType().equals(Config.ZHI_CHU)) {
                            zhiChu += model.getMoney();
                            txtLiuChu.setText(String.format("%.2f", zhiChu));
                        } else {
                            liuRu += model.getMoney();
                            txtLiuRu.setText(String.format("%.2f", liuRu));
                        }
                    }
                }

            }
        });
    }


    private void PayShowList(long id) {
        mList = DaoAccount.queryById(id);
        for (int i = 0; i < mList.size(); i++) {
            BaseModel baseModel = new BaseModel();
            baseModel.setUrl(mList.get(i).getUrl());
            baseModel.setMoney(mList.get(i).getMoney());
            baseModel.setName(mList.get(i).getConsumeType());
            baseModel.setType(Config.AccountModel);
            baseModel.setZhiChuShouRuType(mList.get(i).getZhiChuShouRuType());
            baseModel.setTimeMinSec(mList.get(i).getTimeMinSec());
            baseList.add(baseModel);
        }
        choiceList = DaoAccountBalance.queryById(id);
        for (int i = 0; i < choiceList.size(); i++) {
            baseList.add(choiceList.get(i));
            tvAccountValue = choiceList.get(0).getMoney();
        }

        Collections.sort(baseList, new Comparator<BaseModel>() {
            @Override
            public int compare(BaseModel model1, BaseModel model2) {
                return model2.getTimeMinSec().compareTo(model1.getTimeMinSec());
            }
        });

        if (baseList.size() == 0) {
            mViewStub.setVisibility(View.VISIBLE);
        } else {
            mAdapter.setData(baseList);
        }
        for (BaseModel model : baseList) {
            if (model.getZhiChuShouRuType().equals(Config.ZHI_CHU)) {
                zhiChu += model.getMoney();
                txtLiuChu.setText(String.format("%.2f", zhiChu));
            } else {
                liuRu += model.getMoney();
                txtLiuRu.setText(String.format("%.2f", liuRu));
            }
        }
        mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());

//        if (model.getMAccountType().equals(Config.CASH)) {
//            payShowCommonCode(Config.CASH);
//        } else if (model.getMAccountType().equals(Config.CXK)) {
//            payShowCommonCode(Config.CXK);
//        } else if (model.getMAccountType().equals(Config.XYK)) {
//            payShowCommonCode(Config.XYK);
//        } else if (model.getMAccountType().equals(Config.ZFB)) {
//            payShowCommonCode(Config.ZFB);
//        } else if (model.getMAccountType().equals(Config.WEIXIN)) {
//            payShowCommonCode(Config.WEIXIN);
//        } else if (model.getMAccountType().equals(Config.CZK)) {
//            payShowCommonCode(Config.CZK);
//        } else if (model.getMAccountType().equals(Config.TOUZI)) {
//            payShowCommonCode(Config.TOUZI);
//        } else if (model.getMAccountType().equals(Config.INTENTACCOUNT)) {
//            payShowCommonCode(Config.INTENTACCOUNT);
//        }
    }

//    private void payShowCommonCode(final String accountType) {
//        mList = DaoAccount.queryByAccountType(accountType);
//        for (int i = 0; i < mList.size(); i++) {
//            BaseModel baseModel = new BaseModel();
//            baseModel.setUrl(mList.get(i).getUrl());
//            baseModel.setMoney(mList.get(i).getMoney());
//            baseModel.setName(mList.get(i).getConsumeType());
//            baseModel.setType(Config.AccountModel);
//            baseModel.setZhiChuShouRuType(mList.get(i).getZhiChuShouRuType());
//            baseModel.setTimeMinSec(mList.get(i).getTimeMinSec());
//            baseModel.setAccountType(accountType);
//            baseList.add(baseModel);
//        }
//        choiceList = DaoAccountBalance.queryByType(accountType);
//        for (int i = 0; i < choiceList.size(); i++) {
//            baseList.add(choiceList.get(i));
//            tvAccountValue = choiceList.get(0).getMoney();
//        }
//
//        Collections.sort(baseList, new Comparator<BaseModel>() {
//            @Override
//            public int compare(BaseModel model1, BaseModel model2) {
//                return model2.getTimeMinSec().compareTo(model1.getTimeMinSec());
//            }
//        });
//
//        if (baseList.size() == 0) {
//            mViewStub.setVisibility(View.VISIBLE);
//        } else {
//            mAdapter.setData(baseList);
//        }
//        for (BaseModel model : baseList) {
//            if (model.getZhiChuShouRuType().equals(Config.ZHI_CHU)) {
//                zhiChu += model.getMoney();
//                txtLiuChu.setText(String.format("%.2f", zhiChu));
//            } else {
//                liuRu += model.getMoney();
//                txtLiuRu.setText(String.format("%.2f", liuRu));
//            }
//        }
//        mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
//    }


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
                //setAdapter(DateTimeUtil.getTime(date));
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
                        updateAccountYuer(data.getExtras().getString(Config.TextInPut), Config.CASH);
                    } else if (model.getMAccountType().equals(Config.CXK)) {
                        updateAccountYuer(data.getExtras().getString(Config.TextInPut), Config.CXK);
                    } else if (model.getMAccountType().equals(Config.XYK)) {
                        updateAccountYuer(data.getExtras().getString(Config.TextInPut), Config.XYK);
                    } else if (model.getMAccountType().equals(Config.ZFB)) {
                        updateAccountYuer(data.getExtras().getString(Config.TextInPut), Config.ZFB);
                    } else if (model.getMAccountType().equals(Config.JC)) {
                        updateAccountYuer(data.getExtras().getString(Config.TextInPut), Config.JC);
                    } else if (model.getMAccountType().equals(Config.JR)) {
                        updateAccountYuer(data.getExtras().getString(Config.TextInPut), Config.JR);
                    } else if (model.getMAccountType().equals(Config.WEIXIN)) {
                        updateAccountYuer(data.getExtras().getString(Config.TextInPut), Config.WEIXIN);
                    } else if (model.getMAccountType().equals(Config.CZK)) {
                        updateAccountYuer(data.getExtras().getString(Config.TextInPut), Config.CZK);
                    } else if (model.getMAccountType().equals(Config.TOUZI)) {
                        updateAccountYuer(data.getExtras().getString(Config.TextInPut), Config.TOUZI);
                    } else if (model.getMAccountType().equals(Config.INTENTACCOUNT)) {
                        updateAccountYuer(data.getExtras().getString(Config.TextInPut), Config.INTENTACCOUNT);
                    }
                    break;
            }
        }
    }

    private void updateAccountYuer(String data, String accountType) {
        model.setMoney(Double.parseDouble(data));
        DaoChoiceAccount.updateAccount(model);
        RxBus.getInstance().post(Config.RxPayShowActivityToWalletFragment, position);

        //循环baseList的第一个数据来和插入的数据来比较
        for (int i = 0; i < baseList.size(); i++) {
            tvAccountValue = baseList.get(0).getMoney();
        }
        int s = 1 + (int) (Math.random() * 10000000);
        final BaseModel baseModel = new BaseModel(DaoChoiceAccount.getCount() + s, R.mipmap.yuebiangeng
                , getString(R.string.Balance_change), getString(R.string.pingzhang), Double.parseDouble(data),
                Config.ChoiceAccount,
                Double.parseDouble(data) > tvAccountValue ? Config.SHOU_RU : Config.ZHI_CHU,
                DateTimeUtil.getCurrentTime_Today(), accountType,payShowId);
        Observable.create(new Observable.OnSubscribe<BaseModel>() {
            @Override
            public void call(Subscriber<? super BaseModel> subscriber) {
                DaoAccountBalance.insert(baseModel);
                subscriber.onNext(baseModel);
            }
        }).compose(RxUtil.<BaseModel>applySchedulers()).subscribe(new Action1<BaseModel>() {
            @Override
            public void call(BaseModel model) {
                //如果不插入首位 则会在末端显示数据
                baseList.add(0, model);
                mAdapter.setData(baseList);
                mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
                mViewStub.setVisibility(View.GONE);
                //更新页面数据
                for (BaseModel bm : baseList) {
                    if (bm.getZhiChuShouRuType().equals(Config.ZHI_CHU)) {
                        zhiChu += bm.getMoney();
                        txtLiuChu.setText(String.format("%.2f", zhiChu));
                    } else {
                        liuRu += bm.getMoney();
                        txtLiuRu.setText(String.format("%.2f", liuRu));
                    }
                }
            }
        });
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        Intent intent = new Intent(PayShowActivity.this, PayShowDetailActivity.class);
        intent.putExtra(Config.PayShowDetailModel, baseList.get(position));
        intent.putExtra(Config.Position, position);
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().removeObserverable(Config.RxPayShowDetailToPayShowActivity);
    }

    private class PaySHowAdapter extends BGARecyclerViewAdapter<BaseModel> {
        public PaySHowAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_pay_show);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, BaseModel model) {
            if (needTitle(position)) {
                helper.setVisibility(R.id.item_data, View.VISIBLE);
                String currentData = DateTimeUtil.getCurrentYear();
                String[] timeMinSecs = model.getTimeMinSec().split("-");
                String timeMinSec = timeMinSecs[0] + "-" + timeMinSecs[1] + "-" + timeMinSecs[2];
                if (currentData.equals(timeMinSec)) {
                    helper.setText(R.id.item_data, "今天");
                } else {
                    helper.setText(R.id.item_data, timeMinSec);
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
            if (model.getType().equals(Config.AccountModel)) {
                helper.setVisibility(R.id.item_rd_txtType, View.VISIBLE);
                helper.setVisibility(R.id.item_rd_txtName, View.GONE);
                helper.setVisibility(R.id.item_rd_txtpingzhang, View.GONE);
            } else {
                helper.setVisibility(R.id.item_rd_txtName, View.VISIBLE);
                helper.setVisibility(R.id.item_rd_txtpingzhang, View.VISIBLE);
                helper.setVisibility(R.id.item_rd_txtType, View.GONE);
            }
            helper.setImageResource(R.id.item_rd_image, model.getUrl());
            helper.setText(R.id.item_rd_txtType, model.getName());

        }

        private boolean needTitle(int position) {
            if (position == 0) {
                return true;
            }
            if (position < 0) {
                return false;

            }
            BaseModel currentModel = getItem(position);
            BaseModel previousModel = getItem(position - 1);
            if (currentModel == null || previousModel == null) {
                return false;
            }
            String currentDatas[] = currentModel.getTimeMinSec().split("-");
            String currentData = currentDatas[0] + "-" + currentDatas[1] + "-" + currentDatas[2];
            String previousDatas[] = previousModel.getTimeMinSec().split("-");
            String previousData = previousDatas[0] + "-" + previousDatas[1] + "-" + previousDatas[2];
            if (currentData.equals(previousData)) {
                return false;
            }
            return true;
        }
    }


}
