package com.test.test2.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.test.test2.R
import com.test.test2.activities.ActivityMain
import com.test.test2.activities.ActivitySingleDataInfo
import com.test.test2.adapters.BestSellerAdapter
import com.test.test2.adapters.CategoryAdapter
import com.test.test2.adapters.HotSalesAdapter
import com.test.test2.dagger.BaseFragment
import com.test.test2.data.CategoryData
import com.test.test2.databinding.FragmentHomeBinding
import com.test.test2.dialogs.DialogFilter
import com.test.test2.interfaces.IClickListener

class FragmentHome : BaseFragment<FragmentHomeBinding, ActivityMain>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    private val categoryAdapter by lazy {
        CategoryAdapter(
            requireContext(), listOf(
                CategoryData("Phones", R.drawable.ic_baseline_smartphone_24, true),
                CategoryData("Computer", R.drawable.ic_baseline_computer_24, false),
                CategoryData("Health", R.drawable.ic_baseline_monitor_heart_24, false),
                CategoryData("Books", R.drawable.ic_baseline_library_books_24, false),
                CategoryData("Some...", R.drawable.ic_home, false),
            ), object : IClickListener {

                override fun onClick(position: Int) {
                    onChangeSelection(position)
                }
            }
        )
    }

    private val hotSalesAdapter by lazy {
        HotSalesAdapter(requireContext(), emptyList())
    }

    private val bestSellerAdapter by lazy {
        BestSellerAdapter(requireContext(), emptyList(), object : IClickListener {
            override fun onClick(position: Int) {
                val intent = Intent(requireContext(), ActivitySingleDataInfo::class.java)
                startActivity(intent)
            }
        })
    }

    private var currentCategoryPosition: Int = 0

    private fun onChangeSelection(newPosition: Int) {
        if (currentCategoryPosition == newPosition) {
            return
        }
        currentCategoryPosition = newPosition
        val items = categoryAdapter.items.toMutableList()
        var oldPos: Int = -1
        for (i in 0 until items.size) {
            if (items[i].isSelected) {
                oldPos = i
                break
            }
        }
        items[newPosition].isSelected = true
        categoryAdapter.items = items
        categoryAdapter.notifyItemChanged(newPosition)
        if (oldPos != -1) {
            items[oldPos].isSelected = false
            categoryAdapter.notifyItemChanged(oldPos)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCategory.adapter = categoryAdapter
        binding.rvCategory.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.vpHotSales.adapter = hotSalesAdapter
        binding.vpHotSales.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.rvBestSeller.adapter = bestSellerAdapter
        binding.rvBestSeller.layoutManager = GridLayoutManager(requireContext(), 2)

        parentActivity.apiModel.mainData.observe(viewLifecycleOwner) {
            if (it != null) {
                hotSalesAdapter.updateItems(it.homeStore)
                bestSellerAdapter.updateItems(it.bestSeller)
            }
        }

        binding.ivFilter.setOnClickListener {
            val dialog = DialogFilter()
            dialog.show(parentActivity.supportFragmentManager, dialog.tag)
        }
    }
}