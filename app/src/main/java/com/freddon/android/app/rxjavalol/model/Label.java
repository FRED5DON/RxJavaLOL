package com.freddon.android.app.rxjavalol.model;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.List;

/**
 * Created by fred on 2016/12/7.
 */

public class Label implements Parcelable{

    private String text;

    private String desc;

    private int level;

    private int flag;

    private List<Label> child;

    public Label() {
    }

    public Label(String desc) {
        this.desc=desc;
    }

    protected Label(Parcel in) {
        text = in.readString();
        desc = in.readString();
        level = in.readInt();
        flag = in.readInt();
        child = in.createTypedArrayList(Label.CREATOR);
    }

    public static final Creator<Label> CREATOR = new Creator<Label>() {
        @Override
        public Label createFromParcel(Parcel in) {
            return new Label(in);
        }

        @Override
        public Label[] newArray(int size) {
            return new Label[size];
        }
    };

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Label> getChild() {
        return child;
    }

    public void setChild(List<Label> child) {
        this.child = child;
    }


    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        parcel.writeString(desc);
        parcel.writeInt(level);
        parcel.writeInt(flag);
        parcel.writeTypedList(child);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


}
