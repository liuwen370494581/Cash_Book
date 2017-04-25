package star.liuwen.com.cash_books.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import star.liuwen.com.cash_books.Base.BaseFragment;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.View.ScoreTrend;

/**
 * Created by liuwen on 2017/3/30.
 */
public class TrendFragment extends BaseFragment implements View.OnClickListener {
    private ScoreTrend scoreTrend;
    int score[] = new int[12];
    private RecyclerView mRecyclerView;
    private ViewStub mViewStub;
    private RelativeLayout reChoiceDate;
    private TextView txtChoiceDate;
    private TimePickerView pvTime;


    @Override
    public void lazyInitData() {

    }

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
        scoreTrend = (ScoreTrend) getContentView().findViewById(R.id.scoreTrend);
        txtChoiceDate = (TextView) headView.findViewById(R.id.f_re_txt);
        reChoiceDate = (RelativeLayout) headView.findViewById(R.id.f_re_choicedate);
        reChoiceDate.setOnClickListener(this);
        txtChoiceDate.setText(DateTimeUtil.getCurrentMonth());//只要获取到月份

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

    @Override
    public void onClick(View v) {
        if (v == reChoiceDate) {
            toDate();
        }
    }

    private void toDate() {
        //时间选择器
        pvTime = new TimePickerView(getActivity(), TimePickerView.Type.YEAR_MONTH);
        //设置标题
        pvTime.setTitle("选择日期");
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 30);
        pvTime.setTime(new Date());
        //设置是否循环
        pvTime.setCyclic(false);
        //设置是否可以关闭
        pvTime.setCancelable(true);
        //设置选择监听
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                txtChoiceDate.setText(DateTimeUtil.getTime(date));
            }
        });
        //显示
        pvTime.show();
    }
}
