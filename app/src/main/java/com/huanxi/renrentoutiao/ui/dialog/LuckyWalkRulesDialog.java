package com.huanxi.renrentoutiao.ui.dialog;

import android.content.Context;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;

import butterknife.OnClick;

/**
 * Created by Dinosa on 2018/4/19.
 */

public class LuckyWalkRulesDialog extends BaseDialog {
    private final String mRules;

    public LuckyWalkRulesDialog(Context context, String rules) {
        super(context);
        mRules = rules;
        initView();
    }

    void initView() {
        setContentView(R.layout.dialog_lucky_walk_game_rule);
        ((TextView) findViewById(R.id.tv_game_rules)).setText(mRules);
    }

    @OnClick({R.id.iv_close, R.id.bt_close})
    void close() {
        dismiss();
    }
}
