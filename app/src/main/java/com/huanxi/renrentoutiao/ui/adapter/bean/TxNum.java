package com.huanxi.renrentoutiao.ui.adapter.bean;

public class TxNum {

    private int num;
    private boolean checked;
    private int mLeft = 0;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    public TxNum(int num, boolean checked) {
        this.num = num;
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "TxNum{" +
                "num='" + num + '\'' +
                ", checked=" + checked +
                '}';
    }

    public void setLeft(int left) {
        mLeft = left;
    }

    public int getLeft() {
        return mLeft;
    }
}
