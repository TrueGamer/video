package com.huanxi.renrentoutiao.ui.activity.other;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.base.BaseTitleActivity;
import com.huanxi.renrentoutiao.ui.adapter.PictureDetailAdapter;
import com.huanxi.renrentoutiao.ui.adapter.bean.PictureImageBean;

import java.util.List;

public class PictureDetailActivity extends BaseTitleActivity {

    private ListView lvPictureList;
    private List<PictureImageBean> imageBeanList;
    private PictureDetailAdapter pictureDetailAdapter;

    @Override
    public int getBodyLayoutId() {
        return R.layout.activity_picture_detail_layout;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        super.initView(rootView, savedInstanceState);

        String title = getIntent().getStringExtra("title");
//        imageBeanList = (List<PictureImageBean>) getIntent().getSerializableExtra("imageBeanList");
        imageBeanList = getIntent().getParcelableArrayListExtra("imageBeanList");

        setTitle(title);
        setBackText("");

        lvPictureList = (ListView) findViewById(R.id.lv_pictureList);
        pictureDetailAdapter = new PictureDetailAdapter(imageBeanList , getApplicationContext());
        lvPictureList.setAdapter(pictureDetailAdapter);

    }

}
