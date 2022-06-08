package com.phyothinzaraung.kotlinflow_example.learn.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phyothinzaraung.kotlinflow_example.data.api.ApiHelper
import com.phyothinzaraung.kotlinflow_example.data.local.DatabaseHelper
import com.phyothinzaraung.kotlinflow_example.data.local.entitiy.User
import com.phyothinzaraung.kotlinflow_example.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RoomDBViewModel(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper
) : ViewModel() {

    private val users = MutableLiveData<Resource<List<User>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            databaseHelper.getUsers().flatMapConcat { usersFromDB ->
                if (usersFromDB.isEmpty()) {
                    return@flatMapConcat apiHelper.getUsers()
                        .map { apiUserList ->
                            val userList = mutableListOf<User>()
                            for (apiUser in apiUserList) {
                                val user = User(
                                    id = apiUser.id,
                                    name = apiUser.name,
                                    email = apiUser.email,
                                    avatar = apiUser.avatar
                                )
                                userList.add(user)
                            }
                            userList
                        }.flatMapConcat { userToInsertDb ->
                            databaseHelper.insertAll(userToInsertDb)
                                .flatMapConcat { flow { emit(userToInsertDb) } }
                        }
                } else {
                    return@flatMapConcat flow { emit(usersFromDB) }
                }
            }.flowOn(Dispatchers.Default)
                .catch { e -> users.postValue(Resource.error(e.message.toString(), null)) }
                .collect { users.postValue(Resource.success(it)) }
        }
    }

    fun getUsers(): LiveData<Resource<List<User>>>{
        return users
    }
}