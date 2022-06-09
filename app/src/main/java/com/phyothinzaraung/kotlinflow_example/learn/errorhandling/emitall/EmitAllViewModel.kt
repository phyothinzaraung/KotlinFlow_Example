package com.phyothinzaraung.kotlinflow_example.learn.errorhandling.emitall

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phyothinzaraung.kotlinflow_example.data.api.ApiHelper
import com.phyothinzaraung.kotlinflow_example.data.local.DatabaseHelper
import com.phyothinzaraung.kotlinflow_example.data.model.User
import com.phyothinzaraung.kotlinflow_example.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EmitAllViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper): ViewModel() {

    private val users = MutableLiveData<Resource<List<User>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers(){
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            apiHelper.getUsers()
                .zip(
                    apiHelper.getUsersWithError()
                        .catch { emitAll(flowOf(emptyList())) }){
                    userFromApi, moreUserFromApi ->
                    val allUserFromApi = arrayListOf<User>()
                    allUserFromApi.addAll(userFromApi)
                    allUserFromApi.addAll(moreUserFromApi)
                    return@zip allUserFromApi
                }.flowOn(Dispatchers.Default)
                .catch { e -> users.postValue(Resource.error(e.message, null)) }
                .collect { users.postValue(Resource.success(it)) }
        }
    }

    fun getUsers(): LiveData<Resource<List<User>>>{
        return users
    }
}