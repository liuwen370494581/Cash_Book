package star.liuwen.com.cash_books.Activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import star.liuwen.com.cash_books.Adapter.FragmentAdapter;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Fragment.ShouRuFragment;
import star.liuwen.com.cash_books.Fragment.ZhiChuFragment;
import star.liuwen.com.cash_books.R;

/**
 * Created by liuwen on 2016/12/29.
 */
public class IncomeAndCostActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private FragmentAdapter mFragmentAdapter;
    private ShouRuFragment mShouRuFragment;
    private ZhiChuFragment mZhiChuFragment;
    private ViewPager mViewPager;
    private TextView txtZhiChu, txtShouRu;


    @Override
    public int activityLayoutRes() {
        return R.layout.icome_and_cost_activity;
    }

    @Override
    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_view_pager);
        txtZhiChu = (TextView) findViewById(R.id.id_zhichu_tab);
        txtShouRu = (TextView) findViewById(R.id.id_shouru_tab);


        mShouRuFragment = new ShouRuFragment();
        mZhiChuFragment = new ZhiChuFragment();
        mFragmentAdapter = new FragmentAdapter(getFragmentManager());
        mFragmentAdapter.add(mZhiChuFragment, "支出");
        mFragmentAdapter.add(mShouRuFragment, "收入");
        mViewPager.setAdapter(mFragmentAdapter);

        mViewPager.setOnPageChangeListener(this);
        txtShouRu.setOnClickListener(this);
        txtZhiChu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == txtZhiChu) {
            mViewPager.setCurrentItem(0);
        } else if (v == txtShouRu) {
            mViewPager.setCurrentItem(1);
        }
    }

    public void onClose(View view) {
        IncomeAndCostActivity.this.finish();
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
        txtZhiChu.setTextColor(index == 0 ? getResources().getColor(R.color.white) : getResources().getColor(R.color.text_color_66));
        txtZhiChu.setBackgroundResource(index == 0 ? R.drawable.btn_fullblue_left_shape : R.drawable.btn_lineblue_left_shape);
        txtShouRu.setBackgroundResource(index == 0 ? R.drawable.btn_lineblue_right_shape : R.drawable.btn_fullblue_right_shape);
        txtShouRu.setTextColor(index == 0 ? getResources().getColor(R.color.text_color_33) : getResources().getColor(R.color.white));
    }


}
