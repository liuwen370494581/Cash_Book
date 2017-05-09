package star.liuwen.com.cash_books.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import star.liuwen.com.cash_books.Base.BaseFragment;
import star.liuwen.com.cash_books.R;

/**
 * Created by liuwen on 2017/5/9.
 * 社区交流页面
 */
public class CommunityFragment extends BaseFragment {
    @Override
    public void lazyInitData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_community);
        initView();
        initData();
        return getContentView();
    }

    private void initView() {
    }


    private void initData() {

    }
}
