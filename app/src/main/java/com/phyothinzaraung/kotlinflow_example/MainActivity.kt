package com.phyothinzaraung.kotlinflow_example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.phyothinzaraung.kotlinflow_example.learn.errorhandling.CatchActivity
import com.phyothinzaraung.kotlinflow_example.learn.errorhandling.emitall.EmitAllActivity
import com.phyothinzaraung.kotlinflow_example.learn.retrofit.parallel.ParallelNetworkCallActivity
import com.phyothinzaraung.kotlinflow_example.learn.retrofit.parallel.ParallelNetworkCallViewModel
import com.phyothinzaraung.kotlinflow_example.learn.retrofit.single.SingleNetworkCallActivity
import com.phyothinzaraung.kotlinflow_example.learn.retrofit.series.SeriesNetworkCallActivity
import com.phyothinzaraung.kotlinflow_example.learn.room.RoomDBActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnSingleCall: Button
    private lateinit var btnSeriesCall: Button
    private lateinit var btnParallelCall: Button
    private lateinit var btnRoomDb: Button
    private lateinit var btnCatchError: Button
    private lateinit var btnEmitAll: Button

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

        btnParallelCall = findViewById(R.id.btnParallelCall)
        btnParallelCall.setOnClickListener {
            startActivity(Intent(this, ParallelNetworkCallActivity::class.java))
        }

        btnRoomDb = findViewById(R.id.btnRoomDb)
        btnRoomDb.setOnClickListener {
            startActivity(Intent(this, RoomDBActivity::class.java))
        }

        btnCatchError = findViewById(R.id.btnCatchError)
        btnCatchError.setOnClickListener {
            startActivity(Intent(this, CatchActivity::class.java))
        }

        btnEmitAll = findViewById(R.id.btnEmitAll)
        btnEmitAll.setOnClickListener {
            startActivity(Intent(this, EmitAllActivity::class.java))
        }

    }
}