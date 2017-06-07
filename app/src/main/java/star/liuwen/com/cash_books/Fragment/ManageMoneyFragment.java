package star.liuwen.com.cash_books.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import star.liuwen.com.cash_books.Adapter.FragmentAdapter;
import star.liuwen.com.cash_books.Base.BaseFragment;
import star.liuwen.com.cash_books.R;

/**
 * Created by liuwen on 2017/5/9.
 * 理财管理页面
 */
public class ManageMoneyFragment
        extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private AccountingWealthFragment mChargeWealthFragment;
    private MyWealthFragment mMyWealthFragment;
    private ViewPager mViewPager;
    private TextView txtChargeWealth, txtMyWealth;
    private FragmentAdapter mFragmentAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_manage_money);
        initView();
        initData();
        return getContentView();
    }

    private void initView() {

        mViewPager = (ViewPager) getContentView().findViewById(R.id.id_view_pager);
        txtChargeWealth = (TextView) getContentView().findViewById(R.id.id_chargeWealth_tab);
        txtMyWealth = (TextView) getContentView().findViewById(R.id.id_myWealth_tab);
        mChargeWealthFragment = new AccountingWealthFragment();
        mMyWealthFragment = new MyWealthFragment();
        mFragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager());
        mFragmentAdapter.addFragment(mChargeWealthFragment, "记账财富");
        mFragmentAdapter.addFragment(mMyWealthFragment, "我的财富");
        mViewPager.setAdapter(mFragmentAdapter);

        mViewPager.setOnPageChangeListener(this);
        txtChargeWealth.setOnClickListener(this);
        txtMyWealth.setOnClickListener(this);
    }


    private void initData() {

    }

    @Override
    public void onClick(View v) {
        if (v == txtChargeWealth) {
            mViewPager.setCurrentItem(0);
        } else if (v == txtMyWealth) {
            mViewPager.setCurrentItem(1);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switchTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void switchTab(int index) {
        txtChargeWealth.setTextColor(index == 0 ? getResources().getColor(R.color.white) : getResources().getColor(R.color.text_color_66));
        txtChargeWealth.setBackgroundResource(index == 0 ? R.drawable.btn_fullblue_left_shape : R.drawable.btn_lineblue_left_shape);
        txtMyWealth.setBackgroundResource(index == 0 ? R.drawable.btn_lineblue_right_shape : R.drawable.btn_fullblue_right_shape);
        txtMyWealth.setTextColor(index == 0 ? getResources().getColor(R.color.text_color_33) : getResources().getColor(R.color.white));
    }
}
