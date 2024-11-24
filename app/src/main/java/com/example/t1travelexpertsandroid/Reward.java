package com.example.t1travelexpertsandroid;

import android.os.Parcel;
import android.os.Parcelable;

public class Reward implements Parcelable {
    // Reward variables
    private int id;
    private String name;
    private String description;

    // Constructor for all 3 fields
    public Reward(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Constructor without id
    public Reward(String name, String description) {
        this.name = name;
        this.description = description;
    };

    // For the adapter
    protected Reward(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
    }

    // For the adapter
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

    // Getters and setters
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return this.description;
    }

    // For the adapter
    @Override
    public int describeContents() {
        return 0;
    }

    // For the adapter
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(description);
    }
}