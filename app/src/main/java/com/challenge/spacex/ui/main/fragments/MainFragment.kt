package com.challenge.spacex.ui.main.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.challenge.presentation.MainViewModel
import com.challenge.presentation.model.CompanyInfoUiModel
import com.challenge.presentation.model.LinksUiModel
import com.challenge.presentation.state.TransientUIState.*
import com.challenge.spacex.R
import com.challenge.spacex.databinding.MainBottomSheetBinding
import com.challenge.spacex.databinding.MainFragmentBinding
import com.challenge.spacex.databinding.MainViewFilterDialogBinding
import com.challenge.spacex.ui.main.fragments.adapters.ClickListener
import com.challenge.spacex.ui.main.fragments.adapters.LaunchesListAdapter
import com.challenge.spacex.ui.main.utils.*
import com.google.android.material.bottomsheet.BottomSheetDialog


class MainFragment(viewModelFactory: ViewModelProvider.Factory) : Fragment() {
    private lateinit var binding: MainFragmentBinding

    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    private val toolbar by lazy { binding.filterToolbar.toolbar }
    private val filterButton by lazy { binding.filterToolbar.filterButton }
    private val launchesRecyclerView by lazy { binding.bodyContainer.launchesRecyclerView }
    private val launchesTitle by lazy { binding.bodyContainer.launchesTitle }
    private val bodyProgressBar by lazy { binding.progressBarBody }
    private val companyTitle by lazy { binding.headerContainer.companyTitle }
    private val companyDesc by lazy { binding.headerContainer.companyDescription }
    private val headerError by lazy { binding.headerContainer.headerErrorDescription }
    private val bodyError by lazy { binding.bodyContainer.bodyErrorDescription }
    private val urlUtils by lazy { UrlUtils }


    private val launchesListAdapter by lazy {
        LaunchesListAdapter(launchesClickListener)
    }

    private val launchesClickListener: ClickListener = object : ClickListener {
        override fun onItemClicked(links: LinksUiModel) {
            showBottomSheetDialog(links)
        }
    }

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
        launchesRecyclerView.init(
            { LinearLayoutManager(requireContext()) },
            { launchesListAdapter }
        )
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
        bodyProgressBar.fadeTo(true)
        headerError.fadeTo(false)
        companyDesc.fadeTo(false)
    }

    private fun handlingErrorHeaderState() {
        bodyProgressBar.fadeTo(false)
        companyTitle.fadeTo(true)
        headerError.fadeTo(false)
        companyDesc.fadeTo(false)
    }

    private fun handlingHeaderDisplayState() {
        bodyProgressBar.fadeTo(false)
        headerError.fadeTo(false)
        companyDesc.fadeTo(true)
    }

    private fun handlingBodyDisplayState() {
        bodyProgressBar.fadeTo(false)
        launchesTitle.fadeTo(true)
        launchesRecyclerView.fadeTo(true)
        bodyError.fadeTo(false)
    }

    private fun handlingLoadingBodyState() {
        bodyProgressBar.fadeTo(true)
//        launchesRecyclerView.fadeTo(false)
        bodyError.fadeTo(false)
    }

    private fun handlingErrorBodyState() {
        bodyError.fadeTo(true)
        launchesTitle.fadeTo(true)
        bodyProgressBar.fadeTo(false)
        launchesRecyclerView.fadeTo(false)
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
        filterButton.setOnClickListener { showDialog() }
    }

    private fun showBottomSheetDialog(links: LinksUiModel) {
        val binding = MainBottomSheetBinding.inflate(layoutInflater)

        BottomSheetDialog(requireContext()).run {
            with(links)
            {
                binding.wikipedia.toggleVisibility(wikipedia.isNotEmpty())
                binding.youtube.toggleVisibility(videoLink.isNotEmpty())
                binding.reddit.toggleVisibility(reeditLink.isNotEmpty())
            }
            setContentView(binding.root)
            show()

            with(binding)
            {
                wikipedia.setOnClickListener { openLink(links.wikipedia) }
                youtube.setOnClickListener { openLink(links.videoLink) }
                reddit.setOnClickListener { openLink(links.reeditLink) }
            }
        }
    }

    override fun onDestroyView() {
        binding.root.cancelFadeRecursively()
        launchesRecyclerView.adapter = null

        super.onDestroyView()
    }

    private fun openLink(link: String) {
        urlUtils.navigateTo(activity as Context, link)
    }

    private fun showDialog() {
        val binding = MainViewFilterDialogBinding.inflate(layoutInflater)
        setUpDialogBuilder(binding.orderToggle, binding.dialogYear, binding.root).run { show() }
    }

    private fun setUpDialogBuilder(
        orderToggle: SwitchCompat,
        yearEditText: EditText,
        dialog: View
    ) =
        AlertDialog.Builder(requireContext()).apply {
            setPositiveButton(getString(R.string.dialog_ok_button)) { _, _ ->
                requestFilteredData(
                    orderToggle,
                    yearEditText
                )
            }
            setNegativeButton(getString(R.string.dialog_cancel_button)) { dialog, _ -> dialog.dismiss() }
            setView(dialog)
        }

    private fun requestFilteredData(orderToggle: SwitchCompat, yearEditText: EditText) {
        val ascendant = orderToggle.isChecked
        val yearValue = yearEditText.text.toString()
        val year = Integer.parseInt(integerValueOrZero(yearValue))

        viewModel.launches(year, ascendant)
    }

    private fun integerValueOrZero(yearValue: String) =
        if (yearValue.isNotBlank()) {
            yearValue
        } else {
            "0"
        }
}