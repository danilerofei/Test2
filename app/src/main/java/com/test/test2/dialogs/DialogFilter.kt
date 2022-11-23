package com.test.test2.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.test.test2.databinding.DialogFilterBinding
import com.test.test2.interfaces.IViewBindingFragment

class DialogFilter : BottomSheetDialogFragment(), IViewBindingFragment<DialogFilterBinding> {

    private lateinit var binding: DialogFilterBinding

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogFilterBinding {
        return DialogFilterBinding.inflate(inflater, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = getViewBinding(inflater, container)

        binding.fabBack.setOnClickListener {
            dismiss()
        }

        binding.btnDone.setOnClickListener {
            dismiss()
        }

        val brandItems = arrayOf("Samsung")
        binding.tvBrand.setText(brandItems.first())
        binding.tvBrand.setSimpleItems(brandItems)

        val priceItems = arrayOf("$300 - $500")
        binding.tvPrice.setText(priceItems.first())
        binding.tvPrice.setSimpleItems(priceItems)

        val sizeItems = arrayOf("4.5 to 5.5 inches")
        binding.tvSize.setText(sizeItems.first())
        binding.tvSize.setSimpleItems(sizeItems)

        return binding.root
    }
}