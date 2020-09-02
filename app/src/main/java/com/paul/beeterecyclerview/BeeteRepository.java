package com.paul.beeterecyclerview;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BeeteRepository {

    private BeetDoa mBeetDao;
    private LiveData<List<Beet>> mAllBeete;

    BeeteRepository(Application application) {
        BeeteRoomDatabase db = BeeteRoomDatabase.getDatabase(application);
        mBeetDao = db.beetDoa();
        mAllBeete = mBeetDao.getAllBeete();
    }

    LiveData<List<Beet>> getAllBeete() {
        return mAllBeete;
    }

    void insert(final Beet beet) {
        BeeteRoomDatabase.databaseWriteExecutor.execute(() -> {
            mBeetDao.insert(beet);
        });
    }
}


