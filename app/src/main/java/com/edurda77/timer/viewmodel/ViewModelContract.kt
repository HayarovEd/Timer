package com.edurda77.timer.viewmodel

sealed interface ViewModelContract {
    fun startTimer()
    fun stopTimer()
    fun pauseTimer()
}