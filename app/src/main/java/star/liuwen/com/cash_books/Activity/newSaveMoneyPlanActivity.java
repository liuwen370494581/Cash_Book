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
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.EventBus.C;
import star.liuwen.com.cash_books.EventBus.Event;
import star.liuwen.com.cash_books.EventBus.EventBusUtil;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.bean.PlanSaveMoneyModel;

/**
 * Created by liuwen on 2017/1/19.
 */
public class newSaveMoneyPlanActivity extends BaseActivity implements View.OnClickListener {
    private ImageView imageUrl, imagePhoto;
    private RelativeLayout reMoney, reTime, reMark, rePhoto, rePhotoChoice, rePhotoCancel;
    private TextView txtMoney, txtTime, txtReMark, txtMubiao;
    private TimePickerView pvTime;
    private PlanSaveMoneyModel model;
    private PopupWindow window;
    private Bitmap head;//头像Bitmap


    @Override
    public int activityLayoutRes() {
        return R.layout.new_save_money_plan_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setLeftImage(R.mipmap.fanhui_lan);
        setLeftText(getString(R.string.back));
        setTitle("新建存钱计划");
        setRightText(getString(R.string.finish), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putSaveMoneyPlan();
            }

        });
        model = (PlanSaveMoneyModel) getIntent().getExtras().getSerializable(Config.PlanSaveMoneyModel);
        imageUrl = (ImageView) findViewById(R.id.new_money_image_url);
        imagePhoto = (ImageView) findViewById(R.id.new_money_photo);

        reMoney = (RelativeLayout) findViewById(R.id.re_new_money_mubiao);
        reTime = (RelativeLayout) findViewById(R.id.re_new_money_time);
        reMark = (RelativeLayout) findViewById(R.id.re_new_money_beizhu);

        txtMoney = (TextView) findViewById(R.id.new_money_txt_money);
        txtReMark = (TextView) findViewById(R.id.new_money_txt_beizhu);
        txtMubiao = (TextView) findViewById(R.id.new_money_txt_mubiao);
        txtTime = (TextView) findViewById(R.id.new_money_txt_time);

        reMoney.setOnClickListener(this);
        reTime.setOnClickListener(this);
        imageUrl.setOnClickListener(this);

        reMark.setOnClickListener(this);
        imagePhoto.setOnClickListener(this);

        imageUrl.setImageResource(model.getUrl());
        txtMubiao.setText(model.getPlanName());
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime.setRange(2017, 2055);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                txtTime.setText(DateTimeUtil.getYearMonthDay(date));
            }
        });
        Bitmap bt = BitmapFactory.decodeFile(Config.RootPath + "TargetBg.jpg");
        if (bt != null) {
            imageUrl.setImageBitmap(bt);
        }
    }

    private void putSaveMoneyPlan() {
        String money = txtMoney.getText().toString().trim();
        String planTime = txtTime.getText().toString().trim();
        String remark = txtReMark.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            ToastUtils.showToast(this, "目标金额不能为空");
            return;
        }
        if (TextUtils.isEmpty(planTime)) {
            ToastUtils.showToast(this, "计划完成时间不能为空");
            return;
        }
        if (TextUtils.isEmpty(remark)) {
            ToastUtils.showToast(this, "备注不能为空");
            return;
        }
        SharedPreferencesUtil.setStringPreferences(this, Config.PlanMoney, money);
        SharedPreferencesUtil.setStringPreferences(this, Config.PlanFinishTime, planTime);
        SharedPreferencesUtil.setStringPreferences(this, Config.PlanRemark, remark);
        SharedPreferencesUtil.setBooleanPreferences(this, Config.PlanIsPut, true);
        Intent intent = new Intent(newSaveMoneyPlanActivity.this, ShowSaveMoneyPlanActivity.class);
        intent.putExtra(Config.PlanSaveMoneyModel, model);
        startActivity(intent);
        EventBusUtil.sendEvent(new Event(C.EventCode.Close));
        EventBusUtil.sendEvent(new Event(C.EventCode.NewSaveMoneyPlanActivityToMyFragment, model));
        newSaveMoneyPlanActivity.this.finish();

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(newSaveMoneyPlanActivity.this, UpdateCommonKeyBoardActivity.class);
        if (v == reMoney) {
            intent.putExtra(Config.SaveAPenPlatform, "PlanMoney");
            startActivityForResult(intent, ReMoney);

        } else if (v == reTime) {
            pvTime.show();
        } else if (v == reMark) {
            intent.putExtra(Config.SaveAPenPlatform, "PlanReMark");
            startActivityForResult(intent, ReMark);
        } else if (v == imagePhoto) {
            showPopWindowPhoto();
            if (window.isShowing()) {
                window.dismiss();
            } else {
                window.showAtLocation(imagePhoto, Gravity.BOTTOM, 0, 0);
                backgroundAlpha(0.5f);
            }
        } else if (v == rePhoto) {
            try {
                Intent intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//开启相机应用程序获取并返回图片（capture：俘获）
                intentPhoto.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "TargetBg.jpg")));//指明存储图片或视频的地址URI
                startActivityForResult(intentPhoto, PHOTO);//采用ForResult打开
            } catch (Exception e) {
                ToastUtils.showToast(newSaveMoneyPlanActivity.this, "相机无法启动，请先开启相机权限");
            }
            window.dismiss();
        } else if (v == rePhotoChoice) {
            Intent intentPhotoChoice = new Intent(Intent.ACTION_PICK, null);//返回被选中项的URI
            intentPhotoChoice.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//得到所有图片的URI
            startActivityForResult(intentPhotoChoice, CHOOSE_PICTURE);
            window.dismiss();
        } else if (v == rePhotoCancel) {
            window.dismiss();
        }
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

    private static final int ReMoney = 101;
    private static final int ReMark = 103;

    private static final int CHOOSE_PICTURE = 101;
    private static final int PHOTO = 102;
    private static final int RESULT = 103;


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
                            + "/TargetBg.jpg");
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
                            imageUrl.setImageBitmap(head);//用ImageView显示出来
                            EventBusUtil.sendEvent(new Event(C.EventCode.TarGetUrl, head));

                        }
                    }
                    break;
            }
        } else {
            switch (requestCode) {
                case ReMoney:
                    if (data != null) {
                        txtMoney.setText(data.getExtras().getString(Config.TextInPut));
                    }
                    break;
                case ReMark:
                    if (data != null) {
                        txtReMark.setText(data.getExtras().getString(Config.TextInPut));
                    }
                    break;
            }
        }
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
        String fileName = Config.RootPath + "TargetBg.jpg";//图片名字
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
