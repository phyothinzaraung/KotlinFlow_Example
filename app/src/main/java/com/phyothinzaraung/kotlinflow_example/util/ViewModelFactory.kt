package com.phyothinzaraung.kotlinflow_example.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.phyothinzaraung.kotlinflow_example.data.api.ApiHelper
import com.phyothinzaraung.kotlinflow_example.learn.retrofit.SingleNetworkCallViewModel
import com.phyothinzaraung.kotlinflow_example.learn.series.SeriesNetworkCallViewModel

class ViewModelFactory(private val apiHelper: ApiHelper): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SingleNetworkCallViewModel::class.java)){
            return SingleNetworkCallViewModel(apiHelper) as T
        }
        if(modelClass.isAssignableFrom(SeriesNetworkCallViewModel::class.java)){
            return SeriesNetworkCallViewModel(apiHelper) as T
        }

        throw IllegalArgumentException("Unknown Class Name")
    }


}