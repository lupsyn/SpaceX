package com.challenge.presentation.state

import androidx.lifecycle.MutableLiveData

sealed class TransientUIState {
    data class ErrorUiState(val error: String) : TransientUIState()
    object DisplayDataUIState : TransientUIState()
    object LoadingUiState : TransientUIState()

    companion object {
        fun onTransientState(
            transientUIState: TransientUIState,
            onDisplayState: () -> Unit = { Unit },
            onLoadingState: () -> Unit = { Unit },
            onErrorUiState: (String) -> Unit = { Unit }
        ) = when (transientUIState) {
            is ErrorUiState -> onErrorUiState(transientUIState.error)
            is LoadingUiState -> onLoadingState()
            is DisplayDataUIState -> onDisplayState()
        }
    }
}

fun MutableLiveData<TransientUIState>.emit(throwable: Throwable) {
    this.value = TransientUIState.ErrorUiState(throwable.message.orEmpty())
}

fun MutableLiveData<TransientUIState>.loading() {
    this.value = TransientUIState.LoadingUiState
}

fun MutableLiveData<TransientUIState>.displayData() {
    this.value = TransientUIState.DisplayDataUIState
}