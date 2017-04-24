package star.liuwen.com.cash_books.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import star.liuwen.com.cash_books.Base.BaseFragment;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.View.ScoreTrend;

/**
 * Created by liuwen on 2017/3/30.
 */
public class TrendFragment extends BaseFragment {
    private ScoreTrend scoreTrend;
    int score[] = new int[12];


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.trend_fragment);
        initView();
        initData();
        return getContentView();
    }

    private void initView() {

        View headView = View.inflate(getActivity(), R.layout.head_trendfragment, null);
        scoreTrend = (ScoreTrend) headView.findViewById(R.id.scoreTrend);

        int max = 1250;
        int min = 650;
        scoreTrend.setMaxScore(2000);
        scoreTrend.setMinScore(0);

        Random ramdom = new Random();
        for (int i = 0; i < 12; i++) {
            score[i] = ramdom.nextInt(max) % (max - min + 1) + min;
        }
        scoreTrend.setScore(score);
    }

    private void initData() {

    }
}
