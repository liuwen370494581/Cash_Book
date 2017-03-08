package star.liuwen.com.cash_books.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import star.liuwen.com.cash_books.Adapter.PopWindowAdapter;
import star.liuwen.com.cash_books.Adapter.ZhiChuAdapter;
import star.liuwen.com.cash_books.Base.App;
import star.liuwen.com.cash_books.Base.BaseFragment;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoChoiceAccount;
import star.liuwen.com.cash_books.Enage.DataEnige;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.bean.AccountModel;
import star.liuwen.com.cash_books.bean.ChoiceAccount;
import star.liuwen.com.cash_books.bean.ZhiChuModel;

/**
 * Created by liuwen on 2017/1/5.
 */
public class ShouRuFragment extends BaseFragment implements View.OnClickListener {

    private List<ZhiChuModel> mList;
    private RecyclerView mRecyclerView;
    private ZhiChuAdapter mAdapter;
    private EditText edName;
    private ImageView imageName;
    private TextView txtName, tvData, tvZhanghu, tvSure;
    private int position, AccountUrl;
    private PopupWindow window;
    private List<AccountModel> homListData;
    private String AccountType, AccountData, AccountConsumeType;
    private ListView mListView;
    private PopWindowAdapter mPopWindowAdapter;
    private TimePickerView pvTime;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_shouru);
        initView();
        initData();
        return getContentView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) getContentView().findViewById(R.id.f_shouru_recycler);
        tvData = (TextView) getContentView().findViewById(R.id.f_shouru_data);
        tvZhanghu = (TextView) getContentView().findViewById(R.id.f_shouru_zhanghu);
        tvSure = (TextView) getContentView().findViewById(R.id.f_shouru_sure);

        tvData.setOnClickListener(this);
        tvZhanghu.setOnClickListener(this);
        tvSure.setOnClickListener(this);


        View headView = View.inflate(getActivity(), R.layout.zhichu_shouru_head, null);
        edName = (EditText) headView.findViewById(R.id.zhichu_name);
        imageName = (ImageView) headView.findViewById(R.id.imag_name);
        txtName = (TextView) headView.findViewById(R.id.txt_name);

        mAdapter = new ZhiChuAdapter(mRecyclerView);
        mAdapter.addHeaderView(headView);

        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 5, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mList = new ArrayList<>();
        mList.add(new ZhiChuModel(R.mipmap.icon_shouru_type_gongzi, "工资"));
        mList.add(new ZhiChuModel(R.mipmap.icon_shouru_type_shenghuofei, "生活费"));
        mList.add(new ZhiChuModel(R.mipmap.icon_shouru_type_hongbao, "红包"));
        mList.add(new ZhiChuModel(R.mipmap.icon_shouru_type_linghuaqian, "零花钱"));
        mList.add(new ZhiChuModel(R.mipmap.icon_shouru_type_jianzhiwaikuai, "外快兼职"));
        mList.add(new ZhiChuModel(R.mipmap.icon_shouru_type_touzishouru, "投资收入"));
        mList.add(new ZhiChuModel(R.mipmap.icon_shouru_type_jiangjin, "奖金"));
        mList.add(new ZhiChuModel(R.mipmap.icon_zhichu_type_baoxiaozhang, "报销"));
        mList.add(new ZhiChuModel(R.mipmap.xianjin, "现金"));
        mList.add(new ZhiChuModel(R.mipmap.tuikuan, "退款"));
        mList.add(new ZhiChuModel(R.mipmap.zhifubao, "支付宝"));
        mList.add(new ZhiChuModel(R.mipmap.icon_shouru_type_qita, "其他"));
        mAdapter.setData(mList);
        mAdapter.addLastItem(new ZhiChuModel(R.mipmap.icon_add, "更多"));
        mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {

                if (mAdapter.getItemCount() - 1 == position) {
                    ToastUtils.showToast(getActivity(), "改功能正在完善中");
                } else {
                    txtName.setText(mList.get(position).getName());
                    imageName.setImageResource(mList.get(position).getUrl());
                    AccountConsumeType = mList.get(position).getName();
                    AccountUrl = mList.get(position).getUrl();
                }

            }
        });
    }

    private void initData() {

    }


    @Override
    public void onClick(View v) {
        if (v == edName) {

        } else if (v == tvZhanghu) {
            showZhanghu();
            if (window.isShowing()) {
                window.dismiss();
            } else {
                window.showAtLocation(tvZhanghu, Gravity.BOTTOM, 0, 0);
                backgroundAlpha(0.5f);
            }
        } else if (v == tvData) {
            showData();
        } else if (v == tvSure) {
            doSure();
        }
    }

    private void doSure() {
        homListData = new ArrayList<>();
        String mEdName = edName.getText().toString();
        if (TextUtils.isEmpty(mEdName.trim())) {
            ToastUtils.showToast(getActivity(), "请输入金额");
            return;
        }
        if (tvZhanghu.getText().toString().equals("账户")) {
            ToastUtils.showToast(getActivity(), "请选择账户");
            return;
        }

        if (tvData.getText().toString().equals("选择日期")) {
            ToastUtils.showToast(getActivity(), "请选择日期");
        }

        homListData.add(new AccountModel(AccountType, AccountData, Double.parseDouble(mEdName), AccountConsumeType, AccountUrl, DateTimeUtil.getCurrentTime_Today(), Config.SHOU_RU));
        RxBus.getInstance().post("AccountModel", homListData);
        getActivity().finish();
    }


    private void showZhanghu() {
        View popView = View.inflate(getActivity(), R.layout.pop_zhanghu_dialog, null);
        mListView = (ListView) popView.findViewById(R.id.lv_popup_list);
        window = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mPopWindowAdapter = new PopWindowAdapter(getActivity(), R.layout.item_pop_account);
        if (DaoChoiceAccount.query().size() != 0 || DaoChoiceAccount.query() != null) {
            mPopWindowAdapter.setData(DaoChoiceAccount.query());
        }
        mListView.setAdapter(mPopWindowAdapter);
        mPopWindowAdapter.removeItem(mPopWindowAdapter.getLastItem());
        mPopWindowAdapter.removeItem(mPopWindowAdapter.getItem(4));
        window.setFocusable(true);
        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.setAnimationStyle(R.style.AnimBottom);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                window.dismiss();
                AccountType = mPopWindowAdapter.getItem(position).getAccountName();
                tvZhanghu.setText(AccountType);
            }
        });

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
     * 设置添加屏幕的背景透明
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }


    public void showData() {
        //时间选择器
        pvTime = new TimePickerView(getActivity(), TimePickerView.Type.YEAR_MONTH_DAY);
        //设置标题
        pvTime.setTitle("选择日期");
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 30);
        pvTime.setTime(new Date());
        //设置是否循环
        pvTime.setCyclic(false);
        //设置是否可以关闭
        pvTime.setCancelable(true);
        //设置选择监听
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                tvData.setText(DateTimeUtil.getYearMonthDay_(date));
                AccountData = DateTimeUtil.getYearMonthDay_(date);
            }
        });
        //显示
        pvTime.show();
    }

//    private void showData() {
//        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
//        dialog.show();
//        DatePicker picker = new DatePicker(getActivity());
//        picker.setDate(Integer.parseInt(DateTimeUtil.getShowCurrentTime()[0]), Integer.parseInt(DateTimeUtil.getShowCurrentTime()[1]));
//        picker.setMode(DPMode.SINGLE);
//        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
//            @Override
//            public void onDatePicked(String date) {
//                tvData.setText(date);
//                AccountData = date;
//                dialog.dismiss();
//            }
//        });
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setContentView(picker, params);
//        dialog.getWindow().setGravity(Gravity.CENTER);
//    }
}
