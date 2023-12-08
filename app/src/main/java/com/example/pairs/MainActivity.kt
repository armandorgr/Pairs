package com.example.pairs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.pairs.screens.MainScreen
import com.example.pairs.viewmodel.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenSetUp()
        }
    }
}


@Composable
fun ScreenSetUp(viewModel: ViewModel = viewModel()) {
    MainScreen(viewModel)
}