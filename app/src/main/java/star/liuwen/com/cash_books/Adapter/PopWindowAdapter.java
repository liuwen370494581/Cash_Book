package star.liuwen.com.cash_books.Adapter;

import android.content.Context;
import android.view.View;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.bean.ChoiceAccount;

/**
 * Created by liuwen on 2017/1/18.
 */
public class PopWindowAdapter extends BGAAdapterViewAdapter<ChoiceAccount> {

    public PopWindowAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, ChoiceAccount model) {
        if (model.mAccountType.equals(Config.XYK)) {
            helper.setText(R.id.choose_cash_txt, model.getAccountName()).setImageResource(R.id.choose_cash_img, model.getUrl());
            helper.setVisibility(R.id.layout_cash_y, View.GONE);
            helper.setText(R.id.layout_cash_limit, "剩余额度:" + String.format("%.2f", model.getMoney()) + "元");
            helper.setText(R.id.layout_cash_limit_y, "欠款:" + String.format("%.2f", model.getDebt()) + "元");
        } else {
            helper.setText(R.id.choose_cash_txt, model.getAccountName()).setImageResource(R.id.choose_cash_img, model.getUrl())
                    .setText(R.id.layout_cash_y, "余额(元):" + String.format("%.2f", model.getMoney()));
            helper.setVisibility(R.id.layout_cash_limit, View.GONE).setVisibility(R.id.layout_cash_limit_y, View.GONE);

        }

    }
}