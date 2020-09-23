package com.cinemo.metarbrowser.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cinemo.metarbrowser.db.entity.Info;

import java.util.List;

@Dao
public interface InfoDao {

    @Update
    public void updateInfo(Info info);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertInfoList(List<Info> infoList);

    @Query("SELECT * FROM metar")
    LiveData<List<Info>> getAllInfo();

    @Query("SELECT * FROM metar WHERE id LIKE '%' || :term  || '%'")
    LiveData<List<Info>> getAllInfoFiltered(String term);
}
