package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.GraphicLock.GraphicLockView;
import star.liuwen.com.cash_books.MainActivity;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.RxBus.RxBusResult;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.View.CircleImageView;

/**
 * Created by liuwen on 2017/2/10.
 */
public class LoginLockActivity extends AppCompatActivity implements GraphicLockView.OnGraphicLockListener {
    private CircleImageView imgUser;
    private TextView txtForgetPassword;
    private GraphicLockView mLockView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_lock_activity);
        initView();
        initData();
    }


    private void initView() {

        imgUser = (CircleImageView) findViewById(R.id.user_info_url);
        txtForgetPassword = (TextView) findViewById(R.id.text_forget_password);
        mLockView = (GraphicLockView) findViewById(R.id.agl_gl_lock);

        Bitmap bt = BitmapFactory.decodeFile(Config.RootPath + "head.jpg");
        if (bt != null) {
            imgUser.setImageBitmap(bt);
        }
        initData();
        mLockView.setOnGraphicLockListener(this);
        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginLockActivity.this, LoginActivity.class));
            }
        });

    }

    private void initData() {
        RxBus.getInstance().toObserverableOnMainThread(Config.userUrl, new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                imgUser.setImageBitmap((Bitmap) o);
            }
        });
    }

    @Override
    public void setPwdSuccess(String password) {
        if (SharedPreferencesUtil.getStringPreferences(this, Config.LockPassword, "").equals(password)) {
            ToastUtils.showToast(this, getString(R.string.lock_login_success));
            startActivity(new Intent(LoginLockActivity.this, LoginActivity.class));
            finish();
        } else {
            ToastUtils.showToast(this, getString(R.string.lock_login_error));
        }
    }

    @Override
    public void setPwdFailure() {
        Toast.makeText(this, getString(R.string.lock_login_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().release();
    }
}
