package star.liuwen.com.cash_books.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import star.liuwen.com.cash_books.Activity.ChoiceAccountTypeActivity;
import star.liuwen.com.cash_books.Activity.PayShowActivity;
import star.liuwen.com.cash_books.Base.App;
import star.liuwen.com.cash_books.Base.BaseFragment;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoAccount;
import star.liuwen.com.cash_books.Dao.DaoChoiceAccount;
import star.liuwen.com.cash_books.Dao.DaoZhiChuModel;
import star.liuwen.com.cash_books.Enage.DataEnige;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.RxBus.RxBusResult;
import star.liuwen.com.cash_books.Utils.BitMapUtils;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.Utils.SnackBarUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.View.CustomPopWindow;
import star.liuwen.com.cash_books.bean.AccountModel;
import star.liuwen.com.cash_books.bean.ChoiceAccount;
import star.liuwen.com.cash_books.bean.ZhiChuModel;

/**
 * Created by liuwen on 2017/2/16.
 */
public class WalletFragment extends BaseFragment implements BGAOnRVItemClickListener, View.OnClickListener {
    private RecyclerView mRecyclerView;
    private WalletAdapter mAdapter;

    private RelativeLayout mRyYuer;
    private TextView tvYuer;
    private CustomPopWindow mCustomPopWindow;
    private ImageView imageCash, imageCxk, imageXYk, imageZfb, imageJC, imageJR;
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
                ToastUtils.showToast(getActivity(), "点击了转账");
            }
        });
        initView();
        initData();
        return getContentView();
    }

    private void initView() {
        mRyYuer = (RelativeLayout) getContentView().findViewById(R.id.qb_ry_yuer);
        tvYuer = (TextView) getContentView().findViewById(R.id.yuer_jia);

        mRyYuer.setOnClickListener(this);
        mDrawerLayout = (DrawerLayout) getContentView().findViewById(R.id.drawer_layout);

        mRecyclerView = (RecyclerView) getContentView().findViewById(R.id.wallet_fragment_recyclerView);
        mAdapter = new WalletAdapter(mRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        mRecyclerView.setLayoutManager(mLayoutManager);
        if (DaoChoiceAccount.query() != null) {
            mList = DaoChoiceAccount.query();
            mAdapter.setData(mList);
            mRecyclerView.setAdapter(mAdapter);
            for (int i = 0; i < mList.size(); i++) {
                totalYue = totalYue + mList.get(i).getMoney();
            }
            tvYuer.setText(String.format("%.2f", totalYue));
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

        RxBus.getInstance().toObserverableOnMainThread(Config.CASH, new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                if ((boolean) o) {
                    mList = DaoChoiceAccount.query();
                    mAdapter.setData(mList);
                    mRecyclerView.setAdapter(mAdapter);
                    for (int i = 0; i < mList.size(); i++) {
                        yuer = yuer + mList.get(i).getMoney();
                    }
                    tvYuer.setText(String.format("%.2f", yuer));
                    //因为余额的数值会添加要设为0重新开始算
                    yuer = 0;
                }
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
            startActivity(intent);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().release();
    }

    @Override
    public void onClick(View v) {
        if (v == mRyYuer) {
            showPopWindow();
        }
    }

    private void showPopWindow() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_menu, null);
        //处理popWindow 显示内容
        handleLogic(contentView);

        imageCash = (ImageView) contentView.findViewById(R.id.image_1);
        imageCxk = (ImageView) contentView.findViewById(R.id.image_2);
        imageXYk = (ImageView) contentView.findViewById(R.id.image_3);
        imageZfb = (ImageView) contentView.findViewById(R.id.image_4);
        imageJC = (ImageView) contentView.findViewById(R.id.image_5);
        imageJR = (ImageView) contentView.findViewById(R.id.image_6);

        imageCash.setImageResource(App.isBgCash ? R.mipmap.btg_icon_tick_pressed : R.mipmap.btg_icon_priority_1_normal);
        imageCxk.setImageResource(App.isBgCXK ? R.mipmap.btg_icon_tick_pressed : R.mipmap.btg_icon_priority_1_normal);
        imageXYk.setImageResource(App.isBgXYK ? R.mipmap.btg_icon_tick_pressed : R.mipmap.btg_icon_priority_1_normal);
        imageZfb.setImageResource(App.isBgZFB ? R.mipmap.btg_icon_tick_pressed : R.mipmap.btg_icon_priority_1_normal);
        imageJC.setImageResource(App.isBgJC ? R.mipmap.btg_icon_tick_pressed : R.mipmap.btg_icon_priority_1_normal);
        imageJR.setImageResource(App.isBgJR ? R.mipmap.btg_icon_tick_pressed : R.mipmap.btg_icon_priority_1_normal);

        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)
                .create()
                .showAsDropDown(mRyYuer, 0, 20);
    }

    /**
     * 处理弹出显示内容、点击事件等逻辑
     *
     * @param contentView
     */
    private void handleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.menu1:
                        position = 1;
                        setPopWindowBG(position);
                        break;
                    case R.id.menu2:
                        position = 2;
                        setPopWindowBG(position);
                        break;
                    case R.id.menu3:
                        setPopWindowBG(position);
                        position = 3;
                        break;
                    case R.id.menu4:
                        position = 4;
                        setPopWindowBG(position);
                        break;
                    case R.id.menu5:
                        position = 5;
                        setPopWindowBG(position);
                        break;
                    case R.id.menu6:
                        position = 6;
                        setPopWindowBG(position);
                }
            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
        contentView.findViewById(R.id.menu3).setOnClickListener(listener);
        contentView.findViewById(R.id.menu4).setOnClickListener(listener);
        contentView.findViewById(R.id.menu5).setOnClickListener(listener);
        contentView.findViewById(R.id.menu6).setOnClickListener(listener);
    }

    public void setPopWindowBG(int position) {
        switch (position) {
            case 1:
                imageCash.setImageResource(!App.isBgCash ? R.mipmap.btg_icon_tick_pressed : R.mipmap.btg_icon_priority_1_normal);
                App.isBgCash = !App.isBgCash;
                SharedPreferencesUtil.setBooleanPreferences(getActivity(), Config.isBgCash, App.isBgCash);
                break;
            case 2:
                imageCxk.setImageResource(!App.isBgCXK ? R.mipmap.btg_icon_tick_pressed : R.mipmap.btg_icon_priority_1_normal);
                App.isBgCXK = !App.isBgCXK;
                SharedPreferencesUtil.setBooleanPreferences(getActivity(), Config.isBgCXK, App.isBgCXK);
                break;
            case 3:
                imageXYk.setImageResource(!App.isBgXYK ? R.mipmap.btg_icon_tick_pressed : R.mipmap.btg_icon_priority_1_normal);
                App.isBgXYK = !App.isBgXYK;
                SharedPreferencesUtil.setBooleanPreferences(getActivity(), Config.isBgXYK, App.isBgXYK);
                break;
            case 4:
                imageZfb.setImageResource(!App.isBgZFB ? R.mipmap.btg_icon_tick_pressed : R.mipmap.btg_icon_priority_1_normal);
                App.isBgZFB = !App.isBgZFB;
                SharedPreferencesUtil.setBooleanPreferences(getActivity(), Config.isBgZFB, App.isBgZFB);
                break;
            case 5:
                imageJC.setImageResource(!App.isBgJC ? R.mipmap.btg_icon_tick_pressed : R.mipmap.btg_icon_priority_1_normal);
                App.isBgJC = !App.isBgJC;
                SharedPreferencesUtil.setBooleanPreferences(getActivity(), Config.isBgJC, App.isBgJC);
                break;
            case 6:
                imageJR.setImageResource(!App.isBgJR ? R.mipmap.btg_icon_tick_pressed : R.mipmap.btg_icon_priority_1_normal);
                App.isBgJR = !App.isBgJR;
                SharedPreferencesUtil.setBooleanPreferences(getActivity(), Config.isBgJR, App.isBgJR);
                break;
        }

    }


    public class WalletAdapter extends BGARecyclerViewAdapter<ChoiceAccount> {

        public WalletAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_fragment_wallet);
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
