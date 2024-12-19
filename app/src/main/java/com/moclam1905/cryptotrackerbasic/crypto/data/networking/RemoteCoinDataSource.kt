package com.moclam1905.cryptotrackerbasic.crypto.data.networking

import com.moclam1905.cryptotrackerbasic.core.data.networking.constructURL
import com.moclam1905.cryptotrackerbasic.core.data.networking.safeCall
import com.moclam1905.cryptotrackerbasic.core.domain.util.NetworkError
import com.moclam1905.cryptotrackerbasic.core.domain.util.Result
import com.moclam1905.cryptotrackerbasic.core.domain.util.map
import com.moclam1905.cryptotrackerbasic.crypto.data.mapper.toCoin
import com.moclam1905.cryptotrackerbasic.crypto.data.networking.dto.CoinsResponseDto
import com.moclam1905.cryptotrackerbasic.crypto.domain.Coin
import com.moclam1905.cryptotrackerbasic.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

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
}