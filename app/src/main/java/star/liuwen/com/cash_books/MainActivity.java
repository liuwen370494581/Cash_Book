package star.liuwen.com.cash_books;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.startsmake.mainnavigatetabbar.widget.MainNavigateTabBar;

import star.liuwen.com.cash_books.Activity.IncomeAndCostActivity;
import star.liuwen.com.cash_books.Fragment.HomeFragment;
import star.liuwen.com.cash_books.Fragment.MyFragment;
import star.liuwen.com.cash_books.Fragment.ReportsFragment;
import star.liuwen.com.cash_books.Fragment.WalletFragment;

public class MainActivity extends AppCompatActivity {


    private static final String TAG_PAGE_HOME = "明细";
    private static final String TAG_PAGE_CITY = "钱包";
    private static final String TAG_PAGE_PUBLISH = "开始记账";
    private static final String TAG_PAGE_MESSAGE = "报表";
    private static final String TAG_PAGE_PERSON = "我的";
    private MainNavigateTabBar mNavigateTabBar;
    private Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigateTabBar = (MainNavigateTabBar) findViewById(R.id.mainTabBar);
        mNavigateTabBar.onRestoreInstanceState(savedInstanceState);
        mNavigateTabBar.addTab(HomeFragment.class, new MainNavigateTabBar.TabParam(R.mipmap.shiguangzhou, R.mipmap.shiguangzhou_lan, TAG_PAGE_HOME));
        mNavigateTabBar.addTab(WalletFragment.class, new MainNavigateTabBar.TabParam(R.mipmap.qianbao, R.mipmap.qianbao_lan, TAG_PAGE_CITY));
        mNavigateTabBar.addTab(null, new MainNavigateTabBar.TabParam(0, 0, TAG_PAGE_PUBLISH));
        mNavigateTabBar.addTab(ReportsFragment.class, new MainNavigateTabBar.TabParam(R.mipmap.baobiao, R.mipmap.baobiao_lan, TAG_PAGE_MESSAGE));
        mNavigateTabBar.addTab(MyFragment.class, new MainNavigateTabBar.TabParam(R.mipmap.tixing, R.mipmap.tixing_lan, TAG_PAGE_PERSON));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavigateTabBar.onSaveInstanceState(outState);
    }



    public void onClickPublish(View v) {
        startActivity(new Intent(MainActivity.this, IncomeAndCostActivity.class));
        overridePendingTransition(R.anim.anim_open, R.anim.anim_close);
    }


}
