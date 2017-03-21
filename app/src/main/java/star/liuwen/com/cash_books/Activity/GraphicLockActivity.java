package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.widget.TextView;

import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.GraphicLock.GraphicLockView;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;

/**
 * Created by liuwen on 2017/2/10.
 */
public class GraphicLockActivity extends BaseActivity implements GraphicLockView.OnGraphicLockListener {
    private TextView txtGraphicInfo;
    private GraphicLockView mLockView;
    private boolean isFirstLogin;
    private String mPassWord;
    private int type = 0;//判断是登陆还是需要密码


    @Override
    public int activityLayoutRes() {
        return R.layout.graphic_lock_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setTitle(getString(R.string.lock_title));
        setLeftImage(R.mipmap.fanhui_lan);
        setLeftText(getString(R.string.back));

        mLockView = (GraphicLockView) findViewById(R.id.agl_gl_lock);
        txtGraphicInfo = (TextView) findViewById(R.id.agl_tv_info);
        isFirstLogin = true;
        txtGraphicInfo.setText(getString(R.string.lock_Unlock_pattern));
        mLockView.setOnGraphicLockListener(this);
        initData();

    }

    private void initData() {

    }

    @Override
    public void setPwdSuccess(String password) {
        if (type == 0) {
            if (isFirstLogin) {
                txtGraphicInfo.setText(getString(R.string.lock_again_unlock_pattern));
                mPassWord = password;
                isFirstLogin = false;
            } else {
                if (mPassWord.equals(password)) {
                    ToastUtils.showToast(this, getString(R.string.lock_passWord_success));
                    txtGraphicInfo.setText(getString(R.string.lock_login));
                    Intent intent = new Intent();
                    intent.putExtra(Config.isOpenCodedLock, true);
                    SharedPreferencesUtil.setStringPreferences(GraphicLockActivity.this, Config.LockPassword, mPassWord);
                    setResult(0, intent);
                    type = 1;
                    finish();
                } else {
                    ToastUtils.showToast(this, getString(R.string.lock_password_error));
                }
            }
        } else if (type == 1) {
            if (mPassWord.equals(password)) {
                ToastUtils.showToast(this, getString(R.string.lock_login_success));
                startActivity(new Intent(GraphicLockActivity.this, LoginActivity.class));
                finish();
            } else {
                ToastUtils.showToast(this, getString(R.string.lock_login_error));
            }
        }
    }

    @Override
    public void setPwdFailure() {
        if (type == 0) {
            txtGraphicInfo.setText(getString(R.string.lock_password_short));
            ToastUtils.showToast(this, getString(R.string.lock_password_short));
        } else {
            txtGraphicInfo.setText(getString(R.string.lock_can_login));
            ToastUtils.showToast(this, getString(R.string.lock_password_error));
        }
    }
}
