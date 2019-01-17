package com.huanxi.renrentoutiao.ui.adapter.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.huanxi.renrentoutiao.ui.adapter.TaskAdapter;

import java.util.List;

/**
 * Created by Dinosa on 2018/1/22.
 *
 * 这里是任务的title对象；
 * 1级标题；
 */

public class TaskTitleBean  extends AbstractExpandableItem<TaskItemBean> implements MultiItemEntity {

    public String title;
    public List<TaskItemBean> list;

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return TaskAdapter.TYPE_LEVEL_0;
    }


    //获取列表操作；
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TaskItemBean> getList() {
        return list;
    }

    public void setList(List<TaskItemBean> list) {
        this.list = list;
    }

    /**
     * 这个方法是将所有的数据进行一个填充；
     */
    public void addAllSubItem(){
        if (list != null) {

            for (int i = 0; i < list.size(); i++) {

                TaskItemBean lv1 = list.get(i);

                TaskItemContentBean taskItemContentBean = new TaskItemContentBean(lv1.getContent(),lv1.getButtontitle(),lv1.getUrl(),lv1.getTitle(),lv1.getId());

                lv1.addSubItem(taskItemContentBean);
                addSubItem(lv1);
            }
        }
    }

    public void removePostionItem(int position){

        if (list != null) {
            removeSubItem(list.get(position));
        }
    }
    public void removePostionItem(TaskItemBean bean){
        if (list != null) {
            removeSubItem(bean);
        }
    }
}
