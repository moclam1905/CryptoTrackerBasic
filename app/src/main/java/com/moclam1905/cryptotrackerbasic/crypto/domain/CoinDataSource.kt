package com.moclam1905.cryptotrackerbasic.crypto.domain

import com.moclam1905.cryptotrackerbasic.core.domain.util.NetworkError
import com.moclam1905.cryptotrackerbasic.core.domain.util.Result

interface CoinDataSource {

    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}