package star.liuwen.com.cash_books.Base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import star.liuwen.com.cash_books.EventBus.Event;
import star.liuwen.com.cash_books.EventBus.EventBusUtil;
import star.liuwen.com.cash_books.MainActivity;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.ActivityKiller;
import star.liuwen.com.cash_books.Utils.ToastUtils;

/**
 * Created by liuwen on 2016/12/28.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private RelativeLayout mTitle;
    private TextView title, rightTxt;
    private App mAppInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(activityLayoutRes());
        mTitle = (RelativeLayout) findViewById(R.id.titleBar);
        title = (TextView) findViewById(R.id.title);
        rightTxt = (TextView) findViewById(R.id.toolbar_righ_tv);

        //加入EventBus
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
        initView();
        ActivityKiller.getInstance().addActivity(this);
    }

    protected void protectApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Config.KEY_HOME_ACTION, Config.ACTION_RESTART_APP);
        startActivity(intent);
    }


    public App getAppInfo() {
        if (null == mAppInfo)
            mAppInfo = (App) getApplication();
        return mAppInfo;
    }

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(Event event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    protected void receiveEvent(Event event) {

    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(Event event) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 移除Activity
        ActivityKiller.getInstance().removeActivity(this);
        ToastUtils.removeToast();
        RefWatcher refWatcher = App.getRefWatcher(this);
        refWatcher.watch(this);
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
    }

    /**
     * actiivty layout id ,需要子类去实现
     *
     * @return
     */
    public abstract int activityLayoutRes();

    /**
     * acitivty 初始化组件和数据
     */
    public abstract void initView();

    /**
     * 设置显示右侧返回按钮
     */
    public void setBackView() {
        View backView = findViewById(R.id.back_view);
        ImageView imageLeft = (ImageView) findViewById(R.id.toolbar_left_iv);

        if (backView == null) {
            return;
        }
        if (imageLeft != null) {
            imageLeft.setVisibility(View.VISIBLE);
        }
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void setLeftImage(int resId) {
        ImageView imageLeft = (ImageView) findViewById(R.id.toolbar_left_iv);
        if (imageLeft != null) {
            imageLeft.setImageResource(resId);
        }
    }


    /**
     * 设置显示标题
     *
     * @param txt
     */
    public void setTitle(String txt) {
        TextView title = (TextView) findViewById(R.id.title);
        if (title == null) {
            return;
        }
        title.setVisibility(View.VISIBLE);
        title.setText(txt);
    }

    public void setLeftText(String text) {
        TextView leftText = (TextView) findViewById(R.id.toolbar_left_tv);
        if (leftText != null) {
            leftText.setVisibility(View.VISIBLE);
            leftText.setText(text);
        }
    }

    public void setLeftTextColor(int resId) {
        TextView leftText = (TextView) findViewById(R.id.toolbar_left_tv);
        if (leftText != null) {
            leftText.setVisibility(View.VISIBLE);
            leftText.setTextColor(resId);
        }
    }

    public void setTitleBg(int resId) {
        if (mTitle != null) {
            mTitle.setBackgroundResource(resId);
            mTitle.getBackground().setAlpha(255);// 不透明
        }
    }

    public void setTitlesColor(int resId) {
        if (title != null) {
            title.setTextColor(resId);
        }
    }


    public void setRightTxtColor(int resId) {
        if (rightTxt != null) {
            rightTxt.setTextColor(resId);
        }
    }


    /**
     * 只显示右侧文字以及点击事件
     *
     * @param txt
     * @param onClickListener
     */
    public void setRightText(String txt, View.OnClickListener onClickListener) {
        TextView toolbar_righ_tv = (TextView) findViewById(R.id.toolbar_righ_tv);
        if (toolbar_righ_tv == null) {
            return;
        }
        ImageView toolbar_righ_iv = (ImageView) findViewById(R.id.toolbar_righ_iv);
        if (toolbar_righ_iv == null) {
            return;
        }
        toolbar_righ_iv.setVisibility(View.GONE);
        toolbar_righ_tv.setVisibility(View.VISIBLE);
        toolbar_righ_tv.setText(txt);
        toolbar_righ_tv.setOnClickListener(onClickListener);
    }

    public void setRightTxtVisible(boolean isShow) {
        TextView toolbar_righ_tv = (TextView) findViewById(R.id.toolbar_righ_tv);
        if (toolbar_righ_tv == null) {
            return;
        }
        if (isShow) {
            toolbar_righ_tv.setVisibility(View.VISIBLE);
        } else {
            toolbar_righ_tv.setVisibility(View.GONE);
        }
    }

    /**
     * 右侧只显示一个图片
     *
     * @param resId
     * @param onClickListener
     */
    public void setRightImage(int resId, View.OnClickListener onClickListener) {
        TextView toolbar_righ_tv = (TextView) findViewById(R.id.toolbar_righ_tv);
        if (toolbar_righ_tv == null) {
            return;
        }
        toolbar_righ_tv.setVisibility(View.GONE);
        ImageView toolbar_righ_iv = (ImageView) findViewById(R.id.toolbar_righ_iv);
        if (toolbar_righ_iv == null) {
            return;
        }
        toolbar_righ_iv.setVisibility(View.VISIBLE);
        toolbar_righ_iv.setImageResource(resId);
        toolbar_righ_iv.setOnClickListener(onClickListener);
    }


    /**
     * 左侧显示图片
     *
     * @param resId
     * @param onClickListener
     */
    public void setLeftImages(int resId, View.OnClickListener onClickListener) {
        TextView toolbar_left_tv = (TextView) findViewById(R.id.toolbar_left_tv3);
        if (toolbar_left_tv == null) {
            return;
        }
        toolbar_left_tv.setVisibility(View.GONE);
        ImageView toolbar_left_iv = (ImageView) findViewById(R.id.toolbar_left_iv2);
        if (toolbar_left_iv == null) {
            return;
        }
        toolbar_left_iv.setVisibility(View.VISIBLE);
        toolbar_left_iv.setImageResource(resId);
        toolbar_left_iv.setOnClickListener(onClickListener);
    }


    /**
     * 显示文字和图片，可以设置文字内容及字体颜色，图片资源
     *
     * @param txt
     * @param txtColor
     * @param resId
     * @param onClickListener
     */
    public void setRightTextAndImage(String txt, int txtColor, int resId, View.OnClickListener onClickListener) {
        TextView toolbar_righ_tv = (TextView) findViewById(R.id.toolbar_righ_tv);
        if (toolbar_righ_tv == null) {
            return;
        }
        toolbar_righ_tv.setVisibility(View.VISIBLE);
        toolbar_righ_tv.setTextColor(txtColor);

        ImageView toolbar_righ_iv = (ImageView) findViewById(R.id.toolbar_righ_iv);
        if (toolbar_righ_iv == null) {
            return;
        }
        toolbar_righ_iv.setVisibility(View.VISIBLE);
        toolbar_righ_iv.setImageResource(resId);
        toolbar_righ_iv.setOnClickListener(onClickListener);
        toolbar_righ_tv.setOnClickListener(onClickListener);
    }

}
