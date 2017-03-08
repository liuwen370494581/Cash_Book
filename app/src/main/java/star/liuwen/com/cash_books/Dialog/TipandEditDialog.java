package star.liuwen.com.cash_books.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import star.liuwen.com.cash_books.R;

/**
 * Created by liuwen on 2017/2/9.
 */
public class TipandEditDialog extends Dialog implements View.OnClickListener {
    private String mContent = "";
    private ITipEndEditDialogListener mListener;
    private TextView mTvLeft = null;
    private TextView mTvRight = null;
    private TextView mSLine = null;

    private TextView mTxtContent;
    private RelativeLayout mReLayout;
    private EditText mEditText;

    public TipandEditDialog(Context context, String content) {
        super(context, R.style.CustomDialogStyle);
        this.mContent = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loginout);
        mTxtContent = (TextView) findViewById(R.id.dialog_tv);
        mTxtContent.setText(mContent);
        mSLine = (TextView) findViewById(R.id.line_s);
        mReLayout = (RelativeLayout) findViewById(R.id.layout);

        mTvLeft = (TextView) findViewById(R.id.dialog_left);
        mTvRight = (TextView) findViewById(R.id.dialog_right);
        mEditText = (EditText) findViewById(R.id.edittext_dialog_content_et);
        mTvLeft.setOnClickListener(this);
        mTvRight.setOnClickListener(this);
    }


    public void setEtHint(String text) {
        mEditText.setHint(text);
    }

    public void setInputName(String text) {
        mEditText.setText(text);
    }

    public void setLayoutVisible() {
        mReLayout.setVisibility(View.VISIBLE);
    }

    public interface ITipEndEditDialogListener {
        void ClickLeft();

        void ClickRight();
    }

    /**
     * 暴露接口 给外面方便调用
     *
     * @param listener
     */
    public void setListener(ITipEndEditDialogListener listener) {
        mListener = listener;
    }


    @Override
    public void onClick(View v) {
        if (v == mTvLeft) {
            if (mListener != null) {
                mListener.ClickLeft();
            }
            dismiss();
        } else if (v == mTvRight) {
            if (mListener != null) {
                mListener.ClickRight();
            }
            dismiss();
        }
    }

    public void setRightButtonVisible(boolean isShow) {
        mTvLeft.setBackgroundResource(R.drawable.btn_radius_bottom_selector);
        mTvRight.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mSLine.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void setLeftText(String text) {
        if (mTvLeft != null) {
            mTvLeft.setText(text);
        }

    }

    public void setRightText(String text) {
        if (mTvRight != null) {
            mTvRight.setText(text);
        }
    }

    public void setLeftTextColor(int resID) {
        if (mTvLeft != null) {
            mTvLeft.setTextColor(resID);
        }
    }

    public void setRightTextColor(int resID) {
        if (mTvRight != null) {
            mTvRight.setTextColor(resID);
        }
    }

}
