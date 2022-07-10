package com.ajijul.ahoytestdata.base

import android.view.View
import androidx.fragment.app.Fragment
import com.ajijul.ahoytestdata.utils.MessageHandlerImp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment : Fragment() {


    @Inject
    lateinit var messageHandlerImp: MessageHandlerImp

    protected val mainView: View?  = view

}