package star.liuwen.com.cash_books.Adapter;

import android.content.Context;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.bean.DiscussModel;

/**
 * Created by liuwen on 2017/5/23.
 */
public class PopWindowDiscussAdapter extends BGAAdapterViewAdapter<DiscussModel> {


    public PopWindowDiscussAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, DiscussModel model) {
        helper.setImageResource(R.id.item_rd_image, model.getUserUrl()).setText(
                R.id.item_rd_txtName, model.getUserName()).setText(R.id.item_rd_discuss
                , model.getUserDiscuss());
    }
}
