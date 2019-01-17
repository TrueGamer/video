package com.huanxi.renrentoutiao.ui.adapter.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 美图列表种item得图片列表
 */
public class PictureImageBean implements Parcelable{

    private String small;
    private String middle;
    private String big;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    @Override
    public String toString() {
        return "PictureImageBean{" +
                "small='" + small + '\'' +
                ", middle='" + middle + '\'' +
                ", big='" + big + '\'' +
                '}';
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeString(small);
        out.writeString(middle);
        out.writeString(big);
    }

    public static final Parcelable.Creator<PictureImageBean> CREATOR = new Creator<PictureImageBean>()
    {
        @Override
        public PictureImageBean[] newArray(int size)
        {
            return new PictureImageBean[size];
        }

        @Override
        public PictureImageBean createFromParcel(Parcel in)
        {
            return new PictureImageBean(in);
        }
    };

    public PictureImageBean(Parcel in)
    {
        small = in.readString();
        middle = in.readString();
        big = in.readString();
    }

}
