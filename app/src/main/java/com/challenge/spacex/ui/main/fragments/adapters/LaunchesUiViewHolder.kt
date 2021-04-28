package com.challenge.spacex.ui.main.fragments.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.challenge.presentation.model.LaunchUiModel
import com.challenge.spacex.databinding.MainLaunchDetailsCardBinding


class LaunchesUiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = MainLaunchDetailsCardBinding.bind(itemView)

    fun onBind(uiModel: LaunchUiModel) {
        binding.widgetLaunchDetailsTitle.text = uiModel.missionName
    }
}