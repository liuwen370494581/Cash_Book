package star.liuwen.com.cash_books.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.github.lzyzsd.circleprogress.DonutProgress;

import cn.bingoogolapple.androidcommon.adapter.BGADivider;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.transformer.TransitionEffect;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import star.liuwen.com.cash_books.Activity.WebViewActivity;
import star.liuwen.com.cash_books.Base.BaseFragment;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Enage.DataEnige;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.View.DefineBAGRefreshWithLoadView;
import star.liuwen.com.cash_books.bean.AccountWealthModel;

/**
 * Created by liuwen on 2017/5/9.
 * 记账财富页面
 */
public class AccountingWealthFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {

    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView = null;
    private BGARefreshLayout mBGARefreshLayout;
    private View headView;
    private RecyclerView mRecyclerView;
    private AccountingWealthAdapter mAdapter;
    private BGABanner mBGABanner;
    private int resViews[] = {R.mipmap.welcome_pic_1, R.mipmap.welcome_pic_2, R.mipmap.welcome_pic_3, R.mipmap.welcome_pic_4};
    private RelativeLayout reNewPersonGift, reEveryDaySignIn, reInviteAReward, reSafetyGuarantee;


    @Override
    public void lazyInitData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_accouting_wealth);
        initView();
        initData();
        return getContentView();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        setBgaRefreshLayout();
    }

    private void setBgaRefreshLayout() {
        mDefineBAGRefreshWithLoadView = new DefineBAGRefreshWithLoadView(getActivity(), true, true);
        //设置刷新样式
        mBGARefreshLayout.setRefreshViewHolder(mDefineBAGRefreshWithLoadView);
        mDefineBAGRefreshWithLoadView.setRefreshingText("同步记账财富中...");
        mDefineBAGRefreshWithLoadView.setPullDownRefreshText("同步记账财富中...");
        mDefineBAGRefreshWithLoadView.setReleaseRefreshText("下拉同步财富中...");
    }

    private void initAdapter() {
        headView = View.inflate(getActivity(), R.layout.head_accounting_wealth, null);
        mBGABanner = (BGABanner) headView.findViewById(R.id.banner);
        reNewPersonGift = (RelativeLayout) headView.findViewById(R.id.onNewPersonGift);
        reEveryDaySignIn = (RelativeLayout) headView.findViewById(R.id.onEveryDaySignIn);
        reInviteAReward = (RelativeLayout) headView.findViewById(R.id.inviteAReward);
        reSafetyGuarantee = (RelativeLayout) headView.findViewById(R.id.safetyGuarantee);
        mAdapter = new AccountingWealthAdapter(mRecyclerView);
        mAdapter.addHeaderView(headView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.setData(DataEnige.getWealthData());
        mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
        mRecyclerView.addItemDecoration(BGADivider.newShapeDivider());
        mBGABanner.setData(DataEnige.getBannerDataUrl(), DataEnige.getBannerDataTips());
        //设置动画翻转
        mBGABanner.setTransitionEffect(TransitionEffect.Fade);
        mBGABanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                Glide.with(banner.getContext()).load(model).placeholder(R.mipmap.background).error(R.mipmap.background).dontAnimate().thumbnail(0.1f).into((ImageView) view);
            }
        });

        reNewPersonGift.setOnClickListener(this);
        reSafetyGuarantee.setOnClickListener(this);
        reInviteAReward.setOnClickListener(this);
        reEveryDaySignIn.setOnClickListener(this);
    }

    private void initView() {
        mRecyclerView = (RecyclerView) getContentView().findViewById(R.id.f_A_recycler);
        mBGARefreshLayout = (BGARefreshLayout) getContentView().findViewById(R.id.define_bga_refresh_with_load);   //设置刷新和加载监听
        mBGARefreshLayout.setDelegate(this);

    }


    private void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        if (v == reNewPersonGift) {
            intent.putExtra(Config.IntentTag_ToWebView, Config.NewPersonGift);
            startActivity(intent);
        } else if (v == reEveryDaySignIn) {
            intent.putExtra(Config.IntentTag_ToWebView, Config.EveryDaySignIn);
            startActivity(intent);
        } else if (v == reInviteAReward) {
            intent.putExtra(Config.IntentTag_ToWebView, Config.InviteAReward);
            startActivity(intent);
        } else if (v == reSafetyGuarantee) {
            intent.putExtra(Config.IntentTag_ToWebView, Config.SafetyGuarantee);
            startActivity(intent);
        }
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


    private class AccountingWealthAdapter extends BGARecyclerViewAdapter<AccountWealthModel> {
        private DonutProgress mProgress;

        public AccountingWealthAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_accouting_wealth);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, AccountWealthModel model) {
            if (needPosition(position)) {
                helper.setVisibility(R.id.item_data, View.VISIBLE);
                helper.setText(R.id.item_data, model.getWealthTitle());
            } else {
                helper.setVisibility(R.id.item_data, View.GONE);
            }
            helper.setText(R.id.txt_name, model.getWealthName());
            helper.setText(R.id.txt_shouyilv, String.format("%.2f", model.getWealthPercent()) + "%");
            helper.setText(R.id.txt_day, model.getWealthDay());
            helper.setText(R.id.txt_bao, model.getWealthBaoOrNewOrHot());
            helper.setText(R.id.txt_year_shouyilv, model.getWealthYearYield());
            helper.setText(R.id.txt_time, model.getWealthIsHuoqiOrDingQi());
            mProgress = helper.getView(R.id.double_progress);
            mProgress.setProgress((float) model.getWealthPlan());

            if (model.getWealthIsHuoqiOrDingQi().equals(Config.HUOQI)) {
                helper.setVisibility(R.id.double_progress, View.GONE);
                helper.setVisibility(R.id.re_save_money, View.VISIBLE);
            } else {
                helper.setVisibility(R.id.double_progress, View.VISIBLE);
                helper.setVisibility(R.id.re_save_money, View.GONE);
            }

        }

        private boolean needPosition(int position) {
            //第一个是分类：
            if (position == 0) {
                return true;
            }
            if (position < 0) {
                return false;
            }
            AccountWealthModel currentModel = (AccountWealthModel) getItem(position);
            AccountWealthModel previousModel = (AccountWealthModel) getItem(position - 1);
            if (currentModel == null || previousModel == null) {
                return false;
            }
            String currentTitle = currentModel.getWealthTitle();
            String previousTitle = previousModel.getWealthTitle();

            if (currentTitle.equals(previousTitle)) {
                return false;
            }
            return true;
        }
    }
}
