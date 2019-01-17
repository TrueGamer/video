package com.huanxi.renrentoutiao.ui.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.activity.base.BaseActivity;
import com.huanxi.renrentoutiao.ui.dialog.RedPacketDialog;
import com.huanxi.renrentoutiao.utils.UIUtils;
import com.huanxi.renrentoutiao.utils.frameAnimationUtils.AnimationsContainer;

import java.util.List;

public class TestFourActivity extends BaseActivity {


    private RecyclerView mRvView;
    private ImageView mIvImg;
    private ImageView mImageOnew;
    private ImageView mImageTwo;
    private AnimationsContainer.FramesSequenceAnimation mProgressDialogAnim;
    private ImageView mImageViewThree;
    private ImageView mImageViewFour;

    @Override
    public int getContentLayout() {
        return R.layout.activity_test_four;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里
                Intent intent = new Intent(TestFourActivity.this, TestActivity2.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_get_all_package).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllPackagename();
            }
        });

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = TestFourActivity.this.getPackageManager().getLaunchIntentForPackage("com.tencent.android.qqdownloader");
                startActivity(startIntent);
            }
        });

        mRvView = ((RecyclerView) findViewById(R.id.rv_list));

        findViewById(R.id.btn_start_web).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity( WebHelperActivity.getIntent(TestFourActivity.this,"https://qr.alipay.com/c1x00442ikqtienkzaijae8","点我",false));
            }
        });

       // mIvImg = ((ImageView) findViewById(R.id.iv_img));

        //这里我们要做的一个操作就是和通过layer合成一个图片的操作；
        findViewById(R.id.btn_synthesis_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //这里我们要合成图片并显示的操作；
                Drawable drawable = getResources().getDrawable(R.drawable.share1);
                //接下来我们要做的一个操作就是将图片进行一个合成的操作；

                //mIvImg.setImageResource(R.drawable.layer_qrcode_and_bg);

               // mIvImg.setImageBitmap(createBitmap());
            }
        });

        findViewById(R.id.btn_red_pack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RedPacketDialog redPacketDialog = new RedPacketDialog();

                RedPacketDialog.show(TestFourActivity.this,"10",null);
            }
        });


        mImageOnew = ((ImageView) findViewById(R.id.iv_img_one));

        mImageTwo = ((ImageView) findViewById(R.id.iv_img_two));
        mImageViewThree = ((ImageView) findViewById(R.id.iv_img_three));
        mImageViewFour = ((ImageView) findViewById(R.id.iv_img_four));

        findViewById(R.id.btn_play_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationsContainer.getInstance(R.array.refresh_anim, 25).createProgressDialogAnim(mImageOnew).start();
            }
        });


        findViewById(R.id.btn_play_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AnimationsContainer.getInstance(R.array.loading_anim, 30).createProgressDialogAnim(mImageTwo).start();

                //AnimationsContainer.getInstance(R.array.loading_anim, 30).createProgressDialogAnim(mImageTwo).start();

                 AnimationsContainer.getInstance(R.array.loading_anim, 24).createProgressDialogAnim(mImageTwo).start();
            }
        });
        findViewById(R.id.btn_play_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AnimationsContainer.getInstance(R.array.loading_anim, 30).createProgressDialogAnim(mImageTwo).start();

                //AnimationsContainer.getInstance(R.array.loading_anim, 30).createProgressDialogAnim(mImageTwo).start();

                 AnimationsContainer.getInstance(R.array.loading_anim, 24).createProgressDialogAnim(mImageViewThree).start();
            }
        });
        findViewById(R.id.btn_play_four).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AnimationsContainer.getInstance(R.array.loading_anim, 30).createProgressDialogAnim(mImageTwo).start();

                //AnimationsContainer.getInstance(R.array.loading_anim, 30).createProgressDialogAnim(mImageTwo).start();

                AnimationsContainer.getInstance(R.array.loading_anim, 24).createProgressDialogAnim(mImageViewFour);
               ;
            }
        });


    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {

    }

    /**
     * 这里我们创建一个bitmap对象；
     * @return
     */
    public Bitmap createBitmap(){

        Resources resources = getResources();
        Drawable drawable = resources.getDrawable(R.drawable.share1);


        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        drawable.setBounds(0,0,w,h);

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;

        //这是一个空的Bitmap对象的；
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //创建要给画布对象；
        Canvas canvas = new Canvas(bitmap);
        //画一个背景
        drawable.draw(canvas);


        int size = UIUtils.dip2px(this, 170);

        int left = (drawable.getIntrinsicWidth() - size)/2;
        int top = (drawable.getIntrinsicHeight() - size)/2-UIUtils.dip2px(this,10);

        ColorDrawable colorDrawable = new ColorDrawable(Color.RED);
        colorDrawable.setBounds(left,top,left+size, top+size);

        //画二维码；
        colorDrawable.draw(canvas);

        return bitmap;
    }



    /**
     * 这里我们创建一个bitmap对象；
     * @return
     */
    public Bitmap createBitmap(Drawable bgDrawable,Bitmap qrcodeBitmap){

        Resources resources = getResources();


        int w = bgDrawable.getIntrinsicWidth();
        int h = bgDrawable.getIntrinsicHeight();
        bgDrawable.setBounds(0,0,w,h);

        // 取 drawable 的颜色格式
        Bitmap.Config config = bgDrawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;

        //这是一个空的Bitmap对象的；
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //创建要给画布对象；
        Canvas canvas = new Canvas(bitmap);
        //画一个背景
        bgDrawable.draw(canvas);


        int size = UIUtils.dip2px(this, 170);

        int left = (bgDrawable.getIntrinsicWidth() - size)/2;
        int top = (bgDrawable.getIntrinsicHeight() - size)/2-UIUtils.dip2px(this,10);

       // ColorDrawable colorDrawable = new ColorDrawable(Color.RED);
        //colorDrawable.setBounds(left,top,left+size, top+size);

        //画二维码；
        canvas.drawBitmap(qrcodeBitmap,left,top,new Paint());

        return bitmap;
    }
    /**
     * 将drawable转换为bitmap
     * @return
     */

    public Bitmap drawableToBitmap(Drawable drawable, int width, int height) {
        // 取 drawable 的长宽
        //int w = drawable.getIntrinsicWidth();
        //int h = drawable.getIntrinsicHeight();

        int w=width;
        int h=height;

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }



    private void getAllPackagename() {

        PackageManager pm=getPackageManager();
        List<PackageInfo> list=pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo packageInfo : list) {
            //获取到设备上已经安装的应用的名字,即在AndriodMainfest中的app_name。
            String appName=packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            //获取到应用所在包的名字,即在AndriodMainfest中的package的值。
            String packageName=packageInfo.packageName;

            System.out.println("allPackage: "+packageName);
        }

    }

}
