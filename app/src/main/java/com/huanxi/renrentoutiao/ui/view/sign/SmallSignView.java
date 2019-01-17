package com.huanxi.renrentoutiao.ui.view.sign;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;

/**
 * Created by Dinosa on 2018/3/31.
 */

public class SmallSignView extends FrameLayout {

    private CheckBox mCbImg;
    private CheckBox mCbText;
    private TextView mTvSignStatueText;

    public SmallSignView(@NonNull Context context) {
        this(context, null);
    }

    public SmallSignView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmallSignView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View.inflate(getContext(), R.layout.view_new_sign, this);

        mCbImg = ((CheckBox) findViewById(R.id.cb_award_bg));
        mCbText = ((CheckBox) findViewById(R.id.cb_award_text));
        mTvSignStatueText = ((TextView) findViewById(R.id.tv_text_indicator));
    }

    /**
     * 设置状态
     * @param isChecked
     */
    public void setChecked(boolean isChecked) {
        mCbImg.setChecked(isChecked);
        mCbText.setChecked(isChecked);
        if(isChecked){
            mTvSignStatueText.setText("已签到");
        }
    }

    /**
     * 设置奖励的钱数
     * @param awardText
     */
    public void setAwardText(String awardText) {
        mCbText.setText(awardText);
    }

    /**
     * 设置天数
     * @param indicatorText
     */
    public void setIndicatorText(String indicatorText) {
        mTvSignStatueText.setText(indicatorText);
    }

}
