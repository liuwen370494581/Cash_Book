package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import cn.bingoogolapple.androidcommon.adapter.BGADivider;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoAccount;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.Utils.RxUtil;
import star.liuwen.com.cash_books.bean.AccountModel;

/**
 * Created by liuwen on 2016/12/28.
 */
public class CalendarActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private ViewStub mViewStub;
    private DatePicker datePicker;
    private List<AccountModel> mList;
    private CalendarAdapter mAdapter;
    private ImageView imageAdd;

    @Override
    public int activityLayoutRes() {
        return R.layout.calendar_activity;
    }

    @Override
    public void initView() {
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        datePicker = (DatePicker) findViewById(R.id.main_dp);
        mRecyclerView = (RecyclerView) findViewById(R.id.calendar_recycler_view);
        mViewStub = (ViewStub) findViewById(R.id.view_stub);
        mViewStub.inflate();
        imageAdd = (ImageView) findViewById(R.id.add);
        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, IncomeAndCostActivity.class);
                intent.putExtra(Config.WEIXIN, true);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_open, R.anim.anim_close);
            }
        });
        mViewStub.setVisibility(View.GONE);

        initData();
    }

    private void initData() {
        datePicker.setTodayDisplay(true);
        String currentDate = DateTimeUtil.getCurrentTime_Today();
        String[] sCurrentDate = currentDate.split("-");
        String year = sCurrentDate[0];
        String month = sCurrentDate[1];
        datePicker.setDate(Integer.parseInt(year), Integer.parseInt(month));
        datePicker.setMode(DPMode.SINGLE);
        //单选模式
        datePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                Date dates = DateTimeUtil.getStringDayToDate(date);
                final String da = DateTimeUtil.getYearMonthDay_(dates);
                Observable.create(new Observable.OnSubscribe<List<AccountModel>>() {
                    @Override
                    public void call(Subscriber<? super List<AccountModel>> subscriber) {
                        mList = DaoAccount.queryByDate(da);
                        subscriber.onNext(mList);
                    }
                }).compose(RxUtil.<List<AccountModel>>applySchedulers()).subscribe(new Action1<List<AccountModel>>() {
                    @Override
                    public void call(List<AccountModel> models) {
                        if (models.size() == 0) {
                            mViewStub.setVisibility(View.VISIBLE);
                            mAdapter.setData(models);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            mViewStub.setVisibility(View.GONE);
                            mAdapter.setData(models);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    }
                });
            }
        });

        mList = new ArrayList<>();
        mAdapter = new CalendarAdapter(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(BGADivider.newShapeDivider());
        if (DaoAccount.queryByDate(DateTimeUtil.getCurrentYear()).size() != 0) {
            Observable.create(new Observable.OnSubscribe<List<AccountModel>>() {
                @Override
                public void call(Subscriber<? super List<AccountModel>> subscriber) {
                    mList = DaoAccount.queryByDate(DateTimeUtil.getCurrentYear());
                    subscriber.onNext(mList);
                }
            }).compose(RxUtil.<List<AccountModel>>applySchedulers()).subscribe(new Action1<List<AccountModel>>() {
                @Override
                public void call(List<AccountModel> models) {
                    mAdapter.setData(models);
                    mRecyclerView.setAdapter(mAdapter);
                }
            });
        } else {
            mViewStub.setVisibility(View.VISIBLE);
            mAdapter.setData(mList);
            mRecyclerView.setAdapter(mAdapter);
        }
    }


    private class CalendarAdapter extends BGARecyclerViewAdapter<AccountModel> {

        public CalendarAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_pay_show);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, AccountModel model) {
            if (needTitle(position)) {
                helper.setVisibility(R.id.item_data, View.VISIBLE);
                helper.setText(R.id.item_data, model.getData());
            } else {
                helper.setVisibility(R.id.item_data, View.GONE);
            }
            if (model.getZhiChuShouRuType().equals(Config.SHOU_RU)) {
                helper.setText(R.id.item_rd_money, String.format("+%.2f", model.getMoney()));
                helper.setTextColor(R.id.item_rd_money, getResources().getColor(R.color.blue));
            } else {
                helper.setText(R.id.item_rd_money, String.format("-%.2f", model.getMoney()));
            }
            helper.setVisibility(R.id.item_rd_txtType, View.VISIBLE);
            helper.setVisibility(R.id.item_rd_txtName, View.GONE);
            helper.setVisibility(R.id.item_rd_txtpingzhang, View.GONE);
            helper.setImageResource(R.id.item_rd_image, model.getUrl());
            helper.setText(R.id.item_rd_txtType, model.getConsumeType());
        }

        private boolean needTitle(int position) {
            //第一个是分类
            if (position == 0) {
                return true;
            }
            if (position < 0) {
                return false;
            }
            AccountModel currentModel = (AccountModel) getItem(position);
            AccountModel previousModel = (AccountModel) getItem(position - 1);
            if (currentModel == null || previousModel == null) {
                return false;
            }
            String currentData = currentModel.getData();
            String previousData = previousModel.getData();

            // 当前item分类名和上一个item分类名不同，则表示两item属于不同分类
            if (currentData.equals(previousData)) {
                return false;
            }
            return true;
        }
    }
}
