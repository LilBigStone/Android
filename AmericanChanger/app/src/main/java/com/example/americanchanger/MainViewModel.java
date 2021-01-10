package com.example.americanchanger;
import android.app.Application;
import android.content.ClipData;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class MainViewModel extends ViewModel {
    final MutableLiveData<String> EU_live_data = new MutableLiveData<String>();
    final MutableLiveData<String> US_live_data = new MutableLiveData<String>();

}