package com.cinemo.metarbrowser.background;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.cinemo.metarbrowser.MetarApp;
import com.cinemo.metarbrowser.db.dao.InfoDao;
import com.cinemo.metarbrowser.db.entity.Info;
import com.cinemo.metarbrowser.util.Constants;

import java.util.List;

public class FetchWorker extends Worker {

    private InfoDao infoDao;

    public FetchWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        infoDao = MetarApp.get().getInfoDao();
    }

    @NonNull
    @Override
    public Result doWork() {
        List<Info> result = MetarApp.get().getNetworkRequest().fetchList(Constants.STATIONS);
        if (result.isEmpty()) return Result.retry();
        else {
            MetarApp.get().getExecutors().diskIO().execute(() -> infoDao.insertInfoList(result));
            return Result.success();
        }
    }
}
