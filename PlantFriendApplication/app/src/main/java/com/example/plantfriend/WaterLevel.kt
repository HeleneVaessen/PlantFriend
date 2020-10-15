package com.example.plantfriend

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.Serializable


class WaterLevel() :ViewModel() {
val water: MutableLiveData<Int>by lazy {
    MutableLiveData<Int>()
}
}