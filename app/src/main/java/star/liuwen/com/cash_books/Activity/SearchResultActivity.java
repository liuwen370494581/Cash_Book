package star.liuwen.com.cash_books.Activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;

import java.util.ArrayList;
import java.util.List;

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
import star.liuwen.com.cash_books.Utils.RxUtil;
import star.liuwen.com.cash_books.bean.AccountModel;

/**
 * Created by liuwen on 2017/5/8.
 */
public class SearchResultActivity extends BaseActivity {


    private String searchResult;
    private List<AccountModel> mList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ViewStub mViewStub;
    private SearchResultAdapter mAdapter;


    @Override
    public int activityLayoutRes() {
        return R.layout.search_result_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setLeftImage(R.mipmap.fanhui_lan);
        setTitle(getString(R.string.search_result));
        setLeftText(getString(R.string.back));

        mRecyclerView = (RecyclerView) findViewById(R.id.search_result_recycler_view);
        mViewStub = (ViewStub) findViewById(R.id.view_stub);
        mViewStub.inflate();
        mViewStub.setVisibility(View.GONE);

        mAdapter = new SearchResultAdapter(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(BGADivider.newShapeDivider());

        searchResult = getIntent().getStringExtra(Config.TxtSearchResult);
        if (null != searchResult) {
            if (DaoAccount.queryByKeyWord(searchResult).size() != 0) {
                Observable.create(new Observable.OnSubscribe<List<AccountModel>>() {
                    @Override
                    public void call(Subscriber<? super List<AccountModel>> subscriber) {
                        mList = DaoAccount.queryByKeyWord(searchResult);
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
            }
        }
    }


    private class SearchResultAdapter extends BGARecyclerViewAdapter<AccountModel> {

        public SearchResultAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_reports_detail);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, AccountModel model) {
            if (needTitle(position)) {
                helper.setVisibility(R.id.item_data, View.VISIBLE);
                helper.setText(R.id.item_data, model.getData());
            } else {
                helper.setVisibility(R.id.item_data, View.GONE);
            }
            helper.setImageResource(R.id.item_rd_image, model.getUrl());
            helper.setText(R.id.item_rd_txtName, model.getConsumeType());
            helper.setVisibility(R.id.item_rd_percent, View.GONE);
            if (model.getZhiChuShouRuType().equals(Config.ZHI_CHU)) {
                helper.setText(R.id.item_rd_money, String.format("%.2f", model.getMoney()) + "元");
            } else {
                helper.setText(R.id.item_rd_money, String.format("+%.2f", model.getMoney()) + "元");
                helper.setTextColor(R.id.item_rd_money, getResources().getColor(R.color.blue));
            }


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
