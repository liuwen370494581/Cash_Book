package star.liuwen.com.cash_books.Fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import star.liuwen.com.cash_books.Activity.AboutMeActivity;
import star.liuwen.com.cash_books.Activity.ChangeSkinActivity;
import star.liuwen.com.cash_books.Activity.RemindActivity;
import star.liuwen.com.cash_books.Activity.SaveMoneyActivity;
import star.liuwen.com.cash_books.Activity.SettingActivity;
import star.liuwen.com.cash_books.Activity.ShowSaveMoneyPlanActivity;
import star.liuwen.com.cash_books.Activity.UserInfoActivity;
import star.liuwen.com.cash_books.Base.BaseFragment;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.RxBus.RxBusResult;
import star.liuwen.com.cash_books.Utils.BitMapUtils;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.View.CircleImageView;
import star.liuwen.com.cash_books.bean.PlanSaveMoneyModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {
    private RelativeLayout reUserInfo, reJq, reCq, reHf, reZd, reDaoData, reTx, reSuggest, reSetting, reAbout;
    private DrawerLayout mDrawerLayout;
    private CircleImageView mImageUrl;
    private TextView txtUserNickName, txtSignature;
    private PlanSaveMoneyModel model;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_my);
        setTitle(getString(R.string.my_more));
        initView();
        initData();
        return getContentView();
    }

    private void initView() {
        reUserInfo = (RelativeLayout) getContentView().findViewById(R.id.re_f_my_userInfo);
        reJq = (RelativeLayout) getContentView().findViewById(R.id.re_f_my_jieqian);
        reCq = (RelativeLayout) getContentView().findViewById(R.id.re_f_my_cunqian);
        reHf = (RelativeLayout) getContentView().findViewById(R.id.re_f_my_huanfu);
        reZd = (RelativeLayout) getContentView().findViewById(R.id.re_f_my_zhangdan);
        reDaoData = (RelativeLayout) getContentView().findViewById(R.id.re_f_my_daochushuju);
        reTx = (RelativeLayout) getContentView().findViewById(R.id.re_f_my_tixing);
        reSuggest = (RelativeLayout) getContentView().findViewById(R.id.re_f_my_suggest);
        reSetting = (RelativeLayout) getContentView().findViewById(R.id.re_f_my_setting);
        reAbout = (RelativeLayout) getContentView().findViewById(R.id.re_f_my_about_us);

        txtUserNickName = (TextView) getContentView().findViewById(R.id.f_my_userName);
        txtSignature = (TextView) getContentView().findViewById(R.id.f_my_message);

        mImageUrl = (CircleImageView) getContentView().findViewById(R.id.f_my_touxiang);
        mDrawerLayout = (DrawerLayout) getContentView().findViewById(R.id.drawer_layout);

        reUserInfo.setOnClickListener(this);
        reJq.setOnClickListener(this);
        reCq.setOnClickListener(this);
        reHf.setOnClickListener(this);
        reZd.setOnClickListener(this);
        reDaoData.setOnClickListener(this);
        reTx.setOnClickListener(this);
        reSuggest.setOnClickListener(this);
        reSetting.setOnClickListener(this);
        reAbout.setOnClickListener(this);

        if (SharedPreferencesUtil.getStringPreferences(getActivity(), Config.ChangeBg, null) != null) {
            Bitmap bitmap = BitMapUtils.getBitmapByPath(getActivity(), SharedPreferencesUtil.getStringPreferences(getActivity(), Config.ChangeBg, null), false);
            mDrawerLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
        }


        Bitmap bt = BitmapFactory.decodeFile(Config.RootPath + "head.jpg");
        if (bt != null) {
            mImageUrl.setImageBitmap(bt);
        }

        txtUserNickName.setText(SharedPreferencesUtil.getStringPreferences(getActivity(), Config.userNickName, "").isEmpty() ? getString(R.string.no_setting) : SharedPreferencesUtil.getStringPreferences(getActivity(), Config.userNickName, ""));
        txtSignature.setText(SharedPreferencesUtil.getStringPreferences(getActivity(), Config.userSignature, "").isEmpty() ? getString(R.string.no_setting) : SharedPreferencesUtil.getStringPreferences(getActivity(), Config.userSignature, ""));

    }

    private void initData() {
        RxBus.getInstance().toObserverableOnMainThread(Config.isBgCash, new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                Bitmap bitmap = BitMapUtils.getBitmapByPath(getActivity(), o.toString(), false);
                mDrawerLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
            }
        });

        RxBus.getInstance().toObserverableOnMainThread(Config.userUrl, new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                mImageUrl.setImageBitmap((Bitmap) o);
            }
        });

        RxBus.getInstance().toObserverableOnMainThread(Config.userNickName, new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                txtUserNickName.setText((CharSequence) o);
            }
        });

        RxBus.getInstance().toObserverableOnMainThread(Config.userSignature, new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                txtSignature.setText((CharSequence) o);
            }
        });

        RxBus.getInstance().toObserverableOnMainThread(Config.PlanSaveMoneyModel, new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                model = (PlanSaveMoneyModel) o;
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), SaveMoneyActivity.class);
        if (v == reUserInfo) {
            startActivity(new Intent(getActivity(), UserInfoActivity.class));
        } else if (v == reJq) {

        } else if (v == reCq) {
            intent.putExtra("plan", "cunqian");
            if (SharedPreferencesUtil.getBooleanPreferences(getActivity(), Config.PlanIsPut, false)) {
                Intent intentPlan = new Intent(new Intent(getActivity(), ShowSaveMoneyPlanActivity.class));
                intentPlan.putExtra(Config.PlanSaveMoneyModel, model);
                startActivity(intentPlan);
            } else {
                startActivity(intent);
            }
        } else if (v == reHf) {
            startActivity(new Intent(getActivity(), ChangeSkinActivity.class));
        } else if (v == reZd) {
            intent.putExtra("plan", "zhangdan");
            startActivity(intent);
        } else if (v == reDaoData) {
            ToastUtils.showToast(getActivity(), "功能开发中，敬请期待");
        } else if (v == reTx) {
            startActivity(new Intent(getActivity(), RemindActivity.class));
        } else if (v == reSuggest) {
            ToastUtils.showToast(getActivity(), "功能开发中,敬请期待");
        } else if (v == reSetting) {
            startActivity(new Intent(getActivity(), SettingActivity.class));
        } else if (v == reAbout) {
            startActivity(new Intent(getActivity(), AboutMeActivity.class));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().release();
    }
}
