package star.liuwen.com.cash_books.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import star.liuwen.com.cash_books.Activity.ChoiceAccountTypeActivity;
import star.liuwen.com.cash_books.Activity.PayShowActivity;
import star.liuwen.com.cash_books.Activity.TransferActivity;
import star.liuwen.com.cash_books.Base.BaseFragment;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoChoiceAccount;
import star.liuwen.com.cash_books.EventBus.C;
import star.liuwen.com.cash_books.EventBus.Event;
import star.liuwen.com.cash_books.EventBus.EventBusUtil;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.BitMapUtils;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.Utils.RxUtil;
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
    private DrawerLayout mDrawerLayout;
    private List<ChoiceAccount> mList;
    private double totalYue, yuer, totalPopMoney;

    private List<ChoiceAccount> dialogList = new ArrayList<>();//弹出选择余额的对话框

    private CheckBox checkBox;
    private PopupWindow mPopupWindow;
    private ChoiceAccountPopAdapter mPopAdapter;
    private ListView mListView;


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
        return getContentView();
    }

    //之所以重写这个onViewCreated的方法 是因为首先给用户第一印象的是布局所以为了点击不影响卡段 一些
    //初始化的操作就写在onviewCreated中了
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
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
            dialogList = DaoChoiceAccount.query();
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
        mAdapter.addLastItem(new ChoiceAccount(DaoChoiceAccount.getCount(), R.mipmap.icon_add, "添加账户", 0.00, 0.00, "", "", R.color.transparent, "添加", 0.00, 0.00, DateTimeUtil.getCurrentYear(), ""));
        mAdapter.setOnRVItemClickListener(this);
        if (SharedPreferencesUtil.getStringPreferences(getActivity(), Config.ChangeBg, null) != null) {
            Bitmap bitmap = BitMapUtils.getBitmapByPath(getActivity(), SharedPreferencesUtil.getStringPreferences(getActivity(), Config.ChangeBg, null), false);
            mDrawerLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
        }

        mRyYuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoiceAccountPop();
                if (mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                } else {
                    int[] location = new int[2];
                    mRyYuer.getLocationOnScreen(location);
                    mPopupWindow.showAsDropDown(mRyYuer);
                    //mPopupWindow.showAtLocation(mRyYuer, Gravity.BOTTOM, 0, 0);
                    backgroundAlpha(0.5f);
                }
            }
        });
    }

    private void showChoiceAccountPop() {
        View popView = View.inflate(getActivity(), R.layout.pop_choiceaccount_dialog, null);
        mListView = (ListView) popView.findViewById(R.id.lv_popup_list);
        mPopupWindow = new PopupWindow(popView, 380, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopAdapter = new ChoiceAccountPopAdapter(getActivity(), R.layout.item_dialog_showyuer);
        mPopAdapter.setData(dialogList);
        mListView.setAdapter(mPopAdapter);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.setAnimationStyle(R.style.AnimBottom);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        //先初始化选择的
        mPopAdapter.initSelected(dialogList.size());
        //有被选中了 在调用次方法
        // mPopAdapter.setSelected();

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        switch (event.getCode()) {
            case C.EventCode.PayShowActivityToWalletFragment:
                //接收显示页面传递过来的值
                commonUpdateWalletData();
                break;
            case C.EventCode.newAddAccountActivityToWalletFragment:
                //接收新增账户传递过来的值
                Observable.create(new Observable.OnSubscribe<List<ChoiceAccount>>() {
                    @Override
                    public void call(Subscriber<? super List<ChoiceAccount>> subscriber) {
                        mList = DaoChoiceAccount.query();
                        dialogList = DaoChoiceAccount.query();
                        subscriber.onNext(mList);
                    }
                }).compose(RxUtil.<List<ChoiceAccount>>applySchedulers()).subscribe(new Action1<List<ChoiceAccount>>() {
                    @Override
                    public void call(List<ChoiceAccount> accounts) {
                        mAdapter.setData(accounts);
                        mAdapter.addLastItem(new ChoiceAccount(DaoChoiceAccount.getCount(), R.mipmap.icon_add, "添加账户", 0.00, 0.00, "", "", R.color.transparent, "添加", 0.00, 0.00, DateTimeUtil.getCurrentYear(), ""));
                        for (int i = 0; i < accounts.size(); i++) {
                            yuer = yuer + accounts.get(i).getMoney();
                        }
                        tvYuer.setText(String.format("%.2f", yuer));
                        //因为余额的数值会添加要设为0重新开始算
                        yuer = 0;
                    }
                });
                break;
            case C.EventCode.ZhiChuToHomeFragment:
                //接收主页面传递过来的值
                commonUpdateWalletData();
                break;
            case C.EventCode.PaySettingToWalletFragment:
                //接收设置页面传递过来的值
                commonUpdateWalletData();
                break;
            case C.EventCode.HomeFragmentToReports:
                //接收出页面删除传递过来的值
                commonUpdateWalletData();
                break;
            case C.EventCode.PaySettingToPayShowActivityAndWalletFragment:
                //接收paySetting页面传递过来的值
                commonUpdateWalletData();
                break;
            case C.EventCode.ChoiceColorToPaySettingAndPayShowAndWalletFragment:
                //接收颜色设置页面传递过来的值
                commonUpdateWalletData();
                break;
            case C.EventCode.UserPhoto:
                //接收改变背景页面传递过来的值
                Bitmap bitmap = BitMapUtils.getBitmapByPath(getActivity(), event.getData().toString(), false);
                mDrawerLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
                break;
            case C.EventCode.WalletFragment:
                yuer = 0;
                totalPopMoney = 0;
                HashMap<Long, Boolean> hashMap = (HashMap<Long, Boolean>) event.getData();
                for (int i = 0; i < dialogList.size(); i++) {
                    totalPopMoney += dialogList.get(i).getMoney();
                    if (!hashMap.get(dialogList.get(i).getId())) {
                        yuer += dialogList.get(i).getMoney();
                    }
                }
                yuer = totalPopMoney - yuer;
                tvYuer.setText(String.format("%.2f", yuer));
                break;
        }
    }

    private void commonUpdateWalletData() {
        mList.clear();
        mAdapter.clear();
        dialogList.clear();
        mPopAdapter.clear();
        Observable.create(new Observable.OnSubscribe<List<ChoiceAccount>>() {
            @Override
            public void call(Subscriber<? super List<ChoiceAccount>> subscriber) {
                mList = DaoChoiceAccount.query();
                dialogList = DaoChoiceAccount.query();
                subscriber.onNext(mList);
            }
        }).compose(RxUtil.<List<ChoiceAccount>>applySchedulers()).subscribe(new Action1<List<ChoiceAccount>>() {
            @Override
            public void call(List<ChoiceAccount> accounts) {
                mAdapter.setData(accounts);
                for (int i = 0; i < accounts.size(); i++) {
                    yuer += accounts.get(i).getMoney();
                }
                tvYuer.setText(String.format("%.2f", yuer));
                //因为余额的数值会添加要设为0重新开始算
                yuer = 0;
                mAdapter.addLastItem(new ChoiceAccount(DaoChoiceAccount.getCount(), R.mipmap.icon_add, "添加账户", 0.00, 0.00, "", "", R.color.transparent, "添加", 0.00, 0.00, DateTimeUtil.getCurrentYear(), ""));
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


    public class WalletAdapter extends BGARecyclerViewAdapter<ChoiceAccount> {

        public WalletAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_fragment_wallet);
        }

        @Override
        protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
            super.setItemChildListener(helper, viewType);
            helper.setRVItemChildTouchListener(R.id.qb_ry_xinyka);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, ChoiceAccount model) {

            helper.setBackgroundColorRes(R.id.qb_ry_xinyka, model.getColor());
            if (model.getIssuingBank() == null || model.getIssuingBank().equals("")) {
                helper.setText(R.id.qb_txt_xinyka, model.getAccountName()).setImageResource(R.id.qb_image_xinyka, model.getUrl());
                helper.setText(R.id.qb_txt_xinyka_yuer, model.getAccountName() + "额度");
            } else {
                helper.setText(R.id.qb_txt_xinyka_yuer, model.getIssuingBank() + "额度");
                helper.setText(R.id.qb_txt_xinyka, model.getIssuingBank()).setImageResource(R.id.qb_image_xinyka, model.getUrl());
            }
            if (model.mAccountType.equals(Config.XYK)) {
                helper.setText(R.id.qb_txt_xinyka_yuer, "剩余额度" + String.format("%.2f", model.getMoney() - model.getDebt()) + "元");
                if (model.getMoney() >= model.getDebt()) {
                    helper.setText(R.id.xinyka_jia, String.format("%.2f", model.getMoney()));
                } else {
                    helper.setText(R.id.xinyka_jia, String.format("-%.2f", model.getMoney()));
                }
            } else {
                helper.setText(R.id.xinyka_jia, String.format("%.2f", model.getMoney()));
            }
        }
    }

    private class ChoiceAccountPopAdapter extends BGAAdapterViewAdapter<ChoiceAccount> {
        private HashMap<Long, Boolean> isSelected; //记录checkbox是否被选中


        public ChoiceAccountPopAdapter(Context context, int itemLayoutId) {
            super(context, itemLayoutId);
        }

        public HashMap<Long, Boolean> getIsSelected() {
            return isSelected;
        }

        public void setIsSelected(HashMap<Long, Boolean> isSelected) {
            this.isSelected = isSelected;
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, final int position, final ChoiceAccount model) {
            if (model.getIssuingBank() == null || model.getIssuingBank().equals("")) {
                helper.setText(R.id.item_tv_name, model.getAccountName());
            } else {
                helper.setText(R.id.item_tv_name, model.getIssuingBank());
            }
            checkBox = helper.getView(R.id.cb_choice);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSelected.get(model.getId())) {
                        isSelected.put(model.getId(), false);
                        setIsSelected(isSelected);
                        // SharedPreferencesUtil.setObj(getActivity(), Config.TxtChoiceAccountPop, isSelected);
                        EventBusUtil.sendEvent(new Event(C.EventCode.WalletFragment, isSelected));
                    } else {
                        isSelected.put(model.getId(), true);
                        setIsSelected(isSelected);
                        // SharedPreferencesUtil.setObj(getActivity(), Config.TxtChoiceAccountPop, isSelected);
                        EventBusUtil.sendEvent(new Event(C.EventCode.WalletFragment, isSelected));
                    }
                }
            });
            checkBox.setChecked(getIsSelected().get(model.getId()));
        }

        public void initSelected(int size) {
            if (isSelected == null) {
                isSelected = new HashMap<>();
                for (int i = 0; i < size; i++) {
                    isSelected.put(dialogList.get(i).getId(), true);//设置全部为默认选中状态 设置成false 则默认是没有选中的状态
                }
            }
        }

//        public void setSelected() {
//            if (SharedPreferencesUtil.getObj(getActivity(), Config.TxtChoiceAccountPop) != null) {
//                isSelected = (HashMap<Long, Boolean>) SharedPreferencesUtil.getObj(getActivity(), Config.TxtChoiceAccountPop);
//                notifyDataSetChanged();
//
//            }
//        }
    }
}
