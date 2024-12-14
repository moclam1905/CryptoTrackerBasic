package com.moclam1905.cryptotrackerbasic.crypto.presentation.coin_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.moclam1905.cryptotrackerbasic.crypto.presentation.coin_list.components.ItemCoinList
import com.moclam1905.cryptotrackerbasic.crypto.presentation.coin_list.components.coin
import com.moclam1905.cryptotrackerbasic.crypto.presentation.models.toCoinUi
import com.moclam1905.cryptotrackerbasic.ui.theme.CryptoTrackerBasicTheme

@Composable
fun CoinListScreen(
    modifier: Modifier = Modifier,
    state: CoinListState
) {
    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            state.coins.forEach { coin ->
                item {
                    ItemCoinList(coinUi = coin, onItemClick = {

                    })
                }
            }

        }

    }
}

@PreviewLightDark
@Composable
fun CoinListScreenPreview(modifier: Modifier = Modifier) {
    CryptoTrackerBasicTheme {
        CoinListScreen(
            state = CoinListState(
            isLoading = false,
            coins = (1..10).map {
                coin.toCoinUi().copy(id = it) }
            ),
            modifier = modifier.background(MaterialTheme.colorScheme.background))
    }
}