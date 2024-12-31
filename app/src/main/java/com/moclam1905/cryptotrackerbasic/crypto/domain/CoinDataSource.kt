package com.moclam1905.cryptotrackerbasic.crypto.domain

import com.moclam1905.cryptotrackerbasic.core.domain.util.NetworkError
import com.moclam1905.cryptotrackerbasic.core.domain.util.Result
import java.time.ZonedDateTime

interface CoinDataSource {

    suspend fun getCoins(): Result<List<Coin>, NetworkError>
    suspend fun getCoinById(
        coinId: String,
        startTime: ZonedDateTime,
        endTime: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError>
}