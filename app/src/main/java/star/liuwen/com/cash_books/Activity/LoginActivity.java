package star.liuwen.com.cash_books.Activity;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import star.liuwen.com.cash_books.Base.App;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoUserInfo;
import star.liuwen.com.cash_books.EventBus.C;
import star.liuwen.com.cash_books.EventBus.Event;
import star.liuwen.com.cash_books.EventBus.EventBusUtil;
import star.liuwen.com.cash_books.MainActivity;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.ApkInfoUtils;
import star.liuwen.com.cash_books.Utils.CheckGetUtil;
import star.liuwen.com.cash_books.Utils.RxUtil;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.View.CircleImageView;
import star.liuwen.com.cash_books.View.SmsCodeView;
import star.liuwen.com.cash_books.bean.UserInfoModel;

/**
 * Created by liuwen on 2017/2/9.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText txtUsername, txtPassword, txtPasswordRepeat;
    private ImageView imageUsername, imagePassword, imagePasswordRepeat;
    private CheckBox showpassword;
    private Button btnRegister, btnLogin;

    //用户名，密码
    private String username, password;
    private CircleImageView userImage;
    private RelativeLayout mRelativeLayout;

    private SmsCodeView mImgCode;
    private LinearLayout lyShowSmsCode, lyShowUser;
    private boolean isShowBack = true;
    private boolean IsForgetGesturePassword = false;
    private long firstTime = 0;


    @Override
    public int activityLayoutRes() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.login_activity;
    }


    @Override
    public void initView() {
        txtUsername = (EditText) findViewById(R.id.login_username);
        txtPassword = (EditText) findViewById(R.id.login_password);
        txtPasswordRepeat = (EditText) findViewById(R.id.login_password_repeat);
        imageUsername = (ImageView) findViewById(R.id.login_image_username);
        imagePassword = (ImageView) findViewById(R.id.login_image_password);
        imagePasswordRepeat = (ImageView) findViewById(R.id.login_image_password_repeat);
        mImgCode = (SmsCodeView) findViewById(R.id.sms_code);
        showpassword = (CheckBox) findViewById(R.id.show_password);
        userImage = (CircleImageView) findViewById(R.id.login_image_head);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.re_01);
        btnLogin = (Button) findViewById(R.id.login_btn_login);
        btnRegister = (Button) findViewById(R.id.login_btn_register);

        lyShowSmsCode = (LinearLayout) findViewById(R.id.login_linear3);
        lyShowUser = (LinearLayout) findViewById(R.id.login_linear4);

        //接收忘记手势密码 进入登录页面逻辑
        IsForgetGesturePassword = getIntent().getBooleanExtra(Config.TxtForgetGesturePassword, false);
        txtPassword.setText(IsForgetGesturePassword ? getString(R.string.empty_string) : SharedPreferencesUtil.getStringPreferences(this, Config.UserPassWord, "").isEmpty() ? getString(R.string.empty_string) : SharedPreferencesUtil.getStringPreferences(this, Config.UserPassWord, ""));
        mImgCode.createNewCode();
        mImgCode.setOnClickListener(this);
        txtUsername.setText(SharedPreferencesUtil.getStringPreferences(this, Config.UserName, "").isEmpty() ? getString(R.string.empty_string) : SharedPreferencesUtil.getStringPreferences(this, Config.UserName, ""));

        if (!TextUtils.isEmpty(txtUsername.getText().toString().trim()) && !TextUtils.isEmpty(txtPassword.getText().toString()) && !IsForgetGesturePassword) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        addLayoutListener(mRelativeLayout, btnRegister);
        initData();
        setListener();

        Bitmap bt = BitmapFactory.decodeFile(Config.RootPath + "head.jpg");
        if (bt != null) {
            userImage.setImageBitmap(bt);
        }
    }

    private void setListener() {
        //1.密码 显示/隐藏 监听事件
        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        //1.编辑框获取焦点事件
        txtUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    imageUsername.setImageResource(R.mipmap.icon_email_nor);
                } else {
                    imageUsername.setImageResource(R.mipmap.icon_email_pre);
                }
            }
        });

        //1.2 密码获取焦点的事件
        txtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    imagePassword.setImageResource(R.mipmap.icon_password_nor);
                } else {
                    imagePassword.setImageResource(R.mipmap.icon_password_pre);
                }
            }
        });

        //重复密码获取焦点
        txtPasswordRepeat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    imagePasswordRepeat.setImageResource(R.mipmap.icon_password_nor);
                } else {
                    imagePasswordRepeat.setImageResource(R.mipmap.icon_password_pre);
                }
            }
        });
    }

    private void initData() {

    }

    @Override
    public void onClick(View v) {
        if (v == mImgCode) {
            mImgCode.createNewCode();
        }
    }

    //随便看看
    public void LookAround(View view) {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        ApkInfoUtils.closeSoftInput(this);
        this.finish();
    }

    //登陆和註冊
    public void login(View view) {
        String tvUserName = txtUsername.getText().toString().trim();
        String tvPassword = txtPassword.getText().toString().trim();
        String tvSmsCode = txtPasswordRepeat.getText().toString().trim();

        if (TextUtils.isEmpty(tvUserName.trim())) {
            ToastUtils.showToast(this, "用户名不能为空");
            return;
        }

        if (TextUtils.isEmpty(tvPassword.trim())) {
            ToastUtils.showToast(this, "密码不能为空");
            return;
        }

        if (!isShowBack) {
            if (TextUtils.isEmpty(tvSmsCode.trim())) {
                ToastUtils.showToast(this, "验证码不能为空");
                return;
            }

            if (!CheckGetUtil.checkNum(tvSmsCode, mImgCode.getCode())) {
                mImgCode.createNewCode();
                ToastUtils.showToast(this, "验证码输入错误");
                return;
            }
            //校驗用戶名只能為一個
            List<UserInfoModel> mList = new ArrayList<>();
            mList = DaoUserInfo.query();

            for (int i = 0; i < mList.size(); i++) {
                if (tvUserName.equals(mList.get(i).getUserName())) {
                    username = mList.get(i).getUserName();
                }
            }
            if (tvUserName.equals(username)) {
                ToastUtils.showToast(this, "用戶名已经存在");
                return;
            }

            final UserInfoModel model = new UserInfoModel(DaoUserInfo.getCount(), tvUserName, tvPassword, "", "", "", "", "");
            SharedPreferencesUtil.setStringPreferences(this, Config.UserName, tvUserName);
            SharedPreferencesUtil.setStringPreferences(this, Config.UserPassWord, tvPassword);

            Observable.create(new Observable.OnSubscribe<UserInfoModel>() {
                @Override
                public void call(Subscriber<? super UserInfoModel> subscriber) {
                    DaoUserInfo.insert(model);
                    subscriber.onNext(model);
                }
            }).compose(RxUtil.<UserInfoModel>applySchedulers()).subscribe(new Action1<UserInfoModel>() {
                @Override
                public void call(UserInfoModel model) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("id", 1);
                    startActivity(intent);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            //登錄
            List<UserInfoModel> mList = new ArrayList<>();
            mList = DaoUserInfo.query();
            for (int i = 0; i < mList.size(); i++) {
                if (tvUserName.equals(mList.get(i).getUserName())) {
                    username = mList.get(i).getUserName();
                }

                if (tvPassword.equals(mList.get(i).getPassword())) {
                    password = mList.get(i).getPassword();
                }
            }
            if (!tvUserName.equals(username)) {
                ToastUtils.showToast(this, "用户名不正确");
                return;
            }
            if (!tvPassword.equals(password)) {
                ToastUtils.showToast(this, "密码不正确");
                return;
            }
            SharedPreferencesUtil.setStringPreferences(this, Config.UserName, tvUserName);
            SharedPreferencesUtil.setStringPreferences(this, Config.UserPassWord, tvPassword);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("id", 1);
            startActivity(intent);
            //发送消息到解锁页面 登陆成功之后让解锁页面关闭
            EventBusUtil.sendEvent(new Event(C.EventCode.UserForgetGesturePassword, true));
            this.finish();

        }
    }


    //注册
    public void register(View view) {
        if (isShowBack) {
            txtUsername.setFocusable(true);
            txtUsername.setFocusableInTouchMode(true);
            txtUsername.requestFocus();
            showRegisterModule(isShowBack);
            btnLogin.setText(getString(R.string.register));
            btnRegister.setText(getString(R.string.back_login));
            txtUsername.setText("");
            txtPassword.setText("");
            isShowBack = false;
        } else {
            txtUsername.setFocusable(true);
            txtUsername.setFocusableInTouchMode(true);
            txtUsername.requestFocus();
            showRegisterModule(isShowBack);
            btnLogin.setText(getString(R.string.login));
            btnRegister.setText(getString(R.string.register_cash_book));
            isShowBack = true;
        }

    }

    private void showRegisterModule(boolean isShow) {
        lyShowUser.setVisibility(isShow ? View.VISIBLE : View.GONE);
        lyShowSmsCode.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtils.removeToast();
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - firstTime > 2000) {
            ToastUtils.showToast(this, getString(R.string.press_one_exit));
            firstTime = System.currentTimeMillis();
            return;
        } else {
            App app = (App) getApplication();
            app.destroyReceiver();
            ToastUtils.removeToast();
            finish();

            ActivityManager activityMgr = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(getPackageName());
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);

        }
        super.onBackPressed();
    }


    public void addLayoutListener(final View main, final View scroll) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                main.getWindowVisibleDisplayFrame(rect);
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
                if (mainInvisibleHeight > 150) {
                    int[] location = new int[2];
                    scroll.getLocationInWindow(location);
                    int srollHeight = (location[1] + scroll.getHeight()) - rect.bottom;
                    main.scrollTo(0, srollHeight);
                } else {
                    main.scrollTo(0, 0);
                }
            }
        });
    }
}
