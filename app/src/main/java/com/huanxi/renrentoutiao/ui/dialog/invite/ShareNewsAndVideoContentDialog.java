package com.huanxi.renrentoutiao.ui.dialog.invite;

import android.app.Activity;
import android.view.View;

import com.huanxi.renrentoutiao.R;

/**
 * Created by Dinosa on 2018/4/12.
 */

public class ShareNewsAndVideoContentDialog extends InviteFriendShareDialog {

    public ShareNewsAndVideoContentDialog(Activity context, OnClickShareType onClickShareType,String uid) {
        super(context, onClickShareType,uid);

        mShareDialogView.findViewById(R.id.ll_qq_share_icon).setVisibility(View.GONE);
    }
}
