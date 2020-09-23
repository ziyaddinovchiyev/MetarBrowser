package com.cinemo.metarbrowser.db.entity;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.cinemo.metarbrowser.util.DateConverter;

import java.util.Objects;

@Entity(tableName = "metar")
public class Info {

    public Info() {}

    public Info(@NonNull String id, String lastUpdated, String decode, String raw) {
        this.id = id;
        this.lastUpdated = lastUpdated;
        this.decode = decode;
        this.raw = raw;
    }

    @NonNull
    @PrimaryKey
    private String id = "";

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "last_updated")
    private String lastUpdated;

    @ColumnInfo(name = "decode")
    private String decode;

    @ColumnInfo(name = "raw")
    private String raw;

    @ColumnInfo(name = "isExpanded")
    public boolean isExpanded = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getDecode() {
        return decode;
    }

    public void setDecode(String decode) {
        this.decode = decode;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Info info = (Info) o;
        return id.equals(info.id) &&
                lastUpdated.equals(info.lastUpdated) &&
                decode.equals(info.decode) &&
                raw.equals(info.raw) &&
                (isExpanded == info.isExpanded);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, lastUpdated, decode, raw);
    }
}
