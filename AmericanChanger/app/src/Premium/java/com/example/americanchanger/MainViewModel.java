package com.example.americanchanger;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


class PremMainViewModel extends ViewModel {
    final MutableLiveData<String> EU_live_data = new MutableLiveData<String>();
    final MutableLiveData<String> US_live_data = new MutableLiveData<String>();

}