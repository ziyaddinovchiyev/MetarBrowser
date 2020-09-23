package com.cinemo.metarbrowser.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.cinemo.metarbrowser.db.entity.Info;
import com.cinemo.metarbrowser.repository.InfoRepository;

import java.util.List;

public class MainViewModel extends ViewModel {

    private MutableLiveData<String> lastUpdatedAtLiveData = new MutableLiveData<>();
    private MutableLiveData<String> filterTerm = new MutableLiveData<>("");
    private InfoRepository repository;

    public MainViewModel() {
        repository = new InfoRepository();
    }

    public LiveData<List<Info>> getFiltered() {
        return Transformations.switchMap(filterTerm, term -> repository.fetchDataFiltered(term));
    }

    public LiveData<PagedList<Info>> getFilteredPaged() {
        return Transformations.switchMap(filterTerm, term -> repository.fetchDataFilteredPaged(term));
    }

    public void setFilterTerm(String filterTerm) {
        this.filterTerm.postValue(filterTerm);
    }

    public LiveData<String> getLastUpdatedAtLiveData() {
        return lastUpdatedAtLiveData;
    }

    public void setLastUpdatedAtLiveData(String lastUpdatedAtLiveData) {
        this.lastUpdatedAtLiveData.postValue(lastUpdatedAtLiveData);
    }
}
