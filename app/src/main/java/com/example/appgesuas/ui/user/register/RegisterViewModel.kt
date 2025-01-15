package com.example.appgesuas.ui.user.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    private val _forwarderLiveData = MutableLiveData<String?>()
    val forwarderLiveData: LiveData<String?> = _forwarderLiveData

    private val _motiveLiveData = MutableLiveData<String?>()
    val motiveLiveData: LiveData<String?> = _motiveLiveData

    fun setForwarder(forwarder: String? = null) {
        _forwarderLiveData.value = forwarder
    }

    fun setMotive(motive: String? = null) {
        _motiveLiveData.value = motive
    }

    fun getMotive(): String = motiveLiveData.value.toString()
}
