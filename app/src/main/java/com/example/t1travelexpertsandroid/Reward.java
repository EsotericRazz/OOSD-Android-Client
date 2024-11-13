package com.example.t1travelexpertsandroid;

import android.os.Parcel;
import android.os.Parcelable;

public class Reward implements Parcelable {
    private int id;
    private String name;

    public Reward(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Reward(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<Reward> CREATOR = new Creator<Reward>() {
        @Override
        public Reward createFromParcel(Parcel in) {
            return new Reward(in);
        }

        @Override
        public Reward[] newArray(int size) {
            return new Reward[size];
        }
    };

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }
}