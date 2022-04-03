package com.edurda77.timer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edurda77.timer.model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect


class MainViewModel : ViewModel(), ViewModelContract {
    private val timestampProvider = object : TimestampProvider {
        override fun getMilliseconds(): Long {
            return System.currentTimeMillis()
        }
    }
    val liveData: MutableLiveData<String> = MutableLiveData()
    private val stopwatchListOrchestrator = StopwatchListOrchestrator(
        StopwatchStateHolder(
            StopwatchStateCalculator(
                timestampProvider,
                ElapsedTimeCalculator(timestampProvider)
            ),
            ElapsedTimeCalculator(timestampProvider),
            TimestampMillisecondsFormatter()
        ),
        CoroutineScope(
            Dispatchers.Main
                    + SupervisorJob()
        )
    )
    init {
        viewModelScope.launch {
            stopwatchListOrchestrator.ticker.collect {
                liveData.value = it
        }}
    }

    override fun startTimer() {
        stopwatchListOrchestrator.start()
    }

    override fun stopTimer() {
        stopwatchListOrchestrator.stop()
    }

    override fun pauseTimer() {
        stopwatchListOrchestrator.pause()
    }

}