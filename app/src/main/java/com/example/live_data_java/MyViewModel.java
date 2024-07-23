package com.example.live_data_java;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    private MutableLiveData<Integer> counter = new MutableLiveData<>();

    public void increaseCounter(){
        int currentVal = counter.getValue() != null ? counter.getValue() : 0;
        counter.setValue(currentVal+1);
    }

    public void decreaseCounter(){
        if (counter.getValue() != null && counter.getValue()>0){
            int currentVal = counter.getValue();
            counter.setValue(currentVal-1);
        }
    }

    public LiveData<Integer> getCounter(){
        return counter;
    }

}
