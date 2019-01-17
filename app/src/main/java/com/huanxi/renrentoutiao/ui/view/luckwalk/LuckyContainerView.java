package com.huanxi.renrentoutiao.ui.view.luckwalk;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.utils.UIUtils;

import java.util.List;


/**
 * Created by ZD on 2017/7/4.
 */

public class LuckyContainerView extends RelativeLayout {
    TextView gold, queen;

    public LuckyContainerView(Context context) {
        super(context);
//        initRefreshableListView();
    }

    public LuckyContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        initRefreshableListView();
    }

    public LuckyContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        initRefreshableListView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initView();
    }

    void initView() {
        addMyGoldView(null);
        addMarqueeView(null);
    }

    //添加显示我的金币的view
    public void addMyGoldView(String txt) {
        try {
            if (gold != null)
                gold.setText(TextUtils.isEmpty(txt) ? gold.getText() : ("我的金币:" + txt));
            else {
                gold = new TextView(getContext());
                addView(gold);
                gold.setGravity(Gravity.CENTER);
                gold.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                gold.setText("我的金币:" + (TextUtils.isEmpty(txt) ? "0" : txt));
                gold.setTextColor(ContextCompat.getColor(getContext(), R.color.color_ffffff));
                gold.setBackgroundResource(R.drawable.turn_gold_border);
            }
            int paddingTB = UIUtils.dip2px(getContext(), 2);
            int paddingLR = UIUtils.dip2px(getContext(), 5);
            gold.setPadding(paddingLR, paddingTB, paddingLR, paddingTB);
            LayoutParams layoutParams = (LayoutParams) gold.getLayoutParams();
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.topMargin = (int) (getHeight() * 0.04);

            gold.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    //getContext().startActivity(GoldManagerActivity.getGoldManagerIntent(getContext(),true));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMarqueeView(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        if (strs != null)
            for (int i = 0; i < strs.size(); i++) {
                sb.append(strs.get(i));
                sb.append("    ");
            }
        setFocusable(true);
        try {
            if (queen != null)
                queen.setText(sb.toString());
            else {
                queen = new TextView(getContext());
                queen.setMarqueeRepeatLimit(-1);
                addView(queen);
                queen.setHorizontallyScrolling(true);
                queen.setMaxLines(1);
                queen.setSingleLine();
                queen.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                queen.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                queen.setGravity(Gravity.CENTER);
                queen.setText(sb.toString());
                queen.setTextColor(ContextCompat.getColor(getContext(), R.color.color_ffffff));
            }
            LayoutParams layoutParams = (LayoutParams) queen.getLayoutParams();
            layoutParams.topMargin = (int) (getHeight() * 0.68);
            layoutParams.height = (int) (getHeight() * (0.73 - 0.68));
            layoutParams.width = (int) (getWidth() * (0.8 - 0.2));
            layoutParams.leftMargin = (int) (getWidth() * 0.2);
            queen.setDuplicateParentStateEnabled(true);
            queen.setFocusable(true);
            queen.setClickable(true);
            queen.setFocusableInTouchMode(true);
            queen.isFocused();
            queen.requestFocus();
        } catch (Exception e) {

        }
    }

}
