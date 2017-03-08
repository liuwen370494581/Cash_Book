package star.liuwen.com.cash_books.Activity;

import android.support.v7.widget.RecyclerView;
import android.view.ViewStub;

import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.R;

/**
 * Created by liuwen on 2017/1/13.
 */
public class FixXiaofeiCheckActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private ViewStub mViewStub;

    @Override
    public int activityLayoutRes() {
        return R.layout.fix_xiao_check_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setTitle(getString(R.string.chong_dong_xiaofei));
        setLeftText(getString(R.string.back));
        setLeftImage(R.mipmap.fanhui_lan);
        mRecyclerView = (RecyclerView) findViewById(R.id.xiao_fei_recyclerView);
        mViewStub = (ViewStub) findViewById(R.id.view_stub);
        mViewStub.inflate();
    }
}
