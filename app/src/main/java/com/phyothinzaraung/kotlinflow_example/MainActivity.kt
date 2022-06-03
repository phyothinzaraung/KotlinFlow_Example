package com.phyothinzaraung.kotlinflow_example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.phyothinzaraung.kotlinflow_example.learn.retrofit.SingleNetworkCallActivity
import com.phyothinzaraung.kotlinflow_example.learn.series.SeriesNetworkCallActivity
import com.phyothinzaraung.kotlinflow_example.learn.series.SeriesNetworkCallViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var btnSingleCall: Button
    private lateinit var btnSeriesCall: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSingleCall = findViewById<Button>(R.id.btnSingleCall)
        btnSingleCall.setOnClickListener {
            startActivity(Intent(this, SingleNetworkCallActivity::class.java))
        }

        btnSeriesCall = findViewById(R.id.btnSeriesCall)
        btnSeriesCall.setOnClickListener {
            startActivity(Intent(this, SeriesNetworkCallActivity::class.java))
        }

    }
}