package com.paul.beeterecyclerview;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BeeteRepository {

    private BeetDao mBeetDao; //hier ist das magic BeetDao Objekt, im Repository gespeichert
    //an diesem Objekt enden die DAO Methoden wie Insert, Update
    private LiveData<List<Beet>> mAllBeete;

    BeeteRepository(Application application) {
        BeeteRoomDatabase db = BeeteRoomDatabase.getDatabase(application);
        mBeetDao = db.beetDao();
        mAllBeete = mBeetDao.getAllBeete();
    }

    LiveData<List<Beet>> getAllBeete() {
        return mAllBeete;
    } //get All Beete braucht Repository von Main Activity


    void updateBeet(Beet beet) { //wir rufen diese Methoden aus dem ViewModel
        BeeteRoomDatabase.databaseWriteExecutor.execute(() -> {
            mBeetDao.update(beet); //hier die DAO Methode
        });
    }


    public LiveData<Beet> getBeet(String name) { //get Single Beet gebraucht im Repository der detailed Activity
        return mBeetDao.getBeetByName(name);
    }
}


