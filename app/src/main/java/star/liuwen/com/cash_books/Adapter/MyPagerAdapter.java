package star.liuwen.com.cash_books.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by liuwen on 2017/2/14.
 */
public class MyPagerAdapter extends PagerAdapter {
    private List<View> mViews;
    private Context mcontext;

    public MyPagerAdapter(List<View> views, Context context) {
        this.mViews = views;
        mcontext = context;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(mViews.get(position));
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }


    @Override
    public Object instantiateItem(View container, int position) {
        View view = mViews.get(position);
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view);
        return mViews.get(position);
    }
}
