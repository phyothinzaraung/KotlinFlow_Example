package com.phyothinzaraung.kotlinflow_example.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.phyothinzaraung.kotlinflow_example.data.api.ApiHelper
import com.phyothinzaraung.kotlinflow_example.data.local.DatabaseHelper
import com.phyothinzaraung.kotlinflow_example.learn.errorhandling.CatchViewModel
import com.phyothinzaraung.kotlinflow_example.learn.retrofit.parallel.ParallelNetworkCallActivity
import com.phyothinzaraung.kotlinflow_example.learn.retrofit.parallel.ParallelNetworkCallViewModel
import com.phyothinzaraung.kotlinflow_example.learn.retrofit.single.SingleNetworkCallViewModel
import com.phyothinzaraung.kotlinflow_example.learn.retrofit.series.SeriesNetworkCallViewModel
import com.phyothinzaraung.kotlinflow_example.learn.room.RoomDBViewModel

class ViewModelFactory(private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelper): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SingleNetworkCallViewModel::class.java)){
            return SingleNetworkCallViewModel(apiHelper) as T
        }
        if(modelClass.isAssignableFrom(SeriesNetworkCallViewModel::class.java)){
            return SeriesNetworkCallViewModel(apiHelper) as T
        }
        if(modelClass.isAssignableFrom(ParallelNetworkCallViewModel::class.java)){
            return ParallelNetworkCallViewModel(apiHelper) as T
        }
        if(modelClass.isAssignableFrom(RoomDBViewModel::class.java)){
            return RoomDBViewModel(apiHelper, dbHelper) as T
        }
        if(modelClass.isAssignableFrom(CatchViewModel::class.java)){
            return CatchViewModel(apiHelper, dbHelper) as T
        }

        throw IllegalArgumentException("Unknown Class Name")
    }


}