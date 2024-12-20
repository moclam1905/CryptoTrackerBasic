package com.moclam1905.cryptotrackerbasic.crypto.presentation.coin_list

import com.moclam1905.cryptotrackerbasic.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError): CoinListEvent
}