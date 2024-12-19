package com.moclam1905.cryptotrackerbasic.crypto.presentation.coin_list

import com.moclam1905.cryptotrackerbasic.crypto.presentation.models.CoinUi


// MVI
// User Action -> Intent -> ViewModel -> New State -> View
sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi) : CoinListAction
    data object OnRefresh : CoinListAction
}