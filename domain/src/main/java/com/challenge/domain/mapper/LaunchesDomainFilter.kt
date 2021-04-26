package com.challenge.domain.mapper

import com.challenge.domain.model.LaunchDomainModel

interface LaunchesDomainFilter {
    fun filter(
        launchesDomainModel: List<LaunchDomainModel>,
        filterYear: Int,
        ascendantOrder: Boolean
    ): List<LaunchDomainModel>
}


