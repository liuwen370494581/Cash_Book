package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
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
import android.widget.ScrollView;

import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.MainActivity;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.RxBus.RxBusResult;
import star.liuwen.com.cash_books.View.CircleImageView;

/**
 * Created by liuwen on 2017/2/9.
 */
public class LoginActivity extends AppCompatActivity {
    //初始化控件
    private EditText txtUsername, txtPassword;
    private ImageView imageUsername, imagePassword;
    private CheckBox showpassword;
    private Button btnRegister, btnLogin;

    //用户名，密码
    private String username, password;
    private CircleImageView userImage;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initView();

    }

    public void initView() {
        txtUsername = (EditText) findViewById(R.id.login_username);
        txtPassword = (EditText) findViewById(R.id.login_password);
        imageUsername = (ImageView) findViewById(R.id.login_image_username);
        imagePassword = (ImageView) findViewById(R.id.login_image_password);
        showpassword = (CheckBox) findViewById(R.id.show_password);
        userImage = (CircleImageView) findViewById(R.id.login_image_head);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.re_01);
        btnLogin = (Button) findViewById(R.id.login_btn_login);
        btnRegister = (Button) findViewById(R.id.login_btn_register);

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
    }

    private void initData() {
//        RxBus.getInstance().toObserverableOnMainThread(Config.userUrl, new RxBusResult() {
//            @Override
//            public void onRxBusResult(Object o) {
//                userImage.setImageBitmap((Bitmap) o);
//            }
//        });
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

    public void LookAround(View view) {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // RxBus.getInstance().removeObserverable(Config.userUrl);
    }
}
