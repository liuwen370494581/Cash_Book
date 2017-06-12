package star.liuwen.com.cash_books.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import star.liuwen.com.cash_books.Base.BaseFragment;
import star.liuwen.com.cash_books.R;

/**
 * Created by liuwen on 2017/3/30.
 */
public class TrendFragment extends BaseFragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.trend_fragment);
        return getContentView();
    }


}
