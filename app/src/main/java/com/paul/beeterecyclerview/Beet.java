package com.paul.beeterecyclerview;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class Beet {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @NonNull
    @ColumnInfo(name = "description")
    private String desc;

    @NonNull
    @ColumnInfo(name = "water_levels")
    private String levels;

    @ColumnInfo(name = "uri")
    public String uriString;

    public Beet(@NonNull String desc) {
        this.desc = desc;
        this.levels = String.format("%s", Integer.toString((int) ((Math.random()) * 100 + 1)));
        this.id = UUID.randomUUID().toString();
        this.uriString = null;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getLevels() {
        return this.levels;
    }

    public void setLevels(String s) {
        this.levels = s;
    }

    public void setDesc(String name) {
        this.desc = name;
    }

    public void setUriString(String uri) {
        this.uriString = uri;
    }

    public String getUriString() {
        return this.uriString;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

}
