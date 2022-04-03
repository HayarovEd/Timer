package com.edurda77.timer.model

interface TimestampProvider {
    fun getMilliseconds(): Long
}