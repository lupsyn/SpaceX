package com.challenge.spacex.ui.main.fragments.adapters

import com.challenge.presentation.model.LinksUiModel

interface ClickListener {
    fun onItemClicked(links: LinksUiModel)
}
