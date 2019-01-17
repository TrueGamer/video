package com.huanxi.renrentoutiao.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.utils.UIUtils;

import butterknife.ButterKnife;


/**
 * Created by zd on 16/6/21.
 */
public abstract class BaseDialog extends Dialog {


    View mView;
    Context mContext;

    public View getView() {
        return mView;
    }

    public BaseDialog(Context context) {
        super(context, R.style.dialog);
        this.mContext = context;
    }

    public BaseDialog(Context context, boolean fromButton) {
        super(context, R.style.Dialog_NoTitle);
        if(fromButton) {
            fromBottom();
        }
        this.mContext = context;
    }


    @Override
    public void setContentView(@LayoutRes int resource) {
        mView = LayoutInflater.from(mContext).inflate(resource, null);
        //设置SelectPicPopupWindow的View
        setContentView(mView);
        ButterKnife.bind(this);
        //设置全屏
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    public void fromBottom() {
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        localLayoutParams.gravity = Gravity.BOTTOM | Gravity.LEFT;
        localLayoutParams.width = UIUtils.getScreenWidth(getContext());
        localLayoutParams.y = 0;
        localLayoutParams.x = 0;
        onWindowAttributesChanged(localLayoutParams);
    }
}
