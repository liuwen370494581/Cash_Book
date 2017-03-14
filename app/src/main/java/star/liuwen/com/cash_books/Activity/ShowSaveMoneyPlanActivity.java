
package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoSaveMoneyPlan;
import star.liuwen.com.cash_books.Dialog.TipandEditDialog;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.RxBus.RxBusResult;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.View.NumberAnimTextView;
import star.liuwen.com.cash_books.bean.PlanSaveMoneyModel;
import star.liuwen.com.cash_books.bean.SaveMoneyPlanModel;

/**
 * Created by liuwen on 2017/2/15.
 */
public class ShowSaveMoneyPlanActivity extends BaseActivity implements BGAOnRVItemClickListener, BGAOnRVItemLongClickListener {
    private TextView txtName, txtTime;
    private ImageView url;
    private RecyclerView mRecyclerView;
    private SeekBar mSeekBar;
    private SaveMoneyPlanAdapter mAdapter;
    private List<SaveMoneyPlanModel> mList = new ArrayList<>();
    private NumberAnimTextView txtPercent, txtMoney, txtFinishPercent;
    private double add = 0;
    private SaveMoneyPlanModel mPlanModel;

    @Override
    public int activityLayoutRes() {
        return R.layout.show_save_money_plan_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setTitle(getString(R.string.save_money_plan));
        setLeftImage(R.mipmap.fanhui_lan);
        setLeftText(getString(R.string.back));

        txtName = (TextView) findViewById(R.id.show_money_txt_name);
        txtTime = (TextView) findViewById(R.id.show_money_txt_time);
        txtMoney = (NumberAnimTextView) findViewById(R.id.show_money_plan);
        txtPercent = (NumberAnimTextView) findViewById(R.id.show_money_precent);
        txtFinishPercent = (NumberAnimTextView) findViewById(R.id.show_money_finish_percent);

        url = (ImageView) findViewById(R.id.show_money_image_url);
        mSeekBar = (SeekBar) findViewById(R.id.id_seekbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.show_money_recyclerView);
        initData();

        PlanSaveMoneyModel model = (PlanSaveMoneyModel) getIntent().getExtras().getSerializable(Config.PlanSaveMoneyModel);
        if (model != null) {
            url.setImageResource(model.getUrl());
        }

        mSeekBar.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.seek_bar_progress_bg));
        mSeekBar.setEnabled(false);
        mSeekBar.setClickable(false);

        mAdapter = new SaveMoneyPlanAdapter(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnRVItemClickListener(this);
        mAdapter.setOnRVItemLongClickListener(this);

        if (DaoSaveMoneyPlan.querySaveMoney() != null) {
            mList = DaoSaveMoneyPlan.querySaveMoney();
            mAdapter.addMoreData(mList);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setData(mList);
            mRecyclerView.setAdapter(mAdapter);
        }

        Bitmap bt = BitmapFactory.decodeFile(Config.RootPath + "TargetBg.jpg");
        if (bt != null) {
            url.setImageBitmap(bt);
        }


        if (DaoSaveMoneyPlan.querySaveMoney() != null) {
            for (SaveMoneyPlanModel planModel : DaoSaveMoneyPlan.querySaveMoney()) {
                mSeekBar.setProgress(planModel.getAddPercent());
                txtFinishPercent.setNumberString(planModel.getFinishPercent());
                txtPercent.setNumberString(planModel.getAddSaveMoney());
            }
        }
        txtName.setText(SharedPreferencesUtil.getStringPreferences(this, Config.PlanRemark, "").isEmpty() ? getString(R.string.no_setting) : SharedPreferencesUtil.getStringPreferences(this, Config.PlanRemark, ""));
        txtTime.setText(SharedPreferencesUtil.getStringPreferences(this, Config.PlanFinishTime, "").isEmpty() ? getString(R.string.no_setting) : SharedPreferencesUtil.getStringPreferences(this, Config.PlanFinishTime, ""));
        txtMoney.setNumberString(SharedPreferencesUtil.getStringPreferences(this, Config.PlanMoney, "").isEmpty() ? getString(R.string.ling) : SharedPreferencesUtil.getStringPreferences(this, Config.PlanMoney, ""));
    }

    private void initData() {
        RxBus.getInstance().toObserverableOnMainThread(Config.ModelSaveAPen, new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                mList = (List<SaveMoneyPlanModel>) o;
                mAdapter.addMoreData(mList);
                for (int i = 0; i < mList.size(); i++) {
                    add = add + mList.get(i).getSaveMoney();
                    mPlanModel = mList.get(i);
                }

                String money = SharedPreferencesUtil.getStringPreferences(ShowSaveMoneyPlanActivity.this, Config.PlanMoney, "");
                mSeekBar.setProgress(new Double((add / Double.parseDouble(money)) * 100).intValue());
                txtPercent.setNumberString(String.format("%.2f", add));
                txtFinishPercent.setNumberString(String.format("%.2f", (add / Double.parseDouble(money)) * 100) + "%");
                mPlanModel.setFinishPercent(String.format("%.2f", (add / Double.parseDouble(money)) * 100) + "%");
                mPlanModel.setAddSaveMoney(String.format("%.2f", add));
                mPlanModel.setAddPercent(new Double((add / Double.parseDouble(money)) * 100).intValue());
                mPlanModel.setAdd(add);
                mPlanModel.setId(DaoSaveMoneyPlan.getCount() + 1);
                DaoSaveMoneyPlan.insertSaveMoney(mPlanModel);
                mRecyclerView.setAdapter(mAdapter);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().removeObserverable(Config.ModelSaveAPen);
    }

    public void Save(View view) {
        startActivity(new Intent(this, SaveAPenActivity.class));
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {

    }

    @Override
    public boolean onRVItemLongClick(ViewGroup parent, View itemView, final int position) {
        TipandEditDialog dialog = new TipandEditDialog(this, "确定要删除吗");
        dialog.show();
        dialog.setLeftText(getString(R.string.cancel));
        dialog.setLeftTextColor(getResources().getColor(R.color.jiechu));
        dialog.setRightText(getString(R.string.sure));
        dialog.setRightTextColor(getResources().getColor(R.color.blue));
        dialog.setListener(new TipandEditDialog.ITipEndEditDialogListener() {
            @Override
            public void ClickLeft() {

            }

            @Override
            public void ClickRight() {
                mAdapter.removeItem(mList.get(position));
                DaoSaveMoneyPlan.deleteSaveMoneyByModel(mList.get(position));
                mAdapter.notifyDataSetChanged();
            }
        });
        return true;
    }


    public class SaveMoneyPlanAdapter extends BGARecyclerViewAdapter<SaveMoneyPlanModel> {
        public SaveMoneyPlanAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_show_save_money_plan);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, SaveMoneyPlanModel model) {
            try {
                String startTime = model.getStartTime();
                String endTime = model.getEndTime();
                Double lixi = ((model.getSaveMoney() * model.getYield()) / 100 * DateTimeUtil.LoadDay(startTime, endTime)) / 365;
                if (needTitle(position)) {
                    helper.setVisibility(R.id.re_head_show, View.VISIBLE);
                    helper.setText(R.id.item_plan_platform, model.getPlatForm()).setText(R.id.item_plan_money, "本金：" + String.format("%.2f", model.getSaveMoney()));
                    helper.setText(R.id.item_plan_lixi, "预计利息：" + String.format("%.2f", lixi));
                } else {
                    helper.setVisibility(R.id.re_head_show, View.GONE);
                }
                helper.setText(R.id.item_plan_starttime, model.getStartTime());
                helper.setText(R.id.item_plan_benjin, String.format("%.2f", model.getSaveMoney())).setText(R.id.item_plan_endtime, model.getEndTime());
                helper.setText(R.id.item_plan_daishoulixi, String.format("%.2f", lixi));
                helper.setVisibility(R.id.item_re_show, View.VISIBLE);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


        private boolean needTitle(int position) {
            //第一个是分类
            if (position == 0) {
                return true;
            }
            if (position < 0) {
                return false;
            }
            SaveMoneyPlanModel currentModel = (SaveMoneyPlanModel) getItem(position);
            SaveMoneyPlanModel previousModel = (SaveMoneyPlanModel) getItem(position - 1);
            if (currentModel == null || previousModel == null) {
                return false;
            }
            String currentData = currentModel.getPlatForm();
            String previousData = previousModel.getPlatForm();

            // 当前item分类名和上一个item分类名不同，则表示两item属于不同分类
            if (currentData.equals(previousData)) {
                return false;
            }
            return true;
        }
    }

}

