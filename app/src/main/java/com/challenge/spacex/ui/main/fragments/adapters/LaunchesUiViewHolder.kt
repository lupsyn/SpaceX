package com.challenge.spacex.ui.main.fragments.adapters

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.challenge.presentation.model.LaunchUiModel
import com.challenge.spacex.R
import com.challenge.spacex.databinding.MainCardLaunchBinding
import com.challenge.spacex.ui.main.fragments.widgets.LineDetailsView
import com.squareup.picasso.Picasso


class LaunchesUiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = MainCardLaunchBinding.bind(itemView)
    private val detailMission: LineDetailsView by lazy { binding.detailsMission }
    private val detailTime: LineDetailsView by lazy { binding.detailsDateTime }
    private val detailsRocketName: LineDetailsView by lazy { binding.detailsRocketName }
    private val detailsDays: LineDetailsView by lazy { binding.detailsDays }
    private val missionPatch: ImageView by lazy { binding.missionPatch }
    private val success: ImageView by lazy { binding.viewSuccess }


    fun onBind(uiModel: LaunchUiModel) {
        if (uiModel.links.missionPatchSmall == "") {
            Picasso.get()
                .load(R.drawable.ic_rocket)
                .resize(150, 150)
                .centerCrop()
                .into(missionPatch)
        } else {
            Picasso.get()
                .load(uiModel.links.missionPatchSmall)
                .placeholder(R.drawable.ic_rocket)
                .resize(150, 150)
                .centerCrop()
                .into(missionPatch)
        }

        detailMission.setValue(uiModel.missionName)
        detailTime.setValue(uiModel.launchDate)
        detailsRocketName.setValue(uiModel.rocket.rocketName)

        detailsDays.setTitle(getTitleSinceFrom(uiModel.isPastLaunch, itemView.context))
        detailsDays.setValue(uiModel.differenceOfDays)
        success.setImageDrawable(getSuccessDrawable(uiModel.launchSuccess, itemView.context))
    }

    private fun getTitleSinceFrom(isPastLaunch: Boolean, context: Context) =
        context.getString(if (isPastLaunch) R.string.company_data_since else R.string.company_data_from)

    private fun getSuccessDrawable(launchSuccess: Boolean, context: Context) =
        ResourcesCompat.getDrawable(
            context.resources,
            if (launchSuccess) R.drawable.ic_check else R.drawable.ic_clear,
            null
        )
}