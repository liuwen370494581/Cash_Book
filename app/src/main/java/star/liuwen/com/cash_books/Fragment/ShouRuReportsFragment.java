package star.liuwen.com.cash_books.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGADivider;
import star.liuwen.com.cash_books.Adapter.ReportsDetailAdapter;
import star.liuwen.com.cash_books.Base.BaseFragment;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoAccount;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.bean.AccountModel;

/**
 * Created by liuwen on 2017/2/22.
 */
public class ShouRuReportsFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private ReportsDetailAdapter mAdapter;
    private List<AccountModel> mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.shouru_reports_fragment);
        initView();
        initData();
        return getContentView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) getContentView().findViewById(R.id.shouru_reports_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new ReportsDetailAdapter(mRecyclerView);
        if (DaoAccount.queryByZhiChuSHouRuType(Config.SHOU_RU).size() != 0 || DaoAccount.queryByZhiChuSHouRuType(Config.SHOU_RU) != null) {
            mAdapter.setData(DaoAccount.queryByZhiChuSHouRuType(Config.SHOU_RU));
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setData(mList);
            mRecyclerView.setAdapter(mAdapter);
        }
        mRecyclerView.addItemDecoration(BGADivider.newShapeDivider());
    }

    private void initData() {

    }
}
