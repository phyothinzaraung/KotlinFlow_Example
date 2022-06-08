package com.phyothinzaraung.kotlinflow_example.learn.errorhandling

import android.os.Bundle
import android.util.Log
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
import com.phyothinzaraung.kotlinflow_example.data.model.User
import com.phyothinzaraung.kotlinflow_example.learn.base.UserAdapter
import com.phyothinzaraung.kotlinflow_example.util.Status
import com.phyothinzaraung.kotlinflow_example.util.ViewModelFactory

class CatchActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: CatchViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI(){
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(arrayListOf())
        recyclerView.addItemDecoration(DividerItemDecoration(
            applicationContext, (recyclerView.layoutManager as LinearLayoutManager).orientation
        ))
        recyclerView.adapter = adapter
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(
            this, ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))))
            .get(CatchViewModel::class.java)
    }

    private fun setupObserver(){
        viewModel.getUsers().observe(this){
            Log.v("Status", it.status.toString())
            when(it.status){
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
                    Toast.makeText(applicationContext, it.message.toString(), Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun renderList(users: List<User>){
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
}