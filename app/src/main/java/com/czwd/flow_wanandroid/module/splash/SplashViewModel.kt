package com.czwd.flow_wanandroid.module.splash

import androidx.lifecycle.viewModelScope
import com.czwd.flow_wanandroid.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel() {

    init {
        startCountdown()
    }

    private var _timeFlow = MutableStateFlow(3)
    val timeFlow get() = _timeFlow.asStateFlow()

    private var _navigateEvent = MutableSharedFlow<Unit>()
    val navigateEvent get() = _navigateEvent.asSharedFlow()

    private var countdownJob: Job? = null

    /**
     * 3秒倒计时
     */
    fun startCountdown(){
        countdownJob = viewModelScope.launch {
                for (i in 3 downTo 1){
                    _timeFlow.value = i
                    delay(1000)
                }
            _navigateEvent.emit(Unit)
        }
    }

    fun skipCountdown(){
        countdownJob?.cancel()
        viewModelScope.launch {
            _navigateEvent.emit(Unit)
        }

    }

}