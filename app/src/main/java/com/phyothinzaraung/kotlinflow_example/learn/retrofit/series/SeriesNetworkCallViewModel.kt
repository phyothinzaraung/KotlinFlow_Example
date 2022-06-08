package com.phyothinzaraung.kotlinflow_example.learn.retrofit.series

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
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class SeriesNetworkCallViewModel(private val apiHelper: ApiHelper):ViewModel() {

    private val users = MutableLiveData<Resource<List<User>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers(){
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            val allUsersFromApi = mutableListOf<User>()
            apiHelper.getUsers()
                .flatMapConcat {
                    usersFromApi -> allUsersFromApi.addAll(usersFromApi)
                    apiHelper.getMoreUsers()
                }.flowOn(Dispatchers.Default)
                .catch {
                    error -> users.postValue(Resource.error(error.toString(), null))
                }
                .collect { moreUsersFromApi ->
                    allUsersFromApi.addAll(moreUsersFromApi)
                    users.postValue(Resource.success(allUsersFromApi))
                }
        }
    }

    fun getUsers(): LiveData<Resource<List<User>>>{
        return users
    }
}