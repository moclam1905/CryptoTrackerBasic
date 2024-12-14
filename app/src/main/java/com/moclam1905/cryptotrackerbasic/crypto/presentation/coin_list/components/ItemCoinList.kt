package com.moclam1905.cryptotrackerbasic.crypto.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moclam1905.cryptotrackerbasic.crypto.domain.Coin
import com.moclam1905.cryptotrackerbasic.crypto.presentation.models.CoinUi
import com.moclam1905.cryptotrackerbasic.crypto.presentation.models.toCoinUi
import com.moclam1905.cryptotrackerbasic.ui.theme.CryptoTrackerBasicTheme


@Composable
fun ItemCoinList(
    modifier: Modifier = Modifier,
    coinUi: CoinUi,
    onItemClick: () -> Unit
) {

    val colorContent = if (isSystemInDarkTheme()) Color.White else Color.Black

    Row(
        modifier = modifier
            .clickable { onItemClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = coinUi.iconRes),
            contentDescription = coinUi.name,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(84.dp)

        )

        Column(modifier = modifier.weight(1f)) {
            Text(
                text = coinUi.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorContent
            )
            Text(
                text = coinUi.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = colorContent
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                text = "$ ${coinUi.priceUsd.formatted}",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorContent
            )
            Spacer(modifier = Modifier.size(8.dp))
            PriceChange(
                changePercent = coinUi.changePercent24Hr,
            )
        }
    }
}

@PreviewLightDark
@Composable
fun ItemCoinListPreview(modifier: Modifier = Modifier) {
    CryptoTrackerBasicTheme {
        ItemCoinList(
            coinUi = coin.toCoinUi(),
            onItemClick = {},
            modifier = Modifier.background(
                MaterialTheme.colorScheme.primaryContainer
            )
        )

    }
}

internal val coin = Coin(
    id = 1,
    rank = 1,
    name = "Bitcoin",
    symbol = "BTC",
    marketCapUsd = 31423333.54,
    priceUsd = 121233212.11,
    changePercent24Hr = -12.12
)
