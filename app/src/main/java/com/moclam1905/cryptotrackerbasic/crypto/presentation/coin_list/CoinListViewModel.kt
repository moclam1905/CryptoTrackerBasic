package com.moclam1905.cryptotrackerbasic.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moclam1905.cryptotrackerbasic.core.domain.util.onError
import com.moclam1905.cryptotrackerbasic.core.domain.util.onSuccess
import com.moclam1905.cryptotrackerbasic.crypto.domain.CoinDataSource
import com.moclam1905.cryptotrackerbasic.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state = _state.onStart {
        loadCoins()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        CoinListState()
    )

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {

            }

            is CoinListAction.OnRefresh -> {
                //loadCoins()
            }
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            coinDataSource.getCoins().onSuccess { coins ->
                _state.update {
                    it.copy(isLoading = false,
                        coins = coins.map { it.toCoinUi() })
                }
            }.onError {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}