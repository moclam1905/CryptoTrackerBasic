package com.moclam1905.cryptotrackerbasic.crypto.data.mapper

import com.moclam1905.cryptotrackerbasic.crypto.data.networking.dto.CoinDto
import com.moclam1905.cryptotrackerbasic.crypto.data.networking.dto.CoinPriceDto
import com.moclam1905.cryptotrackerbasic.crypto.domain.Coin
import com.moclam1905.cryptotrackerbasic.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinDto.toCoin(): Coin {
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

fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        dateTime = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault())
    )
}