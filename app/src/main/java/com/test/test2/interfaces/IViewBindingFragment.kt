package com.test.test2.interfaces

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

interface IViewBindingFragment<VB : ViewBinding> {

    fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
}