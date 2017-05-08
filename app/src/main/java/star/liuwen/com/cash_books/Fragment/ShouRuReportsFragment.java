package star.liuwen.com.cash_books.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGADivider;
import star.liuwen.com.cash_books.Adapter.ReportsDetailAdapter;
import star.liuwen.com.cash_books.Base.BaseFragment;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoAccount;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.RxBus.RxBusResult;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.View.PieChart.PieChart;
import star.liuwen.com.cash_books.bean.AccountModel;

/**
 * Created by liuwen on 2017/2/22.
 */
public class ShouRuReportsFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private ReportsDetailAdapter mAdapter;
    private List<AccountModel> mList = new ArrayList<>();
    private ViewStub mViewStub;
    private PieChart mPieChart;
    private RelativeLayout reChoiceDate;
    private TextView txtChoiceDate;
    private TimePickerView pvTime;
    private float[] money;
    private View headView;

    @Override
    public void lazyInitData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.shouru_reports_fragment);
        initView();
        return getContentView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        initData();
    }

    private void initAdapter() {
        headView = View.inflate(getActivity(), R.layout.head_reportfragment, null);
        mPieChart = (PieChart) headView.findViewById(R.id.piechart);
        txtChoiceDate = (TextView) headView.findViewById(R.id.f_re_txt);
        reChoiceDate = (RelativeLayout) headView.findViewById(R.id.f_re_choicedate);
        reChoiceDate.setOnClickListener(this);
        txtChoiceDate.setText(DateTimeUtil.getCurrentYearMonth());

        mList = DaoAccount.queryByZhiChuSHouRuType(Config.SHOU_RU);
        money = new float[mList.size()];
        for (int i = 0; i < mList.size(); i++) {
            money[i] = (float) mList.get(i).getMoney();
        }

        mPieChart.setRadius(230);
        mPieChart.setDescr("总收入");
        mPieChart.initSrc(money, Config.reportsColor, new PieChart.OnItemClickListener() {

            @Override
            public void click(int position) {
                ToastUtils.showToast(getActivity(), "你点击了");
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new ReportsDetailAdapter(mRecyclerView);
        mAdapter.addHeaderView(headView);
        if (DaoAccount.queryByZhiChuSHouRuType(Config.SHOU_RU).size() != 0) {
            mAdapter.setData(DaoAccount.queryByZhiChuSHouRuType(Config.SHOU_RU));
            mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
            mViewStub.setVisibility(View.GONE);
        } else {
            headView.setVisibility(View.GONE);
            mAdapter.setData(mList);
            mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
            mViewStub.setVisibility(View.VISIBLE);
        }
        mRecyclerView.addItemDecoration(BGADivider.newShapeDivider());
    }

    private void initData() {
        RxBus.getInstance().toObserverableOnMainThread("AccountModel", new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                mList = DaoAccount.queryByZhiChuSHouRuType(Config.SHOU_RU);
                if (mList.size() == 0) {
                    mViewStub.setVisibility(View.VISIBLE);
                } else {
                    mViewStub.setVisibility(View.GONE);
                    mAdapter.setData(mList);
                    mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
                    money = new float[mList.size()];
                    for (int i = 0; i < mList.size(); i++) {
                        money[i] = (float) mList.get(i).getMoney();
                    }
                }
                mPieChart.setRadius(230);
                mPieChart.setDescr("总收入");
                mPieChart.initSrc(money, Config.reportsColor, new PieChart.OnItemClickListener() {
                    @Override
                    public void click(int position) {
                    }
                });
            }
        });
        RxBus.getInstance().toObserverableOnMainThread(Config.RxHomeFragmentToReportsFragment, new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                mList.clear();
                mAdapter.clear();
                mList = DaoAccount.queryByZhiChuSHouRuType(Config.SHOU_RU);
                if (mList.size() == 0) {
                    mViewStub.setVisibility(View.VISIBLE);
                    headView.setVisibility(View.GONE);
                } else {
                    headView.setVisibility(View.VISIBLE);
                    money = new float[mList.size()];
                    for (int i = 0; i < mList.size(); i++) {
                        money[i] = (float) mList.get(i).getMoney();
                    }
                    mPieChart.setRadius(230);
                    mPieChart.setDescr("总收入");
                    mPieChart.initSrc(money, Config.reportsColor, new PieChart.OnItemClickListener() {
                        @Override
                        public void click(int position) {
                        }
                    });
                    mAdapter.setData(mList);
                    mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
                }
            }
        });
    }

    private void initView() {
        mRecyclerView = (RecyclerView) getContentView().findViewById(R.id.shouru_reports_recyclerView);
        mViewStub = (ViewStub) getContentView().findViewById(R.id.view_stub);
        mViewStub.inflate();
        mViewStub.setVisibility(View.GONE);


    }

    @Override
    public void onClick(View v) {
        if (v == reChoiceDate) {
            toDate();
        }
    }

    private void toDate() {
        //时间选择器
        pvTime = new TimePickerView(getActivity(), TimePickerView.Type.YEAR_MONTH);
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
                txtChoiceDate.setText(DateTimeUtil.getTime(date));
            }
        });
        //显示
        pvTime.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().removeObserverable("AccountModel");
        RxBus.getInstance().removeObserverable(Config.RxHomeFragmentToReportsFragment);
    }
}
