package com.paul.beeterecyclerview;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Beet {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "description")
    private String desc;

    public Beet(@NonNull String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }
}
