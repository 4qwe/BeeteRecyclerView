package com.paul.beeterecyclerview;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DetailedViewModel extends AndroidViewModel {

    private BeeteRepository mRepository;

    public DetailedViewModel(Application application) {
        super(application);
        mRepository = new BeeteRepository(application); //vergleiche mit MainViewModel Repository
        // hat zusätzlich und nutzt ausschließlich getAll methode und List<Beet> als Feld
    }

    LiveData<Beet> getBeet(String id) {
        return mRepository.getBeet(id);
    }

    void update(Beet beet) {
        mRepository.updateBeet(beet);
    }

}
