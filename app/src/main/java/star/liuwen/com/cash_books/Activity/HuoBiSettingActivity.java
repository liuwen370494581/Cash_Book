package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import cn.bingoogolapple.androidcommon.adapter.BGADivider;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Enage.DataEnige;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.bean.IndexModel;

/**
 * Created by liuwen on 2017/2/7.
 */
public class HuoBiSettingActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private HuoBiAdapter mAdapter;

    @Override
    public int activityLayoutRes() {
        return R.layout.huo_bi_setting_activity;
    }

    @Override
    public void initView() {

        setTitle(getString(R.string.huobi_benweibi));
        setLeftText(getString(R.string.back));
        setLeftImage(R.mipmap.fanhui_lan);
        setBackView();

        mRecyclerView = (RecyclerView) findViewById(R.id.huobi_recyclerView);
        mAdapter = new HuoBiAdapter(mRecyclerView);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter.setData(DataEnige.getHuoBiData());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(BGADivider.newShapeDivider());

        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Intent intent = new Intent();
                intent.putExtra(Config.HuoBICh, mAdapter.getItem(position).topc);
                intent.putExtra(Config.HuoBIEn, mAdapter.getItem(position).name);
                setResult(0, intent);
                finish();
            }
        });

    }

    public class HuoBiAdapter extends BGARecyclerViewAdapter<IndexModel> {

        public HuoBiAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_huobi);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, IndexModel model) {
            helper.setText(R.id.itme_txt_huobi, model.topc).setText(R.id.item_txt_huobi_english, model.name);
        }
    }
}
