package com.huanxi.renrentoutiao.ui.fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.ApiConstactUs;
import com.huanxi.renrentoutiao.net.api.ApiHelp;
import com.huanxi.renrentoutiao.net.bean.ResEmpty;
import com.huanxi.renrentoutiao.net.bean.ResHelpBean;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseLoadingFrament;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by Dinosa on 2018/2/8.
 */

public class HelpFragment extends BaseLoadingFrament {


    @BindView(R.id.et_title)
    EditText mEtTitle;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.tv_can_write_num)
    TextView mTvCanWriteNum;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;

    @BindView(R.id.ll_contacts)
    LinearLayout mLlContacts;



    @Override
    public int getLoadingContentLayoutId() {
        return R.layout.fragment_help;
    }

    @Override
    protected void initData() {
        super.initData();
        //这里获取客服信息；
        getContactInfo();
    }

    @Override
    protected void onRetry() {
        super.onRetry();
        getContactInfo();
    }

    @OnClick(R.id.tv_commit)
    public void onClickCommit(){

        checkLogin(new BaseActivity.CheckLoginCallBack() {
            @Override
            public void loginSuccess() {

                if (mEtTitle.getText().toString().trim().length()<=0) {
                    toast("标题不能为空");
                    return;
                }

                if (mEtContent.getText().toString().trim().length()<=0) {
                    toast("意见和建议不能为空");
                    return;
                }

                //这里执行的一个操作就是提交；
                //这里我们做一波提交操作;
                HashMap<String, String> paramsMap = new HashMap<>();

                paramsMap.put(ApiHelp.FROM_UID,getUserBean().getUserid());
                paramsMap.put(ApiHelp.CONTENT,mEtContent.getText().toString());
                paramsMap.put(ApiHelp.TITLE,mEtTitle.getText().toString());

                ApiHelp apiHelp=new ApiHelp(new HttpOnNextListener<ResEmpty>() {
                    @Override
                    public void onNext(ResEmpty resEmpty) {
                        //这里表示提交成功
                        toast("提交成功");
                        getBaseActivity().finish();
                    }
                },getBaseActivity(),paramsMap);

                HttpManager.getInstance().doHttpDeal(apiHelp);

            }

            @Override
            public void loginFaild() {

            }
        });

    }

    public void getContactInfo() {


        ApiConstactUs apiContactInfo=new ApiConstactUs(new HttpOnNextListener<List<ResHelpBean>>() {

            @Override
            public void onNext(List<ResHelpBean> resContactInfo) {
                updateUI(resContactInfo);
                showSuccess();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showFaild();
            }
        },getBaseActivity());

        HttpManager.getInstance().doHttpDeal(apiContactInfo);
    }

    private void updateUI(List<ResHelpBean> resContactInfo) {

        if (resContactInfo != null) {

            for (ResHelpBean s : resContactInfo) {

                View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_contact_item, mLlContacts, false);
                ((TextView) view).setText(s.getType()+" : "+s.getValue());
                mLlContacts.addView(view);

            }
        }

    }

    @OnTextChanged(R.id.et_content)
    public void onTextChange(){
        //这里我们要做一个判断的操作；
        String content = mEtContent.getText().toString().trim();
        mTvCanWriteNum.setText(  Html.fromHtml("还可以输入<font color='red'>"+(200-content.length())+"</font>字"));
    }
}
