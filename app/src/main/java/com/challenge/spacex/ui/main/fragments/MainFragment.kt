package com.challenge.spacex.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.challenge.presentation.MainViewModel
import com.challenge.presentation.model.CompanyInfoUiModel
import com.challenge.presentation.state.TransientUIState.*
import com.challenge.spacex.R
import com.challenge.spacex.databinding.MainFragmentBinding
import com.challenge.spacex.ui.main.fragments.adapters.LaunchesListAdapter
import com.challenge.spacex.ui.main.utils.fadeTo

class MainFragment(viewModelFactory: ViewModelProvider.Factory) : Fragment() {

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }
    private lateinit var binding: MainFragmentBinding
    private val toolbar by lazy { binding.filterToolbar.toolbar }
    private val filterButton by lazy { binding.filterToolbar.confirmImageButton }

    private val launchesListAdapter by lazy { LaunchesListAdapter() }
    private val launchesRecyclerView by lazy { binding.bodyContainer.launchesRecyclerView }
    private val launchesTitle by lazy { binding.bodyContainer.launchesTitle }
    private val bodyProgressBar by lazy { binding.progressBarBody }
    private val companyTitle by lazy { binding.headerContainer.companyTitle }
    private val companyDesc by lazy { binding.headerContainer.companyDescription }
    private val headerError by lazy { binding.headerContainer.headerErrorDescription }
    private val bodyError by lazy { binding.bodyContainer.bodyErrorDescription }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.requestApplyInsets(view)
        setupRecyclerView()
        setupNavigation()
        setupToolbarFilter()
        setUpObservables()
    }

    private fun setupRecyclerView() {
        with(launchesRecyclerView) {
            adapter = launchesListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUpObservables() {
        viewModel.launches.observe(viewLifecycleOwner, { launches ->
            launchesListAdapter.submitList(launches)
        })

        viewModel.companyInfo.observe(
            viewLifecycleOwner,
            { companyInfo -> companyDesc.text = getDescriptionText(companyInfo) })

        viewModel.bodyTransientUiState.observe(
            viewLifecycleOwner,
            { bodyUiState ->
                when (bodyUiState) {
                    is DisplayDataUIState -> handlingBodyDisplayState()
                    is ErrorUiState -> handlingErrorBodyState()
                    is LoadingUiState -> handlingLoadingBodyState()
                }
            })

        viewModel.headerTransientUiState.observe(
            viewLifecycleOwner,
            { headerUiState ->
                when (headerUiState) {
                    is DisplayDataUIState -> handlingHeaderDisplayState()
                    is ErrorUiState -> handlingErrorHeaderState()
                    is LoadingUiState -> handlingLoadingHeaderState()
                }
            })

    }

    private fun handlingLoadingHeaderState() {
        companyTitle.fadeTo(true)
        bodyProgressBar.fadeTo(true)
        bodyError.fadeTo(false)
        companyDesc.fadeTo(false)
    }

    private fun handlingErrorHeaderState() {
        companyTitle.fadeTo(true)
        bodyProgressBar.fadeTo(false)
        bodyError.fadeTo(false)
        companyDesc.fadeTo(false)
    }

    private fun handlingHeaderDisplayState() {
        companyTitle.fadeTo(true)
        bodyProgressBar.fadeTo(false)
        bodyError.fadeTo(false)
        companyDesc.fadeTo(true)
    }

    private fun handlingBodyDisplayState() {
        launchesTitle.fadeTo(true)
        bodyProgressBar.fadeTo(false)
        launchesRecyclerView.fadeTo(true)
        headerError.fadeTo(false)
    }

    private fun handlingLoadingBodyState() {
        launchesTitle.fadeTo(true)
        bodyProgressBar.fadeTo(true)
        launchesRecyclerView.fadeTo(false)
        headerError.fadeTo(false)
    }

    private fun handlingErrorBodyState() {
        bodyProgressBar.fadeTo(false)
        launchesTitle.fadeTo(true)
        launchesRecyclerView.fadeTo(false)
        headerError.fadeTo(true)
    }

    private fun getDescriptionText(companyInfo: CompanyInfoUiModel): String {
        return String.format(
            getString(R.string.company_data),
            companyInfo.name,
            companyInfo.founder,
            companyInfo.foundedYear,
            companyInfo.employees,
            companyInfo.launchSites,
            companyInfo.valuation
        )
    }

    private fun setupNavigation() {
        requireActivity().let { fragmentActivity ->
            (fragmentActivity as AppCompatActivity).setSupportActionBar(toolbar)
            NavigationUI.setupActionBarWithNavController(
                fragmentActivity, findNavController()
            )
        }
    }

    private fun setupToolbarFilter() {
        filterButton.setOnClickListener {
            viewModel.onFilterClicked()
        }
    }
}