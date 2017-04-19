package star.liuwen.com.cash_books.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import star.liuwen.com.cash_books.Activity.ChoiceAccountTypeActivity;
import star.liuwen.com.cash_books.Activity.PayShowActivity;
import star.liuwen.com.cash_books.Activity.TransferActivity;
import star.liuwen.com.cash_books.Base.BaseFragment;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoChoiceAccount;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.RxBus.RxBusResult;
import star.liuwen.com.cash_books.Utils.BitMapUtils;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.bean.ChoiceAccount;

/**
 * Created by liuwen on 2017/2/16.
 */
public class WalletFragment extends BaseFragment implements BGAOnRVItemClickListener {
    private RecyclerView mRecyclerView;
    private WalletAdapter mAdapter;
    private RelativeLayout mRyYuer;
    private TextView tvYuer;
    private int position;
    private DrawerLayout mDrawerLayout;
    private List<ChoiceAccount> mList;
    private double totalYue, yuer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_wallet);
        setTitle(getString(R.string.qianbao_my));
        setRightText(getString(R.string.qianbao_zhuanzhang), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TransferActivity.class));
            }
        });
        initView();
        initData();
        return getContentView();
    }

    private void initView() {
        View headView = View.inflate(getActivity(), R.layout.head_walletfragment, null);
        mRyYuer = (RelativeLayout) headView.findViewById(R.id.qb_ry_yuer);
        tvYuer = (TextView) headView.findViewById(R.id.yuer_jia);
        mDrawerLayout = (DrawerLayout) getContentView().findViewById(R.id.drawer_layout);
        mRecyclerView = (RecyclerView) getContentView().findViewById(R.id.wallet_fragment_recyclerView);
        mAdapter = new WalletAdapter(mRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList = new ArrayList<>();
        mAdapter.addHeaderView(headView);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (DaoChoiceAccount.query().size() != 0) {
            mList = DaoChoiceAccount.query();
            mAdapter.setData(mList);
            mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
            for (int i = 0; i < mList.size(); i++) {
                totalYue = totalYue + mList.get(i).getMoney();
            }
            tvYuer.setText(String.format("%.2f", totalYue));
        } else {
            mAdapter.setData(mList);
            mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
        }
        mAdapter.addLastItem(new ChoiceAccount(DaoChoiceAccount.getCount(), R.mipmap.icon_add, "添加账户", 0.00, 0.00, "", "", R.color.transparent, "添加", 0.00, 0.00, DateTimeUtil.getCurrentYear()));
        mAdapter.setOnRVItemClickListener(this);
        if (SharedPreferencesUtil.getStringPreferences(getActivity(), Config.ChangeBg, null) != null) {
            Bitmap bitmap = BitMapUtils.getBitmapByPath(getActivity(), SharedPreferencesUtil.getStringPreferences(getActivity(), Config.ChangeBg, null), false);
            mDrawerLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
        }
    }


    private void initData() {

        RxBus.getInstance().toObserverableOnMainThread(Config.isBgCash, new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                Bitmap bitmap = BitMapUtils.getBitmapByPath(getActivity(), o.toString(), false);
                mDrawerLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
            }
        });

        RxBus.getInstance().toObserverableOnMainThread(Config.RxPayShowActivityToWalletFragment, new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                mList = DaoChoiceAccount.query();
                mAdapter.setData(mList);
                mAdapter.addLastItem(new ChoiceAccount(DaoChoiceAccount.getCount(), R.mipmap.icon_add, "添加账户", 0.00, 0.00, "", "", R.color.transparent, "添加", 0.00, 0.00, DateTimeUtil.getCurrentYear()));
                for (int i = 0; i < mList.size(); i++) {
                    yuer = yuer + mList.get(i).getMoney();
                }
                tvYuer.setText(String.format("%.2f", yuer));
                //因为余额的数值会添加要设为0重新开始算
                yuer = 0;
            }
        });

        RxBus.getInstance().toObserverableChildThread(Config.RxZhiChuFragmentToWalletFragment, new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                mList = DaoChoiceAccount.query();
                mAdapter.setData(mList);
                mAdapter.addLastItem(new ChoiceAccount(DaoChoiceAccount.getCount(), R.mipmap.icon_add, "添加账户", 0.00, 0.00, "", "", R.color.transparent, "添加", 0.00, 0.00, DateTimeUtil.getCurrentYear()));
                for (int i = 0; i < mList.size(); i++) {
                    yuer = yuer + mList.get(i).getMoney();
                }
                tvYuer.setText(String.format("%.2f", yuer));
                //因为余额的数值会添加要设为0重新开始算
                yuer = 0;
            }
        });

        RxBus.getInstance().toObserverableOnMainThread(Config.RxModelToWalletFragment, new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                ChoiceAccount account = (ChoiceAccount) o;
                if (mList.size() != 0) {
                    mAdapter.addItem(mList.size() - 1, account);
                }
                for (ChoiceAccount ss : mList) {
                    Log.e("MainActivity", ss.getAccountName() + "" + ss.getMoney());
                }
                for (int i = 0; i < mList.size(); i++) {
                    yuer = yuer + mList.get(i).getMoney();
                }
                tvYuer.setText(String.format("%.2f", yuer));
                //因为余额的数值会添加要设为0重新开始算
                yuer = 0;

            }
        });

        RxBus.getInstance().toObserverableOnMainThread(Config.RxPaySettingToWalletFragment, new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                HashMap<Integer, ChoiceAccount> hashMap = (HashMap<Integer, ChoiceAccount>) o;
                Set<Map.Entry<Integer, ChoiceAccount>> maps = hashMap.entrySet();
                for (Map.Entry<Integer, ChoiceAccount> entry : maps) {
                    DaoChoiceAccount.deleteChoiceAccountByModel(entry.getValue());
                    mAdapter.removeItem(entry.getKey());
                }
                mList = DaoChoiceAccount.query();
                totalYue = 0;
                for (int i = 0; i < mList.size(); i++) {
                    totalYue = totalYue + mList.get(i).getMoney();
                }
                tvYuer.setText(String.format("%.2f", totalYue));

            }
        });
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        if (mAdapter.getItemCount() - 1 == position) {
            startActivity(new Intent(getActivity(), ChoiceAccountTypeActivity.class));
        } else {
            ChoiceAccount model = mList.get(position);
            Intent intent = new Intent(getActivity(), PayShowActivity.class);
            intent.putExtra(Config.ModelWallet, model);
            intent.putExtra(Config.Position, position);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().release();
    }


    public class WalletAdapter extends BGARecyclerViewAdapter<ChoiceAccount> {
        private List<ChoiceAccount> list;

        public WalletAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_fragment_wallet);
        }

        protected void updateWallet(List<ChoiceAccount> accounts) {
            if (null != accounts) {
                list = accounts;
                mAdapter.notifyDataSetChangedWrapper();
            }
        }

        @Override
        protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
            super.setItemChildListener(helper, viewType);
            helper.setRVItemChildTouchListener(R.id.qb_ry_xinyka);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, ChoiceAccount model) {

            helper.setBackgroundColorRes(R.id.qb_ry_xinyka, model.getColor());
            if (model.mAccountType.equals(Config.XYK)) {
                helper.setText(R.id.qb_txt_xinyka, model.getAccountName()).setImageResource(R.id.qb_image_xinyka, model.getUrl());
                helper.setText(R.id.qb_txt_xinyka_yuer, "剩余额度" + String.format("%.2f", model.getDebt()) + "元");
                helper.setText(R.id.xinyka_jia, String.format("%.2f", model.getMoney()));
            } else {
                helper.setText(R.id.qb_txt_xinyka, model.getAccountName()).setImageResource(R.id.qb_image_xinyka, model.getUrl());
                helper.setText(R.id.qb_txt_xinyka_yuer, model.getMAccountType() + "额度");
                helper.setText(R.id.xinyka_jia, String.format("%.2f", model.getMoney()));
            }
        }
    }

}
