package com.moclam1905.cryptotrackerbasic.di

import com.moclam1905.cryptotrackerbasic.core.data.networking.HttpClientFactory
import com.moclam1905.cryptotrackerbasic.crypto.data.networking.RemoteCoinDataSource
import com.moclam1905.cryptotrackerbasic.crypto.domain.CoinDataSource
import com.moclam1905.cryptotrackerbasic.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single {HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()
    viewModelOf(::CoinListViewModel)
}