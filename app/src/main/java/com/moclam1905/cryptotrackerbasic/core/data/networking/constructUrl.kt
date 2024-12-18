package com.moclam1905.cryptotrackerbasic.core.data.networking

import com.moclam1905.cryptotrackerbasic.BuildConfig

fun constructURL(url: String): String {
    return when {
        url.contains(BuildConfig.BASE_URL) -> url
        url.startsWith("/") -> BuildConfig.BASE_URL + url.drop(1)
        else -> BuildConfig.BASE_URL + url
    }
}