package star.liuwen.com.cash_books.Activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.bingoogolapple.androidcommon.adapter.BGADivider;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewScrollHelper;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import star.liuwen.com.cash_books.Adapter.ChoiceIssuingBankAdapter;
import star.liuwen.com.cash_books.Adapter.headBankAdapter;
import star.liuwen.com.cash_books.Base.App;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Enage.DataEnige;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.bean.IndexModel;
import star.liuwen.com.cash_books.widget.IndexView;

/**
 * Created by liuwen on 2017/1/16.
 */
public class ChoiceIssuingBankActivity extends BaseActivity {
    private RecyclerView mRecyclerView, headRecyclerView;
    private IndexView mIndexView;
    private TextView mTextView;
    private ChoiceIssuingBankAdapter mAdapter;
    private BGARecyclerViewScrollHelper mRecyclerViewScrollHelper;
    private LinearLayoutManager mLayoutManager;

    private headBankAdapter mHeadBankAdapter;

    @Override
    public int activityLayoutRes() {
        return R.layout.choice_issuing_bank_activity;
    }

    @Override
    public void initView() {

        setBackView();
        setLeftText(getString(R.string.pay_setting));
        setLeftImage(R.mipmap.fanhui_lan);
        setTitle("选择发卡行");

        mRecyclerView = (RecyclerView) findViewById(R.id.choice_recycler_view);
        mIndexView = (IndexView) findViewById(R.id.choice_indexView);
        mTextView = (TextView) findViewById(R.id.choice_txt_tip);

        mAdapter = new ChoiceIssuingBankAdapter(mRecyclerView);

        View headView = View.inflate(this, R.layout.layout_choice_bank_head, null);
        headRecyclerView = (RecyclerView) headView.findViewById(R.id.choice_head_bank_recycler_view);
        TextView headText = (TextView) headView.findViewById(R.id.tv_item_choice_issuing_bank);
        mHeadBankAdapter = new headBankAdapter(headRecyclerView);
        GridLayoutManager manager = new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false);
        headRecyclerView.setLayoutManager(manager);
        mHeadBankAdapter.setData(DataEnige.getHeadBankData());
        headRecyclerView.setAdapter(mHeadBankAdapter);

        mHeadBankAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Intent intent = new Intent();
                intent.putExtra("bank", mHeadBankAdapter.getItem(position).name);
                setResult(0, intent);
                finish();
            }
        });

        mAdapter.addHeaderView(headView);

        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Intent intent = new Intent();
                intent.putExtra("bank", mAdapter.getItem(position).name);
                setResult(0, intent);
                finish();
            }
        });


        mIndexView.setDelegate(new IndexView.Delegate() {
            @Override
            public void onIndexViewSelectedChanged(IndexView indexView, String text) {
                int position = mAdapter.getPositionForCategory(text.charAt(0));
                if (position != -1) {
                    mRecyclerViewScrollHelper.smoothScrollToPosition(position);
                }
            }
        });

        mRecyclerViewScrollHelper = BGARecyclerViewScrollHelper.newInstance(mRecyclerView);
        mIndexView.setTipTv(mTextView);

        mRecyclerView.addItemDecoration(BGADivider.newBitmapDivider()
                .setStartSkipCount(0)
                .setMarginLeftResource(R.dimen.txr_13)
                .setMarginRightResource(R.dimen.txt_19)
                .setDelegate(new BGADivider.SuspensionCategoryDelegate() {
                    @Override
                    protected boolean isCategory(int position) {
                        return mAdapter.isCategory(position);
                    }

                    @Override
                    protected String getCategoryName(int position) {
                        if (position == 0) {
                            return "常用";
                        } else {
                            return mAdapter.getItem(position).topc;
                        }
                    }

                    @Override
                    protected int getFirstVisibleItemPosition() {
                        return mLayoutManager.findFirstVisibleItemPosition();
                    }
                }));

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter.setData(DataEnige.getXykData());
        mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
    }

}
