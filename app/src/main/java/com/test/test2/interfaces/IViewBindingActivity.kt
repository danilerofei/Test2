package com.test.test2.interfaces

import androidx.viewbinding.ViewBinding

interface IViewBindingActivity<VB : ViewBinding> {

    fun getViewBinding(): VB
}