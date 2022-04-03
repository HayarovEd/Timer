package com.edurda77.timer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edurda77.timer.model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@OptIn(InternalCoroutinesApi::class)
class MainViewModel : ViewModel() {
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
        CoroutineScope(
            Dispatchers.Main
                    + SupervisorJob()
        ).launch {
            stopwatchListOrchestrator.ticker.collect {
                liveData.value = it
            }
        }
    }

    fun startTimer() {
        stopwatchListOrchestrator.start()
    }

    fun stopTimer() {
        stopwatchListOrchestrator.stop()
    }

    fun pauseTimer() {
        stopwatchListOrchestrator.pause()
    }

}