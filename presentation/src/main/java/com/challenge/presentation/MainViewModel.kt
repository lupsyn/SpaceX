package com.challenge.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.domain.usecase.GetCompanyInfo
import com.challenge.domain.usecase.GetLaunches
import com.challenge.presentation.mapper.CompanyInfoDomainToUiModelMapper
import com.challenge.presentation.mapper.LaunchesDomainToUiModelMapper
import com.challenge.presentation.model.CompanyInfoUiModel
import com.challenge.presentation.model.LaunchUiModel
import com.challenge.presentation.state.TransientUIState
import com.challenge.presentation.state.displayData
import com.challenge.presentation.state.emit
import com.challenge.presentation.state.loading
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class MainViewModel : ViewModel() {
    abstract fun launches(yearFilterCriteria: Int = -1, ascendantOrder: Boolean = false)
    abstract fun companyInfo()
    abstract fun openLink(link: String)
    abstract fun onFilterClicked()

    abstract val companyInfo: LiveData<CompanyInfoUiModel>
    abstract val launches: LiveData<List<LaunchUiModel>>
    abstract val headerTransientUiState: LiveData<TransientUIState>
    abstract val bodyTransientUiState: LiveData<TransientUIState>
}

@ExperimentalCoroutinesApi
class MainViewModelImpl(
    private val getLaunches: GetLaunches,
    private val getCompanyInfo: GetCompanyInfo,
    private val companyInfoDomainToUiModelMapper: CompanyInfoDomainToUiModelMapper,
    private val launchesDomainToUiModelMapper: LaunchesDomainToUiModelMapper
) : MainViewModel() {

    private val companyInfoMutableLiveData: MutableLiveData<CompanyInfoUiModel> =
        MutableLiveData()

    override val companyInfo: LiveData<CompanyInfoUiModel> by lazy {
        companyInfo()
        companyInfoMutableLiveData
    }

    private val headerTransientMutableUiState: MutableLiveData<TransientUIState> =
        MutableLiveData()

    override val headerTransientUiState: LiveData<TransientUIState>
        get() = headerTransientMutableUiState

    override val bodyTransientUiState: LiveData<TransientUIState>
        get() = bodyTransientMutableUiState

    private val bodyTransientMutableUiState: MutableLiveData<TransientUIState> =
        MutableLiveData()

    private val launchesMutableLiveData: MutableLiveData<List<LaunchUiModel>> =
        MutableLiveData()

    override val launches: LiveData<List<LaunchUiModel>> by lazy {
        launches()
        launchesMutableLiveData
    }

    override fun launches(yearFilterCriteria: Int, ascendantOrder: Boolean) {
        bodyTransientMutableUiState.loading()
        viewModelScope.launch {
            getLaunches.execute(yearFilterCriteria, ascendantOrder)
                .catch { throwable -> bodyTransientMutableUiState.emit(throwable) }
                .collect { launchesDomainModel ->
                    val launchesUiModel =
                        launchesDomainToUiModelMapper.toUiModel(launchesDomainModel)

                    launchesMutableLiveData.value = launchesUiModel
                    bodyTransientMutableUiState.displayData()
                }
        }
    }

    override fun companyInfo() {
        viewModelScope.launch {
            headerTransientMutableUiState.loading()
            getCompanyInfo.execute()
                .catch { throwable -> headerTransientMutableUiState.emit(throwable) }
                .collect { companyInfoDomainModel ->
                    val companyInfoUiModel =
                        companyInfoDomainToUiModelMapper.toUiModel(companyInfoDomainModel)

                    companyInfoMutableLiveData.value = companyInfoUiModel
                    headerTransientMutableUiState.displayData()
                }
        }
    }

    override fun openLink(link: String) {

    }

    override fun onFilterClicked() {

    }
}