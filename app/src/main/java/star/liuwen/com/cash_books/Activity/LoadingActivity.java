package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import star.liuwen.com.cash_books.Adapter.MyPagerAdapter;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.MainActivity;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;

/**
 * Created by liuwen on 2017/2/14.
 */
public class LoadingActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<View> mViews;
    private boolean mIsFirst;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        mIsFirst = SharedPreferencesUtil.getBooleanPreferences(this, Config.FistStar, true);
        if (mIsFirst) {
            setContentView(R.layout.loading_activity);
        } else {
            setContentView(R.layout.lauach_activity);

        }
        initView();
    }

    private void initView() {
        if (mIsFirst) {
            mViewPager = (ViewPager) findViewById(R.id.loading_view_pager);
            mViews = new ArrayList<>();
            ImageView imageView1 = new ImageView(this);
            imageView1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView1.setBackgroundResource(R.mipmap.one);
            mViews.add(imageView1);
            ImageView imageView2 = new ImageView(this);
            imageView2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView2.setBackgroundResource(R.mipmap.two);
            mViews.add(imageView2);
            ImageView imageView3 = new ImageView(this);
            imageView3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView3.setBackgroundResource(R.mipmap.three);
            mViews.add(imageView3);

            View view = LayoutInflater.from(this).inflate(R.layout.layout_loading, null);
            TextView textView = (TextView) view.findViewById(R.id.layout_loading_txt);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferencesUtil.setStringPreferences(LoadingActivity.this, Config.UserName, "");
                    SharedPreferencesUtil.setStringPreferences(LoadingActivity.this, Config.UserPassWord, "");
                    SharedPreferencesUtil.setBooleanPreferences(LoadingActivity.this, Config.FistStar, false);
                    startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                    LoadingActivity.this.finish();
                }
            });
            mViews.add(view);
            MyPagerAdapter pagerAdapter = new MyPagerAdapter(mViews, LoadingActivity.this);
            mViewPager.setAdapter(pagerAdapter);

        } else {
            handler.postDelayed(runnable, 1000);
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.sendEmptyMessage(0);
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            boolean isFirstStar = SharedPreferencesUtil.getBooleanPreferences(LoadingActivity.this, Config.FistStar, false);
            String paw = SharedPreferencesUtil.getStringPreferences(LoadingActivity.this, Config.UserPassWord, "");
            Intent intent = null;
            if (isFirstStar && !TextUtils.isEmpty(paw)) {
                //判断是否开启了锁
                if (SharedPreferencesUtil.getBooleanPreferences(LoadingActivity.this, Config.isOpenCodedLock, false)) {
                    intent = new Intent(LoadingActivity.this, LoginLockActivity.class);
                } else {
                    intent = new Intent(LoadingActivity.this, MainActivity.class);
                }
            } else {
                //判断是否开启了锁
                if (SharedPreferencesUtil.getBooleanPreferences(LoadingActivity.this, Config.isOpenCodedLock, false)) {
                    intent = new Intent(LoadingActivity.this, LoginLockActivity.class);
                } else {
                    intent = new Intent(LoadingActivity.this, LoginActivity.class);
                }
            }
            startActivity(intent);
            LoadingActivity.this.finish();
        }
    };
}
