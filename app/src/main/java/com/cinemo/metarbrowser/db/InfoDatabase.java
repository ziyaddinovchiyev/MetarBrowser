package com.cinemo.metarbrowser.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;

import com.cinemo.metarbrowser.MetarApp;
import com.cinemo.metarbrowser.background.FetchWorker;
import com.cinemo.metarbrowser.db.dao.InfoDao;
import com.cinemo.metarbrowser.db.entity.Info;
import com.cinemo.metarbrowser.util.Constants;

import java.util.concurrent.TimeUnit;

@Database(entities = {Info.class}, version = 1, exportSchema = false)
public abstract class InfoDatabase extends RoomDatabase {

    private static InfoDatabase INSTANCE;

    public abstract InfoDao infoDao();

    public static InfoDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (InfoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            InfoDatabase.class,
                            "metar_database")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                    Constraints constraints = new Constraints.Builder()
                                            .setRequiredNetworkType(NetworkType.CONNECTED)
                                            .build();

                                    PeriodicWorkRequest periodicSyncDataWork =
                                            new PeriodicWorkRequest.Builder(FetchWorker.class, 15, TimeUnit.MINUTES)
                                                    .addTag(Constants.WORKER_TAG)
                                                    .setConstraints(constraints)
                                                    .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                                                    .build();

                                    MetarApp.get().getWorkManager().enqueueUniquePeriodicWork(
                                            Constants.WORK_NAME,
                                            ExistingPeriodicWorkPolicy.KEEP, //Existing Periodic Work policy
                                            periodicSyncDataWork //work request
                                    );
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
