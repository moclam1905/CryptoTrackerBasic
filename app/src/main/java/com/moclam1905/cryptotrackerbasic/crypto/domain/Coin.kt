package com.moclam1905.cryptotrackerbasic.crypto.domain

data class Coin(
    val id: Int,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: Double,
    val priceUsd: Double,
    val changePercent24Hr: Double

)
