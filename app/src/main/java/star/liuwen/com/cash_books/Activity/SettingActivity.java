package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.sharesdk.framework.ShareSDK;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dialog.TipandEditDialog;
import star.liuwen.com.cash_books.EventBus.C;
import star.liuwen.com.cash_books.EventBus.Event;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.ActivityKiller;
import star.liuwen.com.cash_books.Utils.BitMapUtils;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.Utils.SnackBarUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.onekeyshare.OnekeyShare;

/**
 * Created by liuwen on 2017/2/7.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout reSaveAccount, reHuoBi, reCleanHunCun, reShare, reUpdate, reCleanAllData, rePopQq, rePopWeXin, rePopFriend, rePopSina, reCancel;
    private DrawerLayout mDrawerLayout;
    private PopupWindow window;

    private OnekeyShare mOks;
    private String mDownLoadUrl;
    private String mShareAppName;
    private String mShareContent;
    private String mFilePath;

    @Override
    public int activityLayoutRes() {
        return R.layout.setting_activity;
    }

    @Override
    public void initView() {
        setTitle(getString(R.string.setting));
        setBackView();
        setLeftText(getString(R.string.back));
        setLeftImage(R.mipmap.fanhui_lan);
        initDate();
        copyShareImgToSD();

        reSaveAccount = (RelativeLayout) findViewById(R.id.re_setting_save_account);
        reHuoBi = (RelativeLayout) findViewById(R.id.re_setting_huobi);
        reCleanHunCun = (RelativeLayout) findViewById(R.id.re_setting_hucun);
        reShare = (RelativeLayout) findViewById(R.id.re_setting_share);
        reUpdate = (RelativeLayout) findViewById(R.id.re_setting_update);
        reCleanAllData = (RelativeLayout) findViewById(R.id.re_setting_clean_all_data);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        reSaveAccount.setOnClickListener(this);
        reHuoBi.setOnClickListener(this);
        reCleanHunCun.setOnClickListener(this);
        reShare.setOnClickListener(this);
        reUpdate.setOnClickListener(this);
        reCleanAllData.setOnClickListener(this);

        if (SharedPreferencesUtil.getStringPreferences(this, Config.ChangeBg, null) != null) {
            Bitmap bitmap = BitMapUtils.getBitmapByPath(this, SharedPreferencesUtil.getStringPreferences(this, Config.ChangeBg, null), false);
            mDrawerLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
        }

    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        switch (event.getCode()) {
            case C.EventCode.UserPhoto:
                Bitmap bitmap = BitMapUtils.getBitmapByPath(SettingActivity.this, event.getData().toString(), false);
                mDrawerLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
                break;
        }
    }

    private void initDate() {
        mDownLoadUrl = "http://sap.dyajb.com/jiaju_share.html";
        mShareAppName = getString(R.string.app_name);
        mShareContent = getString(R.string.share_content);
        mFilePath = Config.SHARE_LOGO;
    }

    @Override
    public void onClick(View v) {
        if (v == reSaveAccount) {
            startActivity(new Intent(SettingActivity.this, CodedLockActivity.class));
        } else if (v == reHuoBi) {
            startActivity(new Intent(SettingActivity.this, HuoBiActivity.class));
        } else if (v == reCleanHunCun) {
            SnackBarUtil.show(reCleanHunCun, "缓存已经清除");
        } else if (v == reShare) {
            showPopShare();
            if (window.isShowing()) {
                window.dismiss();
            } else {
                window.showAtLocation(reShare, Gravity.BOTTOM, 0, 0);
                backgroundAlpha(0.5f);
            }
        } else if (v == reUpdate) {
            SnackBarUtil.show(reUpdate, "该版本已经是最新版本");
        } else if (v == reCleanAllData) {
            CleanAllData();
        } else if (v == rePopQq) {
            shareMethod(0);
        } else if (v == rePopFriend) {
            shareMethod(3);
        } else if (v == rePopSina) {
            shareMethod(1);
        } else if (v == rePopWeXin) {
            shareMethod(2);
        } else if (v == reCancel) {
            window.dismiss();
        }
    }

    private void CleanAllData() {
        final TipandEditDialog dialog = new TipandEditDialog(SettingActivity.this, getString(R.string.clean_all_data));
        dialog.show();
        dialog.setLeftText(getString(R.string.cancel));
        dialog.setLeftTextColor(getResources().getColor(R.color.jiechu));
        dialog.setRightText(getString(R.string.sure));
        dialog.setRightTextColor(getResources().getColor(R.color.blue));
        dialog.setListener(new TipandEditDialog.ITipEndEditDialogListener() {
            @Override
            public void ClickLeft() {
                dialog.dismiss();
            }

            @Override
            public void ClickRight() {
                SharedPreferencesUtil.cleanSharePreferences(SettingActivity.this, Config.ChangeBg);
                ActivityKiller.getInstance().exitActivityInList();
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                dialog.dismiss();
            }

        });
    }

    private void shareMethod(int type) {
        try {
            ShareSDK.initSDK(this);
            mOks = new OnekeyShare();
            mOks.setAddress("");
            mOks.setTitleUrl(mDownLoadUrl);
            mOks.setUrl(mDownLoadUrl);
            mOks.setSilent(true);// 设置成直接分享
            switch (type) {
                case 0:
                    mOks.setTitle(mShareAppName);
                    mOks.setImagePath(mFilePath + "logo_asset.png");
                    mOks.setText(mShareContent);
                    mOks.setPlatform("QQ");
                    mOks.show(this);
                    break;
                case 1:
                    mOks.disableSSOWhenAuthorize();
                    mOks.setTitle(mShareAppName);
                    mOks.setImagePath(mFilePath + "logo_asset.png");
                    mOks.setText(mShareContent + "下载地址:" + mDownLoadUrl);
                    mOks.setPlatform("SinaWeibo");
                    mOks.show(this);

                    break;
                case 2:
                    mOks.setTitle(mShareAppName);
                    mOks.setImagePath(mFilePath + "logo_asset.png");
                    mOks.setText(mShareContent);
                    mOks.setPlatform("Wechat");
                    mOks.show(this);

                    break;
                case 3:
                    mOks.setTitle(mShareContent);
                    mOks.setImagePath(mFilePath + "logo_asset.png");
                    mOks.setText(mShareContent);
                    mOks.setPlatform("WechatMoments");
                    mOks.show(this);
                    break;
                default:
                    break;
            }
            // ToastUtils.centerToast(this, getString(R.string.sharing));
            // this.finish();
        } catch (Exception e) {
            ToastUtils.showToast(SettingActivity.this, getString(R.string.share_fail));
            ShareSDK.stopSDK(this);
        }
    }


    private void showPopShare() {
        View popView = View.inflate(this, R.layout.pop_share, null);
        rePopQq = (RelativeLayout) popView.findViewById(R.id.re_pop_qq);
        rePopSina = (RelativeLayout) popView.findViewById(R.id.re_pop_sina);
        rePopWeXin = (RelativeLayout) popView.findViewById(R.id.re_pop_weixin);
        rePopFriend = (RelativeLayout) popView.findViewById(R.id.re_pop_weixin_friend);
        reCancel = (RelativeLayout) popView.findViewById(R.id.re_pop_share_cancel);

        rePopQq.setOnClickListener(this);
        rePopSina.setOnClickListener(this);
        rePopFriend.setOnClickListener(this);
        rePopWeXin.setOnClickListener(this);
        reCancel.setOnClickListener(this);

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


    private void copyShareImgToSD() {
        String fileName = "logo_asset.png";

        try {
            File file = new File(mFilePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(mFilePath + fileName);
            if (!file.exists()) {
                // 本地不存在才copy至sdcard，由于copy文件较大 使用线程来进行
                file.createNewFile();
                new Thread(runnableCopyFile).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    Runnable runnableCopyFile = new Runnable() {

        @Override
        public void run() {
            try {
                AssetManager am = getAssets();
                String fileName = "logo_asset.png";
                InputStream is = is = am.open(fileName);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                File file = new File(mFilePath + fileName);
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            } catch (Exception e) {

            }
        }
    };
}
