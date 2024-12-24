package com.moclam1905.cryptotrackerbasic.crypto.presentation.coin_detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moclam1905.cryptotrackerbasic.R
import com.moclam1905.cryptotrackerbasic.ui.theme.CryptoTrackerBasicTheme

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    title: String,
    formattedText: String,
    icon: ImageVector,
    contentColor: Color = MaterialTheme.colorScheme.onSurface
) {

    val defaultTextStyle: TextStyle = LocalTextStyle.current.copy(
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        color = contentColor
    )
    Card(
        modifier = modifier
            .padding(8.dp)
            .shadow(
                elevation = 16.dp,
                shape = RectangleShape,
                ambientColor = MaterialTheme.colorScheme.primary,
                spotColor = MaterialTheme.colorScheme.primary
            ),
        shape = RectangleShape,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = contentColor
        )
    ) {
        AnimatedContent(
            targetState = icon, label = "IconAnimation",
            modifier = modifier.align(
                alignment = Alignment.CenterHorizontally
            )
        ) { icon ->
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = modifier
                    .size(75.dp)
                    .padding(top = 16.dp),
                tint = contentColor
            )
        }

        Spacer(modifier = modifier.size(8.dp))

        AnimatedContent(
            targetState = formattedText, label = "ValueAnimation",
            modifier = modifier.align(
                alignment = Alignment.CenterHorizontally
            )
        ) { formattedText ->
            Text(
                text = formattedText,
                style = defaultTextStyle,
                modifier = modifier.padding(16.dp)
            )

        }

        Spacer(modifier = modifier.size(8.dp))

        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            color = contentColor
        )

    }

}

@PreviewLightDark
@Composable
fun InfoCardPreview(modifier: Modifier = Modifier) {
    CryptoTrackerBasicTheme {
        InfoCard(
            modifier = modifier,
            title = "Market Cap",
            formattedText = "$1,000,000,000",
            icon = ImageVector.vectorResource(id = R.drawable.dollar)
        )
    }
}