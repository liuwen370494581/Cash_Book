package star.liuwen.com.cash_books.Fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import star.liuwen.com.cash_books.Activity.ReportsDetailActivity;
import star.liuwen.com.cash_books.Base.BaseFragment;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoAccount;
import star.liuwen.com.cash_books.Enage.DataEnige;
import star.liuwen.com.cash_books.PieChart.OnDateChangedLinstener;
import star.liuwen.com.cash_books.PieChart.StatisticsView;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.RxBus.RxBusResult;
import star.liuwen.com.cash_books.Utils.BitMapUtils;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.bean.AccountModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportsFragment extends Fragment implements OnDateChangedLinstener, StatisticsView.OnClickDetailListener {
    private StatisticsView mView;
    private int total = 100;
    private float[] moneys;
    private String[] types;
    private float zhiChuAdd;
    private List<AccountModel> mList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistics_layout, container, false);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        initData();
        if (DaoAccount.queryByZhiChuSHouRuType(Config.ZHI_CHU).size() != 0) {
            mList = DaoAccount.queryByZhiChuSHouRuType(Config.ZHI_CHU);
            types = new String[mList.size()];
            moneys = new float[mList.size()];
            for (int i = 0; i < mList.size(); i++) {
                zhiChuAdd = zhiChuAdd + Float.parseFloat(String.valueOf(mList.get(i).getMoney()));
                types[i] = mList.get(i).getConsumeType();
                moneys[i] = Float.parseFloat(String.valueOf(mList.get(i).getMoney()));
            }
        }
        mView = new StatisticsView(getActivity(), moneys, total, types);
        if (DaoAccount.queryByZhiChuSHouRuType(Config.ZHI_CHU).size() != 0) {
            mView.setReVisible(true);//显示
            mView.setViewStubVisible(true);//不显示
        } else {
            mView.setReVisible(false);//不显示
            mView.setViewStubVisible(false);//显示
        }
        mView.setConsumeDetail(zhiChuAdd);
        mView.setCurrDate(year, month);
        mView.setDateChangedListener(this);
        mView.setDetailListener(this);

        return mView;
    }

    private void initData() {
        RxBus.getInstance().toObserverableOnMainThread(Config.RxToReports, new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                boolean isShow = (boolean) o;
                mView.setReVisible(false);
                mView.setViewStubVisible(false);
            }
        });

        RxBus.getInstance().toObserverableOnMainThread("AccountModel", new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                mView.setReVisible(true);
                mView.setViewStubVisible(true);
            }
        });
    }

    @Override
    public void onDateChanged(String startDate, String endDate) {
        ToastUtils.showToast(getActivity(), "点击了日期" + startDate + "--" + endDate);
    }

    @Override
    public void showDetail() {
        startActivity(new Intent(getActivity(), ReportsDetailActivity.class));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().release();
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.i("TAG", "onPause");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("TAG", "onStart");
        Log.i("TAG", "onNext");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v("MainActivity", "onstop");
    }
}
