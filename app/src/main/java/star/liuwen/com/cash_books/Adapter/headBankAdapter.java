package star.liuwen.com.cash_books.Adapter;

import android.support.v7.widget.RecyclerView;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.bean.IndexModel;

/**
 * Created by liuwen on 2017/1/16.
 */
public class headBankAdapter extends BGARecyclerViewAdapter<IndexModel> {

    public headBankAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_choice_head_bank);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, IndexModel model) {
        helper.setText(R.id.item_head_bank, model.name);
    }
}