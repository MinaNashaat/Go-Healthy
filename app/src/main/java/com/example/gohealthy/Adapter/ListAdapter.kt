package com.example.gohealthy.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gohealthy.databinding.ItemSimpleRowBinding

class ListAdapter(
  private val items: List<String>,
  private val onClick: (String) -> Unit
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

  inner class ViewHolder(val binding: ItemSimpleRowBinding)
    : RecyclerView.ViewHolder(binding.root) {
    init {
      binding.root.setOnClickListener {
        onClick(items[adapterPosition])
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = ItemSimpleRowBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.binding.itemName.text = items[position]
  }

  override fun getItemCount() = items.size
}
