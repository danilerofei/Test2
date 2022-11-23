package com.test.test2.dagger

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.test.test2.interfaces.IViewBindingFragment

abstract class BaseAdapter<T, VB : ViewBinding>(
    protected val context: Context,
    var items: List<T>
) : RecyclerView.Adapter<BaseAdapter<T, VB>.ViewHolder>(), IViewBindingFragment<VB>,
    IBaseAdapter<T, VB> {

    private val inflater by lazy {
        LayoutInflater.from(context)
    }

    inner class ViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = getViewBinding(inflater, parent)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
        bind(data, holder.binding, position)
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(items: List<T>) {
        this.items = items
        notifyDataSetChanged()
    }
}

interface IBaseAdapter<T, VB : ViewBinding> {

    fun bind(data: T, binding: VB, position: Int)
}