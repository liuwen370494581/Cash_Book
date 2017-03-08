package star.liuwen.com.cash_books.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Map;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.bean.AccountModel;

/**
 * Created by liuwen on 2017/1/9.
 */
public class HomeAdapter extends BGARecyclerViewAdapter<AccountModel> {

    public HomeAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_home);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, AccountModel model) {

        if (model.getZhiChuShouRuType().equals(Config.ZHI_CHU)) {
            helper.setVisibility(R.id.re_zhichu, View.VISIBLE);
            helper.setVisibility(R.id.re_shouru, View.GONE);
            helper.setText(R.id.item_h_txt_xiaofei_type, "消费类型：" + model.getAccountType());
            helper.setText(R.id.item_h_txt_zhichu_type, model.getConsumeType() + ":");
            helper.setText(R.id.item_h_txt_zhichu, String.format("%.2f", model.getMoney()));
            helper.setText(R.id.item_h_txt_zhichu_time, DateTimeUtil.getShowCurrentTime(model.getTimeMinSec()));
            helper.setImageResource(R.id.item_h_image_zhichu, model.getUrl());
            if (needTitle(position)) {
                helper.setVisibility(R.id.item_h__txt_data, View.VISIBLE);
                helper.setText(R.id.item_h__txt_data, model.getData());
                helper.setVisibility(R.id.view_1, View.VISIBLE);
                helper.setVisibility(R.id.itme_h__img_data, View.VISIBLE);
            }
        } else {
            helper.setVisibility(R.id.re_shouru, View.VISIBLE);
            helper.setVisibility(R.id.re_zhichu, View.GONE);
            helper.setText(R.id.item_h_txt_chucun_type, "存款类型：" + model.getAccountType());
            helper.setText(R.id.item_h_txt_shouru_type, model.getConsumeType() + ":");
            helper.setText(R.id.item_h_txt_shouru, String.format("%.2f", model.getMoney()));
            helper.setText(R.id.item_h_txt_shouru_time, DateTimeUtil.getShowCurrentTime(model.getTimeMinSec()));
            helper.setImageResource(R.id.item_h_image_shouru, model.getUrl());
            if (needTitle(position)) {
                helper.setVisibility(R.id.item_h__txt_data, View.VISIBLE);
                helper.setText(R.id.item_h__txt_data, model.getData());
                helper.setVisibility(R.id.view_1, View.VISIBLE);
                helper.setVisibility(R.id.itme_h__img_data, View.VISIBLE);
            }
        }
    }

    private boolean needTitle(int position) {
        if (position == 0) {
            return true;
        }
        if (position < 0) {
            return false;
        }
        AccountModel currentModel = getItem(position);
        AccountModel previousModel = getItem(position - 1);
        if (currentModel == null || previousModel == null) {
            return false;
        }
        String currentDate = currentModel.getData();
        String previousDate = previousModel.getData();
        if (currentDate.equals(previousDate)) {
            return false;
        }
        return true;
    }
}
