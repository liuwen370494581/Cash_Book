package star.liuwen.com.cash_books.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import java.util.ArrayList;
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
import star.liuwen.com.cash_books.bean.AccountModel;

/**
 * Created by liuwen on 2017/2/22.
 */
public class ZhiChuReportsFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private ReportsDetailAdapter mAdapter;
    private List<AccountModel> mList = new ArrayList<>();
    private ViewStub mViewStub;


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
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new ReportsDetailAdapter(mRecyclerView);
        if (DaoAccount.queryByZhiChuSHouRuType(Config.ZHI_CHU).size() != 0) {
            mAdapter.setData(DaoAccount.queryByZhiChuSHouRuType(Config.ZHI_CHU));
            mRecyclerView.setAdapter(mAdapter);
            mViewStub.setVisibility(View.GONE);
        } else {
            mAdapter.setData(mList);
            mRecyclerView.setAdapter(mAdapter);
            mViewStub.setVisibility(View.VISIBLE);
        }
        mRecyclerView.addItemDecoration(BGADivider.newShapeDivider());
    }

    private void initData() {

    }

}
