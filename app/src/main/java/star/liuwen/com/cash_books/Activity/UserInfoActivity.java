package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import star.liuwen.com.cash_books.Adapter.PopWindowAdapter;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.View.CircleImageView;

/**
 * Created by liuwen on 2017/1/21.
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout reNickName, reSignature, reSex, reLocation, reOut, rePhoto, rePhotoChoice, rePhotoCancel, reMan, reWoman, reBaomi;
    private TextView txtNickName, txtSignature, txtSex, txtLocation;
    private CircleImageView userImage;
    private PopupWindow window;
    private Bitmap head;//头像Bitmap
    private static String path = "/sdcard/DemoHead/";//sd路径

    @Override
    public int activityLayoutRes() {
        return R.layout.user_info_activity;
    }

    @Override
    public void initView() {

        setBackView();
        setLeftImage(R.mipmap.fanhui_lan);
        setTitle(getString(R.string.user_ino));
        setLeftText(getString(R.string.back));

        reNickName = (RelativeLayout) findViewById(R.id.re_user_info_nickName);
        reSignature = (RelativeLayout) findViewById(R.id.re_user_info_signature);
        reSex = (RelativeLayout) findViewById(R.id.re_user_info_sex);
        reLocation = (RelativeLayout) findViewById(R.id.re_user_info_location);
        reOut = (RelativeLayout) findViewById(R.id.re_user_info_out);

        txtNickName = (TextView) findViewById(R.id.user_info_nickName);
        txtSignature = (TextView) findViewById(R.id.user_info_signature);
        txtSex = (TextView) findViewById(R.id.user_info_sex);
        txtLocation = (TextView) findViewById(R.id.user_info_location);

        userImage = (CircleImageView) findViewById(R.id.user_info_url);

        reNickName.setOnClickListener(this);
        reSex.setOnClickListener(this);
        reSignature.setOnClickListener(this);
        reLocation.setOnClickListener(this);
        reOut.setOnClickListener(this);
        userImage.setOnClickListener(this);

        Bitmap bt = BitmapFactory.decodeFile(Config.RootPath + "head.jpg");
        if (bt != null) {
            userImage.setImageBitmap(bt);
        }

        txtNickName.setText(SharedPreferencesUtil.getStringPreferences(this, Config.userNickName, "").isEmpty() ? getString(R.string.no_setting) : SharedPreferencesUtil.getStringPreferences(this, Config.userNickName, ""));
        txtSignature.setText(SharedPreferencesUtil.getStringPreferences(this, Config.userSignature, "").isEmpty() ? getString(R.string.no_setting) : SharedPreferencesUtil.getStringPreferences(this, Config.userSignature, ""));
        txtSex.setText(SharedPreferencesUtil.getStringPreferences(this, Config.userSex, "").isEmpty() ? getString(R.string.no_setting) : SharedPreferencesUtil.getStringPreferences(this, Config.userSex, ""));
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, UpdateCommonKeyBoardActivity.class);
        if (v == reNickName) {
            intent.putExtra(Config.SaveAPenPlatform, "reNickName");
            startActivityForResult(intent, ReNickName);
        } else if (v == reSignature) {
            intent.putExtra(Config.SaveAPenPlatform, "reSignature");
            startActivityForResult(intent, ReSignature);
        } else if (v == reSex) {
            showPopWindowSex();
            if (window.isShowing()) {
                window.dismiss();
            } else {
                window.showAtLocation(reSex, Gravity.BOTTOM, 0, 0);
                backgroundAlpha(0.5f);
            }
        } else if (v == reLocation) {

        } else if (v == reOut) {

        } else if (v == rePhoto) {
            try {
                Intent intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//开启相机应用程序获取并返回图片（capture：俘获）
                intentPhoto.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "head.jpg")));//指明存储图片或视频的地址URI
                startActivityForResult(intentPhoto, PHOTO);//采用ForResult打开
            } catch (Exception e) {
                ToastUtils.showToast(UserInfoActivity.this, "相机无法启动，请先开启相机权限");
            }
            window.dismiss();
        } else if (v == rePhotoChoice) {
            Intent intentPhotoChoice = new Intent(Intent.ACTION_PICK, null);//返回被选中项的URI
            intentPhotoChoice.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//得到所有图片的URI
            startActivityForResult(intentPhotoChoice, CHOOSE_PICTURE);
            window.dismiss();
        } else if (v == rePhotoCancel) {

            window.dismiss();

        } else if (v == reMan) {
            txtSex.setText("男");
            SharedPreferencesUtil.setStringPreferences(this, Config.userSex, "男");
            window.dismiss();
        } else if (v == reWoman) {
            txtSex.setText("女");
            SharedPreferencesUtil.setStringPreferences(this, Config.userSex, "女");
            window.dismiss();
        } else if (v == reBaomi) {
            txtSex.setText("保密");
            SharedPreferencesUtil.setStringPreferences(this, Config.userSex, "保密");
            window.dismiss();
        } else if (v == userImage) {
            showPopWindowPhoto();
            if (window.isShowing()) {
                window.dismiss();
            } else {
                window.showAtLocation(userImage, Gravity.BOTTOM, 0, 0);
                backgroundAlpha(0.5f);
            }
        }
    }

    private static final int CHOOSE_PICTURE = 101;
    private static final int PHOTO = 102;
    private static final int RESULT = 103;
    private static final int ReNickName = 104;
    private static final int ReSignature = 105;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_PICTURE:
                    cropPhoto(data.getData());
                    break;
                case PHOTO:
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));
                    break;
                case RESULT:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        head = extras.getParcelable("data");
                        if (head != null) {
                            /**
                             * 上传服务器代码
                             */
                            setPicToView(head);//保存在SD卡中
                            userImage.setImageBitmap(head);//用ImageView显示出来
                            RxBus.getInstance().post(Config.userUrl, head);
                        }
                    }
                    break;
            }
        } else {
            switch (requestCode) {
                case ReNickName:
                    if (data != null) {
                        txtNickName.setText(data.getExtras().getString(Config.TextInPut));
                        SharedPreferencesUtil.setStringPreferences(UserInfoActivity.this, Config.userNickName, data.getExtras().getString(Config.TextInPut));
                        RxBus.getInstance().post(Config.userNickName, data.getExtras().getString(Config.TextInPut));
                    }
                    break;
                case ReSignature:
                    if (data != null) {
                        txtSignature.setText(data.getExtras().getString(Config.TextInPut));
                        SharedPreferencesUtil.setStringPreferences(UserInfoActivity.this, Config.userSignature, data.getExtras().getString(Config.TextInPut));
                        RxBus.getInstance().post(Config.userSignature, data.getExtras().getString(Config.TextInPut));
                    }
            }
        }
    }

    private void showPopWindowSex() {
        View popView = View.inflate(this, R.layout.pop_user_sex, null);
        reMan = (RelativeLayout) popView.findViewById(R.id.re_pop_sex_man);
        reWoman = (RelativeLayout) popView.findViewById(R.id.re_pop_sex_woman);
        reBaomi = (RelativeLayout) popView.findViewById(R.id.re_pop_sex_baomi);

        reMan.setOnClickListener(this);
        reWoman.setOnClickListener(this);
        reBaomi.setOnClickListener(this);

        window = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.setAnimationStyle(R.style.AnimBottom);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setOutsideTouchable(true);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });


    }

    private void showPopWindowPhoto() {
        View popView = View.inflate(this, R.layout.pop_user_photo, null);
        rePhoto = (RelativeLayout) popView.findViewById(R.id.re_pop_photo);
        rePhotoChoice = (RelativeLayout) popView.findViewById(R.id.re_pop_photo_choice);
        rePhotoCancel = (RelativeLayout) popView.findViewById(R.id.re_pop_photo_cancel);

        rePhoto.setOnClickListener(this);
        rePhotoCancel.setOnClickListener(this);
        rePhotoChoice.setOnClickListener(this);

        window = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.setAnimationStyle(R.style.AnimBottom);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setOutsideTouchable(true);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }


    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        //找到指定URI对应的资源图片
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        //进入系统裁剪图片的界面
        startActivityForResult(intent, RESULT);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd卡是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(Config.RootPath);
        file.mkdirs();// 创建以此File对象为名（path）的文件夹
        String fileName = Config.RootPath + "head.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件（compress：压缩）

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
