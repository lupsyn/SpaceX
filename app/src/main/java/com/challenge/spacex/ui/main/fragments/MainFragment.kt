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
import com.challenge.presentation.state.TransientUIState
import com.challenge.spacex.databinding.MainFragmentBinding
import com.challenge.spacex.ui.main.fragments.adapters.LaunchesListAdapter

class MainFragment(viewModelFactory: ViewModelProvider.Factory) : Fragment() {

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }
    private lateinit var binding: MainFragmentBinding
    private val toolbar by lazy { binding.filterToolbar.toolbar }
    private val filterButton by lazy { binding.filterToolbar.confirmImageButton }

    private val launchesListAdapter by lazy { LaunchesListAdapter() }

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
        with(binding.bodyContainer.launchesRecyclerView) {
            adapter = launchesListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUpObservables() {
        viewModel.launches.observe(viewLifecycleOwner, { launches ->
            launchesListAdapter.submitList(launches)
        })

        viewModel.companyInfo.observe(viewLifecycleOwner, { companyInfo -> })

        viewModel.bodyTransientUiState.observe(
            viewLifecycleOwner,
            { bodyUiState ->
                when (bodyUiState) {
                    TransientUIState.DisplayDataUIState -> binding.bodyContainer.launchesRecyclerView.visibility =
                        View.VISIBLE
                    is TransientUIState.ErrorUiState -> binding.bodyContainer.launchesRecyclerView.visibility =
                        View.VISIBLE
                    TransientUIState.LoadingUiState -> binding.bodyContainer.launchesRecyclerView.visibility =
                        View.VISIBLE
                }
            })

        viewModel.headerTransientUiState.observe(
            viewLifecycleOwner,
            { headerUiState -> })

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