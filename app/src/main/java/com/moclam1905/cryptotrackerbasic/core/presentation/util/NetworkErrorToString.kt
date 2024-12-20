package com.moclam1905.cryptotrackerbasic.core.presentation.util

import android.content.Context
import com.moclam1905.cryptotrackerbasic.R
import com.moclam1905.cryptotrackerbasic.core.domain.util.NetworkError

fun NetworkError.toString(context: Context): String {
    val resId = when (this) {
        NetworkError.REQUEST_TIMEOUT -> R.string.err_request_timeout
        NetworkError.TOO_MANY_REQUESTS -> R.string.err_too_many_requests
        NetworkError.NO_INTERNET_CONNECTION -> R.string.err_no_internet_connection
        NetworkError.SERVER_ERROR -> R.string.err_unknown
        NetworkError.SERIALIZATION_ERROR -> R.string.err_serialization
        NetworkError.UNKNOWN_ERROR -> R.string.err_unknown
    }

    return context.getString(resId)
}