package star.liuwen.com.cash_books.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.bingoogolapple.androidcommon.adapter.BGADivider;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewScrollHelper;
import star.liuwen.com.cash_books.Adapter.ChoiceIssuingBankAdapter;
import star.liuwen.com.cash_books.Base.BaseFragment;
import star.liuwen.com.cash_books.Enage.DataEnige;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.widget.IndexView;

/**
 * Created by liuwen on 2017/2/15.
 */
public class P2PFragment extends BaseFragment {
    private RecyclerView mRecyclerView;

    private IndexView mIndexView;
    private TextView mTextView;
    private ChoiceIssuingBankAdapter mAdapter;
    private BGARecyclerViewScrollHelper mRecyclerViewScrollHelper;
    private LinearLayoutManager mLayoutManager;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_p2p);
        initView();
        initData();
        return getContentView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) getContentView().findViewById(R.id.p2p_recyclerView);
        mIndexView = (IndexView) getContentView().findViewById(R.id.choice_indexView);
        mTextView = (TextView) getContentView().findViewById(R.id.choice_txt_tip);

        mAdapter = new ChoiceIssuingBankAdapter(mRecyclerView);


        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Intent intent = new Intent();
                intent.putExtra("bank", mAdapter.getItem(position).name);
                getActivity().setResult(0, intent);
                getActivity().finish();
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

                        return mAdapter.getItem(position).topc;
                    }

                    @Override
                    protected int getFirstVisibleItemPosition() {
                        return mLayoutManager.findFirstVisibleItemPosition();
                    }
                }));

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter.setData(DataEnige.getP2PData());
        mRecyclerView.setAdapter(mAdapter);
    }


    private void initData() {

    }


}
