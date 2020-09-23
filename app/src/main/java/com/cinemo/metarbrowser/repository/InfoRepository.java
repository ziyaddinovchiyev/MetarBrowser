package com.cinemo.metarbrowser.repository;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

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

    public LiveData<PagedList<Info>> fetchDataFilteredPaged(String term) {
        DataSource.Factory<Integer, Info> factory = MetarApp.get().getInfoDao().getAllInfoFilteredPaged(term);
        PagedList.Config config = new PagedList.Config.Builder().setPageSize(10).build();
        LivePagedListBuilder<Integer, Info> builder = new LivePagedListBuilder<>(factory, config);
        return builder.build();
    }
}
