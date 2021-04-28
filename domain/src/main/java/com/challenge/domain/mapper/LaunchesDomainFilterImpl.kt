package com.challenge.domain.mapper

import com.challenge.domain.model.LaunchDomainModel

class LaunchesDomainFilterImpl : LaunchesDomainFilter {
    override fun filter(
        launchesDomainModel: List<LaunchDomainModel>,
        filterYear: Int,
        ascendantOrder: Boolean
    ): List<LaunchDomainModel> = if (filterYear > 0) {
        val launches = launchesDomainModel.filter { launchDomainModel ->
            launchDomainModel.launchDate.year == filterYear
        }.filter { launchDomainModel ->
            launchDomainModel.launchSuccess
        }
        order(launches, ascendantOrder)
    } else {
        order(launchesDomainModel, ascendantOrder)
    }

    private fun order(
        launches: List<LaunchDomainModel>,
        ascendantOrder: Boolean
    ): List<LaunchDomainModel> =
        if (ascendantOrder) {
            launches.sortedByDescending { launchDomainModel -> launchDomainModel.launchDate }
        } else {
            launches.sortedBy { launchDomainModel -> launchDomainModel.launchDate }
        }
}