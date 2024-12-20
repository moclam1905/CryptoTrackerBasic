package com.moclam1905.cryptotrackerbasic.crypto.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinDto(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val priceUsd: Double,
    val marketCapUsd: Double,
    val changePercent24Hr: Double
)
