package com.moclam1905.cryptotrackerbasic.crypto.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Vertices
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moclam1905.cryptotrackerbasic.crypto.presentation.models.DisplayableNumber
import com.moclam1905.cryptotrackerbasic.ui.theme.CryptoTrackerBasicTheme
import com.moclam1905.cryptotrackerbasic.ui.theme.greenBackground

@Composable
fun PriceChange(
    modifier: Modifier = Modifier,
    changePercent: DisplayableNumber
) {
    val contentColor =
        if (changePercent.value < 0.0) MaterialTheme.colorScheme.onErrorContainer else Color.Green

    val backgroundColor =
        if (changePercent.value < 0.0) MaterialTheme.colorScheme.errorContainer else greenBackground

    val iconChange =
        if (changePercent.value < 0.0) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .background(backgroundColor)
            .padding(start = 2.dp, end = 8.dp),
        verticalAlignment  = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = iconChange, contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = contentColor
        )

        Text(
            text = "${changePercent.formatted} %",
            fontWeight = FontWeight.Medium,
            color = contentColor,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 2.dp)
        )
    }
}

@Preview
@Composable
fun PriceChangePreview(modifier: Modifier = Modifier) {
    CryptoTrackerBasicTheme {
        PriceChange(changePercent = DisplayableNumber(-12.12, "12.12"))

    }

}