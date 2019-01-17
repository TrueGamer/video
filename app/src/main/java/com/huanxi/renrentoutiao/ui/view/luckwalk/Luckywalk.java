package com.huanxi.renrentoutiao.ui.view.luckwalk;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huanxi.renrentoutiao.R;

import java.util.List;


/**
 * Created by zd on 2016/8/2.
 */
public class Luckywalk extends RelativeLayout {
    private String imgUrl = "http://m.yuecp.net/activity/lucky/";
    //当前奖品下标
    int currPrizeIndex = 0;
    //转动的最大时间
    int maxTime = 5;
    int mWidth = 0;
    int mHeight = 0;
    //每行排列几个商品
    int mLineCount = 3;
    //顶间距
    int mTopMargin = 0;
    //左间距
    int mLeftMargin = 0;
    //两者的间距
    int margin = 10;
    List<LuckyWalkBean> datas;
    String data[] = new String[]{"happy_default_1", "happy_default_2", "happy_default_3", "happy_default_4",
            "happy_default_5", "happy_default_6", "happy_default_7", "happy_default_8"};
    int screenWidth = 0;
    //获取所有商品能够排列成几行
    int mCountLine;
    //控制最终奖品
    int giftPosition = 7;
    //允许转动
    boolean allowTurn = true;

    public Luckywalk(Context context) {
        super(context);
        initView();
    }

