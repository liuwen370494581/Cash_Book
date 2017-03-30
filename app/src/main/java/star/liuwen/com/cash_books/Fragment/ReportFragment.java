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

import android.support.v4.app.FragmentManager;

import star.liuwen.com.cash_books.R;

/**
 * Created by liuwen on 2017/3/30.
 */
public class ReportFragment extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private TextView mTvTabOne, mTvTabTwo, mTvTabThree;
    private ViewPager mViewPager;
    private FragmentAdapter mFragmentAdapter;
    private ShouRuReportsFragment mShouRuReportsFragment;
    private ZhiChuReportsFragment mZhiChuReportsFragment;
    private TrendFragment mTrendFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.report_fragment);
        initView();
        initData();
        return getContentView();
    }

    private void initView() {
        mViewPager = (ViewPager) getContentView().findViewById(R.id.reposts_viewpager);
        mTvTabOne = (TextView) getContentView().findViewById(R.id.reports_head_zhichu);
        mTvTabTwo = (TextView) getContentView().findViewById(R.id.reports_head_shouru);
        mTvTabThree = (TextView) getContentView().findViewById(R.id.reports_head_qushi);

        mShouRuReportsFragment = new ShouRuReportsFragment();
        mZhiChuReportsFragment = new ZhiChuReportsFragment();
        mTrendFragment = new TrendFragment();
        mFragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager());
        mFragmentAdapter.addFragment(mZhiChuReportsFragment, "我的消费");
        mFragmentAdapter.addFragment(mShouRuReportsFragment, "我的存款");
        mFragmentAdapter.addFragment(mTrendFragment, "我的趋势");


        mViewPager.setAdapter(mFragmentAdapter);

        mViewPager.setOnPageChangeListener(this);
        mTvTabOne.setOnClickListener(this);
        mTvTabTwo.setOnClickListener(this);
        mTvTabThree.setOnClickListener(this);


    }

    private void initData() {

    }


    @Override
    public void onClick(View v) {
        if (v == mTvTabOne) {
            mViewPager.setCurrentItem(0);
        } else if (v == mTvTabTwo) {
            mViewPager.setCurrentItem(1);
        } else if (v == mTvTabThree) {
            mViewPager.setCurrentItem(2);
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
        switch (index) {
            case 0:
                mTvTabOne.setTextColor(getResources().getColor(R.color.white));
                mTvTabOne.setBackgroundResource(R.drawable.btn_fullblue_left_shape);
                mTvTabTwo.setTextColor(getResources().getColor(R.color.text_color_66));
                mTvTabThree.setTextColor(getResources().getColor(R.color.text_color_66));
                mTvTabTwo.setBackgroundResource(R.drawable.btn_lineblue_center_shape);
                mTvTabThree.setBackgroundResource(R.drawable.btn_lineblue_right_shape);
                break;
            case 1:
                mTvTabTwo.setTextColor(getResources().getColor(R.color.white));
                mTvTabTwo.setBackgroundResource(R.drawable.btn_fullblue_center_shape);
                mTvTabOne.setTextColor(getResources().getColor(R.color.text_color_66));
                mTvTabThree.setTextColor(getResources().getColor(R.color.text_color_66));
                mTvTabOne.setBackgroundResource(R.drawable.btn_lineblue_left_shape);
                mTvTabThree.setBackgroundResource(R.drawable.btn_lineblue_right_shape);
                break;
            case 2:
                mTvTabThree.setTextColor(getResources().getColor(R.color.white));
                mTvTabThree.setBackgroundResource(R.drawable.btn_fullblue_right_shape);
                mTvTabOne.setTextColor(getResources().getColor(R.color.text_color_66));
                mTvTabTwo.setTextColor(getResources().getColor(R.color.text_color_66));
                mTvTabOne.setBackgroundResource(R.drawable.btn_lineblue_left_shape);
                mTvTabTwo.setBackgroundResource(R.drawable.btn_lineblue_center_shape);
                break;
        }
    }
}
