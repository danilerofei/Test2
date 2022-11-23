package com.test.test2.activities

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.test.test2.R
import com.test.test2.adapters.AdapterCapacity
import com.test.test2.adapters.AdapterColors
import com.test.test2.adapters.AdapterDeviceImages
import com.test.test2.dagger.BindingActivity
import com.test.test2.data.CapacityCheck
import com.test.test2.data.ColorCheck
import com.test.test2.databinding.ActivitySingleInfoBinding
import com.test.test2.interfaces.IClickListener
import com.test.test2.models.ApiModel
import com.test.test2.utils.HorizontalMarginItemDecoration
import java.util.*

class ActivitySingleDataInfo : BindingActivity<ActivitySingleInfoBinding>() {

    private val apiModel by lazy {
        modelProvider[ApiModel::class.java]
    }

    private val colorAdapter by lazy {
        AdapterColors(this, emptyList(), object : IClickListener {
            override fun onClick(position: Int) {
                changeColorSelection(position)
            }
        })
    }

    private val capacityAdapter by lazy {
        AdapterCapacity(this, emptyList(), object : IClickListener {
            override fun onClick(position: Int) {
                changeCapacitySelection(position)
            }
        })
    }

    private fun changeColorSelection(newPosition: Int) {
        val items = colorAdapter.items.toMutableList()
        var oldPos: Int = -1
        for (i in 0 until items.size) {
            if (items[i].isChecked) {
                oldPos = i
                break
            }
        }
        items[newPosition].isChecked = true
        colorAdapter.items = items
        colorAdapter.notifyItemChanged(newPosition)
        if (oldPos != -1 && newPosition != oldPos) {
            items[oldPos].isChecked = false
            colorAdapter.notifyItemChanged(oldPos)
        }
    }

    private fun changeCapacitySelection(newPosition: Int) {
        val items = capacityAdapter.items.toMutableList()
        var oldPos: Int = -1
        for (i in 0 until items.size) {
            if (items[i].isChecked) {
                oldPos = i
                break
            }
        }
        items[newPosition].isChecked = true
        capacityAdapter.items = items
        capacityAdapter.notifyItemChanged(newPosition)
        if (oldPos != -1 && newPosition != oldPos) {
            items[oldPos].isChecked = false
            capacityAdapter.notifyItemChanged(oldPos)
        }
    }

    override fun onCreate() {
        apiModel.loadProductInfo()
        apiModel.productData.observe(this) { p ->
            binding.tvTitle.text = p.title
            binding.ratingBar.rating = p.rating
            binding.tvCamera.text = p.camera
            binding.tvCpu.text = p.cpu
            binding.tvRam.text = p.ssd
            binding.tvStorage.text = p.sd

            val colorItems = mutableListOf<ColorCheck>()
            for (i in p.colors) {
                val item = ColorCheck(i, i == p.colors.first())
                colorItems.add(item)
            }
            colorAdapter.updateItems(colorItems)

            val capacityItems = mutableListOf<CapacityCheck>()
            for (i in p.capacity) {
                val item = CapacityCheck(i, i == p.capacity.first())
                capacityItems.add(item)
            }
            capacityAdapter.updateItems(capacityItems)

            binding

            binding.pageContainer.adapter = AdapterDeviceImages(this, p.images)

            setupVP()

            val strFor = String.format(Locale.US, "%,d", p.price)
            binding.btnAddToCart.text = getString(R.string.add_to_cart, strFor)

            if (p.isFav) {
                binding.fabFav.setImageResource(R.drawable.ic_baseline_favorite_24)
            }
        }
        binding.rvColors.apply {
            adapter = colorAdapter
            layoutManager = LinearLayoutManager(
                this@ActivitySingleDataInfo,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }

        binding.rvCapacity.apply {
            adapter = capacityAdapter
            layoutManager = LinearLayoutManager(
                this@ActivitySingleDataInfo,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }

        binding.fabBack.setOnClickListener {
            onBack()
        }

        binding.fabCart.setOnClickListener {
            val intent = Intent(this, ActivityCart::class.java)
            startActivity(intent)
        }
    }

    private fun setupVP() {
        binding.pageContainer.offscreenPageLimit = 1
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
            page.alpha = 0.25f + (1 - kotlin.math.abs(position))
        }
        binding.pageContainer.setPageTransformer(pageTransformer)
        val itemDecoration = HorizontalMarginItemDecoration(
            this,
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.pageContainer.addItemDecoration(itemDecoration)
    }

    override fun getViewBinding(): ActivitySingleInfoBinding {
        return ActivitySingleInfoBinding.inflate(layoutInflater)
    }
}