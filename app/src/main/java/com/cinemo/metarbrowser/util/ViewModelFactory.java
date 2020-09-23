package com.cinemo.metarbrowser.util;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cinemo.metarbrowser.MetarApp;
import com.cinemo.metarbrowser.vm.MainViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private MetarApp context;

    public ViewModelFactory(MetarApp context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) return (T) new MainViewModel();
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}