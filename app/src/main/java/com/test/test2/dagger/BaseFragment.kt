package com.test.test2.dagger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.test.test2.interfaces.IViewBindingFragment

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<VB : ViewBinding, A : InjectActivity> : Fragment(),
    IViewBindingFragment<VB> {

    protected val parentActivity by lazy {
        requireActivity() as A
    }

    lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(inflater, container)
        return binding.root
    }
}