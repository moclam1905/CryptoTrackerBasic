package com.moclam1905.cryptotrackerbasic.crypto.data.mapper

import com.moclam1905.cryptotrackerbasic.crypto.data.networking.dto.CoinDto
import com.moclam1905.cryptotrackerbasic.crypto.domain.Coin

fun CoinDto.toCoin() : Coin {
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        priceUsd = priceUsd,
        marketCapUsd = marketCapUsd,
        changePercent24Hr = changePercent24Hr
    )
}