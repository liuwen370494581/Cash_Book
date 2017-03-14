package star.liuwen.com.cash_books.Activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import star.liuwen.com.cash_books.Adapter.FragmentAdapter;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Fragment.BankFragment;
import star.liuwen.com.cash_books.Fragment.P2PFragment;
import star.liuwen.com.cash_books.R;

/**
 * Created by liuwen on 2017/2/15.
 */
public class SavePlatformActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private FragmentAdapter mFragmentAdapter;
    private P2PFragment mP2PFragment;
    private BankFragment mBankFragment;
    private ViewPager mViewPager;
    private TextView txtP2P, txtBank;


    @Override
    public int activityLayoutRes() {
        return R.layout.save_platform_activity;
    }

    @Override
    public void initView() {

        mViewPager = (ViewPager) findViewById(R.id.id_view_pager);
        txtP2P = (TextView) findViewById(R.id.id_p2p_tab);
        txtBank = (TextView) findViewById(R.id.id_bank_tab);


        mP2PFragment = new P2PFragment();
        mBankFragment = new BankFragment();
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        mFragmentAdapter.addFragment(mP2PFragment, "P2P");
        mFragmentAdapter.addFragment(mBankFragment, "银行");
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOnPageChangeListener(this);
        txtP2P.setOnClickListener(this);
        txtBank.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == txtP2P) {
            mViewPager.setCurrentItem(0);
        } else if (v == txtBank) {
            mViewPager.setCurrentItem(1);
        }
    }

    public void onClose(View view) {
        SavePlatformActivity.this.finish();
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
        txtP2P.setTextColor(index == 0 ? getResources().getColor(R.color.white) : getResources().getColor(R.color.text_color_66));
        txtP2P.setBackgroundResource(index == 0 ? R.drawable.btn_fullblue_left_shape : R.drawable.btn_lineblue_left_shape);
        txtBank.setBackgroundResource(index == 0 ? R.drawable.btn_lineblue_right_shape : R.drawable.btn_fullblue_right_shape);
        txtBank.setTextColor(index == 0 ? getResources().getColor(R.color.text_color_33) : getResources().getColor(R.color.white));
    }
}
