package com.phyothinzaraung.kotlinflow_example.learn.retrofit.parallel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phyothinzaraung.kotlinflow_example.data.api.ApiHelper
import com.phyothinzaraung.kotlinflow_example.data.model.User
import com.phyothinzaraung.kotlinflow_example.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class ParallelNetworkCallViewModel(private val apiHelper: ApiHelper): ViewModel() {

    private val users = MutableLiveData<Resource<List<User>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers(){
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            apiHelper.getUsers()
                .zip(apiHelper.getMoreUsers()){ usersFromApi, moreUsersFromApi ->
                    val allUsersFromApi = mutableListOf<User>()
                    allUsersFromApi.addAll(usersFromApi)
                    allUsersFromApi.addAll(moreUsersFromApi)
                    return@zip allUsersFromApi
                }.flowOn(Dispatchers.Default)
                .catch { e ->
                    users.postValue(Resource.error(e.message, null))
                }.collect {
                    users.postValue(Resource.success(it))
                }
        }
    }

    fun getUsers(): LiveData<Resource<List<User>>>{
        return users
    }
}