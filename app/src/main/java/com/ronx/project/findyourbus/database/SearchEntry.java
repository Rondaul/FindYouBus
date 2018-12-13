package com.ronx.project.findyourbus.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "search")
public class SearchEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String from;
    private String to;
    private String type;
    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @Ignore
    public SearchEntry(String from, String to, String type, Date updatedAt) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.updatedAt = updatedAt;
    }

    public SearchEntry(int id, String from, String to, String type, Date updatedAt) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.type = type;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
