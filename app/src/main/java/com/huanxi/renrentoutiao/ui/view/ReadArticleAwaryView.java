package com.huanxi.renrentoutiao.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;

/**
 * Created by Dinosa on 2018/2/28.
 */

public class ReadArticleAwaryView extends FrameLayout {

    private TextView mTvGoldNumber;
    private TextView mTvReadProgress;

    public ReadArticleAwaryView(@NonNull Context context) {
        this(context,null);
    }

    public ReadArticleAwaryView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ReadArticleAwaryView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_read_article, this);
        mTvGoldNumber = ((TextView) findViewById(R.id.tv_gold_number));
        mTvReadProgress = ((TextView) findViewById(R.id.tv_read_progress));

        Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(),"font/read_award_font.ttf");
        mTvGoldNumber.setTypeface(typeFace);
    }


    public void init(String goldNumber,String strProgress){

        mTvGoldNumber.setText("恭喜您获得"+goldNumber+"金币");
        mTvReadProgress.setText("阅读奖励"+strProgress);

    }


    public void setTitleAndProgress(String title,String progress){

        mTvGoldNumber.setText(title);
        mTvReadProgress.setText(progress);
    }
}
