package com.moclam1905.cryptotrackerbasic.crypto.data.networking

import com.moclam1905.cryptotrackerbasic.core.data.networking.constructURL
import com.moclam1905.cryptotrackerbasic.core.data.networking.safeCall
import com.moclam1905.cryptotrackerbasic.core.domain.util.NetworkError
import com.moclam1905.cryptotrackerbasic.core.domain.util.Result
import com.moclam1905.cryptotrackerbasic.core.domain.util.map
import com.moclam1905.cryptotrackerbasic.crypto.data.mapper.toCoin
import com.moclam1905.cryptotrackerbasic.crypto.data.mapper.toCoinPrice
import com.moclam1905.cryptotrackerbasic.crypto.data.networking.dto.CoinHistoryResponseDto
import com.moclam1905.cryptotrackerbasic.crypto.data.networking.dto.CoinsResponseDto
import com.moclam1905.cryptotrackerbasic.crypto.domain.Coin
import com.moclam1905.cryptotrackerbasic.crypto.domain.CoinDataSource
import com.moclam1905.cryptotrackerbasic.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructURL("/assets")
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }

    override suspend fun getCoinById(
        coinId: String,
        startTime: ZonedDateTime,
        endTime: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = startTime.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        val endMillis = endTime.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        return safeCall<CoinHistoryResponseDto> {
            httpClient.get(
                urlString = constructURL(
                    "/assets/$coinId/history"
                )
            ) {
                parameter("interval", "h4")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map { it.toCoinPrice() }
        }
    }
}