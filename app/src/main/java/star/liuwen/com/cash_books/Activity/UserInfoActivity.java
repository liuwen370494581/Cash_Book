package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dialog.TipandEditDialog;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.View.CircleImageView;
import star.liuwen.com.cash_books.View.Wheel.AddrXmlParser;
import star.liuwen.com.cash_books.View.Wheel.CityInfoModel;
import star.liuwen.com.cash_books.View.Wheel.DistrictInfoModel;
import star.liuwen.com.cash_books.View.Wheel.ProvinceInfoModel;
import star.liuwen.com.cash_books.View.Wheel.WheelView;

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

    private PopupWindow addressPopWindow;
    private WheelView mProvincePicker;
    private WheelView mCityPicker;
    private WheelView mCountyPicker;
    private LinearLayout boxBtnCancel;
    private LinearLayout boxBtnOk;
    protected boolean isDataLoaded = false;

    /**
     * 与选择地址相关
     */
    protected ArrayList<String> mProvinceDatas;
    protected Map<String, ArrayList<String>> mCitisDatasMap = new HashMap<String, ArrayList<String>>();
    protected Map<String, ArrayList<String>> mDistrictDatasMap = new HashMap<String, ArrayList<String>>();
    protected String mCurrentProviceName;
    protected String mCurrentCityName;
    protected String mCurrentDistrictName;
    private boolean isAddrChoosed = false;

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
        initProviceSelectView();

        Bitmap bt = BitmapFactory.decodeFile(Config.RootPath + "head.jpg");
        if (bt != null) {
            userImage.setImageBitmap(bt);
        }

        txtNickName.setText(SharedPreferencesUtil.getStringPreferences(this, Config.userNickName, "").isEmpty() ? SharedPreferencesUtil.getStringPreferences(this, Config.UserName, "").isEmpty() ? getString(R.string.no_setting) : SharedPreferencesUtil.getStringPreferences(this, Config.UserName, "") : SharedPreferencesUtil.getStringPreferences(this, Config.userNickName, ""));
        txtSignature.setText(SharedPreferencesUtil.getStringPreferences(this, Config.userSignature, "").isEmpty() ? getString(R.string.no_setting) : SharedPreferencesUtil.getStringPreferences(this, Config.userSignature, ""));
        txtSex.setText(SharedPreferencesUtil.getStringPreferences(this, Config.userSex, "").isEmpty() ? getString(R.string.no_setting) : SharedPreferencesUtil.getStringPreferences(this, Config.userSex, ""));
        txtLocation.setText(SharedPreferencesUtil.getStringPreferences(this, Config.UserLocation, "").isEmpty() ? getString(R.string.no_setting) : SharedPreferencesUtil.getStringPreferences(this, Config.UserLocation, ""));
    }

    private void initProviceSelectView() {

        View contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_city_picker, null);
        mProvincePicker = (WheelView) contentView.findViewById(R.id.province);
        mCityPicker = (WheelView) contentView.findViewById(R.id.city);
        mCountyPicker = (WheelView) contentView.findViewById(R.id.county);
        boxBtnCancel = (LinearLayout) contentView.findViewById(R.id.box_btn_cancel);
        boxBtnOk = (LinearLayout) contentView.findViewById(R.id.box_btn_ok);
        addressPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        addressPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addressPopWindow.setOutsideTouchable(true);

        addressPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        mProvincePicker.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                String provinceText = mProvinceDatas.get(id);
                if (!mCurrentProviceName.equals(provinceText)) {
                    mCurrentProviceName = provinceText;
                    ArrayList<String> mCityData = mCitisDatasMap.get(mCurrentProviceName);
                    mCityPicker.resetData(mCityData);
                    mCityPicker.setDefault(0);
                    mCurrentCityName = mCityData.get(0);

                    ArrayList<String> mDistrictData = mDistrictDatasMap.get(mCurrentCityName);
                    mCountyPicker.resetData(mDistrictData);
                    mCountyPicker.setDefault(0);
                    mCurrentDistrictName = mDistrictData.get(0);
                }
            }

            @Override
            public void selecting(int id, String text) {
            }
        });

        mCityPicker.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                ArrayList<String> mCityData = mCitisDatasMap.get(mCurrentProviceName);
                String cityText = mCityData.get(id);
                if (!mCurrentCityName.equals(cityText)) {
                    mCurrentCityName = cityText;
                    ArrayList<String> mCountyData = mDistrictDatasMap.get(mCurrentCityName);
                    mCountyPicker.resetData(mCountyData);
                    mCountyPicker.setDefault(0);
                    mCurrentDistrictName = mCountyData.get(0);
                }
            }

            @Override
            public void selecting(int id, String text) {

            }
        });

        mCountyPicker.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                ArrayList<String> mDistrictData = mDistrictDatasMap.get(mCurrentCityName);
                String districtText = mDistrictData.get(id);
                if (!mCurrentDistrictName.equals(districtText)) {
                    mCurrentDistrictName = districtText;
                }
            }

            @Override
            public void selecting(int id, String text) {
            }
        });

        boxBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressPopWindow.dismiss();
            }
        });

        boxBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAddrChoosed = true;
                String addr = mCurrentProviceName + mCurrentCityName;
                if (!mCurrentDistrictName.equals("其他")) {
                    addr += mCurrentDistrictName;
                }
                txtLocation.setText(addr);
                SharedPreferencesUtil.setStringPreferences(UserInfoActivity.this, Config.UserLocation, addr);
                addressPopWindow.dismiss();
            }
        });

        // 启动线程读取数据
        new Thread() {
            @Override
            public void run() {
                // 读取数据
                isDataLoaded = readAddrDatas();

                // 通知界面线程
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mProvincePicker.setData(mProvinceDatas);
                        mProvincePicker.setDefault(0);
                        mCurrentProviceName = mProvinceDatas.get(0);

                        ArrayList<String> mCityData = mCitisDatasMap.get(mCurrentProviceName);
                        mCityPicker.setData(mCityData);
                        mCityPicker.setDefault(0);
                        mCurrentCityName = mCityData.get(0);

                        ArrayList<String> mDistrictData = mDistrictDatasMap.get(mCurrentCityName);
                        mCountyPicker.setData(mDistrictData);
                        mCountyPicker.setDefault(0);
                        mCurrentDistrictName = mDistrictData.get(0);
                    }
                });
            }
        }.start();

    }

    /**
     * 读取地址数据，请使用线程进行调用
     *
     * @return
     */
    protected boolean readAddrDatas() {
        List<ProvinceInfoModel> provinceList = null;
        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            AddrXmlParser handler = new AddrXmlParser();
            parser.parse(input, handler);
            input.close();
            provinceList = handler.getDataList();
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityInfoModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictInfoModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                }
            }
            mProvinceDatas = new ArrayList<String>();
            for (int i = 0; i < provinceList.size(); i++) {
                mProvinceDatas.add(provinceList.get(i).getName());
                List<CityInfoModel> cityList = provinceList.get(i).getCityList();
                ArrayList<String> cityNames = new ArrayList<String>();
                for (int j = 0; j < cityList.size(); j++) {
                    cityNames.add(cityList.get(j).getName());
                    List<DistrictInfoModel> districtList = cityList.get(j).getDistrictList();
                    ArrayList<String> distrinctNameArray = new ArrayList<String>();
                    DistrictInfoModel[] distrinctArray = new DistrictInfoModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        DistrictInfoModel districtModel = new DistrictInfoModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray.add(districtModel.getName());
                    }
                    mDistrictDatasMap.put(cityNames.get(j), distrinctNameArray);
                }
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }
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
            if (isDataLoaded) {
                if (addressPopWindow.isShowing()) {
                    addressPopWindow.dismiss();
                } else {
                    addressPopWindow.showAtLocation(reLocation, Gravity.BOTTOM, 0, 0);
                    backgroundAlpha(0.5f);
                }
            } else {
                ToastUtils.showToast(this, "加载数据失败,请稍后再试");
            }

        } else if (v == reOut) {
            LoginOut();
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


    private void LoginOut() {
        final TipandEditDialog dialog = new TipandEditDialog(this, "确定要退出吗");
        dialog.show();
        dialog.setLeftTextColor(getResources().getColor(R.color.jiechu));
        dialog.setRightTextColor(getResources().getColor(R.color.blue));
        dialog.setListener(new TipandEditDialog.ITipEndEditDialogListener() {
            @Override
            public void ClickLeft() {
                dialog.dismiss();
            }

            @Override
            public void ClickRight() {
                SharedPreferencesUtil.cleanSharePreferences(UserInfoActivity.this, Config.userSex);
                SharedPreferencesUtil.cleanSharePreferences(UserInfoActivity.this, Config.userSignature);
                SharedPreferencesUtil.cleanSharePreferences(UserInfoActivity.this, Config.UserName);
                SharedPreferencesUtil.cleanSharePreferences(UserInfoActivity.this, Config.UserPassWord);
                startActivity(new Intent(UserInfoActivity.this, LoginActivity.class));
                RxBus.getInstance().post(Config.RxUserInFoToMyFragment, true);
                finish();
            }
        });
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
