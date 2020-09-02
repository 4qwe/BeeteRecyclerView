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

    @NonNull
    @ColumnInfo(name = "water_levels")
    public String levels;

    public Beet(@NonNull String desc) {
        this.desc = desc;
        this.levels = String.format("%s%%", Integer.toString((int) ((Math.random()) * 100 + 1)));
    }

    public String getDesc() {
        return this.desc;
    }

    public String getLevels() {
        return this.levels;
    }

}
