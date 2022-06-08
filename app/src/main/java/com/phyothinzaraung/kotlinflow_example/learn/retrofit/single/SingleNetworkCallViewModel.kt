package com.phyothinzaraung.kotlinflow_example.learn.retrofit.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phyothinzaraung.kotlinflow_example.data.api.ApiHelper
import com.phyothinzaraung.kotlinflow_example.data.model.User
import com.phyothinzaraung.kotlinflow_example.util.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SingleNetworkCallViewModel(private val apiHelper: ApiHelper): ViewModel() {

    private val users = MutableLiveData<Resource<List<User>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers(){
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            apiHelper.getUsers()
                .catch { error -> users.postValue(Resource.error(error.toString(), null)) }
                .collect { users.postValue(Resource.success(it)) }
        }
    }

    fun getUsers(): LiveData<Resource<List<User>>>{
        return users
    }
}