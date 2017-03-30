package star.liuwen.com.cash_books.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import star.liuwen.com.cash_books.Adapter.ReportsDetailAdapter;
import star.liuwen.com.cash_books.Base.BaseFragment;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoAccount;
import star.liuwen.com.cash_books.Enage.DataEnige;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.View.PieChart.IPieElement;
import star.liuwen.com.cash_books.View.PieChart.PieChart;
import star.liuwen.com.cash_books.View.PieChart.TestEntity;
import star.liuwen.com.cash_books.bean.AccountModel;

/**
 * Created by liuwen on 2017/2/22.
 */
public class ZhiChuReportsFragment extends BaseFragment implements OnClickListener {
    private RecyclerView mRecyclerView;
    private ReportsDetailAdapter mAdapter;
    private List<AccountModel> mList = new ArrayList<>();
    private ViewStub mViewStub;
    private PieChart mPieChart;
    private RelativeLayout reChoiceDate;
    private TextView txtChoiceDate;
    private TimePickerView pvTime;
    private double sum;
    private String color;
    private float part;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.zhichu_reports_fragment);
        initView();
        initData();
        return getContentView();
    }

    private void initView() {

        mRecyclerView = (RecyclerView) getContentView().findViewById(R.id.zhichu_reports_recyclerView);
        mViewStub = (ViewStub) getContentView().findViewById(R.id.view_stub);
        mViewStub.inflate();
        mViewStub.setVisibility(View.GONE);


        View headView = View.inflate(getActivity(), R.layout.head_reportfragment, null);
        mPieChart = (PieChart) headView.findViewById(R.id.piechart);
        txtChoiceDate = (TextView) headView.findViewById(R.id.f_re_txt);
        reChoiceDate = (RelativeLayout) headView.findViewById(R.id.f_re_choicedate);
        reChoiceDate.setOnClickListener(this);
        txtChoiceDate.setText(DateTimeUtil.getCurrentYearMonth());

//        TestEntity entity = new TestEntity(10000, "#FF7F50");
//        TestEntity entity1 = new TestEntity(38, "#DC143C");
//        TestEntity entity2 = new TestEntity(79, "#00008B");
//        TestEntity entity3 = new TestEntity(20, "#A9A9A9");
//        TestEntity entity4 = new TestEntity(105, "#8B0000");
//        TestEntity entity5 = new TestEntity(53, "#9400D3");
//        TestEntity entity6 = new TestEntity(800, "#FFD700");
        List<IPieElement> list = new ArrayList<>();
        mList = DaoAccount.queryByZhiChuSHouRuType(Config.ZHI_CHU);
        for (int i = 0; i < mList.size(); i++) {
            sum += mList.get(i).getMoney();
            part = (float) mList.get(i).getMoney();
            color = mList.get(i).getColor();
            list.add(new TestEntity(part, "#E96297"));
        }



        mPieChart.setData(list);
        mPieChart.setTitleText("总支出" + String.format("%.2f", sum));

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new ReportsDetailAdapter(mRecyclerView);
        mAdapter.addHeaderView(headView);
        if (DaoAccount.queryByZhiChuSHouRuType(Config.ZHI_CHU).size() != 0) {
            mList = DaoAccount.queryByZhiChuSHouRuType(Config.ZHI_CHU);
            mAdapter.setData(DaoAccount.queryByZhiChuSHouRuType(Config.ZHI_CHU));
            mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
            mViewStub.setVisibility(View.GONE);
        } else {
            mAdapter.setData(mList);
            mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
            mViewStub.setVisibility(View.VISIBLE);
        }
        mRecyclerView.addItemDecoration(BGADivider.newShapeDivider());
    }

    private void initData() {


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

}
