package com.ajijul.ahoytestdata.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ajijul.ahoytestdata.utils.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {
    protected var screenState = MutableLiveData<ScreenState>().apply { value = ScreenState.RENDER }
}