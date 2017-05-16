package star.liuwen.com.cash_books.Activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoChoiceAccount;
import star.liuwen.com.cash_books.Enage.DataEnige;
import star.liuwen.com.cash_books.EventBus.C;
import star.liuwen.com.cash_books.EventBus.Event;
import star.liuwen.com.cash_books.EventBus.EventBusUtil;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.bean.ChoiceAccount;
import star.liuwen.com.cash_books.bean.ColorModel;

/**
 * Created by liuwen on 2017/5/10.
 */
public class ChoiceAccountColorActivity extends BaseActivity implements BGAOnRVItemClickListener {

    private RecyclerView mRecyclerView;
    private ImageView imgCardUrl;
    private TextView txtCardName, txtCardYuer, txtCardMoney;
    private RelativeLayout reChoiceColorBg;
    private ChoiceColorAdapter mAdapter;
    private ChoiceAccount model;
    private List<ColorModel> mList;
    private int color_show;


    @Override
    public int activityLayoutRes() {
        return R.layout.choice_account_color_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setTitle("选择颜色");
        setLeftText(getString(R.string.back));
        setLeftImage(R.mipmap.fanhui_lan);

        mRecyclerView = (RecyclerView) findViewById(R.id.choice_color_recycler_view);
        imgCardUrl = (ImageView) findViewById(R.id.qb_image_xinyka);
        txtCardName = (TextView) findViewById(R.id.qb_txt_xinyka);
        txtCardYuer = (TextView) findViewById(R.id.qb_txt_xinyka_yuer);
        txtCardMoney = (TextView) findViewById(R.id.xinyka_jia);
        reChoiceColorBg = (RelativeLayout) findViewById(R.id.qb_ry_xinyka);

        model = (ChoiceAccount) getIntent().getExtras().getSerializable(Config.ModelWallet);
        if (model != null) {
            color_show = model.getColor();
            imgCardUrl.setImageResource(model.getUrl());
            reChoiceColorBg.setBackgroundResource(model.getColor());
            txtCardName.setText(model.getAccountName());
            txtCardMoney.setText(String.format("%.2f", model.getMoney()));
            if (model.getMAccountType().equals(Config.XYK)) {
                txtCardYuer.setText(String.format("%.2f", model.getMoney() - model.getDebt()) + "額度");
            } else {
                txtCardYuer.setText(model.getAccountName() + "額度");
            }
        }

        mAdapter = new ChoiceColorAdapter(mRecyclerView);
        mList = DataEnige.getColorData();
        GridLayoutManager manager = new GridLayoutManager(this, 9, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mAdapter.setData(mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnRVItemClickListener(this);
        mAdapter.setShowGouColor(color_show);
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        reChoiceColorBg.setBackgroundResource(mList.get(position).getColor());
        color_show = mList.get(position).getColor();
        //显示选中的账户的颜色
        mAdapter.setShowGouColor(color_show);
        updateChoiceAccountColor();
    }

    private void updateChoiceAccountColor() {
        ChoiceAccount choiceModel = DaoChoiceAccount.queryByAccountId(model.getId()).get(0);
        choiceModel.setColor(color_show);
        DaoChoiceAccount.updateAccount(choiceModel);
        EventBusUtil.sendEvent(new Event(C.EventCode.ChoiceColorToPaySettingAndPayShowAndWalletFragment, color_show));

    }


    private class ChoiceColorAdapter extends BGARecyclerViewAdapter<ColorModel> {
        private int color_show;

        public ChoiceColorAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_choice_color);
        }


        protected void setShowGouColor(int color) {
            this.color_show = color;
            mAdapter.notifyDataSetChanged();
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, ColorModel model) {
            helper.setBackgroundColorRes(R.id.re_choice_color, model.getColor());
            if (model.getColor() == color_show) {
                helper.setVisibility(R.id.txt_choice_color_gou, View.VISIBLE);
            } else {
                helper.setVisibility(R.id.txt_choice_color_gou, View.GONE);
            }
        }
    }
}
