package com.paul.beeterecyclerview;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private BeeteRepository mRepository;

    private LiveData<List<Beet>> mAllBeete;

    public MainViewModel(Application application) {
        super(application);
        mRepository = new BeeteRepository(application);
        mAllBeete = mRepository.getAllBeete();
    }

    LiveData<List<Beet>> getAllBeete() {
        return mAllBeete;
    }

    public void deleteBeet(Beet beet) {
        mRepository.deleteBeet(beet);
    }


}
