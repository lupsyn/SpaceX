package com.challenge.spacex.ui.main.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.challenge.presentation.model.LaunchUiModel
import com.challenge.spacex.R

class LaunchesListAdapter :
    ListAdapter<LaunchUiModel, LaunchesUiViewHolder>(LaunchesUiDiffUtils()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchesUiViewHolder =
        LaunchesUiViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_card_launch, parent, false)
        )

    override fun onBindViewHolder(holder: LaunchesUiViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}

class LaunchesUiDiffUtils : DiffUtil.ItemCallback<LaunchUiModel>() {
    override fun areItemsTheSame(
        oldItem: LaunchUiModel,
        newItem: LaunchUiModel
    ) =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: LaunchUiModel,
        newItem: LaunchUiModel
    ) = false
}
