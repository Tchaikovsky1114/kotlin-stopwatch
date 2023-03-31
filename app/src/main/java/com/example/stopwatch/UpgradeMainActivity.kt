package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.Timer
import kotlin.concurrent.timer

class UpgradeMainActivity : AppCompatActivity(), View.OnClickListener {


    private lateinit var btn_start : Button
    private lateinit var btn_refresh : Button
    private lateinit var tv_minute : TextView
    private lateinit var tv_second : TextView
    private lateinit var tv_millisecond : TextView

    private var isRunning = false

    private var timer : Timer? = null
    private var time = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_upgrade)

        btn_start = findViewById(R.id.button_start)
        btn_refresh = findViewById(R.id.button_refresh)
        tv_minute = findViewById(R.id.minute)
        tv_second = findViewById(R.id.second)
        tv_millisecond = findViewById(R.id.millisecond)

        // this = OnClickListener를 뜻함.
        btn_start.setOnClickListener(this)
        btn_refresh.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.button_start -> {
                if(isRunning) {
                    pause()
                } else {
                    start()
                }
            }
            R.id.button_refresh -> {
                refresh()
            }
        }
    }

    private fun start() {
        btn_start.text = getString(R.string.btn_pause_eng)
        isRunning = true

        timer = timer(period = 10) {
            time++

            val milliSecond = time%100
            val second = (time % 6000) / 100
            val minute = time / 6000

            // background 쓰레드는 UI에 접근할 수 없음.
            // 접근하기 위해 runOnUiThread 사용.
            runOnUiThread {
                if(isRunning){
                    tv_millisecond.text = if(milliSecond < 10) ".0${milliSecond}" else ".${milliSecond}"
                    tv_second.text = if(second < 10) ".0${second}" else ".${second}"
                    tv_minute.text = "$minute"
                }
            }
        }
    }
    private fun pause() {
        timer?.cancel()
        btn_start.text = getString(R.string.btn_start_eng)
        isRunning = false
    }

    private fun refresh() {
        time = 0
        runOnUiThread {
            tv_millisecond.text = ".00"
            tv_second.text = ".00"
            tv_minute.text = "00"
        }
        pause()

    }
}