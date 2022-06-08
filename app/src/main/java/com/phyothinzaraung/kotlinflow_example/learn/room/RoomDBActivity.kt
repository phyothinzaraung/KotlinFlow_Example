package com.phyothinzaraung.kotlinflow_example.learn.room

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phyothinzaraung.kotlinflow_example.R
import com.phyothinzaraung.kotlinflow_example.data.api.ApiHelperImpl
import com.phyothinzaraung.kotlinflow_example.data.api.RetrofitBuilder
import com.phyothinzaraung.kotlinflow_example.data.local.DatabaseBuilder
import com.phyothinzaraung.kotlinflow_example.data.local.DatabaseHelperImpl
import com.phyothinzaraung.kotlinflow_example.data.local.entitiy.User
import com.phyothinzaraung.kotlinflow_example.learn.base.LocalUserAdapter
import com.phyothinzaraung.kotlinflow_example.learn.base.UserAdapter
import com.phyothinzaraung.kotlinflow_example.util.Status
import com.phyothinzaraung.kotlinflow_example.util.ViewModelFactory

class RoomDBActivity: AppCompatActivity() {

    private lateinit var viewModel: RoomDBViewModel
    private lateinit var adapter: LocalUserAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        setupUI()
        setupViewModel()
        setupObserver()
    }

    fun setupUI(){
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = LocalUserAdapter(arrayListOf())
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context,
            (recyclerView.layoutManager as LinearLayoutManager).orientation))
        recyclerView.adapter = adapter

    }

    fun setupViewModel(){
        viewModel = ViewModelProvider(this, ViewModelFactory(
            ApiHelperImpl(RetrofitBuilder.apiService),
            DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
        )).get(RoomDBViewModel::class.java)
    }

    fun setupObserver(){
        viewModel.getUsers().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun renderList(users: List<User>){
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
}