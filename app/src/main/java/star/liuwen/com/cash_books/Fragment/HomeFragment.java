package star.liuwen.com.cash_books.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.TextView;

import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import star.liuwen.com.cash_books.Activity.BudgetActivity;
import star.liuwen.com.cash_books.Activity.CalendarActivity;
import star.liuwen.com.cash_books.Activity.SearchResultActivity;
import star.liuwen.com.cash_books.Base.BaseFragment;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoAccount;
import star.liuwen.com.cash_books.Dao.DaoBudget;
import star.liuwen.com.cash_books.Dialog.TipandEditDialog;
import star.liuwen.com.cash_books.EventBus.C;
import star.liuwen.com.cash_books.EventBus.Event;
import star.liuwen.com.cash_books.EventBus.EventBusUtil;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.Utils.RxUtil;
import star.liuwen.com.cash_books.View.DefineBAGRefreshWithLoadView;
import star.liuwen.com.cash_books.View.NumberAnimTextView;
import star.liuwen.com.cash_books.View.WaveLoadingView;
import star.liuwen.com.cash_books.bean.AccountModel;
import star.liuwen.com.cash_books.bean.BudgetModel;

/**
 * 明细
 */
public class HomeFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, BGAOnRVItemClickListener, BGAOnRVItemLongClickListener, IOnSearchClickListener, View.OnClickListener {
    private RecyclerView mRecyclerView;
    private HomesAdapter mAdapter;

    private List<AccountModel> mList;
    private TextView tvShouRuMonth, tvZhiChuMonth;
    private NumberAnimTextView tvShouRuData, tvZhiChuData;

    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView = null;
    private BGARefreshLayout mBGARefreshLayout;

    private ViewStub mViewStub;
    private View headView;
    private double zhiChuAdd, shouRuAdd;
    private double totalZhiChuAdd, totalShouRuAdd;
    private AccountModel model;

