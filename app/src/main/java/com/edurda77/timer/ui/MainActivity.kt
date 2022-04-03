package com.edurda77.timer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edurda77.timer.R
import com.edurda77.timer.databinding.ActivityMainBinding
import com.edurda77.timer.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel =  MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.liveData.observe(this) {
            binding.textTime.text = it
        }
        binding.buttonStart.setOnClickListener {
            viewModel.startTimer()
        }
        binding.buttonStop.setOnClickListener {
            viewModel.stopTimer()
        }
        binding.buttonPause.setOnClickListener {
            viewModel.pauseTimer()
        }
    }
}