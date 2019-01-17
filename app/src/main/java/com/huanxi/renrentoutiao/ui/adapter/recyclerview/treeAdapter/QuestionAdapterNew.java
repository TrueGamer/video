package com.huanxi.renrentoutiao.ui.adapter.recyclerview.treeAdapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.R;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.treeAdapter.bean.Level2Bean;
import com.huanxi.renrentoutiao.ui.adapter.recyclerview.treeAdapter.bean.Level3Bean;

import java.util.List;

/**
 * Created by Dinosa on 2018/4/16.
 * 这里是问题的adapter
 */

public class QuestionAdapterNew extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {



    public static final int Level_1=0;
    public static final int Level_2=1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public QuestionAdapterNew(List<MultiItemEntity> data) {
        super(data);

        addItemType(Level_1, R.layout.item_question_level2);
        addItemType(Level_2, R.layout.item_question_level3);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {

        switch (helper.getItemViewType()) {
            case Level_1:
                //这里是一级title的操作；
                init1(helper, ((Level2Bean) item),this);
                break;
            case Level_2:
                //这里是二级标题；
                init2(helper, ((Level3Bean) item),this);
                break;

        }
    }

    private void init2(BaseViewHolder viewHolder, Level3Bean item, QuestionAdapterNew questionAdapterNew) {

        viewHolder.setText(R.id.tv_task_content,item.getContent());

    }

    private void init1(final BaseViewHolder viewHolder, final Level2Bean bean, final QuestionAdapterNew adapter) {
        int imgRes=bean.isExpanded()?R.drawable.icon_arrow_up:R.drawable.icon_arrow_down;

        viewHolder.setImageResource(R.id.iv_arrow,imgRes);


        viewHolder.setText(R.id.tv_task_title,bean.getQuestionContent());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bean.isExpanded()) {
                    //是需要让其收缩起来的；
                    adapter.collapse(viewHolder.getAdapterPosition());
                }else{
                    adapter.expand(viewHolder.getAdapterPosition());
                }

            }
        });

    }


}
