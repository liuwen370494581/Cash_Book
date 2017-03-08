package star.liuwen.com.cash_books.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.bean.AccountModel;

/**
 * Created by liuwen on 2017/3/2.
 */
public class ReportsDetailAdapter extends BGARecyclerViewAdapter<AccountModel> {


    public ReportsDetailAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_reports_detail);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, AccountModel model) {
        if (needTitle(position)) {
            helper.setVisibility(R.id.item_data, View.VISIBLE);
            helper.setText(R.id.item_data, model.getData());
        } else {
            helper.setVisibility(R.id.item_data, View.GONE);
        }
        helper.setImageResource(R.id.item_rd_image, model.getUrl());
        helper.setText(R.id.item_rd_txtName, model.getConsumeType());
        helper.setText(R.id.item_rd_percent, String.format("%.2f", model.getConsumePercent()) + "%");
        helper.setText(R.id.item_rd_money, String.format("%.2f", model.getMoney()) + "元");

    }

    private boolean needTitle(int position) {
        //第一个是分类
        if (position == 0) {
            return true;
        }
        if (position < 0) {
            return false;
        }
        AccountModel currentModel = (AccountModel) getItem(position);
        AccountModel previousModel = (AccountModel) getItem(position - 1);
        if (currentModel == null || previousModel == null) {
            return false;
        }
        String currentData = currentModel.getData();
        String previousData = previousModel.getData();

        // 当前item分类名和上一个item分类名不同，则表示两item属于不同分类
        if (currentData.equals(previousData)) {
            return false;
        }
        return true;
    }
}
