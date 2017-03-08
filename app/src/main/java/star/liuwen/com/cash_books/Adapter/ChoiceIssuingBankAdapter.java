package star.liuwen.com.cash_books.Adapter;

import android.support.v7.widget.RecyclerView;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.bean.IndexModel;

/**
 * Created by liuwen on 2017/1/16.
 */

public class ChoiceIssuingBankAdapter extends BGARecyclerViewAdapter<IndexModel> {

    public ChoiceIssuingBankAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_choice_issuing_bank);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, IndexModel model) {
        helper.setText(R.id.tv_item_choice_issuing_bank, model.name);
    }

    public boolean isCategory(int position) {
        int category = getItem(position).topc.charAt(0);
        return position == getPositionForCategory(category);
    }

    public int getPositionForCategory(int category) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = getItem(i).topc;
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == category) {
                return i;
            }
        }
        return -1;
    }
}
