package com.example.fetchexercise.presentation.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.core.data.remote.model.HiringResponseModel
import com.example.fetchexercise.databinding.RvRowItemHiringBinding

class HiringViewHolder(
    private val binding: RvRowItemHiringBinding
) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(hiringItem: HiringResponseModel){
        binding.apply {
            tvListIdValue.text = hiringItem.listId
            tvIdValue.text = hiringItem.id
            tvNameValue.text = hiringItem.name
        }
    }
}