    public Luckywalk(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public Luckywalk(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    void initView() {
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = getMeasuredWidth();
        mTopMargin = (int) (getMeasuredHeight() * 0.14f);
        mLeftMargin = (int) (getMeasuredWidth() * 0.13f);
        mWidth = (screenWidth - mLeftMargin * 2 - margin * (mLineCount - 1)) / mLineCount;
        initGoods();
    }

    void initGoods() {
        removeAllViews();
        int countGood = data.length;
        if (countGood % 2 != 0)
            Log.e("error", "商品数目请不要为单数");
//            throw new RuntimeException("商品数目请不要为单数");
        //获取所有商品能够排列成几行
        mCountLine = (countGood - mLineCount * 2) / 2 + 2;//（总数-首尾）/ 中间左右一个 + 首尾默认2 = 总行数
        if (mCountLine < 3 || data.length < 8)
            Log.e("error", "当前商品数目无法形成四边形");
//            throw new RuntimeException("当前商品数目无法形成四边形");
        mHeight = ((int) (getMeasuredHeight() * (0.63 - 0.14)) - margin * (mCountLine - 1)) / mCountLine;
        Log.e("initGoods", mWidth + "_" + mHeight);
        /**
         *
         *               top
         *        ****************
         *        *              *
         *        *              *
         *   left *              * right (lfCount = 5去头尾)
         *        *              *
         *        *              *
         *        ****************
         *               button
         * **/

        int lfCount = (countGood - mLineCount * 2) / 2;//左右两边的数字个数
        if (lfCount < 1)
            Log.e("error", "左右边数量 " + lfCount + " 无法形成四边形");
//            throw new RuntimeException("左右边数量 " + lfCount + " 无法形成四边形");
        /** 上下左右每行格子数 **/
        int lineCount[][] = new int[4][];
        /** 从top->right->button->left顺序叠加数字 **/
        int currIndex = 0;
        for (int i = 0; i < 4; i++) {
            /** 判断是否同边 例如0 2 代表上下 并且数目一致 **/
            int lineCountGood = (i % 2 == 0 ? mLineCount : lfCount);
            lineCount[i] = new int[lineCountGood];
            for (int j = 0; j < lineCountGood; j++) {
                lineCount[i][j] = currIndex;
                int top = 0;
                int left = 0;
                if (i == 0) {
                    top = mTopMargin;
                    left = j * (mWidth + margin) + mLeftMargin;
                } else if (i == 1) {
                    top = (j + 1) * (mHeight + margin) + mTopMargin;
                    left = (lineCount[i - 1].length - 1) * (mWidth + margin) + mLeftMargin;
                } else if (i == 2) {
                    top = (lineCount[i - 1].length + 1) * (mHeight + margin) + mTopMargin;
                    left = (lineCountGood - j - 1) * (mWidth + margin) + mLeftMargin;
                } else if (i == 3) {
                    top = (lineCountGood - j) * (mHeight + margin) + mTopMargin;
                    left = mLeftMargin;
                }

                addView(currIndex, left, top);

                currIndex++;
            }
            System.out.println("");
            System.out.println("");
        }

        addButton();
    }


    public void addView(int index, int left, int top) {
        LayoutParams lp = new LayoutParams(mWidth, mHeight);
        lp.leftMargin = left;
        lp.topMargin = top;
        View inflate = View.inflate(getContext(), R.layout.view_good, null);
        inflate.setFocusable(false);
        inflate.setFocusableInTouchMode(false);
        final ImageView icon = (ImageView) inflate.findViewById(R.id.iv_icon);
        icon.setImageResource(BitmapUtil.getResIdByFolder(getContext(), data[index], "mipmap"));
        icon.setFocusable(false);
        icon.setFocusableInTouchMode(false);
        icon.setClickable(false);
        if (datas != null) {
            LuckyWalkBean bean = datas.get(index);
            Glide.with(getContext()).load( bean.getUrl()).
                    placeholder(BitmapUtil.getResIdByFolder(getContext(), data[index], "mipmap")).
                    error(BitmapUtil.getResIdByFolder(getContext(), data[index], "mipmap")).
                    into(icon);
        }
        addView(inflate);
        inflate.setTag(data[index]);
        inflate.setLayoutParams(lp);
    }

    public void addButton() {
        LayoutParams lp = new LayoutParams(mWidth, mHeight);
        lp.leftMargin = (mLineCount - 1) / 2 * (mWidth + margin) - mLineCount / 2 + mLeftMargin;
        lp.topMargin = mCountLine / 2 * (mHeight + margin) - mCountLine / 2 + mTopMargin;
        TextView click = new TextView(getContext());
        click.setGravity(Gravity.CENTER);
        click.setBackgroundResource(R.drawable.happy_default_9);
        addView(click);
        click.setTag("点我抽奖");
        click.setLayoutParams(lp);
        click.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                start();
            }
        });
    }

    public void start() {
        start(null, null);
    }

    public void start(LuckyWalkBean destLuckWalk, Callback callback) {
        this.callback = callback;
        if (!allowTurn) return;
        if (destLuckWalk != null) {
///            int random = new Random().nextInt(o.size() > data.length ? data.length : o.size());
//        String schemeId = o.getJSONObject(random).getString("schemeId");
            //这里我们需要通过传递过来的对象来决定最终显示的位置；
            giftPosition = datas.indexOf(destLuckWalk);
            Log.e("LuckWalk","id="+destLuckWalk.getId()+",position="+giftPosition+",datas:"+datas);
        }
        Log.e("giftPosition", "--->" + giftPosition);
        allowTurn = false;
        handler.postDelayed(new MyRunable(), 500);
    }

    public void start(Callback callback) {
        start(null, callback);
    }

    private Handler handler = new Handler();

    public class MyRunable implements Runnable {
        public int index = currPrizeIndex;
        public View oldV;
        public long currTime = 0;
        //加速度
        int speed = 500;
        //最快速度
        int fast = 50;
        //最慢速度
        int slow = 500;

        public MyRunable() {
            currTime = System.currentTimeMillis();
        }

        @Override
        public void run() {
            post(new Runnable() {
                @Override
                public void run() {
                    if (index >= data.length) index = 0;
                    //正在转圈的时间
                    float diff = (System.currentTimeMillis() - currTime) / 1000f;
                    //转圈分为慢-快-慢的过程 分三个时间段
                    float sjd = maxTime / 3f;
                    //时间段内递减递增范围 例如 总共从启动到结束转圈时间为9s
                    // 那么分三个阶段 第一阶段为3s 除掉每个格子之间的间隔时间
                    float timeCount = sjd * 1000 / speed;
                    if (diff < sjd) {//1阶段
                        speed -= slow / timeCount;
                        handler.postDelayed(this, speed);
                    } else if (diff >= sjd && diff < sjd * 2) {//2 阶段
                        speed = fast;
                        handler.postDelayed(this, speed);
                    } else {//3阶段为奖品最终获得阶段
                        //第三个阶段如果转动的下标index - giftPosition >0
                        //表明最后阶段的选中位置在预期奖品后面
                        //所以补位继续转动两者之间的差值
                        int dis = data.length - 1 - Math.abs(index - giftPosition);
                        //如果在后面则需要在转一圈+原来的补位位置来达到逼真的转动效果
                        // 否则会直接转动到目的位置停止
                        if (index < giftPosition || dis == 0 || (dis < 3 && index > giftPosition))
                            dis += data.length - 1;
                        speed += slow / dis;
                        if (index != giftPosition || dis > 0) {
                            handler.postDelayed(this, speed);
                        } else {
                            handler.removeCallbacks(this);
                        }
                        Log.e("giftPosition", "->" + speed + " _ " + index + " _ " + giftPosition + " _ " + dis);
                    }
                    //如果已经是预期目标则同步位置作为下次转动的起点
                    if (diff > maxTime && index == giftPosition) {
                        allowTurn = true;
                        currPrizeIndex = giftPosition;
                        handler.removeCallbacks(this);
                        if (callback != null)
                            callback.callbackSuccess();
                    }
                    if (getChildAt(index) instanceof TextView) return;
                    if (oldV != null)
                        oldV.findViewById(R.id.cover).setBackgroundResource(R.color.transparent);
                    oldV = getChildAt(index);
                    getChildAt(index).findViewById(R.id.cover).setBackgroundResource(R.drawable.turn_shop_border);
                    index++;
                }
            });
        }
    }

    public List<LuckyWalkBean> getDatas() {
        return datas;
    }

    public void setDatas(List<LuckyWalkBean> datas) {
        this.datas = datas;
        initGoods();
    }

    private Callback callback;

    public int getGiftPosition() {
        return giftPosition;
    }

    public void setGiftPosition(int giftPosition) {
        this.giftPosition = giftPosition;
    }
}
