package com.cinemo.metarbrowser;

import android.app.Application;

import androidx.work.WorkManager;

import com.cinemo.metarbrowser.db.InfoDatabase;
import com.cinemo.metarbrowser.db.dao.InfoDao;
import com.cinemo.metarbrowser.util.AppExecutors;
import com.cinemo.metarbrowser.background.NetworkRequest;

public class MetarApp extends Application {

    private static MetarApp INSTANCE;
    private static AppExecutors mAppExecutors;
    private static InfoDao mInfoDao;
    private static NetworkRequest networkRequest;
    private static WorkManager workManager;

    public static MetarApp get() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;
        mAppExecutors = new AppExecutors();
        workManager = WorkManager.getInstance(this);
        networkRequest = new NetworkRequest();
        mInfoDao = InfoDatabase.getDatabase(getApplicationContext()).infoDao();
    }

    public AppExecutors getExecutors() {
        return mAppExecutors;
    }

    public InfoDao getInfoDao() {
        return mInfoDao;
    }

    public NetworkRequest getNetworkRequest(){
        return networkRequest;
    }

    public WorkManager getWorkManager() {
        return workManager;
    }
}
