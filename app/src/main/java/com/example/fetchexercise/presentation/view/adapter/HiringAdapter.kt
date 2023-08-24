package com.example.fetchexercise.presentation.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.core.data.remote.model.HiringResponseModel
import com.example.fetchexercise.databinding.RvRowItemHiringBinding
import com.example.fetchexercise.presentation.view.viewholder.HiringViewHolder

class HiringAdapter : ListAdapter<HiringResponseModel, HiringViewHolder>(HiringDiff) {

    private val LOG_TAG:String = "LT_HiringAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HiringViewHolder {
        val binding: RvRowItemHiringBinding = RvRowItemHiringBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HiringViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HiringViewHolder, position: Int) {
        val hiringItem = getItem(position)
        Log.d(LOG_TAG, "bind() position:$position hiringItem $hiringItem")
        holder.bind(hiringItem = hiringItem)
    }

    object HiringDiff: DiffUtil.ItemCallback<HiringResponseModel>(){
        override fun areItemsTheSame(
            oldItem: HiringResponseModel,
            newItem: HiringResponseModel
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: HiringResponseModel,
            newItem: HiringResponseModel
        ): Boolean =
            oldItem.id == newItem.id &&
                    oldItem.listId == newItem.listId &&
                    oldItem.name == newItem.name

    }
}