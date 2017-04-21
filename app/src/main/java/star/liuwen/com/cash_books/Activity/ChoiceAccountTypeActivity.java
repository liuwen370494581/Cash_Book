package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGADivider;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Enage.DataEnige;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.RxUtil;
import star.liuwen.com.cash_books.bean.PlanSaveMoneyModel;

/**
 * Created by liuwen on 2017/3/21.
 */
public class ChoiceAccountTypeActivity extends BaseActivity implements BGAOnRVItemClickListener {
    private RecyclerView mRecyclerView;
    private ChoiceAccountTypeAdapter mAdapter;
    private List<PlanSaveMoneyModel> mList = new ArrayList<>();

    @Override
    public int activityLayoutRes() {
        return R.layout.choice_account_type_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setTitle(getString(R.string.choice_account_type));
        setLeftText(getString(R.string.back));
        setLeftImage(R.mipmap.fanhui_lan);
        mRecyclerView = (RecyclerView) findViewById(R.id.choice_account_type_recyclerView);
        mAdapter = new ChoiceAccountTypeAdapter(mRecyclerView);
        Observable.create(new Observable.OnSubscribe<List<PlanSaveMoneyModel>>() {
            @Override
            public void call(Subscriber<? super List<PlanSaveMoneyModel>> subscriber) {
                mList = DataEnige.getAddChoiceAccount();
                subscriber.onNext(mList);
            }
        }).compose(RxUtil.<List<PlanSaveMoneyModel>>applySchedulers()).subscribe(new Action1<List<PlanSaveMoneyModel>>() {
            @Override
            public void call(List<PlanSaveMoneyModel> models) {
                mAdapter.setData(models);
                LinearLayoutManager manager = new LinearLayoutManager(ChoiceAccountTypeActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(manager);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnRVItemClickListener(ChoiceAccountTypeActivity.this);
                mRecyclerView.addItemDecoration(BGADivider.newShapeDivider());
            }
        });
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        Intent intent = new Intent(ChoiceAccountTypeActivity.this, newAddAccountActivity.class);
        intent.putExtra(Config.ModelChoiceAccount, mList.get(position));
        startActivity(intent);
    }

    protected class ChoiceAccountTypeAdapter extends BGARecyclerViewAdapter<PlanSaveMoneyModel> {
        public ChoiceAccountTypeAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_choice_account_type);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, PlanSaveMoneyModel model) {
            helper.setImageResource(R.id.item_rd_image, model.getUrl());
            helper.setText(R.id.item_rd_txtName, model.getAdd());
            helper.setText(R.id.item_rd_remark, model.getMessage());
        }
    }
}
