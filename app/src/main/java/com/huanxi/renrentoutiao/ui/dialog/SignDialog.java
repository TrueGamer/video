package com.huanxi.renrentoutiao.ui.dialog;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;

/**
 * Created by Dinosa on 2018/4/21.
 */

public class SignDialog extends BaseDialog {
    private final String mCurMoney;
    private final String mGetMoney;
    private TextView mTvCurrentMoney;
    private TextView mTvGetMoney;

    public SignDialog(Context context,String curMoney,String getMoney) {
        super(context);
        mCurMoney = curMoney;
        mGetMoney = getMoney;
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_sign_layout);

        mTvCurrentMoney = ((TextView) findViewById(R.id.tv_current_money));
        mTvGetMoney = ((TextView) findViewById(R.id.tv_get_money));

        mTvCurrentMoney.setText("当前金币"+mCurMoney);

        String content="<font color='#f0283e'><big>+5</big></font>金币";
        mTvGetMoney.setText(Html.fromHtml(content));

        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
