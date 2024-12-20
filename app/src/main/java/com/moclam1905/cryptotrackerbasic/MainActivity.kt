package com.moclam1905.cryptotrackerbasic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moclam1905.cryptotrackerbasic.crypto.presentation.coin_list.CoinListScreen
import com.moclam1905.cryptotrackerbasic.crypto.presentation.coin_list.CoinListViewModel
import com.moclam1905.cryptotrackerbasic.ui.theme.CryptoTrackerBasicTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerBasicTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel = koinViewModel<CoinListViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()

                    CoinListScreen(
                        state = state,
                        modifier = Modifier.padding((innerPadding))
                    )
                }
            }
        }
    }
}
