package com.moclam1905.cryptotrackerbasic.crypto.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinPriceDto(
    val priceUsd: Double,
    val dateTime: Long
)
