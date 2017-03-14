package star.liuwen.com.cash_books.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.leakcanary.RefWatcher;

import star.liuwen.com.cash_books.Dao.DaoChoiceAccount;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.SharedPreferencesUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.bean.ChoiceAccount;

/**
 * Created by liuwen on 2016/12/28.
 */
public class BaseFragment extends Fragment {
    private RelativeLayout mTitle;

    private View view;
    private LayoutInflater inflater;
    private ViewGroup container;


    public View setContentView(int resourceId) {
        view = inflater.inflate(resourceId, container, false);
        return view;
    }

    public View getContentView() {
        return this.view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
//        mTitle = (RelativeLayout) getContentView().findViewById(R.id.titleBar);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    public void setTitleBg(int resId) {
        if (mTitle != null) {
            mTitle.setBackgroundResource(resId);
            mTitle.getBackground().setAlpha(255);// 不透明
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToastUtils.removeToast();
        RefWatcher refWatcher = App.getRefWatcher(getActivity());
        refWatcher.watch(getActivity());
    }

    /**
     * 设置显示右侧返回按钮
     */
    public void setBackView() {
        View backView = view.findViewById(R.id.back_view);
        if (backView == null) {
            return;
        }
        backView.setVisibility(View.VISIBLE);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    /**
     * 设置显示标题
     *
     * @param txt
     */
    public void setTitle(String txt) {
        TextView title = (TextView) view.findViewById(R.id.title);
        if (title == null) {
            return;
        }
        title.setVisibility(View.VISIBLE);
        title.setText(txt);
    }

    /**
     * 只显示右侧文字以及点击事件
     *
     * @param txt
     * @param onClickListener
     */
    public void setRightText(String txt, View.OnClickListener onClickListener) {
        TextView toolbar_righ_tv = (TextView) view.findViewById(R.id.toolbar_righ_tv);
        if (toolbar_righ_tv == null) {
            return;
        }
        ImageView toolbar_righ_iv = (ImageView) view.findViewById(R.id.toolbar_righ_iv);
        if (toolbar_righ_iv == null) {
            return;
        }
        toolbar_righ_iv.setVisibility(View.GONE);
        toolbar_righ_tv.setVisibility(View.VISIBLE);
        toolbar_righ_tv.setText(txt);
        toolbar_righ_tv.setOnClickListener(onClickListener);
    }

    /**
     * 右侧只显示一个图片
     *
     * @param resId
     * @param onClickListener
     */
    public void setRightImage(int resId, View.OnClickListener onClickListener) {
        TextView toolbar_righ_tv = (TextView) view.findViewById(R.id.toolbar_righ_tv);
        if (toolbar_righ_tv == null) {
            return;
        }
        toolbar_righ_tv.setVisibility(View.GONE);
        ImageView toolbar_righ_iv = (ImageView) view.findViewById(R.id.toolbar_righ_iv);
        if (toolbar_righ_iv == null) {
            return;
        }
        toolbar_righ_iv.setVisibility(View.VISIBLE);
        toolbar_righ_iv.setImageResource(resId);
        toolbar_righ_iv.setOnClickListener(onClickListener);
    }

    /**
     * 显示文字和图片，可以设置文字内容及字体颜色，图片资源
     *
     * @param txt
     * @param txtColor
     * @param resId
     * @param onClickListener
     */
    public void setRightTextAndImage(String txt, int txtColor, int resId, View.OnClickListener onClickListener) {
        TextView toolbar_righ_tv = (TextView) view.findViewById(R.id.toolbar_righ_tv);
        if (toolbar_righ_tv == null) {
            return;
        }
        toolbar_righ_tv.setVisibility(View.VISIBLE);
        toolbar_righ_tv.setTextColor(txtColor);

        ImageView toolbar_righ_iv = (ImageView) view.findViewById(R.id.toolbar_righ_iv);
        if (toolbar_righ_iv == null) {
            return;
        }
        toolbar_righ_iv.setVisibility(View.VISIBLE);
        toolbar_righ_iv.setImageResource(resId);

        toolbar_righ_iv.setOnClickListener(onClickListener);
        toolbar_righ_tv.setOnClickListener(onClickListener);
    }
}
