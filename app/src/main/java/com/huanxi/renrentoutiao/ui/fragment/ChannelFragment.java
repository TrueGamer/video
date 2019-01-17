package com.huanxi.renrentoutiao.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.net.api.ApiCheckService;
import com.huanxi.renrentoutiao.net.api.news.ApiNewAdLog;
import com.huanxi.renrentoutiao.ui.activity.other.GuideActivity;
import com.huanxi.renrentoutiao.ui.activity.other.SplashActivity;
import com.huanxi.renrentoutiao.ui.fragment.base.BaseFragment;
import com.huanxi.renrentoutiao.utils.InfoUtil;
import com.huanxi.renrentoutiao.utils.SharedPreferencesUtils;
import com.zhxu.library.exception.HttpTimeException;
import com.zhxu.library.http.HttpManager;
import com.zhxu.library.listener.HttpOnNextListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import butterknife.BindView;
import cn.tongdun.android.shell.db.utils.LogUtil;

/**
 * Created by gdhuo on 2019/1/9.
 */
public class ChannelFragment extends BaseFragment {

    @BindView(R.id.etText)
    EditText etText;

    private String from;

    @Override
    protected View getContentView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_channel, null);
    }

    @Override
    protected void initView() {
        if(getArguments() != null){
            from = getArguments().getString("from");
        }
        String channel = SharedPreferencesUtils.getInstance(getActivity()).getString(SharedPreferencesUtils.CHANNEL);
        if (TextUtils.isEmpty(channel)) {
            etText.setHint("请输入渠道号");
        } else {
            etText.setText(channel);
            if(from != null && "from_mine".equals(from)) {

            } else {

            }
        }

        /*String mac = InfoUtil.getMacAddress();
        Log.d("TAG", "产品 " + Build.BRAND + " Model: " + android.os.Build.MODEL + "\nAPI: " + android.os.Build.VERSION.SDK + "\n系统版本: " + android.os.Build.VERSION.RELEASE);
        Log.d("TAG", "mac " + mac);
        InfoUtil.getNetIp(new InfoUtil.NetCallback() {
            @Override
            public void onSuccess(String value) {
                Log.d("TAG","ip address "+value);
            }

            @Override
            public void onFail(Exception e) {

            }
        });*/

        /*etText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                if (null != keyEvent && KeyEvent.KEYCODE_ENTER == keyEvent.getKeyCode()) {
                    switch (keyEvent.getAction()) {
                        case KeyEvent.ACTION_UP:
                            String newData = etText.getText().toString();
                            if(!TextUtils.isEmpty(newData)) {
                                SharedPreferencesUtils.getInstance(getActivity()).writeString(SharedPreferencesUtils.CHANNEL,newData);
                            }
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });*/
    }

    public void onClickRight() {
        String newData = etText.getText().toString();
        if (!TextUtils.isEmpty(newData)) {
            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put(ApiNewAdLog.SERVER_NUMBER,newData);

            ApiCheckService apiCheckService = new ApiCheckService(new HttpOnNextListener<String>() {
                @Override
                public void onNext(String o) {
                    SharedPreferencesUtils.getInstance(getActivity()).writeString(SharedPreferencesUtils.CHANNEL, newData);
                    Toast.makeText(getActivity(), "渠道号修改完成", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    if(from != null && "from_mine".equals(from)) {

                    } else {
                        startActivity(new Intent(getActivity(),SplashActivity.class));
                    }
                }

                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    if(e instanceof HttpTimeException) {
                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            },getBaseActivity(),paramsMap);
            HttpManager.getInstance().doHttpDeal(apiCheckService);
        }
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1) {
                Log.d("TAG","ipaddress "+msg.obj);
            }
        }
    };
}
