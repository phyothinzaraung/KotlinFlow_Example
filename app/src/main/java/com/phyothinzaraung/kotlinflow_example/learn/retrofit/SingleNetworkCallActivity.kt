package com.phyothinzaraung.kotlinflow_example.learn.retrofit

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
import com.phyothinzaraung.kotlinflow_example.data.api.ApiHelper
import com.phyothinzaraung.kotlinflow_example.data.api.ApiHelperImpl
import com.phyothinzaraung.kotlinflow_example.data.api.RetrofitBuilder
import com.phyothinzaraung.kotlinflow_example.data.model.User
import com.phyothinzaraung.kotlinflow_example.learn.base.UserAdapter
import com.phyothinzaraung.kotlinflow_example.util.Status
import com.phyothinzaraung.kotlinflow_example.util.ViewModelFactory

class SingleNetworkCallActivity: AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: SingleNetworkCallViewModel

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
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation)
        )
        adapter = UserAdapter(arrayListOf())
        recyclerView.adapter = adapter
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this,
            ViewModelFactory(ApiHelperImpl(RetrofitBuilder.apiService))).get(SingleNetworkCallViewModel::class.java)
    }

    private fun setupObserver(){
        viewModel.getUsers().observe(this){
            when(it.status){
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    it.data?.let { users -> renderList(users) }
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

    fun renderList(users: List<User>){
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
}