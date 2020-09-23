package com.cinemo.metarbrowser.repository;

import androidx.lifecycle.LiveData;
import com.cinemo.metarbrowser.MetarApp;
import com.cinemo.metarbrowser.db.entity.Info;

import java.util.List;

public class InfoRepository {

    public LiveData<List<Info>> fetchData() {
        return MetarApp.get().getInfoDao().getAllInfo();
    }

    public LiveData<List<Info>> fetchDataFiltered(String term) {
        return MetarApp.get().getInfoDao().getAllInfoFiltered(term);
    }
}
