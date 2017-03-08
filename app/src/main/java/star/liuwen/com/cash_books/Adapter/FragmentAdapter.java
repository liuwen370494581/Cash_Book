package star.liuwen.com.cash_books.Adapter;


import android.app.Fragment;
import android.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import star.liuwen.com.cash_books.Fragment.ShouRuFragment;

/**
 * Created by liuwen on 2017/1/5.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragment = new ArrayList<>();
    private List<String> mTitle = new ArrayList<>();


    public void add(Fragment fragment, String title) {
        mFragment.add(fragment);
        mTitle.add(title);
    }

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}