    private SearchFragment searchFragment;//增加了主页搜索功能
    private WaveLoadingView mCircleProgress; //预算
    private String budgetMoney;
    private BudgetModel mBudgetModel;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_home);
        setTitle(getString(R.string.home_mingxi));
        setRightImage(R.mipmap.icon_zhichu_type_baoxiaozhang, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CalendarActivity.class));
            }
        });
        setLeftImages(R.mipmap.search_icon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFragment.show(getActivity().getSupportFragmentManager(), SearchFragment.TAG);
            }
        });
        initView();
        return getContentView();
    }

    /**
     * 回调搜索结果
     *
     * @param keyword
     */
    @Override
    public void OnSearchClick(String keyword) {
        Intent intent = new Intent(getActivity(), SearchResultActivity.class);
        intent.putExtra(Config.TxtSearchResult, keyword);
        startActivity(intent);
        ((Activity) getContext()).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在界面中点击文本输入框，键盘弹出后有时会将界面中的布局顶起。为避免该情况，可在onCreate()方法中加入以下代码:
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //当点击搜索的时候 会把底部的布局弹出
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        setBgaRefreshLayout();
    }

    private void initAdapter() {
        headView = View.inflate(getActivity(), R.layout.layout_head_home, null);
        tvShouRuMonth = (TextView) headView.findViewById(R.id.home_shouru_month);
        tvZhiChuMonth = (TextView) headView.findViewById(R.id.home_zhichu_month);
        tvShouRuData = (NumberAnimTextView) headView.findViewById(R.id.home_shouru_data);
        tvZhiChuData = (NumberAnimTextView) headView.findViewById(R.id.home_zhichu_data);

        mCircleProgress = (WaveLoadingView) headView.findViewById(R.id.f_h_image);
        mCircleProgress.setDescribe(getString(R.string.no_setting_month_Budget));
        mCircleProgress.setOnClickListener(this);

        if (DaoBudget.getCount() != 0) {
            mBudgetModel = DaoBudget.query().get(0);
            if(mBudgetModel.getOpenBudget()){
                mCircleProgress.setDescribe(mBudgetModel.getBudgetDescription());
                mCircleProgress.setMoney(mBudgetModel.getBudgetMoney());
                mCircleProgress.setPercent(mBudgetModel.getBudgetRemainMoney());
            }
        }
        tvShouRuMonth.setText(String.format("%s收入", DateTimeUtil.getCurrentMonth()));
        tvZhiChuMonth.setText(String.format("%s支出", DateTimeUtil.getCurrentMonth()));
        mList = new ArrayList<>();
        mAdapter = new HomesAdapter(mRecyclerView);
        mAdapter.addHeaderView(headView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        if (DaoAccount.query().size() != 0) {
            Observable.create(new Observable.OnSubscribe<List<AccountModel>>() {
                @Override
                public void call(Subscriber<? super List<AccountModel>> subscriber) {
                    mList = DaoAccount.query();
                    subscriber.onNext(mList);
                }
            }).compose(RxUtil.<List<AccountModel>>applySchedulers()).subscribe(new Action1<List<AccountModel>>() {
                @Override
                public void call(List<AccountModel> models) {
                    for (int i = 0; i < models.size(); i++) {
                        totalZhiChuAdd = totalZhiChuAdd + models.get(i).getZhiCHuAdd();
                        totalShouRuAdd = totalShouRuAdd + models.get(i).getSHouRuAdd();
                    }
                    tvZhiChuData.setNumberString(String.format("%.2f", totalZhiChuAdd));
                    tvShouRuData.setNumberString(String.format("%.2f", totalShouRuAdd));
                    mAdapter.addNewData(models);
                    mAdapter.addLastItem(new AccountModel(DaoAccount.getCount(), "", "", 0, "", R.mipmap.xiaolian, "", "", 0, 0, 0, 0, "你于" + DateTimeUtil.getCurrentYear() + "开启了你的记账之路", -1, ""));
                    mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
                }
            });
        } else {
            mViewStub.inflate();
            headView.setVisibility(View.GONE);
            mBGARefreshLayout.setVisibility(View.GONE);
            mAdapter.addNewData(mList);
            mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
        }
        mAdapter.setOnRVItemClickListener(this);
        mAdapter.setOnRVItemLongClickListener(this);
    }


    /**
     * 设置 BGARefreshLayout刷新和加载
     */
    private void setBgaRefreshLayout() {
        mDefineBAGRefreshWithLoadView = new DefineBAGRefreshWithLoadView(getActivity(), true, true);
        //设置刷新样式
        mBGARefreshLayout.setRefreshViewHolder(mDefineBAGRefreshWithLoadView);
        mDefineBAGRefreshWithLoadView.setRefreshingText("同步账单中...");
        mDefineBAGRefreshWithLoadView.setPullDownRefreshText("同步账单中...");
        mDefineBAGRefreshWithLoadView.setReleaseRefreshText("下拉同步账单中...");
    }

    private void initView() {
        mViewStub = (ViewStub) getContentView().findViewById(R.id.view_stub);
        mRecyclerView = (RecyclerView) getContentView().findViewById(R.id.f_h_recycler);
        mBGARefreshLayout = (BGARefreshLayout) getContentView().findViewById(R.id.define_bga_refresh_with_load);   //设置刷新和加载监听
        mBGARefreshLayout.setDelegate(this);
        searchFragment = SearchFragment.newInstance();
        searchFragment.setOnSearchClickListener(this);

    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        switch (event.getCode()) {
            case C.EventCode.ZhiChuToHomeFragment:
                //接收从支出或者是收入页面传递过来的值
                mList = (List<AccountModel>) event.getData();
                insertHomeList();
                if (mBudgetModel.getOpenBudget()) {
                    mBudgetModel = DaoBudget.query().get(0);
                    mCircleProgress.setMoney(mBudgetModel.getBudgetMoney());
                    mCircleProgress.setDescribe(getString(R.string.monthBudget));
                    mCircleProgress.setPercent(mBudgetModel.getBudgetRemainMoney());
                }
                break;
            case C.EventCode.BudgetActivityToHomeFragment:
                mBudgetModel = (BudgetModel) event.getData();
                mCircleProgress.setDescribe(getString(R.string.monthBudget));
                mCircleProgress.setPercent(100);
                mCircleProgress.setMoney(mBudgetModel.getBudgetMoney());
                break;
        }
    }
    private void insertHomeList() {
        mAdapter.addNewData(mList);
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getZhiChuShouRuType().equals(Config.ZHI_CHU)) {
                zhiChuAdd += mList.get(i).getMoney() + totalZhiChuAdd;
                //避免第二次总数在相加 只需要加一次
                totalZhiChuAdd = 0;
                tvZhiChuData.setNumberString(String.format("%.2f", zhiChuAdd));
                model = new AccountModel();
                int y = 1 + (int) (Math.random() * 1000);
                model.setId(DaoAccount.getCount() + y);
                model.setAccountType(mList.get(i).getAccountType());
                model.setData(mList.get(i).getData());
                model.setMoney(mList.get(i).getMoney());
                model.setConsumeType(mList.get(i).getConsumeType());
                model.setUrl(mList.get(i).getUrl());
                model.setTimeMinSec(DateTimeUtil.getCurrentTime_Today());
                model.setZhiChuShouRuType(Config.ZHI_CHU);
                model.setZhiCHuAdd(mList.get(i).getMoney());
                model.setConsumePercent((float) (model.getMoney() / zhiChuAdd) * 100);
                model.setShowFirstDate("");
                model.setChoiceAccountId(mList.get(i).getChoiceAccountId());
                //这里设置金额主要是为了和预算挂钩 消费支出的金额和预算并且更新
                mBudgetModel.setBudgetMoney(String.format("%.2f", Double.parseDouble(mBudgetModel.getBudgetMoney()) - mList.get(i).getMoney()));
                double dPercent = (mList.get(i).getMoney() / Double.parseDouble(mBudgetModel.getBudgetMoney())) * 100;
                int budgetPercent = (int) dPercent;
                mBudgetModel.setBudgetRemainMoney(100 - budgetPercent);
                DaoBudget.update(mBudgetModel);
                Observable.create(new Observable.OnSubscribe<AccountModel>() {
                    @Override
                    public void call(Subscriber<? super AccountModel> subscriber) {
                        DaoAccount.insertAccount(model);
                        subscriber.onNext(model);
                    }
                }).compose(RxUtil.<AccountModel>applySchedulers()).subscribe(new Action1<AccountModel>() {
                    @Override
                    public void call(AccountModel model) {
                    }
                });

                //在这里加的一段主要是为了只添加一次你于什么时候开启了你的记账之路
                Observable.create(new Observable.OnSubscribe<List<AccountModel>>() {
                    @Override
                    public void call(Subscriber<? super List<AccountModel>> subscriber) {
                        List<AccountModel> list = DaoAccount.query();
                        subscriber.onNext(list);
                    }
                }).compose(RxUtil.<List<AccountModel>>applySchedulers()).subscribe(new Action1<List<AccountModel>>() {
                    @Override
                    public void call(List<AccountModel> models) {
                        if (models.size() == 1) {
                            mAdapter.addLastItem(new AccountModel(DaoAccount.getCount(), "", "", 0, "", R.mipmap.xiaolian, "", "", 0, 0, 0, 0, "你于" + DateTimeUtil.getCurrentYear() + "开启了你的记账之路", -1, ""));
                        }
                    }
                });

            } else {
                shouRuAdd += mList.get(i).getMoney() + totalShouRuAdd;
                //避免第二次总数在相加 只需要加一次
                totalShouRuAdd = 0;
                tvShouRuData.setNumberString(String.format("%.2f", shouRuAdd));
                model = new AccountModel();
                //为了解决ID的唯一性产生的bug 当删除一个item的时候 id依然存在数据库中 在插入的时候 会插入同样的数据 所以使用了随机数
                int y = 1 + (int) (Math.random() * 1000);
                model.setId(DaoAccount.getCount() + y);
                model.setAccountType(mList.get(i).getAccountType());
                model.setData(mList.get(i).getData());
                model.setMoney(mList.get(i).getMoney());
                model.setConsumeType(mList.get(i).getConsumeType());
                model.setUrl(mList.get(i).getUrl());
                model.setTimeMinSec(DateTimeUtil.getCurrentTime_Today());
                model.setZhiChuShouRuType(Config.SHOU_RU);
                model.setSHouRuAdd(mList.get(i).getMoney());
                model.setConsumePercent((float) (model.getMoney() / shouRuAdd) * 100);
                model.setShowFirstDate("");
                model.setChoiceAccountId(mList.get(i).getChoiceAccountId());
                Observable.create(new Observable.OnSubscribe<AccountModel>() {
                    @Override
                    public void call(Subscriber<? super AccountModel> subscriber) {
                        DaoAccount.insertAccount(model);
                        subscriber.onNext(model);
                    }
                }).compose(RxUtil.<AccountModel>applySchedulers()).subscribe(new Action1<AccountModel>() {
                    @Override
                    public void call(AccountModel model) {
                    }
                });

                //在这里加的一段主要是为了只添加一次你于什么时候开启了你的记账之路
                Observable.create(new Observable.OnSubscribe<List<AccountModel>>() {
                    @Override
                    public void call(Subscriber<? super List<AccountModel>> subscriber) {
                        List<AccountModel> list = DaoAccount.query();
                        subscriber.onNext(list);
                    }
                }).compose(RxUtil.<List<AccountModel>>applySchedulers()).subscribe(new Action1<List<AccountModel>>() {
                    @Override
                    public void call(List<AccountModel> models) {
                        if (models.size() == 1) {
                            mAdapter.addLastItem(new AccountModel(DaoAccount.getCount(), "", "", 0, "", R.mipmap.xiaolian, "", "", 0, 0, 0, 0, "你于" + DateTimeUtil.getCurrentYear() + "开启了你的记账之路", -1, ""));
                        }
                    }
                });
            }
        }
        mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
        mViewStub.setVisibility(View.GONE);
        headView.setVisibility(View.VISIBLE);
        mBGARefreshLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mDefineBAGRefreshWithLoadView.showLoadingMoreImg();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBGARefreshLayout.endRefreshing();
            }
        }, 1500);

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

    }

    @Override
    public boolean onRVItemLongClick(final ViewGroup parent, View itemView, final int position) {
        if (mAdapter.getItemCount() - 1 == position) {
            return false;
        }
        final TipandEditDialog dialog = new TipandEditDialog(getActivity(), "确定要删除吗?");
        dialog.show();
        dialog.setLeftText(getString(R.string.cancel));
        dialog.setLeftTextColor(getResources().getColor(R.color.jiechu));
        dialog.setRightText(getString(R.string.sure));
        dialog.setRightTextColor(getResources().getColor(R.color.blue));
        dialog.setListener(new TipandEditDialog.ITipEndEditDialogListener() {
                               @Override
                               public void ClickLeft() {
                                   dialog.dismiss();
                               }

                               @Override
                               public void ClickRight() {
                                   mAdapter.removeItem(position);
                                   DaoAccount.deleteAccountById(DaoAccount.query().get(position).getId());
                                   EventBusUtil.sendEvent(new Event(C.EventCode.HomeFragmentToReports, true));
                                   mList = DaoAccount.query();
                                   if (DaoAccount.query().size() == 0) {
                                       mAdapter.clear();
                                       totalShouRuAdd = 0;
                                       totalZhiChuAdd = 0;
                                       headView.setVisibility(View.GONE);
                                       mBGARefreshLayout.setVisibility(View.GONE);
                                       mViewStub.setVisibility(View.VISIBLE);
                                   } else {
                                       totalShouRuAdd = 0;
                                       totalZhiChuAdd = 0;
                                       for (int i = 0; i < mList.size(); i++) {
                                           totalZhiChuAdd = totalZhiChuAdd + mList.get(i).getZhiCHuAdd();
                                           totalShouRuAdd = totalShouRuAdd + mList.get(i).getSHouRuAdd();
                                       }
                                       tvZhiChuData.setText(String.format("%.2f", totalZhiChuAdd));
                                       tvShouRuData.setText(String.format("%.2f", totalShouRuAdd));
                                   }
                               }
                           }

        );
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == mCircleProgress) {
            Intent intent = new Intent(getActivity(), BudgetActivity.class);
            intent.putExtra(Config.ModelBudget, mBudgetModel);
            startActivity(intent);
        }
    }


    public class HomesAdapter extends BGARecyclerViewAdapter<AccountModel> {

        public HomesAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_homefragment);

        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, AccountModel model) {
            helper.setText(R.id.item_home_show, model.getShowFirstDate());
            if (model.getZhiChuShouRuType().equals(Config.ZHI_CHU)) {
                helper.setVisibility(R.id.view_3, View.VISIBLE);
                helper.setVisibility(R.id.item_home_rezhichu, View.VISIBLE);
                helper.setVisibility(R.id.item_home_reshouru, View.GONE);
                helper.setImageResource(R.id.item_home_url, model.getUrl());
                helper.setText(R.id.item_home_txtzhichuname, model.getConsumeType());
                if (model.getMoney() != 0) {
                    helper.setText(R.id.item_home_txtzhichumoney, String.format("%.2f", model.getMoney()));
                } else {
                    helper.setVisibility(R.id.view_3, View.GONE);
                    helper.setText(R.id.item_home_txtzhichumoney, "");
                }
                helper.setText(R.id.item_home_txtzhiRemark, model.getAccountType());
            } else {
                helper.setVisibility(R.id.view_3, View.VISIBLE);
                helper.setVisibility(R.id.item_home_reshouru, View.VISIBLE);
                helper.setVisibility(R.id.item_home_rezhichu, View.GONE);
                helper.setImageResource(R.id.item_home_url, model.getUrl());
                helper.setText(R.id.item_home_txtshouruname, model.getConsumeType());
                if (model.getMoney() != 0) {
                    helper.setVisibility(R.id.view_3, View.VISIBLE);
                    helper.setText(R.id.item_home_txtshourumoney, String.format("%.2f", model.getMoney()));
                } else {
                    helper.setVisibility(R.id.view_3, View.GONE);
                    helper.setText(R.id.item_home_txtshourumoney, "");
                }
                helper.setText(R.id.item_home_txtshouruRemark, model.getAccountType());
            }
        }
    }

}


